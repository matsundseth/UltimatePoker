package prosjekt;

public class Chips {
	private int playerChips;
	private int chipsOnTable;
	private int ante;
	private int blind;
	
	
	public Chips(int startChips) {
		if (startChips<=0) {
			throw new IllegalArgumentException("Can't play without chips");
		}
		else {
			playerChips=startChips;
			chipsOnTable=0;
			ante=0;
			blind=0;
		}
	}
	
	public void betAnte(int n) {
		if (n<=0 || n>playerChips/2) {
			throw new IllegalArgumentException("Ante must be a positive integer. Cannot exceed balance/2.");
		}
		else {
			playerChips-=n;
			chipsOnTable+=2*n;
			ante+=n;
		}
	}
	
	
	public void betBlind(int n) {
		if (n<0 || n>playerChips-2) {
			throw new IllegalArgumentException("Blind must be a non-negative integer. Cannot exceed balance-2.");
		}
		else {
			playerChips-=n;
			blind+=n;
		}
	}
	
	
	public void betPlay1(int n) {
		if (n!=3 && n!=4) {
			throw new IllegalArgumentException("First play bet must be 3 or 4 times ante");
		}
		else if (n*ante>playerChips) {
			throw new IllegalArgumentException("Must afford to place bet");
		}
		else {
			playerChips-=n*ante;
			chipsOnTable+=2*n*ante;
		}
	}
	
	
	public void betPlay2() {
		if (2*ante>playerChips) {
			throw new IllegalArgumentException("Must afford to place bet");
		}
		else {
			playerChips-=2*ante;
			chipsOnTable+=4*ante;
		}
	}
	
	
	public void betPlay3() {
		if (ante>playerChips) {
			throw new IllegalArgumentException("Must afford to place bet");
		}
		else {
			playerChips-=ante;
			chipsOnTable+=2*ante;
		}
	}
	
	
	public int checkBlind(int score) {
		if (score==9) {
			return 500;
		}
		else if (score==8) {
			return 50;
		}
		else if (score==7) {
			return 10;
		}
		else if (score==6 || score==5) {
			return 3;
		}
		else if (score==4) {
			return 1;
		}
		else return 0;
	}
	
	
	public void chipsLost() {
		chipsOnTable=0;
		ante=0;
		blind=0;
	}
	
	public void chipsTied() {
		playerChips+=chipsOnTable/2;
		playerChips+=blind;
		chipsOnTable=0;
		ante=0;
		blind=0;
	}
	
	
	public void chipsWon(int score) {
		playerChips+=chipsOnTable;
		playerChips+=blind;
		playerChips+=checkBlind(score)*blind;
		chipsOnTable=0;
		ante=0;
		if (checkBlind(score)!=0) {
			blind=0;
		}
	}
	
	public void fold () {
		chipsOnTable=0;
		ante=0;
		blind=0;
	}


	public int getPlayerChips() {
		return playerChips;
	}


	public int getChipsOnTable() {
		return chipsOnTable;
	}
	
	
	public int getAnte() {
		return ante;
	}
	
	
	public int getBlind() {
		return blind;
	}
	
}
