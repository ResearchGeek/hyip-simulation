package CredibilityGame.producerchoice.strategy;

import repast.simphony.context.Context;
import CredibilityGame.Player;
import CredibilityGame.HYIPowner;

public class RandomProducerStrategy extends ProducerChoiceStrategy{

	@Override
	public HYIPowner choose(Context<Player> context) {
		return (HYIPowner)context.getRandomObjects(HYIPowner.class, 1).iterator().next();
	}

}
