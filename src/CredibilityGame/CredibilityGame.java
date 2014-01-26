package CredibilityGame;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

public class CredibilityGame extends DefaultContext<Object> implements ContextBuilder<Object> {
	public static Context<Object> STRATEGIES;
	public static Context<Player> PLAYERS;
	
	public static int CURRENT_RUN = 0; 
	
	@Override
	public Context<Object> build(Context<Object> context) {
		System.out.println("Context builder loaded");
		context.setId("CredibilityGame");
		Parameters params = RunEnvironment.getInstance().getParameters();
		System.out.println("PARAM: "+params.getValue("generation_number"));
		STRATEGIES = new Strategies();
		context.addSubContext(STRATEGIES);
		PLAYERS = new Players();
		context.addSubContext(PLAYERS);
		context.add(new GameController());
		CURRENT_RUN++;
		
		return context;
	}

}
