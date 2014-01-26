package CredibilityGame.producerchoice.strategy;

import repast.simphony.context.Context;
import CredibilityGame.Player;
import CredibilityGame.HYIPowner;

public abstract class ProducerChoiceStrategy {
	
	public abstract HYIPowner choose(Context<Player> context);
}
