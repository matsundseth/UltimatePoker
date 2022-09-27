package prosjekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//Hele klassen er inspirert av SaveHandler.java fra øvingsforelesning 10
public class SaveHandler implements Saver {
	public final static String SAVE_FOLDER = "src/main/java/prosjekt/saves/";
	
	public void save(String filename, Table table) throws FileNotFoundException {
		try (PrintWriter printWriter = new PrintWriter(getFilePath(filename))) {
			printWriter.println(table.getChips().getPlayerChips());
			}
	}
	
	
	public static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}
	
	public Table load(String filename) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(getFilePath(filename)))) {
			Table table = new Table(scanner.nextInt());
			return table;
		}
	}
}
