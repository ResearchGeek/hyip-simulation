package CredibilityGame.rating;

import java.util.ArrayList;

public abstract class Rating {
	
	public abstract Rating clone();
	
	/*
	 * Aggregates this rating with a given list of other ones
	 */
	public abstract void aggregate(ArrayList<Rating> ratings);
	
	public abstract void aggregate(Rating rating);
	
	/*
	 * Returns numerical representation of this rating  
	 */
	public abstract Double getRatingAsDouble(); 
	

}
