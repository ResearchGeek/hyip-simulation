package CredibilityGame;

import java.util.ArrayList;
import java.util.Hashtable;

public class Information {
	private ProducerStrategy strategy;
	private Hashtable<Boolean, ArrayList</*Consumer*/Acceptance>> acceptanceList;
	
	public Information(ProducerStrategy strategy){
		this.strategy = strategy;
		acceptanceList = new Hashtable<Boolean, ArrayList</*Consumer*/Acceptance>>();
		acceptanceList.put(true, new ArrayList</*Consumer*/Acceptance>());
		acceptanceList.put(false, new ArrayList</*Consumer*/Acceptance>());
	}
	
	public double getTruthfulness() {
		return strategy.getTruthfulness();
	}

	public double getLook() {
		return strategy.getLook();
	}
	
	public ProducerStrategy getStrategy() {
		return strategy;
	}

	public ArrayList</*Consumer*/Acceptance> getAcceptingConsumers(){
		return acceptanceList.get(true);
	}
	
	public double getAcceptedBySignal(){
		ArrayList<Acceptance> acceptances = getAcceptingConsumers();
		double counter = 0;
		for(Acceptance a:acceptances){
			counter += a.isAcceptedBySignal()?1:0;
		}
		return counter;
	}
	
	public double getAcceptedByReputation(){
		ArrayList<Acceptance> acceptances = getAcceptingConsumers();
		double counter = 0;
		for(Acceptance a:acceptances){
			counter += a.isAcceptedByReputation()?1:0;
		}
		return counter;
	}
	
	public double getAcceptedBySignalAndReputation(){
		ArrayList<Acceptance> acceptances = getAcceptingConsumers();
		double counter = 0;
		for(Acceptance a:acceptances){
			counter += (a.isAcceptedBySignal() && a.isAcceptedByReputation())?1:0;
		}
		return counter;
	}
	
	public double getAverageAcceptanceSignal(){
		ArrayList<Acceptance> acceptances = getAcceptingConsumers();
		double avgSignal = 0;
		for(Acceptance a:acceptances){
			avgSignal += a.getSignal();
		}
		return avgSignal/acceptances.size();
	}
	
	public double getAverageRejectionSignal(){
		ArrayList<Acceptance> rejections = getRejectingConsumers();
		double avgSignal = 0;
		for(Acceptance a:rejections){
			avgSignal += a.getSignal();
		}
		return avgSignal/rejections.size();
	}
	
	public ArrayList</*Consumer*/Acceptance> getRejectingConsumers(){
		return acceptanceList.get(false);
	}
	
	public void accept(Investor consumer, boolean acceptedBySignal, boolean acceptedByReputation){
		acceptanceList.get(true).add(new Acceptance(consumer, acceptedBySignal, acceptedByReputation, ((AcceptanceStrategy)consumer.getStrategy()).getSignal()));
		double acceptancePayoff = getConsumerAcceptancePayoff();
		consumer.setGain(consumer.getGain()+acceptancePayoff);
		if(consumer.getRatingStrategy().isForRating(this, true, acceptancePayoff))
			strategy.getProducer().getPendingRating().aggregate(consumer.getRatingStrategy().rate(this, true, acceptancePayoff, acceptedBySignal));
	}
	
	public void reject(Investor consumer, boolean acceptedBySignal, boolean acceptedByReputation){
		acceptanceList.get(false).add(new Acceptance(consumer, acceptedBySignal, acceptedByReputation, ((AcceptanceStrategy)consumer.getStrategy()).getSignal()));
		double rejectionPayoff = getConsumerRejectionPayoff();
		consumer.setGain(consumer.getGain()+rejectionPayoff);
		if(consumer.getRatingStrategy().isForRating(this, false, rejectionPayoff))
			strategy.getProducer().getPendingRating().aggregate(consumer.getRatingStrategy().rate(this, false, rejectionPayoff, acceptedBySignal));
	}
	
	public double getProducerPayoff(){
		return getProducerAcceptancePayoff()+getProducerRejectionPayoff();
	}
	
	private double getConsumerAcceptancePayoff(){
		double payoff = 0;
		if(getTruthfulness()==1){
			if(getLook()==1){
				payoff = Investor.PAYOFFS.get("TGA");
			}else{
				payoff = Investor.PAYOFFS.get("TBA");
			}
		}else{
			if(getLook()==1){
				payoff = Investor.PAYOFFS.get("FGA");
			}else{
				payoff = Investor.PAYOFFS.get("FBA");
			}
		}
		return payoff;
	}
	
	private double getConsumerRejectionPayoff(){
		double payoff = 0;
		if(getTruthfulness()==1){
			if(getLook()==1){
				payoff = Investor.PAYOFFS.get("TGR");
			}else{
				payoff = Investor.PAYOFFS.get("TBR");
			}
		}else{
			if(getLook()==1){
				payoff = Investor.PAYOFFS.get("FGR");
			}else{
				payoff = Investor.PAYOFFS.get("FBR");
			}
		}
		return payoff;
	}
	
	private double getProducerAcceptancePayoff(){
	    double payoff = 0;
		if(getTruthfulness()==1){
			if(getLook()==1){
				payoff = (getStrategy().getProducer().isHonest()?Hyip.HONEST_PAYOFFS.get("TGA"):Hyip.LIAR_PAYOFFS.get("TGA"))*getAcceptingConsumers().size();
			}else{
				payoff = (getStrategy().getProducer().isHonest()?Hyip.HONEST_PAYOFFS.get("TBA"):Hyip.LIAR_PAYOFFS.get("TBA"))*getAcceptingConsumers().size();
			}
		}else{
			if(getLook()==1){
				payoff = (getStrategy().getProducer().isHonest()?Hyip.HONEST_PAYOFFS.get("FGA"):Hyip.LIAR_PAYOFFS.get("FGA"))*getAcceptingConsumers().size();
			}else{
				payoff = (getStrategy().getProducer().isHonest()?Hyip.HONEST_PAYOFFS.get("FBA"):Hyip.LIAR_PAYOFFS.get("FBA"))*getAcceptingConsumers().size();
			}
		}
		return payoff;
	}

	private double getProducerRejectionPayoff(){
		double payoff = 0;
		if(getTruthfulness()==1){
			if(getLook()==1){
				payoff = (getStrategy().getProducer().isHonest()?Hyip.HONEST_PAYOFFS.get("TGR"):Hyip.LIAR_PAYOFFS.get("TGR"))*getRejectingConsumers().size();
			}else{
				payoff = (getStrategy().getProducer().isHonest()?Hyip.HONEST_PAYOFFS.get("TBR"):Hyip.LIAR_PAYOFFS.get("TBR"))*getRejectingConsumers().size();
			}
		}else{
			if(getLook()==1){
				payoff = (getStrategy().getProducer().isHonest()?Hyip.HONEST_PAYOFFS.get("FGR"):Hyip.LIAR_PAYOFFS.get("FGR"))*getRejectingConsumers().size();
			}else{
				payoff = (getStrategy().getProducer().isHonest()?Hyip.HONEST_PAYOFFS.get("FBR"):Hyip.LIAR_PAYOFFS.get("FBR"))*getRejectingConsumers().size();
			}
		}
		return payoff;
	}
	
}
