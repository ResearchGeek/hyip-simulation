package CredibilityGame;

import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.graph.NetworkBuilder;

public class Strategies extends DefaultContext<Object>{

	public Strategies(){
		super("Strategies");
		System.out.println("Strategies context loaded");
		
		AcceptanceStrategy.initialize();
		
		new NetworkBuilder<Object>("StrategiesToTypes", this, false).buildNetwork();
		ProducerStrategyType type1 = new ProducerStrategyType("<1.0,1.0>");
		ProducerStrategyType type2 = new ProducerStrategyType("<1.0,0.0>");
		ProducerStrategyType type3 = new ProducerStrategyType("<0.0,0.0>");
		ProducerStrategyType type4 = new ProducerStrategyType("<0.0,1.0>");
		this.add(type1);
		this.add(type2);
		this.add(type3);
		this.add(type4);
	}
}
