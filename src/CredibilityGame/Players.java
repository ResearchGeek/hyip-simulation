package CredibilityGame;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import CredibilityGame.HyipType.BadLooking;
import CredibilityGame.HyipType.GoodLooking;
import HyipGame.Percentage;
import HyipGame.PercentageCollection;

public class Players extends DefaultContext<Player> {

	private ArrayList<Percentage> getDailyPercentages(boolean win) {
		File file = new File(win ? "data\\daily_percentage_n.xml"
				: "data/daily_percentage_n.xml");
		ArrayList<Percentage> result = null;

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(PercentageCollection.class);

			/* -- try to read data */
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			PercentageCollection forSaveMultiple = (PercentageCollection) jaxbUnmarshaller
					.unmarshal(file);

			result = forSaveMultiple.getList();

		} catch (JAXBException ex) {
			System.out.println("JAXBException");
			System.out.println(ex.toString());
		}

		return result;
	}

	private ArrayList<Percentage> getWeeklyPercentages(boolean win) {
		File file = new File(win ? "data\\weekly_percentage_n.xml"
				: "data/weekly_percentage_n.xml");
		ArrayList<Percentage> result = null;

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(PercentageCollection.class);

			/* -- try to read data */
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			PercentageCollection forSaveMultiple = (PercentageCollection) jaxbUnmarshaller
					.unmarshal(file);

			result = forSaveMultiple.getList();

		} catch (JAXBException ex) {
			System.out.println("JAXBException");
			System.out.println(ex.toString());
		}

		return result;
	}

	public Players() {
		super("Players");
		System.out.println(Constraints.PLAYERS_LOADED);
		String osName = System.getProperty("os.name").toLowerCase();

		//boolean isMacOs = osName.startsWith("mac");
		boolean isWin = osName.startsWith("win");

		Parameters params = RunEnvironment.getInstance().getParameters();
		int producerPopulationSize = (Integer) params
				.getValue("hyip_population_size");

		int inv_0 = (Integer) params.getValue("inv_0");
		int inv_1 = (Integer) params.getValue("inv_1");
		int inv_2 = (Integer) params.getValue("inv_2");

		Hyip.initialize();

		Iterator<GoodLooking> goodLookingHyips = HyipType.goodLooking
				.iterator();
		
		List<Hyip> goodHyipsPercentageRev = new ArrayList<Hyip>();
		
		for (int x = 0; x < HyipType.goodLooking.size(); x++) {
			assert goodLookingHyips.hasNext();
			GoodLooking goodLooking = goodLookingHyips.next();
			int z = (producerPopulationSize / 2) / HyipType.goodLooking.size();
			for (int i = 0; i < z; i++) {
				GoodLookingHyip goodLookingHyip = new GoodLookingHyip(
						goodLooking);
				goodHyipsPercentageRev.add(goodLookingHyip);
				this.add(goodLookingHyip);
			}
		}
		
		List<Hyip> badHyipsPercentageRev = new ArrayList<Hyip>();

		Iterator<BadLooking> badLookingHyips = HyipType.badLooking.iterator();
		for (int x = 0; x < HyipType.badLooking.size(); x++) {
			assert badLookingHyips.hasNext();
			BadLooking badLooking = badLookingHyips.next();
			int z = (producerPopulationSize / 2) / HyipType.badLooking.size();
			for (int i = 0; i < z; i++) {
				BadLookingHyip badLookingHyip = new BadLookingHyip(badLooking);
				this.add(badLookingHyip);
				badHyipsPercentageRev.add(badLookingHyip);
			}
		}
		
		List<Percentage> listOfDPercentages = getDailyPercentages(isWin);
		int daily_iterator = 1;
		Iterator<Percentage> di = listOfDPercentages.iterator();
		Percentage current_daily = nextNonEmptyPercentage(di);
		
		List<Percentage> listOfWPercentages = getWeeklyPercentages(isWin);
		int weekly_iterator = 1;
		Iterator<Percentage> wi = listOfWPercentages.iterator();
		Percentage current_weekly = nextNonEmptyPercentage(wi);
		
		for(Hyip hyip : goodHyipsPercentageRev){
			if (hyip.getFirstOffert().getForHowLong() >= 6){
				if (current_weekly.getFreq() >= weekly_iterator++){
					hyip.getFirstOffert().setPercent(current_weekly.getPerc());
				} else {
					current_weekly = nextNonEmptyPercentage(wi);
					hyip.getFirstOffert().setPercent(current_weekly.getPerc());
					weekly_iterator = 2;
				}
			} else {
				if (current_daily.getFreq() >= daily_iterator++){
					hyip.getFirstOffert().setPercent(current_daily.getPerc());
				} else {
					current_daily = nextNonEmptyPercentage(di);
					hyip.getFirstOffert().setPercent(current_daily.getPerc());
					daily_iterator = 2;
				}
			}
		}
		
		daily_iterator = 1;
		di = listOfDPercentages.iterator();
		current_daily = nextNonEmptyPercentage(di);
		
		weekly_iterator = 1;
		wi = listOfWPercentages.iterator();
		current_weekly = nextNonEmptyPercentage(wi);
		
		for(Hyip hyip : badHyipsPercentageRev){
			if (hyip.getFirstOffert().getForHowLong() >= 6){
				if (current_weekly.getFreq() >= weekly_iterator++){
					hyip.getFirstOffert().setPercent(current_weekly.getPerc());
				} else {
					current_weekly = nextNonEmptyPercentage(wi);
					hyip.getFirstOffert().setPercent(current_weekly.getPerc());
					weekly_iterator = 2;
				}
			} else {
				if (current_daily.getFreq() >= daily_iterator++){
					hyip.getFirstOffert().setPercent(current_daily.getPerc());
				} else {
					current_daily = nextNonEmptyPercentage(di);
					hyip.getFirstOffert().setPercent(current_daily.getPerc());
					daily_iterator = 2;
				}
			}
		}

		for (int i = 0; i < inv_0; i++) {
			Investor investor = new Investor(InvestorType.HIGH_AVERSION);
			investor.initializeWallet();
			this.add(investor);
		}
		for (int i = 0; i < inv_1; i++) {
			Investor investor = new Investor(InvestorType.MEDIUM_AVERSION);
			investor.initializeWallet();
			this.add(investor);
		}
		for (int i = 0; i < inv_2; i++) {
			Investor investor = new Investor(InvestorType.LOW_AVERSION);
			investor.initializeWallet();
			this.add(investor);
		}

	}

	private Percentage nextNonEmptyPercentage(Iterator i) {
		Percentage pi = null;
		while (i.hasNext()){
			pi = (Percentage) i.next();
			if ( (pi).getFreq() > 0 )
				break; 
		}
		return pi;
	}
}
