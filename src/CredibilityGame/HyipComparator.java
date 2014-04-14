package CredibilityGame;

import java.util.Comparator;

public class HyipComparator implements Comparator<Hyip> {

	@Override
	public int compare(Hyip o1, Hyip o2) {
		double diff = o1.getCash() - o2.getCash();
		if (diff == 0)
			return 0;
		else
			return (diff > 0 ? -1 : 1);
	}

}
