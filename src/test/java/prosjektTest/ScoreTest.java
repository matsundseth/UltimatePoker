package prosjektTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prosjekt.Card;
import prosjekt.Chips;
import prosjekt.Score;

public class ScoreTest {
	
	private List<Card> hand;
	private Card H14;
	private Card H13;
	private Card H12;
	private Card H11;
	private Card H10;
	private Card H9;
	private Card H5;
	private Card H4;
	private Card H3;
	private Card H2;
	private Card C14;
	private Card S14;
	private Card D14;
	private Card D13;
	private Card C13;
	private Card C12;
	private Card C11;
	private Card C10;
	private Card C9;
	private Card C6;
	private Card C5;
	private Card C4;
	
	@BeforeEach
	public void setup() {
		H14 = new Card('H',14);
		H13 = new Card('H',13);
		H12 = new Card('H',12);
		H11 = new Card('H',11);
		H10 = new Card('H',10);
		H9 = new Card('H',9);
		H2 = new Card('H',2);
		H3 = new Card('H',3);
		H4 = new Card('H',4);
		H5 = new Card('H',5);
		S14 = new Card('S',14);
		C14 = new Card('C',14);
		D14 = new Card('D', 14);
		D13 = new Card('D', 13);
		C13 = new Card('C', 13);
		C12 = new Card('C', 12);
		C11 = new Card('C', 11);
		C10 = new Card('C', 10);
		C9 = new Card('C', 9);
		C6 = new Card('C', 6);
		C5 = new Card('C', 5);
		C4 = new Card('C', 4);
		hand = new ArrayList<Card>();
	}
	
	@Test
	public void testHandSizeException() {
		assertThrows(
				IllegalArgumentException.class,
				() -> {Score score = new Score(hand);}
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> {Score score = new Score(null);}
				);
	}
	
	//Tester at den klassen gir royal flush, og returnerer riktig bestHand.
	@Test
	public void testRoyalFlush() {
		hand.add(H14);
		hand.add(H13);
		hand.add(H12);
		hand.add(H11);
		hand.add(H10);
		hand.add(C14);
		hand.add(S14);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(H14, H13, H12, H11, H10));
		assertEquals(9, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	@Test
	public void testStraightFlush() {
		//Tester at den gir straight flush, der ess brukes som 1.
		hand.add(D14);
		hand.add(H14);
		hand.add(H2);
		hand.add(H3);
		hand.add(H4);
		hand.add(H5);
		hand.add(S14);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(H5, H4, H3, H2, H14));
		assertEquals(8, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	@Test
	public void testFourOfAKind() {
		//Passer på at den returnerer at det er 4 like, og ikke fullt hus eller 3 like.
		//Sjekker også at den legger til det høyeste kortet i bestHand i tillegg.
		hand.add(D14);
		hand.add(H14);
		hand.add(C14);
		hand.add(S14);
		hand.add(C13);
		hand.add(H12);
		hand.add(C12);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(D14, H14, C14, S14, C13));
		assertEquals(7, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	//Tester at klassen gir fullt hus.
	//Sjekker også at den legger 3 like før 2 like, og at den legger til det høyeste paret i bestHand.
	@Test
	public void testFullHouse() {
		hand.add(C13);
		hand.add(H13);
		hand.add(D14);
		hand.add(H14);
		hand.add(C14);
		hand.add(H4);
		hand.add(C4);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(D14, H14, C14, C13, H13));
		assertEquals(6, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	//Tester at klassen gir flush, og at den legger de 5 høyeste kortene i riktig rekkefølge i bestHand.
	@Test
	public void testFlush() {
		hand.add(H13);
		hand.add(H14);
		hand.add(H10);
		hand.add(H9);
		hand.add(H4);
		hand.add(H12);
		hand.add(H2);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(H14, H13, H12, H10, H9));
		assertEquals(5, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	//Tester at klassen gir straight, og at klassen legger den høyeste straighten i bestHand.
	@Test
	public void testStraight() {
		hand.add(H13);
		hand.add(H12);
		hand.add(C11);
		hand.add(H14);
		hand.add(C10);
		hand.add(C9);
		hand.add(H2);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(H14, H13, H12, C11, C10));
		assertEquals(4, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	//Tester tre like og sjekker at den også legger til de to høyeste kortene.
	@Test
	public void testCheckThreeOfAKind() {
		hand.add(H14);
		hand.add(C14);
		hand.add(D14);
		hand.add(C10);
		hand.add(H4);
		hand.add(H3);
		hand.add(C11);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(H14, C14, D14, C11, C10));
		assertEquals(3, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	//Tester 2 par, og sjekker at ved 3 par så legger den til de to beste parene og det høyeste kortet i bestHand.
	@Test
	public void testCheckTwoPairs() {
		hand.add(H14);
		hand.add(C14);
		hand.add(H13);
		hand.add(C13);
		hand.add(C4);
		hand.add(C9);
		hand.add(H4);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(H14, C14, H13, C13, C9));
		assertEquals(2, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	//Tester et par, og legger til kortene med høyest verdi.
	@Test
	public void testCheckPair() {
		hand.add(H14);
		hand.add(C14);
		hand.add(C12);
		hand.add(H5);
		hand.add(C4);
		hand.add(H2);
		hand.add(H13);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(H14, C14, H13, C12, H5));
		assertEquals(1, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
	//Tester at ved ingenting så returnerer den 0 og legger til de høyeste kortene i rekkefølge i bestHand.
	@Test
	public void testHighCard() {
		hand.add(H12);
		hand.add(H10);
		hand.add(H14);
		hand.add(C9);
		hand.add(H2);
		hand.add(C4);
		hand.add(C13);
		Score score = new Score(hand);
		List<Card> testList = new ArrayList<>(Arrays.asList(H14, C13, H12, H10, C9));
		assertEquals(0, score.getScore());
		assertEquals(testList, score.getBestHand());
	}
	
}
