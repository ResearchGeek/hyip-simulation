package CredibilityGame;

import repast.simphony.context.Context;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;
import repast.simphony.util.ContextUtils;

public class ProducerStrategyType {
	private String name;
	
	public ProducerStrategyType(String name){
		this.name = name;
	}
	
	public static void assignToType(ProducerStrategy s){
		Context<Object> context = ContextUtils.getContext(s);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		for(RepastEdge<Object> edge:strategiesToTypes.getEdges(s)){
			strategiesToTypes.removeEdge(edge);
		}
		strategiesToTypes.addEdge(getAppropriateType(s), s);
	}
	
	private static ProducerStrategyType getAppropriateType(ProducerStrategy s){
		ProducerStrategyType result = null;
		Context<Object> context = ContextUtils.getContext(s);
		for(Object st:context.getObjects(ProducerStrategyType.class)){
			if(((ProducerStrategyType)st).toString().equals("<"+s.getTruthfulness()+","+s.getLook()+">")){
				result = (ProducerStrategyType)st;
			}
		}
		return result;
	}
	
	//data for the data gatherers and outputters
	
	public int getCurrentRun(){
		return CredibilityGame.CURRENT_RUN;
	}
	
	public double getTruthfulnessWeight(){
		return AcceptanceStrategy.getSignalTruthfulnessWeight();
	}
	
	public double getLookWeight(){
		return AcceptanceStrategy.getSignalLookWeight();
	}
	/*
	public int getProducerTypeLow(){
		return Hyip.getProducerTypeL();
	}
	
	public int getProducerTypeHigh(){
		return Hyip.getProducerTypeH();
	}
	
	public int getConsumerTypeLow(){
		return Investor.getConsumerTypeL();
	}
	
	public int getConsumerTypeHigh(){
		return Investor.getConsumerTypeH();
	}
	*/
	public int getCurrentIteration(){
		Context<Object> context = ContextUtils.getContext(this);
		Context<Object> parentContext = ContextUtils.getParentContext(context);
		GameController controller = (GameController)parentContext.getObjects(GameController.class).get(0);
		return controller.getCurrentIteration();
	}
	
	public int getCurrentGeneration(){
		Context<Object> context = ContextUtils.getContext(this);
		Context<Object> parentContext = ContextUtils.getParentContext(context);
		GameController controller = (GameController)parentContext.getObjects(GameController.class).get(0);
		return controller.getCurrentGeneration();
	}
	
