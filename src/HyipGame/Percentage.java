package HyipGame;

import javax.xml.bind.annotation.*;

/* -- define order of saved properties */
@XmlType(propOrder = { "perc", "freq" })
public class Percentage {

	public Percentage() {
		id = 0;
	};

	private int id;
	private double perc;
	private int freq;

	/* -- this property is saved as item attribute (not as element) */
	@XmlAttribute(name = "id", required = true)
	public int getId() {
		return id;
	}

	/* -- saved as element */
	@XmlElement
	public double getPerc() {
		return perc;
	}

	/* -- saved as element */
	@XmlElement
	public int getFreq() {
		return freq;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPerc(double perc) {
		this.perc = perc;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}
}