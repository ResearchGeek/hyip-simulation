package HyipSimulator;

public interface Strategy {
	
	/*
	 * Creates new strategy by making copy of the 
	 * thruthfulness/look (ProducerStrategy)
	 * or threshold (ConsumerStrategy) attributes, but 
	 * without adding the object to the context etc.
	 */
	public Strategy copy();
	
	/*
	 * Removes history f.e. of previously generated information 
	 * (ProducerStrategy)
	 */
	public void clear();
	
	/*
	 * Changes the truthfulness/look of the existing 
	 * strategy
	 */
	public void copyStrategy(Strategy copyFrom);
}
