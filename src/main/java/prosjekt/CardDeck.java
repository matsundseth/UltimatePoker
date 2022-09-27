package prosjekt;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {

	private ArrayList<Card> deck=new ArrayList();
	private char[] suit= {'S', 'H', 'D', 'C'};
	
	public CardDeck(int n) {
		if(n<=0 || n>14)
			throw new IllegalArgumentException("Must contain atleast one card and can't contain more than 14 of each suit");
		for (int i=0; i<suit.length; i++) {
			for (int j=2; j<=n; j++) {
				Card c=new Card(suit[i], j);
				deck.add(c);
			}
		}
	}
	
	public int getCardCount() {
		return deck.size();
	}
	
	public Card getCard(int n) {
		if (n<0 || n>=deck.size()) {
			throw new IllegalArgumentException("Selected card is not in the deck");
		}
		else {
			return deck.get(n);
		}
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
		
	}
}
