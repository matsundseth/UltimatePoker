package prosjekt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.util.converter.NumberStringConverter;

public class Score {
	private List<Card> hand = new ArrayList<>();
	private int score;
	private List<Card> bestHand=new ArrayList<Card>();
	private String resultString;
	
	
	public Score(List<Card> hand) {
		if (hand == null || hand.size() != 7) {
			throw new IllegalArgumentException("Hand must contain 7 cards");
		}
		this.hand = hand;
		if(checkRoyalFlush(hand)) {
			score = 9;
			resultString = "royal flush";
		}
		else if(checkStraightFlush(hand)) {
			score = 8;
			resultString = "straight flush";
		}
		else if(checkFourOfAKind(hand)) {
			score = 7;
			resultString = "four of a kind";
		}
		else if(checkFullHouse(hand)) {
			score = 6;
			resultString = "full house";
		}
		else if(checkFlush(hand)) {
			score = 5;
			flushBestHand(bestHand);
			resultString = "flush";
		}
		else if(checkStraight(hand)) {
			score = 4;
			resultString = "straight";
		}
		else if(checkThreeOfAKind(hand)) {
			score = 3;
			resultString = "three of a kind";
		}
		else if(checkTwoPairs(hand)) {
			score = 2;
			resultString = "two pairs";
		}
		else if(checkPairs(hand)) {
			score = 1;
			resultString = "one pair";
		}
		else {
			score = 0;
			highCard();
			resultString = "high card " + String.valueOf(bestHand.get(0).getFace());
		}
		
	}
	
	//Funksjon som sjekker om kortene gir royal flush.
	private boolean checkRoyalFlush(List<Card> hand) {
		int aceAndKingCheck = 0;
		//Sjekker for straight flush:
		if (checkStraightFlush(hand)) {
			//Sjekker for ess og konge blant kortene som gir straight flush:
			for (int i=0; i<bestHand.size(); i++) {
				if (bestHand.get(i).getFace()==14 || bestHand.get(i).getFace()==13) {
					aceAndKingCheck++;
				}
			}
			if(aceAndKingCheck == 2) {
				return true;
			}
		}
		return false;
	}
	
	//Funksjon som sjekker om man har straight flush
	private boolean checkStraightFlush(List<Card> hand) {
		//Sjekker for flush:
		if (checkFlush(hand)) {
			//Sjekker for straight blant kortene som ga flush:
			if (checkStraight(bestHand)) {
				return true;
			}
		}
		return false;
	}
	
	
	//Bruker en funksjon som sjekker om det er fire like kort på hånden.
	//Inspirert av https://stackoverflow.com/questions/39935327/finding-four-of-a-kind-in-a-5-card-game
	private boolean checkFourOfAKind(List<Card> hand) { 
		List<Integer> cardValues = handWithoutFace(hand);
		int counter = 0;
		int numberOfAKind = 0;
		//Sjekker om det finnes flere kort med samme tallverdi:
		for(int i = 0; i < cardValues.size(); i++) {
			counter = 0;
			for(int j = 0; j < cardValues.size(); j++) {
				if(cardValues.get(i) == cardValues.get(j)) {
					counter++;
				}
			}
			if(counter == 4) {
				List<Card> helpList = new ArrayList<Card>();
				List<Card> copyList = new ArrayList<Card>(hand);
				//Finner de 4 like kortenes index, og legger de til helpList:
				for (int k = 0; k < cardValues.size(); k++) {
					numberOfAKind = 0;
					for (int j = 0; j < cardValues.size(); j++) {
						if (cardValues.get(k) == cardValues.get(j) && k != j) {
							numberOfAKind++;
							if(numberOfAKind == 3) {
								helpList.add(hand.get(k));
								copyList.remove(hand.get(k));
							}
						}
					}
				}
				//Bruker copyList og helpList for å få laget en bestHand, der den også tar med
				//kortet med høyest tallverdi.
				helpList.add(checkHighestCard(copyList));
				copyList.remove(checkHighestCard(copyList));
				bestHand = helpList;
				return true;
			}
		}
		return false;
	}
	
