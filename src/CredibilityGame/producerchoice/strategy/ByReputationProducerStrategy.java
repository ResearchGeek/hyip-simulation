package CredibilityGame.producerchoice.strategy;

import java.util.Iterator;

import repast.simphony.context.Context;
import CredibilityGame.Player;
import CredibilityGame.HYIPowner;

public class ByReputationProducerStrategy extends ProducerChoiceStrategy{

	@Override
	public HYIPowner choose(Context<Player> context) {
		HYIPowner chosenProducer = null;
		HYIPowner temp = null;
		Iterator<Player> iterator = context.getRandomObjects(HYIPowner.class, 3).iterator();
		while(iterator.hasNext()){
			temp = (HYIPowner)iterator.next();
			if(chosenProducer == null)
				chosenProducer = temp;
			else{
				if(temp.getCurrentRating().getRatingAsDouble()>chosenProducer.getCurrentRating().getRatingAsDouble()){
					chosenProducer = temp;
				}
			}
		}
		return chosenProducer;
	}

}
