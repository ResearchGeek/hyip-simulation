package CredibilityGame.rating;

import java.util.ArrayList;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;

public class UpDownRating extends Rating{
	private static int PRODUCER_RATING_H;
	private static int PRODUCER_RATING_L;
	
	private double balance; 
	
	public static void initialize(){
		Parameters params = RunEnvironment.getInstance().getParameters();
		PRODUCER_RATING_H = (Integer)params.getValue("producer_rating_h");
		PRODUCER_RATING_L = (Integer)params.getValue("producer_rating_l");
	}
	
	public UpDownRating(){
		balance = RandomHelper.createUniform(PRODUCER_RATING_L, PRODUCER_RATING_H).nextInt();
	}
	
	public UpDownRating(double balance){
		this.balance = balance;
	}
	
	private void modifyBalance(double modifyBy){
		balance += modifyBy;
	}
	
	@Override
	public void aggregate(ArrayList<Rating> ratings) {
		for(Rating r:ratings){
			this.modifyBalance(((UpDownRating)r).getRatingAsDouble());
		}
	}

	@Override
	public void aggregate(Rating rating) {
		this.modifyBalance(((UpDownRating)rating).getRatingAsDouble());
	}
	
	@Override
	public Double getRatingAsDouble() {
		return balance;
	}

	@Override
	public Rating clone() {
		return new UpDownRating(this.balance);
	}
	
	public String toString(){
		return ""+this.balance;
	}

}
