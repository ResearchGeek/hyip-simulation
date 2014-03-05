package CredibilityGame;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;

public class CredibilityGame extends DefaultContext<Object> implements
		ContextBuilder<Object> {
	public static Context<Object> STRATEGIES;
	public static Context<Player> PLAYERS;

	public static int CURRENT_RUN = 0;

	@Override
	public Context<Object> build(Context<Object> context) {
		System.out.println("Hyip game context builder loaded...");
		RandomHelper.init();

		context.setId("CredibilityGame");
		Parameters params = RunEnvironment.getInstance().getParameters();
		say("generation_number is set to: "
				+ params.getValue("generation_number"));
		say("iteration_number is set to: "
				+ params.getValue("iteration_number"));

		STRATEGIES = new Strategies();
		context.addSubContext(STRATEGIES);
		PLAYERS = new Players();
		context.addSubContext(PLAYERS);
		context.add(new GameController());
		CURRENT_RUN++;

		return context;
	}

	private void say(String s) {
		System.out.println(s);
	}

}
