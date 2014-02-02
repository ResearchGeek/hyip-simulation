package CredibilityGame.producerchoice.strategy;

import java.util.Iterator;

import repast.simphony.context.Context;
import CredibilityGame.Player;
import CredibilityGame.Hyip;

public class ByReputationProducerStrategy extends ProducerChoiceStrategy{

	@Override
	public Hyip choose(Context<Player> context) {
		Hyip chosenProducer = null;
		Hyip temp = null;
		Iterator<Player> iterator = context.getRandomObjects(Hyip.class, 3).iterator();
		while(iterator.hasNext()){
			temp = (Hyip)iterator.next();
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
