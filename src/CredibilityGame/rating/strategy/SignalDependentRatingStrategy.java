package CredibilityGame.rating.strategy;

import CredibilityGame.Information;
import CredibilityGame.rating.Rating;
import CredibilityGame.rating.UpDownRating;

public class SignalDependentRatingStrategy extends RatingStrategy {

	@Override
	public Rating rate(Information i, boolean isAccepted, double payoff, boolean acceptedBySignal) {
		return new UpDownRating(acceptedBySignal?1:-1);
	}

	@Override
	public boolean isForRating(Information i, boolean isAccepted,
			double payoff) {
		return true;
	}

}
