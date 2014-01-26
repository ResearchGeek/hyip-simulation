package CredibilityGame.rating.strategy;

import repast.simphony.random.RandomHelper;
import CredibilityGame.Information;
import CredibilityGame.rating.Rating;
import CredibilityGame.rating.UpDownRating;

public class RandomRatingStrategy extends RatingStrategy {

	@Override
	public Rating rate(Information i, boolean isAccepted, double payoff, boolean acceptedBySignal) {
		return new UpDownRating(RandomHelper.createUniform(-2,1).nextInt()>0?1:-1);
	}

	@Override
	public boolean isForRating(Information i, boolean isAccepted,
			double payoff) {
		return true;
	}

}
