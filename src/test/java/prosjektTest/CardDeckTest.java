package prosjektTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prosjekt.CardDeck;

public class CardDeckTest {

	private CardDeck deck;
	
	@Test
	public void testConstructor() {
		assertThrows(
				IllegalArgumentException.class,
				() -> { deck = new CardDeck(15);}
				);
		
	}
	
	//Teoretisk sjanse for at kortene kan stå i nøyaktig samme rekkefølge, men den sjansen er ekstremt liten.
	@Test
	public void testShuffleDeck() {
		deck = new CardDeck(14);
		CardDeck deck2 = new CardDeck(14);
		deck.shuffleDeck();
		boolean validator = false;
		for(int i = 0; i < deck.getCardCount(); i++) {
			if(deck.getCard(i) != deck2.getCard(i))
				validator = true;
		}
		assertTrue(validator);
	}
	
	@Test
	public void testGetCard() {
		deck = new CardDeck(14);
		assertThrows(
				IllegalArgumentException.class,
				() -> {deck.getCard(53);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {deck.getCard(-1);}
				);
		assertEquals(2, deck.getCard(0).getFace());
		assertEquals('S', deck.getCard(0).getSuit());		
	}
	
	@Test
	public void testGetCardCount() {
		deck = new CardDeck(14);
		assertEquals(52, deck.getCardCount());
		CardDeck deck2 = new CardDeck(10);
		assertEquals(36, deck2.getCardCount());
	}
}
