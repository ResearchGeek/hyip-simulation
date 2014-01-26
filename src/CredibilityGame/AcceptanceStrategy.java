package CredibilityGame;

import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.ContextUtils;
import cern.jet.random.Normal;

public class AcceptanceStrategy implements Strategy{
	private static Random random = new Random();
	private static double maxMutation;
	private static double signalTruthfulnessWeight;
	private static double signalLookWeight;
	private static boolean signalConstant;
	private static double signalValue;
	
	private double threshold;
	private double signal; //stores current signal value (for data analysis)
	private double reputationThreshold;
	private Investor consumer;
	private Integer acceptanceStrategy;
	
	public static void initialize(){
		//Parameters params = RunEnvironment.getInstance().getParameters();
		//maxMutation = (Double)params.getValue("consumer_max_threshold_mutation");
		//signalTruthfulnessWeight = (Double)params.getValue("signal_truthfulness_weight");
		//signalLookWeight = (Double)params.getValue("signal_look_weight");
		//signalConstant = (Boolean)params.getValue("signal_constant");
		//signalValue = (Double)params.getValue("signal_value");
	}
	
	public AcceptanceStrategy(Investor consumer){
		Parameters params = RunEnvironment.getInstance().getParameters();
		threshold = random.nextDouble();
		reputationThreshold = RandomHelper.createUniform(0, ((Integer)params.getValue("consumer_population_size")/2)).nextInt();
		this.consumer = consumer;
		acceptanceStrategy = (Integer) params.getValue("consumer_acceptance_strategy");
		if(acceptanceStrategy==-1){
			/**
			 * consumer_acceptance_strategy
			 * 0 – BY_SIGNAL_ONLY 
			 * 1 – BY_REPUTATION_ONLY 
			 * 2 – BY_SIGNAL_AND_REPUTATION 
			 * 3 – BY_SIGNAL_OR_REPUTATION
			 */
			acceptanceStrategy = new Random().nextInt(4);
		}
	}
	
	private AcceptanceStrategy(double threshold){
		this.threshold = threshold;
	}
	
	private AcceptanceStrategy(double threshold, double reputationThreshold, int acceptanceStrategy){
		this(threshold);
		setReputationThreshold(reputationThreshold);
		setAcceptanceStrategy(acceptanceStrategy);
	}
	
	public void setAcceptanceStrategy(int strategy){
		acceptanceStrategy = strategy;
	}
	
	public int getAcceptanceStrategy(){
		return acceptanceStrategy;
	}
	
	public double getThreshold() {
		return threshold;
	}
	
	public double getSignal() {
		return signal;
	}
	
	public double getReputationThreshold(){
		return reputationThreshold;
	}
	
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public static double getSignalTruthfulnessWeight() {
		return signalTruthfulnessWeight;
	}

	public static double getSignalLookWeight() {
		return signalLookWeight;
	}
	
	public void consume(Information i){
		boolean acceptableBySignal = isAcceptableBySignal(i);
		boolean acceptableByReputation = isAcceptableByReputation(i);

		if (acceptanceStrategy == 2) {
			if (acceptableBySignal && acceptableByReputation) {
				accept(i, acceptableBySignal, acceptableByReputation);
			} else {
				reject(i, acceptableBySignal, acceptableByReputation);
			}
		} else if (acceptanceStrategy == 3) {
			if (acceptableBySignal || acceptableByReputation) {
				accept(i, acceptableBySignal, acceptableByReputation);
			} else {
				reject(i, acceptableBySignal, acceptableByReputation);
			}
		} else if (acceptanceStrategy == 1) {
			if (acceptableByReputation) {
				accept(i, acceptableBySignal, acceptableByReputation);
			} else {
				reject(i, acceptableBySignal, acceptableByReputation);
			}
		} else if (acceptanceStrategy == 0) {
			if (acceptableBySignal) {
				accept(i, acceptableBySignal, acceptableByReputation);
			} else {
				reject(i, acceptableBySignal, acceptableByReputation);
			}
		} else {
			System.err.println("ERROR: BAD consumer_acceptance_strategy <int>");
		}
	}
	
	private boolean isAcceptableBySignal(Information i){
		signal = 0;
		if(signalConstant){
			signal = signalValue;
		}
		else{
			Normal normal = RandomHelper.createNormal(((signalTruthfulnessWeight)*i.getTruthfulness())+((signalLookWeight)*i.getLook()), consumer.getExpertise());
			signal = normal.nextDouble();
		}
		return signal>threshold;
	}
	
	private boolean isAcceptableByReputation(Information i){
		Context<Object> parentContext = ContextUtils.getParentContext(ContextUtils.getContext(consumer));
		GameController controller = (GameController)parentContext.getObjects(GameController.class).get(0);
		return controller.isWarmedUp()?i.getStrategy().getProducer().getCurrentRating().getRatingAsDouble()>reputationThreshold:true;
	}
	
	private void accept(Information i, boolean acceptedBySignal, boolean acceptedByReputation){
		i.accept(consumer, acceptedBySignal, acceptedByReputation);
	}
	
	private void reject(Information i, boolean acceptedBySignal, boolean acceptedByReputation){
		i.reject(consumer, acceptedBySignal, acceptedByReputation);
	}

	@Override
	public Strategy copy() {
		return new AcceptanceStrategy(getThreshold(), getReputationThreshold(), getAcceptanceStrategy());
	}
	
	@Override
	public void clear() {
		//Nothing for now
	}
	
	public void setReputationThreshold(double threshold){
		reputationThreshold = threshold;
	}
	
	@Override
	public void copyStrategy(Strategy copyFrom) {
		//Copying signal strategy
		double mutation = (random.nextDouble()*(maxMutation*2))-maxMutation;
		double newThreshold = (((AcceptanceStrategy)copyFrom).getThreshold())+mutation;
		if(newThreshold<0) newThreshold = 0;
		else if(newThreshold>1) newThreshold = 1;
		setThreshold(newThreshold);
		
		//Copying reputation strategy
		mutation = RandomHelper.createUniform(-20, 50).nextInt();
		//TODO: uncomment for standard runs
		newThreshold = (((AcceptanceStrategy)copyFrom).getReputationThreshold())+mutation;
		//newThreshold = 1000;
		if(newThreshold<0) newThreshold = 0;
		setReputationThreshold(newThreshold);
		setAcceptanceStrategy(((AcceptanceStrategy)copyFrom).getAcceptanceStrategy());
	}
}