	public double getGain(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		double gain = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			gain += ((ProducerStrategy)producerStrategy).getProducer().getGain();
		}
		return gain;
	}
	
	public double getAvgConsumerGain(){
		Context<Player> context = CredibilityGame.PLAYERS;
		double sum = 0;
		int counter = 0;
		for(Player c:context.getObjects(Investor.class)){
			sum += c.getGain();
			counter++;
		}
		return sum/counter;
	}
	
	public double getAvgProducerGain(){
		Context<Player> context = CredibilityGame.PLAYERS;
		double sum = 0;
		int counter = 0;
		for(Player c:context.getObjects(Hyip.class)){
			sum += c.getGain();
			counter++;
		}
		return sum/counter;
	}
	
	public double getAverageReputationThreshold(){
		Context<Player> context = CredibilityGame.PLAYERS;
		double sum = 0;
		int counter = 0;
		for(Player c:context.getObjects(Investor.class)){
			sum += ((AcceptanceStrategy)(((Investor)c).getStrategy())).getReputationThreshold();
			counter++;
		}
		return sum/counter;
	}
	
	
	
	public double getAverageRating(){
		Context<Player> context = CredibilityGame.PLAYERS;
		double sum = 0;
		int counter = 0;
		for(Player p:context.getObjects(Hyip.class)){
			sum += ((Hyip)p).getCurrentRating().getRatingAsDouble();
			counter++;
		}
		return sum/counter;
	}
	
	public int getAcceptanceRate(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedBy = 0;
		int acceptedBy = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			ratedBy += i.getAcceptingConsumers().size();
			ratedBy += i.getRejectingConsumers().size();
			acceptedBy += i.getAcceptingConsumers().size();
		}
		return ratedBy==0?0:(int)(((double)acceptedBy/(double)ratedBy)*100);
	}
	
	public double getAverageSignalThreshold(){
		Context<Player> context = CredibilityGame.PLAYERS;
		double sum = 0;
		int counter = 0;
		for(Player c:context.getObjects(Investor.class)){
			sum += ((AcceptanceStrategy)(((Investor)c).getStrategy())).getThreshold();
			counter++;
		}
		return sum/counter;
	}
	
	public double getAverageSignal(){
		Context<Player> context = CredibilityGame.PLAYERS;
		double sum = 0;
		int counter = 0;
		for(Player c:context.getObjects(Investor.class)){
			sum += ((AcceptanceStrategy)(((Investor)c).getStrategy())).getSignal();
			counter++;
		}
		return sum/counter;
	}
	
	//How many experts rated this strategy (not in %)
	public int getRatedByExperts(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedBy = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				ratedBy += a.consumer.getExpertise() < 0.16?1:0;
			}
			for(Acceptance a:i.getRejectingConsumers()){
				ratedBy += a.consumer.getExpertise() < 0.16?1:0;
			}
		}
		return ratedBy;
	}
	
	//What was the average expert signal threshold
	public double getAverageExpertSignalThreshold(){
		Context<Player> context = CredibilityGame.PLAYERS;
		double sum = 0;
		int counter = 0;
		for(Player c:context.getObjects(Investor.class)){
			if(((Investor)c).getExpertise()<0.16){
				sum += ((AcceptanceStrategy)(((Investor)c).getStrategy())).getThreshold();
				counter++;
			}
		}
		return sum/counter;
	}
	
	public double getAverageNonExpertSignalThreshold(){
		Context<Player> context = CredibilityGame.PLAYERS;
		double sum = 0;
		int counter = 0;
		for(Player c:context.getObjects(Investor.class)){
			if(((Investor)c).getExpertise()>=0.66){
				sum += ((AcceptanceStrategy)(((Investor)c).getStrategy())).getThreshold();
				counter++;
			}
		}
		return sum/counter;
	}
	
	public int getAcceptanceBySignalStrategy(){
		Context<Player> context = CredibilityGame.PLAYERS;
		int sum = 0;
		for(Player c:context.getObjects(Investor.class)){
			if(((AcceptanceStrategy)((Investor)c).getStrategy()).getAcceptanceStrategy() == 0){
				sum ++;
			}
		}
		return sum;
	}
	
	public int getAcceptanceByReputationStrategy(){
		Context<Player> context = CredibilityGame.PLAYERS;
		int sum = 0;
		for(Player c:context.getObjects(Investor.class)){
			if(((AcceptanceStrategy)((Investor)c).getStrategy()).getAcceptanceStrategy() == 1){
				sum ++;
			}
		}
		return sum;
	}
	
	public int getAcceptanceByReputationAndSignalStrategy(){
		Context<Player> context = CredibilityGame.PLAYERS;
		int sum = 0;
		for(Player c:context.getObjects(Investor.class)){
			if(((AcceptanceStrategy)((Investor)c).getStrategy()).getAcceptanceStrategy() == 2){
				sum ++;
			}
		}
		return sum;
	}
	
	public int getAcceptanceByReputationOrSignalStrategy(){
		Context<Player> context = CredibilityGame.PLAYERS;
		int sum = 0;
		for(Player c:context.getObjects(Investor.class)){
			if(((AcceptanceStrategy)((Investor)c).getStrategy()).getAcceptanceStrategy() == 3){
				sum ++;
			}
		}
		return sum;
	}
	
	//How many consuments accepted (not in %)
	public int getAcceptedBy(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int acceptedBy = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			acceptedBy += i.getAcceptingConsumers().size();
		}
		return acceptedBy;
	}
	
	public int getAcceptedBySignal(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int acceptedBy = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				if(a.isAcceptedBySignal()){
					acceptedBy++;
				}
			}
		}
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getRejectingConsumers()){
				if(a.isAcceptedBySignal()){
					acceptedBy++;
				}
			}
		}
		return acceptedBy;
	}
	
	public int getAcceptedByReputation(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int acceptedBy = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				if(a.isAcceptedByReputation()){
					acceptedBy++;
				}
			}
		}
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getRejectingConsumers()){
				if(a.isAcceptedByReputation()){
					acceptedBy++;
				}
			}
		}
		return acceptedBy;
	}
	
	public int getRatedBy(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedBy = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			ratedBy += i.getAcceptingConsumers().size();
			ratedBy += i.getRejectingConsumers().size();
		}
		return ratedBy;
	}
	
	public int getRatedByRange1(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.2){
					ratedByRange++;
				}
			}
			for(Acceptance a:i.getRejectingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.2){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getAcceptedByRange1(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.2){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getRatedByRange2(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.4 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.2){
					ratedByRange++;
				}
			}
			for(Acceptance a:i.getRejectingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.4 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.2){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getAcceptedByRange2(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.4 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.2){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getRatedByRange3(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.6 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.4){
					ratedByRange++;
				}
			}
			for(Acceptance a:i.getRejectingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.6 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.4){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getAcceptedByRange3(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.6 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.4){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getRatedByRange4(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.8 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.6){
					ratedByRange++;
				}
			}
			for(Acceptance a:i.getRejectingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.8 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.6){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getAcceptedByRange4(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()<0.8 &&
				   ((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.6){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getRatedByRange5(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.8){
					ratedByRange++;
				}
			}
			for(Acceptance a:i.getRejectingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.8){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getAcceptedByRange5(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int ratedByRange = 0;
		for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
			Information i = ((ProducerStrategy)producerStrategy).getInformation();
			for(Acceptance a:i.getAcceptingConsumers()){
				Investor c = a.getConsumer();
				if(((AcceptanceStrategy)c.getStrategy()).getThreshold()>=0.8){
					ratedByRange++;
				}
			}
		}
		return ratedByRange;
	}
	
	public int getHonestRate(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		int all = strategiesToTypes.getDegree(this);
		if(all == 0){
			return 0;
		}
		else{
			int honest = 0;
			for(Object producerStrategy:strategiesToTypes.getAdjacent(this)){
				if(((ProducerStrategy)producerStrategy).getProducer().isHonest())
					honest++;
			}
			return (int)(((double)honest/(double)all)*100);
		}
	}
	
	public int getProducedBy(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> strategiesToTypes = (Network<Object>)context.getProjection("StrategiesToTypes");
		return strategiesToTypes.getDegree(this);
	}
	
	//if modified, also modify the getAppropriateType(...) method!!
	public String toString(){
		return name;
	}
}
