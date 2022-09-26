package prosjektTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prosjekt.Chips;

public class ChipsTest {
	
	private Chips chips;
	
	@BeforeEach
	public void setup() {
		chips=new Chips(1000);
	}
	
	
	@Test
	public void testConstructor() {
		assertEquals(chips.getPlayerChips(), 1000);
		assertThrows(
				IllegalArgumentException.class,
				() -> {Chips chips2=new Chips(0);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {Chips chips3=new Chips(-1);}
				);
	}
	
	
	@Test
	public void testBetAnte() {
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betAnte(501);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betAnte(0);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betAnte(-1);}
				);
		chips.betAnte(500);
		assertEquals(500, chips.getPlayerChips());
		assertEquals(1000, chips.getChipsOnTable());
		assertEquals(500, chips.getAnte());
	}
	
	
	@Test
	public void testBetBlind() {
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betBlind(999);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betBlind(-1);}
				);
		chips.betBlind(500);
		assertEquals(500, chips.getPlayerChips());
		assertEquals(500, chips.getBlind());
	}
	
	@Test
	public void testBetPlay1() {
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betPlay1(5);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betPlay1(2);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betPlay1(-3);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betPlay1(-4);}
				);
		chips.betAnte(250);
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betPlay1(4);}
				);
		chips.betPlay1(3);
		assertEquals(0, chips.getPlayerChips());
		assertEquals(2000, chips.getChipsOnTable());
	}
	
	@Test
	public void testBetPlay2() {
		chips.betAnte(250);
		chips.betPlay2();
		assertEquals(250, chips.getPlayerChips());
		assertEquals(1500, chips.getChipsOnTable());
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betAnte(334); chips.betPlay2();}
				);
	}
	
	@Test
	public void testBetPlay3() {
		chips.betAnte(500);
		chips.betPlay3();
		assertEquals(0, chips.getPlayerChips());
		assertEquals(2000, chips.getChipsOnTable());
		assertThrows(
				IllegalArgumentException.class,
				() -> {chips.betAnte(501); chips.betPlay3();}
				);
	}
	
	@Test
	public void testCheckBlind() {
		assertEquals(500, chips.checkBlind(9));
		assertEquals(50, chips.checkBlind(8));
		assertEquals(10, chips.checkBlind(7));
		assertEquals(3, chips.checkBlind(6));
		assertEquals(3, chips.checkBlind(5));
		assertEquals(1, chips.checkBlind(4));
		assertEquals(0, chips.checkBlind(3));
		assertEquals(0, chips.checkBlind(2));
		assertEquals(0, chips.checkBlind(1));
		assertEquals(0, chips.checkBlind(0));
		assertEquals(0, chips.checkBlind(-1));
		assertEquals(0, chips.checkBlind(10));
	}
	
	@Test
	public void testChipsLost() {
		chips.betBlind(500);
		chips.betAnte(100);
		chips.betPlay2();
		chips.chipsLost();
		assertEquals(200, chips.getPlayerChips());
		assertEquals(0, chips.getChipsOnTable());
		assertEquals(0, chips.getAnte());
		assertEquals(0, chips.getBlind());
	}
	
	@Test
	public void testChipsTied() {
		chips.betBlind(500);
		chips.betAnte(100);
		chips.betPlay2();
		chips.chipsTied();
		assertEquals(1000, chips.getPlayerChips());
		assertEquals(0, chips.getChipsOnTable());
		assertEquals(0, chips.getAnte());
		assertEquals(0, chips.getBlind());
		
	}
	
	@Test
	public void testChipsWon() {
		chips.betBlind(500);
		chips.betAnte(100);
		chips.betPlay2();
		chips.chipsWon(0);
		assertEquals(1300, chips.getPlayerChips());
		assertEquals(0, chips.getChipsOnTable());
		assertEquals(0, chips.getAnte());
		assertEquals(500, chips.getBlind());
	}
	
	@Test
	public void testFold() {
		chips.betBlind(500);
		chips.betAnte(100);
		chips.betPlay2();
		chips.fold();
		assertEquals(200, chips.getPlayerChips());
		assertEquals(0, chips.getChipsOnTable());
		assertEquals(0, chips.getAnte());
		assertEquals(0, chips.getBlind());
		
	}

}
