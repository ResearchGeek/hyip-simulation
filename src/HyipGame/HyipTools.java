package HyipGame;

public class HyipTools {

	public static double[] normalizeEP(double e_use, double p_use) {
		return new double[] { (e_use / (e_use + p_use)),
				(p_use / (e_use + p_use)) };
	}
	
	/**
	 * It should be for first generation only, because after randomizing euse
	 * puse params only mutate
	 * 
	 * @param x_e_use
	 * @param x_p_use
	 * @return 0.1
	 */
	public static double[] interpretEP_use(double x_e_use, double x_p_use) {
		return new double[] { x_e_use * x_p_use, x_e_use * (1 - x_p_use) };
	}

}
