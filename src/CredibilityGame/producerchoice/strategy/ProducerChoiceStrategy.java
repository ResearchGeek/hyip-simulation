package CredibilityGame.producerchoice.strategy;

import repast.simphony.context.Context;
import CredibilityGame.Player;
import CredibilityGame.Hyip;

public abstract class ProducerChoiceStrategy {
	
	public abstract Hyip choose(Context<Player> context);
}
