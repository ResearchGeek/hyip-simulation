package CredibilityGame;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.util.ContextUtils;
import CredibilityGame.producerchoice.strategy.ProducerChoiceStrategy;

public class Investor extends Player{

	private static Class producerChoiceStrategyClass;
	
	
	
	private ProducerChoiceStrategy producerChoiceStrategy;
	private String PRODUCER_CHOICE_STRATEGY;
	private int risk_level;
	private int invest_money; //do zmiany
	private double inv_invest;
	private double inv_rec;
	
	public static void initialize(){
	
		
		
		Parameters params = RunEnvironment.getInstance().getParameters();
		
	}
	
	public Investor(int typ){
		
		//setStrategy(new AcceptanceStrategy(this));
		
		Parameters params = RunEnvironment.getInstance().getParameters(); // pobranie parametr—w
		inv_invest  = (Double)params.getValue("inv_invest");	
		inv_rec  = (Double)params.getValue("inv_rec");
		risk_level=typ;	
	}
				
	//	PRODUCER_CHOICE_STRATEGY = (String) params.getValue("producer_choice_strategy");
				
	
	

	@ScheduledMethod(start=1.0, interval=1.0, priority=100)
	public void step(){
		HYIPowner p = chooseProducer();	
		invest_money=(int)(invest_money*(1+p.perc));			
		if (Math.random()<inv_invest+p.getAdvert()) invest(p);
		if (Math.random()<inv_rec) p.changeMoney(-invest_money);
		//((AcceptanceStrategy)getStrategy()).consume(((ProducerStrategy)p.getStrategy()).getInformation());
	}
	
	private void invest(HYIPowner p)
	{
		int invest = 0;
		if (risk_level==0) invest=(int)(Math.random()*4)+1;
		if (risk_level==1) invest=(int)(Math.random()*10)+5;
		if (risk_level==2) invest=(int)(Math.random()*985)+15;
		invest_money=invest_money+invest;
		p.changeMoney(invest);
	}
	
	private HYIPowner chooseProducer(){
		Context<Player> context = ContextUtils.getContext(this);
		return (HYIPowner)context.getRandomObjects(HYIPowner.class, 1).iterator().next();
	}
	
	public static void reset(){
		for(Object p:CredibilityGame.PLAYERS.getObjects(Investor.class)){
			((Investor)p).setGain(0);
			((Investor)p).getStrategy().clear();
		}
	}
	/*
	public static void evolve(){
		IndexedIterable<Player> allConsumers = CredibilityGame.PLAYERS.getObjects(Investor.class);
		HashMap<Double,ArrayList<Player>> consumers = new HashMap<Double, ArrayList<Player>>();
		for(double type:EXPERTISE_LEVELS){
			consumers.put(type, new ArrayList<Player>());
		}
		for(Object consumer:allConsumers){
			consumers.get(((Investor)consumer).getExpertise()).add(((Investor)consumer));
		}
		for(double type:consumers.keySet()){
			Player.stochasticSampling(consumers.get(type));
		}
		Iterable<Player> mutatedConsumers = CredibilityGame.PLAYERS.getRandomObjects(Investor.class, (int)(allConsumers.size()*0.01));
		for(Object c:mutatedConsumers){
			((AcceptanceStrategy)((Investor)c).getStrategy()).setThreshold(random.nextDouble());
		}
	}
	*/
}
