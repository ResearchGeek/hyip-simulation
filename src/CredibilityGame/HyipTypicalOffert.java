package CredibilityGame;

public class HyipTypicalOffert {

	public static final HyipOffert LOW_RISK_1D = new HyipOffert(1.0, 5.0, true,
			1, 20);
	public static final HyipOffert MEDIUM_RISK_1D = new HyipOffert(5.0, 50.0,
			true, 1, 20);
	public static final HyipOffert HIGH_RISK_1D = new HyipOffert(10.0, 1000.0,
			true, 1, 20);

	public static final HyipOffert LOW_RISK_7D = new HyipOffert(1.0, 5.0, true,
			7, 50);
	public static final HyipOffert MEDIUM_RISK_7D = new HyipOffert(5.0, 50.0,
			true, 7, 50);
	public static final HyipOffert HIGH_RISK_7D = new HyipOffert(10.0, 1000.0,
			true, 7, 50);

}
