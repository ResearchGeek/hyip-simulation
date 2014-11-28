package HyipSimulator;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		double diff = o1.getGain()-o2.getGain();
		if(diff==0) return 0;
		else return (diff>0?-1:1);
	}

}
