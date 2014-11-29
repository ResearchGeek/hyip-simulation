package HyipSimulator;

import java.text.DecimalFormat;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

public class Constraints {

	private static Parameters params = RunEnvironment.getInstance()
			.getParameters();

	public static final String LOGGER_INITIALIZED = "Logger initialied";
	public static final String OPENING_BRACKET = "[";
	public static final String CLOSING_BRACKET = "]";
	public static final String CONSIDERING_RUNNING_AWAY = "The HYIP is considering running away";
	public static final String CALCULATE_ROIS_EXECUTED = "calculateRois() executed";
	public static final DecimalFormat DECIMAL_SHORT = new DecimalFormat(
			"#.######");

	public static final int OUTPUT_SHORTENER = (int) params
			.getInteger("output_shortener");

	public static final boolean CONSTANT_PERC = (boolean) params
			.getBoolean("hyip_const_perc");
	public static final boolean REAL_PERC = (boolean) params
			.getBoolean("hyip_real_perc");
	public static final double HYIP_PERC = (double) params
			.getDouble("hyip_perc");
	
	public static final boolean ENABLE_PANIC_EFFECT = (boolean) params
			.getBoolean("inv_rec_stampede");
	public static final int PANIC_START_TICK = (int) params
			.getInteger("panic_start_tick");
	public static final int PANIC_LENGTH = (int) params
			.getInteger("panic_length");
	public static final double STAMPEDE_INV_REC = (double) params
			.getDouble("panic_inv_rec");
	public static final double START_INV_REC_GL = (double) params
			.getDouble("start_inv_rec_gl");
	public static final double START_INV_REC_BL = (double) params
			.getDouble("start_inv_rec_bl");

	public static final String COMMA = ",";
	public static final String SEPERATOR = "|";
	public static final double MUTATE_FACTOR = (double) params
			.getDouble("mutate_factor");
	public static final double MUTATE_CHANCE = (double) params
			.getDouble("mutate_chance");
	public static final String RESET_ALL_HYIPS_MESSAGE = "resetAllHyips() starts work. Choosing all producers (HYIPs).";
	public static final String RESET_ALL_INVESTORS_MESSAGE = "resetAllInvestors() starts work. Choosing all consumers (Investors).";
	public static final String CALCULATE_ROIS_MESSAGE = "Let's calculate some ROI's";

	public static final int MemoryAllocForQueue = 50000;

	public static final String PLAYERS_LOADED = "Players (extends DefaultContext<Player>) context loaded";
}
