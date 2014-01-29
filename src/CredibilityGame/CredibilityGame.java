package CredibilityGame;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

/**
 * Hyip strategies game
 * 
 * High Yield Investment Program multi-agent simulation
 * 
 * A high-yield investment program (HYIP) is a type of Ponzi scheme, an
 * investment scam that promises unsustainably high return on investment by
 * paying previous investors with the money invested by new investors. Most of
 * these scams work from anonymous offshore bases which make them hard to track
 * down.
 * 
 * @author
 * @since 1.0
 */
public class CredibilityGame extends DefaultContext<Object> implements
		ContextBuilder<Object> {

	public static Context<Object> STRATEGIES;
	public static Context<Player> PLAYERS;

	@Override
	public Context<Object> build(Context<Object> context) {
		say("Context builder loaded");
		context.setId("CredibilityGame");

		Parameters params = RunEnvironment.getInstance().getParameters();
		say("PARAM: " + params.getValue("generation_number"));

		STRATEGIES = new Strategies();
		context.addSubContext(STRATEGIES);
		PLAYERS = new Players();

		context.addSubContext(PLAYERS);
		context.add(new GameController());

		return context;
	}

	private void say(String string) {
		System.out.println(string);
	}

}