	//Funksjon som sjekker om man har fullt hus
	private boolean checkFullHouse(List<Card> hand) {
		//Sjekker om det er 3 like:
		if (checkThreeOfAKind(hand)) {
			//Sjekker om det i tillegg til 3 like er minst 2 andre kort som er like:
			List<Integer> sortedHand = new ArrayList<Integer>();
			sortedHand=sortByNumber(hand);
			Set<Integer> set = new HashSet<Integer>();
			set.addAll(sortedHand);
			if(set.size() <= hand.size()-3) {
				List<Integer> cardValues = handWithoutFace(hand);
				List<Card> helpList = new ArrayList<Card>();
				List<Card> helpList2 = new ArrayList<Card>();
				int numberOfAKind = 0;
				//Legger først til kort av typen 3 like i en hjelpeliste
				for (int i = 0; i < cardValues.size(); i++) {
					numberOfAKind=0;
					for (int j = 0; j < cardValues.size(); j++) {
						if (cardValues.get(i) == cardValues.get(j) && i != j) {
							numberOfAKind++;
						}
					}
					if (numberOfAKind==2) {
						helpList.add(hand.get(i));
					}
				}
				//Fjerner den laveste trippelen dersom det er 2x3 like
				while (helpList.size()>3) {
					helpList2.add(checkLowestCard(helpList));
					helpList.remove(checkLowestCard(helpList));
				}
				//Legger deretter til kort av typen 2 like i en annen hjelpeliste.
				for (int i = 0; i < cardValues.size(); i++) {
					numberOfAKind=0;
					for (int j = 0; j < cardValues.size(); j++) {
						if (cardValues.get(i) == cardValues.get(j) && i != j) {
							numberOfAKind++;
						}
					}
					if (numberOfAKind==1) {
						helpList2.add(hand.get(i));
					}
				}
				bestHand = helpList;
				//Legger til det h�yeste paret fra helpList2
				bestHand.add(checkHighestCard(helpList2));
				helpList2.remove(checkHighestCard(helpList2));
				bestHand.add(checkHighestCard(helpList2));
				helpList2.remove(checkHighestCard(helpList2));
				return true;
			}
		}
		return false;
	}
	
	//Funksjon som sjekker om man har flush
	private boolean checkFlush(List<Card> hand) {
		List<Card> copyList = new ArrayList<>(hand);
		int S=0;
		for (int i=0; i<hand.size(); i++) {
			if (hand.get(i).getSuit()=='S') {
				S+=1;
			}
		}
		if (S>=5) {
			List<Card> toBeRemoved=new ArrayList<Card>();
			for (int i=0; i<hand.size(); i++) {
				if (hand.get(i).getSuit()!='S') {
					toBeRemoved.add(hand.get(i));
				}
			}
			copyList.removeAll(toBeRemoved);
		}
		int H=0;
		for (int i=0; i<hand.size(); i++) {
			if (hand.get(i).getSuit()=='H') {
				H+=1;
			}
		}
		if (H>=5) {
			List<Card> toBeRemoved=new ArrayList<Card>();
			for (int i=0; i<hand.size(); i++) {
				if (hand.get(i).getSuit()!='H') {
					toBeRemoved.add(hand.get(i));
				}
			}
			copyList.removeAll(toBeRemoved);
		}
		int D=0;
		for (int i=0; i<hand.size(); i++) {
			if (hand.get(i).getSuit()=='D') {
				D+=1;
			}
		}
		if (D>=5) {
			List<Card> toBeRemoved=new ArrayList<Card>();
			for (int i=0; i<hand.size(); i++) {
				if (hand.get(i).getSuit()!='D') {
					toBeRemoved.add(hand.get(i));
				}
			}
			copyList.removeAll(toBeRemoved);
		}
		int C=0;
		for (int i=0; i<hand.size(); i++) {
			if (hand.get(i).getSuit()=='C') {
				C+=1;
			}
		}
		if (C>=5) {
			List<Card> toBeRemoved=new ArrayList<Card>();
			for (int i=0; i<hand.size(); i++) {
				if (hand.get(i).getSuit()!='C') {
					toBeRemoved.add(hand.get(i));
				}
			}
			copyList.removeAll(toBeRemoved);
		}
		if (S>=5 || H>=5 || D>=5 || C>=5) {
			bestHand.clear();
			for (int k=0; k<copyList.size(); k++) {
				bestHand.add(checkHighestCard(copyList));
				copyList.remove(checkHighestCard(copyList));
				k--;
			}
			return true;
		}
		return false;
	}
	
	//Funksjon som fjerner overfl�dige kort fra flush
	private void flushBestHand(List<Card> hand) {
		while (hand.size()>5) {
			hand.remove(checkLowestCard(hand));
		}
		bestHand=hand;
	}
	
