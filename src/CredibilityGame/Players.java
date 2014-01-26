package CredibilityGame;

import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

public class Players extends DefaultContext<Player> {
	
	public Players(){
		super("Players");
		System.out.println("Players context loaded");
		Parameters params = RunEnvironment.getInstance().getParameters();
		int producerPopulationSize = (Integer)params.getValue("hyip_population_size");
		//double producerLiarRate = (Double)params.getValue("producer_liar_rate");
		int inv_0 = (Integer)params.getValue("inv_0");
		int inv_1 = (Integer)params.getValue("inv_1");
		int inv_2 = (Integer)params.getValue("inv_2");
		
		HYIPowner.initialize();
		Investor.initialize();
		
		//int numberOfLiars = (int)(producerPopulationSize*producerLiarRate);
		
		for(int i=0; i<(producerPopulationSize); i++){
			this.add(new HYIPowner());
		}
		
		for(int i=0; i<inv_0; i++){
			this.add(new Investor(0));
		}
		for(int i=0; i<inv_1; i++){
			this.add(new Investor(1));
		}
		for(int i=0; i<inv_2; i++){
			this.add(new Investor(2));
		}
		
		
	}
}
