import com.debacharya.nsgaii.Reporter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileTest {

	public static void main(String[] args) throws IOException {

		Files.createDirectories(Paths.get("abc/xyz/"));
		Files.createDirectories(Paths.get(""));

		try {
			FileWriter writer = new FileWriter("abc/xyz/test.txt");

			writer.write("test");
			writer.close();
		} catch (Exception e) {
			System.out.println("\n!!! COULD NOT WRITE FILE TO DISK!\n\n");
		}
	}
}