	//Funksjon som sjekker om man har straight
	private boolean checkStraight(List<Card> hand) {
		List<Card> copyList = new ArrayList<>(hand);
		List<Integer> numbers=sortByNumber(hand);
		Set<Integer> set = new HashSet<>();
		set.addAll(numbers);
		//Fjerner kort med lik tallverdi fra hand
		if (set.size()<copyList.size()) {
			for (int i=0; i<copyList.size(); i++) {
				for (int j=0; j<copyList.size(); j++) {
					if (copyList.get(i).getFace()==copyList.get(j).getFace() && i!=j) {
						copyList.remove(j);
					}
				}
			}
		}
		numbers.clear();
		numbers.addAll(set);
		Collections.sort(numbers, Collections.reverseOrder());
		List<Integer> cardValues=handWithoutFace(copyList);
		
		for(int i = 0; i < numbers.size()-4; i++) {
			int counter = 0;
			int difference=1;
			for(int j = i+1; j < numbers.size(); j++) {
				if(numbers.get(i) == numbers.get(j)+difference) {
					counter++;
				}
				difference++;
			}
			if (counter >= 4) {
				while (copyList.size()>5) {
					if (i==0) {
						copyList.remove(checkLowestCard(copyList));
					}
					if (i==1) {
						copyList.remove(checkHighestCard(copyList));
						if (copyList.size()>5) {
							copyList.remove(checkLowestCard(copyList));
						}
					}
					if (i==2) {
						copyList.remove(checkHighestCard(copyList));
					}
				}
				bestHand.clear();
				for (int k=0; k<copyList.size(); k++) {
					bestHand.add(checkHighestCard(copyList));
					copyList.remove(checkHighestCard(copyList));
					k--;
				}
				return true;
			}
		}
		
		//Sjekker om ess som verdien 1 gir straight
		if (numbers.contains(14) &&
			numbers.contains(2) &&
			numbers.contains(3) &&
			numbers.contains(4) &&
			numbers.contains(5) &&
			numbers.contains(6)==false ) {
			List<Card> newBestHand=new ArrayList<Card>();
			newBestHand.add(hand.get(cardValues.indexOf(5)));
			newBestHand.add(hand.get(cardValues.indexOf(4)));
			newBestHand.add(hand.get(cardValues.indexOf(3)));
			newBestHand.add(hand.get(cardValues.indexOf(2)));
			newBestHand.add(hand.get(cardValues.indexOf(14)));
			bestHand=newBestHand;
			return true;
		}
		return false;
	}
	
//Funksjon som sjekker om man har 3 like
	private boolean checkThreeOfAKind(List<Card> hand) { 
		List<Integer> cardValues = handWithoutFace(hand);
		List<Card> helpList = new ArrayList<Card>();
		List<Card> copyList = new ArrayList<Card>(hand);
		int counter = 0;
		int numberOfAKind = 0;
		for(int i = 0; i < cardValues.size(); i++) {
			counter = 0;
			for(int j = 0; j < cardValues.size(); j++) {
				if(cardValues.get(i) == cardValues.get(j)) {
					counter++;
				}
			}
			//Legger de like kortene i helpList, copyList holder på kortene slik at 
			//vi også kan legge til de høyeste kortene. Bruker hjelpelistene slik 
			//at vi kan iterere gjennom hånden, mens vi kan flytte på kort.
			if(counter == 3) {
				for (int k = 0; k < cardValues.size(); k++) {
					numberOfAKind = 0;
					for (int j = 0; j < cardValues.size(); j++) {
						if (cardValues.get(k) == cardValues.get(j) && k != j) {
							numberOfAKind++;
							if(numberOfAKind == 2) {
								helpList.add(hand.get(k));
								copyList.remove(hand.get(k));
							}
						}
					}
				}
				helpList.add(checkHighestCard(copyList));
				copyList.remove(checkHighestCard(copyList));
				helpList.add(checkHighestCard(copyList));
				copyList.remove(checkHighestCard(copyList));
				bestHand = helpList;
				return true;
			}
		}
		return false;
	}
	
	//Funksjon som sjekker om man har to par
	private boolean checkTwoPairs(List<Card> hand) {
		List<Integer> cardValues = handWithoutFace(hand);
		List<Card> copyList = new ArrayList<>(hand);
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(cardValues);
		List<Card> helpList = new ArrayList<Card>();
		//Sjekker at vi mister minst 2 eller 3 kort når vi gjør om til til et set, og at vi ikke har 3 like, 4 like eller hus.
		if((set.size() == hand.size()-2 || set.size() == hand.size()-3) && !checkThreeOfAKind(hand) && !checkFourOfAKind(hand) &&!checkFullHouse(hand)) {
			for (int i = 0; i < cardValues.size(); i++) {
				for (int j = 0; j < cardValues.size(); j++) {
					if (cardValues.get(i) == cardValues.get(j) && i != j) {
						helpList.add(hand.get(i));
						copyList.remove(hand.get(i));
					}
				}
			}
			//Plukke ut de to høyeste parrene ved 3 par
			if(helpList.size() == 6) {
				List<Card> copyList2 = new ArrayList<>(helpList);
				for(int i = 0; i < helpList.size(); i++) {
					if(helpList.get(i).getFace() == checkLowestCard(helpList).getFace()) {
						copyList2.remove(helpList.get(i));
						copyList.add(helpList.get(i));
					}
				}
				helpList = copyList2;
			}
			bestHand.clear();
			for (int k=0; k<helpList.size(); k++) {
				bestHand.add(checkHighestCard(helpList));
				helpList.remove(checkHighestCard(helpList));
				k--;
			}
			//Legger til det høyeste kortet, dersom spiller og cpu har samme par.
			bestHand.add(checkHighestCard(copyList));
			return true;
		}
		return false;
	}
	
