package CredibilityGame;

public class HyipOffert {
	
	private double percent;
	private double minimum;
	private boolean noMinimum;
	private double forHowLong;
	private double penalty;
	
	public HyipOffert(double percent, double minimum, boolean noMinimum, 
			double forHowLong, double penalty){
		this.percent = percent;
		this.minimum = minimum;
		this.noMinimum = noMinimum;
		this.forHowLong = forHowLong;
		this.penalty = penalty;
	}
	
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public double getMinimum() {
		return minimum;
	}
	public void setMinimum(double minimum) {
		this.minimum = minimum;
	}
	public boolean isNoMinimum() {
		return noMinimum;
	}
	public void setNoMinimum(boolean noMinimum) {
		this.noMinimum = noMinimum;
	}
	public double getForHowLong() {
		return forHowLong;
	}
	public void setForHowLong(double forHowLong) {
		this.forHowLong = forHowLong;
	}
	public double getPenalty() {
		return penalty;
	}
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	
	static class TypicalHyipOffer{
		public static final HyipOffert LOW_RISK = new HyipOffert(2.0, 5.0, true, 1, 20);
		public static final HyipOffert MEDIUM_RISK = new HyipOffert(2.0, 15.0, true, 2, 20);
		public static final HyipOffert HIGH_RISK = new HyipOffert(2.0, 1000.0, true, 3, 20);
	}

}
