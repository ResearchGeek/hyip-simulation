package CredibilityGame;

import HyipGame.HyipEvolve;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;

public class GameController {
	private int generationNumber;
	private int iterationNumber;
	private int currentGeneration;
	private int currentIteration;
	//private int counterGeneration;
	//private int counterIteration;
	
	public GameController(){
		Parameters params = RunEnvironment.getInstance().getParameters();
		iterationNumber = (Integer)params.getValue("iteration_number");
		// zmienna oznacza po ilu tickach zakonczyn dany run
		// ustawione w jednym z batchy na 200
		generationNumber = (Integer)params.getValue("generation_number");
		// oznacza po ilu generacjach zakonczyc batch job
		// ustawione w jednym z batchy na 10
		System.out.println("generationNumber: " + generationNumber);
		System.out.println("iterationNumber: " + iterationNumber);
	}
	
	@ScheduledMethod(start=1.0, interval=1.0, priority=ScheduleParameters.FIRST_PRIORITY)
	public void firstStep(){
		if(currentIteration==(iterationNumber-1)){
			System.out.println("counterIteration: " + currentIteration);
			System.out.println("Execute generation end protocols");
			// dochodzimy do konca trwania generacji, ewoluj
			HyipEvolve.evolve(this);
			// niszcz obiekty - resetuj hyipy i inwestycje
			Hyip.reset();
			// resetuj inwestorow
			Investor.reset();
		}
	}
	
	@ScheduledMethod(start=1.0, interval=1.0, priority=ScheduleParameters.LAST_PRIORITY)
	public void step(){
		//calculate roi
		System.out.println("Let's calculate some ROI's");
		Hyip.calculateRois();
		
		//check whether this is the last generation/iteration
		if(currentIteration==(iterationNumber-1)){
			System.out.println("This is the last iteration");
			currentIteration=0;
			if(currentGeneration==(generationNumber-1)){
				System.out.println("Ending instance run");
				RunEnvironment.getInstance().endRun();
			}
			else{
				//Hyip.evolve();
				System.out.println("Ending current generation");
				//Consumer.evolve();
				currentGeneration++;
			}
		}
		else
		{
			System.out.println("Incrementing current iteration number");
			currentIteration++;
			//	if(isWarmedUp())
			//	Hyip.recalculateRatings();
			//else
				//System.out.println("Skipping reputation aggregation");
		}
	}
	
	public int getCurrentGeneration(){
		return currentGeneration;
	}
	
	public int getCurrentIteration(){
		return currentIteration;
	}
	
	public boolean isWarmedUp(){
		return currentIteration>=(iterationNumber*0.05);
	}
	
}
