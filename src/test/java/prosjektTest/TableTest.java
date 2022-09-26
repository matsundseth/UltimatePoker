package prosjektTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prosjekt.Table;

public class TableTest {
	
	private Table table;
	
	@BeforeEach
	public void setup() {
		table=new Table(1000);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(52, table.getDeck().getCardCount());
		assertEquals(1000, table.getChips().getPlayerChips());
		//addCardsToHands() kj�res i konstrukt�ren
		assertEquals(7, table.getPlayerHand().size());
		assertEquals(7, table.getCpuHand().size());
		assertFalse(table.getPlayerHand()==table.getCpuHand());
		assertEquals(table.getPlayerHand().get(0), table.getDeck().getCard(0));
		assertEquals(table.getPlayerHand().get(1), table.getDeck().getCard(2));
		assertEquals(table.getPlayerHand().get(2), table.getDeck().getCard(5));
		assertEquals(table.getPlayerHand().get(3), table.getDeck().getCard(6));
		assertEquals(table.getPlayerHand().get(4), table.getDeck().getCard(7));
		assertEquals(table.getPlayerHand().get(5), table.getDeck().getCard(9));
		assertEquals(table.getPlayerHand().get(6), table.getDeck().getCard(11));
		assertEquals(table.getCpuHand().get(0), table.getDeck().getCard(1));
		assertEquals(table.getCpuHand().get(1), table.getDeck().getCard(3));
		assertEquals(table.getCpuHand().get(2), table.getDeck().getCard(5));
		assertEquals(table.getCpuHand().get(3), table.getDeck().getCard(6));
		assertEquals(table.getCpuHand().get(4), table.getDeck().getCard(7));
		assertEquals(table.getCpuHand().get(5), table.getDeck().getCard(9));
		assertEquals(table.getCpuHand().get(6), table.getDeck().getCard(11));
	}
	
	@Test
	public void testAddCardsToHands() {
		table.addCardsToHands();
		assertEquals(7, table.getPlayerHand().size());
		assertEquals(7, table.getCpuHand().size());
		assertNotNull(table.getPlayerScore());
		assertNotNull(table.getCpuScore());
		assertNotNull(table.getPlayerScore());
		assertNotNull(table.getPlayerScore());

	}

}
