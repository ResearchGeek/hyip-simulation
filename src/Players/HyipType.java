package Players;

import java.util.EnumSet;

public class HyipType {

	public enum GoodLooking {
		GOOD_LOOKING_1A, GOOD_LOOKING_2A, GOOD_LOOKING_3A, GOOD_LOOKING_4A
	}

	public enum BadLooking {
		BAD_LOOKING_1B, BAD_LOOKING_2B, BAD_LOOKING_3B, BAD_LOOKING_4B
	}

	public static EnumSet<GoodLooking> goodLooking = EnumSet.of(GoodLooking.GOOD_LOOKING_1A,
			GoodLooking.GOOD_LOOKING_2A, GoodLooking.GOOD_LOOKING_3A,
			GoodLooking.GOOD_LOOKING_4A);
	
	public static EnumSet<BadLooking> badLooking = EnumSet.of(BadLooking.BAD_LOOKING_1B,
			BadLooking.BAD_LOOKING_2B, BadLooking.BAD_LOOKING_3B,
			BadLooking.BAD_LOOKING_4B);
	
}
