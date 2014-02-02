package CredibilityGame.producerchoice.strategy;

import repast.simphony.context.Context;
import CredibilityGame.Player;
import CredibilityGame.Hyip;

public class RandomProducerStrategy extends ProducerChoiceStrategy{

	@Override
	public Hyip choose(Context<Player> context) {
		return (Hyip)context.getRandomObjects(Hyip.class, 1).iterator().next();
	}

}
