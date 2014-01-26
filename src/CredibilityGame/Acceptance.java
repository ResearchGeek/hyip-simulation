package CredibilityGame;

public class Acceptance {
	Investor consumer;
	boolean acceptedBySignal;
	boolean acceptedByReputation;
	double signal;
	
	public Acceptance(Investor consumer, boolean acceptedBySignal, boolean acceptedByReputation, double signal){
		this.consumer = consumer;
		this.acceptedBySignal = acceptedBySignal;
		this.acceptedByReputation = acceptedByReputation;
		this.signal = signal;
	}
	
	public Investor getConsumer() {
		return consumer;
	}
	
	public boolean isAcceptedBySignal() {
		return acceptedBySignal;
	}
	
	public boolean isAcceptedByReputation() {
		return acceptedByReputation;
	}

	public double getSignal(){
		return signal;
	}
}
