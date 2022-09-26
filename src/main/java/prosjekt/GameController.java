package prosjekt;


import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class GameController {
	private Table table;
	private SaveHandler saveHandler = new SaveHandler();
	@FXML private Button betButton;
	@FXML private Button betButton2;
	@FXML private Button foldButton;
	@FXML private Button checkButton;
	@FXML private Button saveButton;
	@FXML private Button loadButton;
	@FXML private ImageView pHand1;
	@FXML private ImageView pHand2;
	@FXML private ImageView dHand1;
	@FXML private ImageView dHand2;
	@FXML private ImageView flop1;
	@FXML private ImageView flop2;
	@FXML private ImageView flop3;
	@FXML private ImageView turn;
	@FXML private ImageView river;
	@FXML private TextField betInput;
	@FXML private TextArea balance;
	@FXML private TextArea finishOutput;
	private int gameCounter;
	private boolean gameLoaded;
	
	public void initialize() {
		table = new Table(1000);
		balance.setEditable(false);
		setTable();
		gameCounter = 0;
		gameLoaded = false;
	}
	
	@FXML
	private void setTable() {
		pHand1.setImage(table.getBackCardImage());
		pHand2.setImage(table.getBackCardImage());
		dHand1.setImage(table.getBackCardImage());
		dHand2.setImage(table.getBackCardImage());
		flop1.setImage(table.getBackCardImage());
		flop2.setImage(table.getBackCardImage());
		flop3.setImage(table.getBackCardImage());
		turn.setImage(table.getBackCardImage());
		river.setImage(table.getBackCardImage());
		checkButton.setVisible(false);
		checkButton.setText("Check");
		betButton.setText("Bet Blind");
		betButton.setVisible(true);
		betButton2.setVisible(false);
		betInput.setVisible(true);
		foldButton.setVisible(true);
		finishOutput.clear();
		gameCounter = 0;
		setBalanceArea();
		table.getChips().fold();
		if(!gameLoaded) {
			loadButton.setVisible(true);
		}
		saveButton.setVisible(true);
		if (table.getChips().getPlayerChips()<=1) {
			finishOutput.setText("You lost! \nThere are not enough chips left.\nStart over to keep playing.");
			loadButton.setVisible(true);
			loadButton.setText("Re-Start");
			saveButton.setVisible(false);
			betButton.setVisible(false);
			betInput.setVisible(false);
			foldButton.setVisible(false);
		}
	}
	
	@FXML
	private void fold() {
		table.addCardsToHands();
		setTable();
		table.getChips().fold();
		setBalanceArea();
	}
	
	@FXML
	private void check() {
		if (gameCounter == 2) {
			betButton2.setVisible(false);
			betButton.setText("Bet 2x Ante");
			flop1.setImage(table.getPlayerHand().get(2).getImage());
			flop2.setImage(table.getPlayerHand().get(3).getImage());
			flop3.setImage(table.getPlayerHand().get(4).getImage());
			finishOutput.clear();
		}
		if (gameCounter == 3) {
			turn.setImage(table.getPlayerHand().get(5).getImage());
			river.setImage(table.getPlayerHand().get(6).getImage());
			setBalanceArea();
			betButton.setText("Bet 1x Ante");
			checkButton.setVisible(false);
			finishOutput.clear();
		}
		if (gameCounter == 6) {
			table.addCardsToHands();
			setTable();
			setBalanceArea();
			gameCounter = -1;
		}
		gameCounter++;
	}
	
	@FXML
	private void bet() {
		if (gameCounter == 0) {
			try {
				table.getChips().betBlind(Integer.parseInt(betInput.getText()));
				setBalanceArea();
				betInput.clear();
				betButton.setText("Bet Ante");	
				saveButton.setVisible(false);
				loadButton.setVisible(false);
				finishOutput.clear();
			}
			catch (IllegalArgumentException i) {
				finishOutput.setText("Blind must be a non-negative \ninteger. \nBlind cannot exceed balance-2. \nTry again.");
				gameCounter--;
			}
		}
		if (gameCounter == 1) {
			try {
				table.getChips().betAnte(Integer.parseInt(betInput.getText()));
				setBalanceArea();
				betInput.clear();
				checkButton.setVisible(true);
				pHand1.setImage(table.getPlayerHand().get(0).getImage());
				pHand2.setImage(table.getPlayerHand().get(1).getImage());
				betButton2.setVisible(true);
				betButton.setText("Bet 3x Ante");
				betInput.setVisible(false);
				finishOutput.clear();
			}
			catch (IllegalArgumentException i) {
				finishOutput.setText("Ante must be a positive integer. \nAnte cannot exceed balance/2. \nTry again.");
				gameCounter--;
			}
		}
		if (gameCounter == 2) {
			try {
				table.getChips().betPlay1(3);
				setBalanceArea();
				finishGame();
			}
			catch (IllegalArgumentException i) {
				finishOutput.setText("Cannot afford to bet 3x ante. \nCheck or fold.");
				gameCounter--;
			}
		}
		if (gameCounter == 3) {
			try {
				table.getChips().betPlay2();
				setBalanceArea();
				finishGame();
			}
			catch (IllegalArgumentException i) {
				finishOutput.setText("Cannot afford to bet 2x ante. \nCheck or fold.");
				gameCounter--;
			}
		}
		if (gameCounter == 4) {
			table.getChips().betPlay3();
			finishGame();
			checkButton.setVisible(true);
			}
		gameCounter++;
	}
	
	@FXML
	private void bet2() {
		if (gameCounter == 2) {
			try {
				table.getChips().betPlay1(4);
				setBalanceArea();
				finishGame();
				gameCounter++;
			}
			catch (IllegalArgumentException i) {
				finishOutput.setText("Cannot afford to bet 4x ante. \nBet 3x ante, check or fold.");
			}
		}
	}
	
	@FXML
	private void finishGame() {
		flop1.setImage(table.getPlayerHand().get(2).getImage());
		flop2.setImage(table.getPlayerHand().get(3).getImage());
		flop3.setImage(table.getPlayerHand().get(4).getImage());
		turn.setImage(table.getPlayerHand().get(5).getImage());
		river.setImage(table.getPlayerHand().get(6).getImage());
		dHand1.setImage(table.getCpuHand().get(0).getImage());
		dHand2.setImage(table.getCpuHand().get(1).getImage());
		betButton.setVisible(false);
		betButton2.setVisible(false);
		foldButton.setVisible(false);
		checkButton.setText("New Game");
		finishOutput.setText(table.checkWinner());
		setBalanceArea();
		gameCounter = 5;
	}
	
	@FXML
	private void saveBalance() {
		try {
		saveHandler.save("saveFile", table);
		}
		catch (FileNotFoundException e){
			finishOutput.setText("Could not save to file");
		}
	}
	
	@FXML
	private void loadBalance() {
		if (table.getChips().getPlayerChips()>1) {
			try {
				table = saveHandler.load("saveFile");
				gameLoaded = true;
			} catch (FileNotFoundException e) {
				finishOutput.setText("Could not load previous save");
			}
		}
		else {
			table=new Table(1000);
		}
		table.addCardsToHands();
		setTable();
		setBalanceArea();
		finishOutput.clear();
		gameCounter = 0;
		loadButton.setText("Load");
		if (gameLoaded) {
			loadButton.setVisible(false);
		}
	}
	
	@FXML
	private void setBalanceArea() {
		balance.setText("Balance: " + String.valueOf(table.getChips().getPlayerChips()) + "\nBlind: "
				+ String.valueOf(table.getChips().getBlind()) + "\nAnte: " +  String.valueOf(table.getChips().getChipsOnTable()/2));
	}
}
