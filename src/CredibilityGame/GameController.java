package CredibilityGame;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.util.collections.IndexedIterable;

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
		generationNumber = (Integer)params.getValue("generation_number");
	}
	
	@ScheduledMethod(start=1.0, interval=1.0, priority=ScheduleParameters.FIRST_PRIORITY)
	public void firstStep(){
		if(currentIteration==(iterationNumber-1)){
		//	Hyip.reset();
			//TODO: usun¹æ, kiedy po³¹czê z typami konsumentów
		//	Investor.evolve();
		//	Investor.reset();
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
				//TODO: wrzuciæ tu typy konsumentów, ¿eby efekty ewolucji zapisywa³y siê
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
