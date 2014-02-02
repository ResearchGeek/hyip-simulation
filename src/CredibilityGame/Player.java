package CredibilityGame;

import java.util.Random;

public abstract class Player {
	protected static Random random = new Random();
	private double gain;
	private Strategy strategy;
	
	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}
	
	public Strategy getStrategy() {
		return strategy;
	}
	
	public void setStrategy(Strategy strategy){
		this.strategy = strategy;
	}

}
