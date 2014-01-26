package CredibilityGame.rating.strategy;

import CredibilityGame.Information;
import CredibilityGame.rating.Rating;
import CredibilityGame.rating.UpDownRating;

public class PayoffDependentRatingStrategy extends RatingStrategy {

	@Override
	public Rating rate(Information i, boolean isAccepted, double payoff, boolean acceptedBySignal) {
		return new UpDownRating(payoff>=0?1:-1);
	}

	@Override
	public boolean isForRating(Information i, boolean isAccepted,
			double payoff) {
		return payoff==0?false:true;
	}

}
