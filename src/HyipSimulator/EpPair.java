package HyipSimulator;

public class EpPair {
	private double e_use;
	private double p_use;
	
	public EpPair(double e_use, double p_use){
		this.e_use = e_use;
		this.p_use = p_use;
	}
	
	public double getP_use() {
		return p_use;
	}
	public void setP_use(double p_use) {
		this.p_use = p_use;
	}
	public double getE_use() {
		return e_use;
	}
	public void setE_use(double e_use) {
		this.e_use = e_use;
	}
	
	public EpPair copy() {
		return new EpPair(getE_use(), getP_use());
	}
	
	public void copyStrategy(EpPair copyFrom) {
		this.e_use = copyFrom.getE_use();
		this.p_use = copyFrom.getP_use();
	}
}
