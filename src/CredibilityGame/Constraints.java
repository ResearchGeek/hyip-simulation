package CredibilityGame;

import java.text.DecimalFormat;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

public class Constraints {
	
	private static Parameters params = RunEnvironment.getInstance().getParameters();

	public static final String LOGGER_INITIALIZED = "Logger initialied";
	public static final String OPENING_BRACKET = "[";
	public static final String CLOSING_BRACKET = "]";
	public static final String CONSIDERING_RUNNING_AWAY = 
			"The HYIP is considering running away";
	public static final String CALCULATE_ROIS_EXECUTED = 
			"calculateRois() executed";
	public static final DecimalFormat DECIMAL_SHORT = new DecimalFormat(
			"#.######");
	
	public static final String COMMA = ",";
	public static final String SEPERATOR = "|";
	public static final double MUTATE_FACTOR = (double) params.getValue("mutate_factor");
	public static final int MUTATE_CHANCE = (int) params.getValue("mutate_chance");
	public static final String RESET_ALL_HYIPS_MESSAGE = 
			"resetAllHyips() starts work. Choosing all producers (HYIPs).";
	public static final String RESET_ALL_INVESTORS_MESSAGE = 
			"resetAllInvestors() starts work. Choosing all consumers (Investors).";
	public static final String CALCULATE_ROIS_MESSAGE = 
			"Let's calculate some ROI's";

	public static final int MemoryAllocForQueue = 50000;

	public static final String PLAYERS_LOADED = 
			"Players (extends DefaultContext<Player>) context loaded";
}
