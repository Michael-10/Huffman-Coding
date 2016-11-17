import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Main {

	public static HashMap<String, String> map;
	public static final String FILENAME1 = "Dictionary.txt"; 
	public static final String FILENAME2 = "Mystery.txt"; 

	public static void main(String[] args) {
		String text = readFile(FILENAME1);
		String[] lines = splitString(text);
		createMap(lines);
		map.put("01", " ");
//		printMap();
		String compressedText = readFile(FILENAME2);
		String decodedText = decode(compressedText);
		System.out.println(decodedText);
	}
	
	private static String decode(String compressedText) {
		String decoded = "";
		String bitString = "";
		
		for (int i = 0; i < compressedText.length(); i++) {
			bitString += compressedText.charAt(i);
			if (map.containsKey(bitString)) {
				decoded += map.get(bitString);
				bitString = "";
			}
		}
		return decoded;
	}

	private static void printMap() {
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}
	}

	private static void createMap(String[] lines) {
		String[] parts;
		map = new HashMap<>();
		for (int i = 0; i < lines.length; i++) {
			parts = lines[i].split(" ");
			if (parts[0].contains("<LF>")) {
				parts[0] = "\n";
			}
//			System.out.println(parts[0] + " " + parts[1]);
			map.put(parts[1], parts[0]);
		}
	}

	private static String[] splitString(String text) {
		String[] lines = text.split("\n");
		
		return lines;
	}

	private static String readFile(String filename) {
		File file = new File(filename);
		BufferedReader reader; 
		String line = "";
		String text = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				text += line + "\n";
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error opening file");
		} catch (IOException e) {
			System.err.println("Error reading file");
		}
		return text;
	} // end readFile method
	
}
