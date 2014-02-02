package CredibilityGame;

import java.util.ArrayList;
import java.util.Random;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;

public class ProducerStrategy implements Strategy{
	private static Random random = new Random();
	
	private double truthfulness;
	private double look;
	private Hyip producer;
	private ArrayList<Information> generatedInformation;
	private int resetThreshold;
	
	public ProducerStrategy(Hyip producer){
		/*
		 * TODO: Dodaæ losowanie wg rozk³adów!!!
		 */
		this.truthfulness = RandomHelper.createUniform(Hyip.getProducerTypeL(), Hyip.getProducerTypeH()).nextInt()>0?1.0:0.0;//producer.isHonest()?1:0;
		this.look = random.nextInt(2);
		this.producer = producer;
		generatedInformation = new ArrayList<Information>();
		CredibilityGame.STRATEGIES.add(this);
		Parameters params = RunEnvironment.getInstance().getParameters();
		resetThreshold = (Integer)params.getValue("producer_reset_threshold");
	}
	
	private ProducerStrategy(double truthfulness, double look){
		this.truthfulness = truthfulness;
		this.look = look;
	}
	
	@ScheduledMethod(start=1.0)
	public void initialize(){
		ProducerStrategyType.assignToType(this);
	}

	public double getTruthfulness() {
		return truthfulness;
	}

	public void setTruthfulness(double truthfulness) {
		this.truthfulness = truthfulness;
	}

	public double getLook() {
		return look;
	}

	public void setLook(double look) {
		this.look = look;
	}
	
	public Hyip getProducer() {
		return producer;
	}
	
	public Information getInformation(){
		return generatedInformation.get(generatedInformation.size()-1);
	}
	
	@Override
	public Strategy copy() {
		return new ProducerStrategy(getTruthfulness(), getLook());
	}
	
	@Override
	public void clear() {
		generatedInformation.clear();
		ProducerStrategyType.assignToType(this);
	}
	
	public void copyStrategy(Strategy copyFrom){
		setTruthfulness(((ProducerStrategy)copyFrom).getTruthfulness());
		setLook(((ProducerStrategy)copyFrom).getLook());
	}
	
	public void generateInformation(){
		generatedInformation.add(new Information(this));
		//TODO: Tu mo¿na modyfikowaæ szansê resetowania reputacji
		if (RandomHelper.createUniform(0, 100).nextInt()<resetThreshold){
			producer.resetReputation();
		}
	}

	public String toString(){
		return "<"+getTruthfulness()+","+getLook()+">";
	}

}
