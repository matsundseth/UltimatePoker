package prosjekt;

import java.io.FileNotFoundException;

public interface Saver {

	void save(String filename, Table table) throws FileNotFoundException;
	Table load(String filename) throws FileNotFoundException;
}
