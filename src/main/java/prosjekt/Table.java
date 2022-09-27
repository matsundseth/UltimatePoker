package prosjekt;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Table {
	private List<Card> playerFullHand=new ArrayList<Card>();
	private List<Card> cpuFullHand=new ArrayList<Card>();
	private Card flop1;
	private Card flop2;
	private Card flop3;
	private Card turn;
	private Card river;
	private Score playerScore;
	private Score cpuScore;
	private List<Card> playerBestHand=new ArrayList<Card>();
	private List<Card> cpuBestHand=new ArrayList<Card>();
	private Chips chips;
	private CardDeck deck;
	

	public Table(int chips) {
		if(chips <= 0) {
			throw new IllegalArgumentException("Cant start a game with 0 or less chips");
		}
		deck=new CardDeck(14);
		addCardsToHands();
		this.chips = new Chips(chips);
		
	}
	
	//Stokker kortene og deler dem ut, gir også en score til spiller og computer basert på 
	//de utdelte kortene.
	public void addCardsToHands() {
		deck.shuffleDeck();
		playerFullHand.clear();
		cpuFullHand.clear();
		playerFullHand.add(deck.getCard(0));
		playerFullHand.add(deck.getCard(2));
		cpuFullHand.add(deck.getCard(1));
		cpuFullHand.add(deck.getCard(3));
		flop1=deck.getCard(5);
		flop2=deck.getCard(6);
		flop3=deck.getCard(7);
		turn=deck.getCard(9);
		river=deck.getCard(11);
		playerFullHand.add(flop1);
		playerFullHand.add(flop2);
		playerFullHand.add(flop3);
		playerFullHand.add(turn);
		playerFullHand.add(river);
		cpuFullHand.add(flop1);
		cpuFullHand.add(flop2);
		cpuFullHand.add(flop3);
		cpuFullHand.add(turn);
		cpuFullHand.add(river);
		playerScore = new Score(playerFullHand);
		cpuScore = new Score(cpuFullHand);
	}
	
	
	//Funksjon som finner ut hvem som har vinner basert på hvem som har best score
	//Ved lik score sjekker den hvem som har best hånd.
	public String checkWinner() {
		Score playerScore=new Score(getPlayerHand());
		playerBestHand=playerScore.getBestHand();
		Score cpuScore=new Score(getCpuHand());
		cpuBestHand=cpuScore.getBestHand();
		if (playerScore.getScore()>cpuScore.getScore()) {
			return youWin();
		}
		else if (playerScore.getScore()<cpuScore.getScore()) {
			return youLose();
		}
		//Ved samme score avgjøres vinneren basert på hvem som har høyest verdi på de tellende kortene.
		else if (playerScore.getScore() == cpuScore.getScore()) {
			for(int i = 0; i < playerScore.getBestHand().size(); i++) {
				if(playerScore.getBestHand().get(i).getFace() > cpuScore.getBestHand().get(i).getFace()) {
					return youWin();
				}
				else if(playerScore.getBestHand().get(i).getFace() < cpuScore.getBestHand().get(i).getFace()) {
					return youLose();
				}
			}
		}
		return tie();
	}
	
	public String youWin() {
		chips.chipsWon(playerScore.getScore());
		return "You won with " + playerScore.getResultString() + ":\n" + playerScore.getBestHand()
				+ "\n\nComputer had " + cpuScore.getResultString() + ":\n" + cpuScore.getBestHand();
	}
	
	public String youLose() {
		chips.chipsLost();
		return "You lost with " + playerScore.getResultString() + ":\n" + playerScore.getBestHand()
				+ "\n\nComputer had " + cpuScore.getResultString()  + ":\n" + cpuScore.getBestHand();
	}
	
	public String tie() {
		chips.chipsTied();
		return "You tied with " + playerScore.getResultString() + ".";
	}


	public List<Card> getPlayerBestHand() {
		return playerBestHand;
	}


	public List<Card> getCpuBestHand() {
		return cpuBestHand;
	}


	public List<Card> getPlayerHand() {
		return playerFullHand;
	}


	public List<Card> getCpuHand() {
		return cpuFullHand;
	}
	
	public CardDeck getDeck() {
		return deck;
	}
	
	public Image getBackCardImage() {
		Image image = new Image(getClass().getResource("images/red_back.png").toString(), true);
		return image;
	}
	
	public Chips getChips() {
		return chips;
	}
	
	public Score getPlayerScore() {
		return playerScore;
	}
	
	public Score getCpuScore() {
		return cpuScore;
	}

	
}
