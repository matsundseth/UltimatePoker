package prosjekt;

import javafx.scene.image.Image;

public class Card {
	private char suit;
	private int face;
	
	public Card(char suit, int face) {
		validateCard(suit, face);
		this.suit = suit;
		this.face = face;
	}
	
	//Sjekker at kortet har verdi mellom 2 og 14, og er en av de 4 typene
	private void validateCard(char suit, int face) {
		if ((suit != 'S' && suit != 'D' && suit != 'H' && suit != 'C') || face < 2 || face > 14) {
			throw new IllegalArgumentException("Not valid card");
		}
	}

	public char getSuit() {
		return suit;
	}

	public int getFace() {
		return face;
	}
	
	public Image getImage() {
		String filename = "" + String.valueOf(face) + String.valueOf(suit) + ".png";
		Image image = new Image(getClass().getResource("images/" + filename).toString(), true);
		return image;
	}
	
	public String toString() {
		return String.format("%s%s", suit, face);
	}

}
