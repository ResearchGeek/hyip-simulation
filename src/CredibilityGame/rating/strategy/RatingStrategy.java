package CredibilityGame.rating.strategy;

import CredibilityGame.Information;
import CredibilityGame.rating.Rating;

public abstract class RatingStrategy {
	
	public abstract boolean isForRating(Information i, boolean isAccepted, double payoff);

	public abstract Rating rate(Information i, boolean isAccepted, double payoff, boolean acceptedBySignal);
}
