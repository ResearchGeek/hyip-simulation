package HyipGame;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/* -- here is defined root element name */
@XmlRootElement(name = "percentages")
public class PercentageCollection {

	private ArrayList<Percentage> list = new ArrayList<Percentage>();

	public ArrayList<Percentage> getList() {
		return list;
	}

	/* -- this element name "wrap" all person record */
	@XmlElementWrapper(name = "percents")
	/* -- this is element name for every person record */
	@XmlElement(name = "percent")
	public void setList(ArrayList<Percentage> orderDetailList) {
		this.list = orderDetailList;
	}
}