	//Funksjon som sjekker om man har ett par
	private boolean checkPairs(List<Card> hand) {
		List<Integer> cardValues = handWithoutFace(hand);
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(cardValues);
		//Sjekker at det finnes to kort som er like
		if(set.size() == cardValues.size()-1) {
			List<Card> helpList = new ArrayList<Card>();
			List<Card> copyList = new ArrayList<>(hand);
			for (int i = 0; i < cardValues.size(); i++) {
				for (int j = 0; j < cardValues.size(); j++) {
					if (cardValues.get(i) == cardValues.get(j) && i != j) {
						helpList.add(hand.get(i));
						copyList.remove(hand.get(i));
					}
				}
			}
			//Legger til de tre høyeste kortene i tillegg til par, for å avgjøre dersom spiller
			// og cpu har samme par.
			helpList.add(checkHighestCard(copyList));
			copyList.remove(checkHighestCard(copyList));
			helpList.add(checkHighestCard(copyList));
			copyList.remove(checkHighestCard(copyList));
			helpList.add(checkHighestCard(copyList));
			copyList.remove(checkHighestCard(copyList));
			bestHand = helpList;
			return true;
		}
		return false;
	}
	
	//Legger til de 5 h�yeste korta i bestHand
	private void highCard() {
		List<Card> copyList = new ArrayList<Card>(hand);
		bestHand.clear();
		for (int i=0; i<5; i++) {
			bestHand.add(checkHighestCard(copyList));
			copyList.remove(checkHighestCard(copyList));
		}
	}
	
	//Funksjon som returnerer det høyeste kortet i en liste med kort
	private Card checkHighestCard(List<Card> hand) {
		for (int k = 0; k < hand.size(); k++) {
			if(Collections.max(handWithoutFace(hand)) == handWithoutFace(hand).get(k)) {
				return hand.get(k);
			}
		}
		return null;
	}
	
	//Funksjon som returnerer kortet med lavest verdi
	private Card checkLowestCard(List<Card> hand) {
		for (int k = 0; k < hand.size(); k++) {
			if(Collections.min(handWithoutFace(hand)) == handWithoutFace(hand).get(k)) {
				return hand.get(k);
			}
		}
		return null;
	}
	
	//Funksjon som tar ut verdiene til kortene og sorterer de synkende i en ArrayList.
	private List<Integer> sortByNumber(List<Card> hand) {
		List<Integer> sortedHand = new ArrayList<Integer>();
		for(int i = 0; i < hand.size(); i++) {
			sortedHand.add(hand.get(i).getFace());
		}
		Collections.sort(sortedHand, Collections.reverseOrder());
		return sortedHand;
	}
	
	//Funksjon som lager en liste med bare verdiene til kortene
	private List<Integer> handWithoutFace(List<Card> hand) {
		List<Integer> sortedHand = new ArrayList<Integer>();
		for(int i = 0; i < hand.size(); i++) {
			sortedHand.add(hand.get(i).getFace());
		}
		return sortedHand;
	}
	
	public int getScore() {
		return score;
	}
	public List<Card> getHand() {
		return hand;
	}
	public List<Card> getBestHand() {
		return bestHand;
	}
	public String getResultString() {
		return resultString;
	}
	
	public static void main(String[] args) {
		List<Card> testHand = new ArrayList<Card>();
		Card kort1 = new Card('D',11);
		Card kort2 = new Card('D',12);
		Card kort3 = new Card('D',10);
		Card kort4 = new Card('D',8);
		Card kort5 = new Card('D',13);
		Card kort6 = new Card('D',9);
		Card kort7 = new Card('D',14);
		testHand.add(kort7);
		testHand.add(kort6);
		testHand.add(kort5);
		testHand.add(kort4);
		testHand.add(kort3);
		testHand.add(kort2);
		testHand.add(kort1);
		Score score = new Score(testHand);
		//score.checkStraight(testHand);
		System.out.println(score.getResultString());
		System.out.println(score.getBestHand());
	}
}
