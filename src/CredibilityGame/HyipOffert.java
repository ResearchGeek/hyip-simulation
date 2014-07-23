package CredibilityGame;

/**
 * Represents a single type of a Hyip offer.
 * Can be shared between Hyips.
 * 
 * @author Oskar Jarczyk
 * @since 1.0
 */
public class HyipOffert {
	
	private double percent; // procent
	private double minimum; // minimalna wplata [opcjonalne, patrz dalej]
	private boolean noMinimum; // czy jest minimalna wplata
	private int forHowLong; // czas trwania "inwestycji" (w dniach)
	private double penalty; // kara za zerwanie (na razie nie uzywane)
	
	public HyipOffert(double percent, double minimum, boolean noMinimum, 
			int forHowLong, double penalty){
		this.percent = percent;
		this.minimum = minimum;
		this.noMinimum = noMinimum;
		this.forHowLong = forHowLong;
		this.penalty = penalty;
	}
	
	public HyipOffert(HyipOffert copyFrom){
		this.percent = copyFrom.percent;
		this.minimum = copyFrom.minimum;
		this.noMinimum = copyFrom.noMinimum;
		this.forHowLong = copyFrom.forHowLong;
		this.penalty = copyFrom.penalty;
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
	public int getForHowLong() {
		return forHowLong;
	}
	public void setForHowLong(int forHowLong) {
		this.forHowLong = forHowLong;
	}
	public double getPenalty() {
		return penalty;
	}
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

}
