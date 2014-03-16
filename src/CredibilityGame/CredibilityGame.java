package CredibilityGame;

import java.io.IOException;

import logger.EndRunLogger;
import logger.PjiitLogger;
import logger.PjiitOutputter;
import logger.SanityLogger;
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
		try {
			System.out.println("Hyip game context builder loaded...");
			RandomHelper.init();
			initializeLoggers();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
	
	private void initializeLoggers() throws IOException {
		PjiitLogger.init();
		say(Constraints.LOGGER_INITIALIZED);
		SanityLogger.init();
		sanity(Constraints.LOGGER_INITIALIZED);
		EndRunLogger.init();
	}
	
	private void sanity(String s) {
		PjiitOutputter.sanity(s);
	}

}
