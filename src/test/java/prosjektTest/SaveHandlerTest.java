package prosjektTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prosjekt.CardDeck;
import prosjekt.SaveHandler;
import prosjekt.Table;

public class SaveHandlerTest {


	private SaveHandler saver;
	private Table table = new Table(600);
	
	@BeforeEach
	public void setup() {
		saver = new SaveHandler();
	}
	
	@Test
	public void testLoad() {
		Table testTable = table;
		try {
			testTable = saver.load("test-save");
		} catch(FileNotFoundException e) {
			fail("Could not load saved file");
			return;
		}
		assertEquals(600, testTable.getChips().getPlayerChips());	
	}
	
	@Test
	public void testLoadFromNonExistingFile() {
		assertThrows(
				FileNotFoundException.class,
				() -> saver.load("notAFile"),
					"Should throw exception");
	}
	
	//Koden er inspirert av øvingsforelesning 13
	//Sjekker at den saveHandler lagrer riktig til fil.
	@Test
	public void testSave() {
		try {
			saver.save("test-save2", table);
		} catch(FileNotFoundException e) {
			fail("Could not save the file");
		}
		
		byte[] file1 = null;
		byte[] file2 = null;
		
		try {
			file1 = Files.readAllBytes(Path.of(SaveHandler.getFilePath("test-save")));
		} catch (IOException e) {
			fail("Could not read file");
		}
		
		try {
			file2 = Files.readAllBytes(Path.of(SaveHandler.getFilePath("test-save2")));
		} catch (IOException e) {
			fail("Could not read file");
		}
		assertTrue(Arrays.equals(file1, file2));
		assertNotNull(file1);
		assertNotNull(file2);
	
		
	}
	
	@AfterAll
	static void teardown() {
		File testSaveFile = new File(SaveHandler.getFilePath("test-save2"));
		testSaveFile.delete();
	}
}
