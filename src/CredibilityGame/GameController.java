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
	private int counterGeneration;
	private int counterIteration;
	
	public GameController(){
		Parameters params = RunEnvironment.getInstance().getParameters();
		iterationNumber = (Integer)params.getValue("iteration_number");
		// zmienna oznacza po ilu tickach zakonczyn dany run
		generationNumber = (Integer)params.getValue("generation_number");
		// oznacza po ilu generacjach zakonczyc batch job
	}
	
	@ScheduledMethod(start=1.0, interval=1.0, priority=ScheduleParameters.FIRST_PRIORITY)
	public void firstStep(){
		if(currentIteration==(iterationNumber-1)){
			HyipEvolve.evolve(this);
			Hyip.reset();
			Investor.reset();
		}
	}
	
	@ScheduledMethod(start=1.0, interval=1.0, priority=ScheduleParameters.LAST_PRIORITY)
	public void step(){
		//calculate payoffs
		//Hyip.calculatePayoffs();
		
		//update iteration/generation numbers for file outputter
		currentIteration = counterIteration;
		currentGeneration = counterGeneration;
		
		//check whether this is the last generation/iteration
		if(counterIteration==(iterationNumber-1)){
			//Hyip.recalculateRatings();
			counterIteration=0;
			if(counterGeneration==(generationNumber-1)){
				RunEnvironment.getInstance().endRun();
			}
			//else{
			//	Hyip.evolve();
				//TODO: wrzuci� tu typy konsument�w, �eby efekty ewolucji zapisywa�y si�
				//do pliku dopiero po reset()!!!
				//Consumer.evolve();
			//	counterGeneration++;
			//}
		}
		else
		{
			counterIteration++;
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
