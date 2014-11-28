package HyipSimulator;

public class HyipTypicalOffert {

	public static final HyipOffert LOW_RISK_1D = new HyipOffert(0.01, 5.0,
			true, 1, 20);
	public static final HyipOffert MEDIUM_RISK_1D = new HyipOffert(0.05, 50.0,
			true, 1, 20);
	public static final HyipOffert HIGH_RISK_1D = new HyipOffert(0.1, 1000.0,
			true, 1, 20);

	public static final HyipOffert LOW_RISK_7D = new HyipOffert(0.01, 5.0,
			true, 7, 50);
	public static final HyipOffert MEDIUM_RISK_7D = new HyipOffert(0.05, 50.0,
			true, 7, 50);
	public static final HyipOffert HIGH_RISK_7D = new HyipOffert(0.1, 1000.0,
			true, 7, 50);

	public static final HyipOffert CONST_TYPICAL_OFFER = new HyipOffert(
			Constraints.HYIP_PERC, 0.0, true, 1, 50);

}
