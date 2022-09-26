package prosjektTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import prosjekt.Card;

public class CardTest {
	private Card card = null;

	@Test
	public void testConstructor() {
		assertThrows(
				IllegalArgumentException.class,
				() -> card = new Card('K', 10),
					"Should throw IllegalArgumentException when invalid suit is used");
		
		assertThrows(
				IllegalArgumentException.class,
				() -> card = new Card('S', 1),
					"Should throw IllegalArgumentException when face is 1 or lower");
		assertThrows(
				IllegalArgumentException.class,
				() -> card = new Card('S', 15),
					"Should throw IllegalArgumentException when face is 15 or higher");
		card = new Card('H', 10);
		assertNotNull(card);
		assertEquals('H', card.getSuit());
		assertEquals(10, card.getFace());
		
	}
}
