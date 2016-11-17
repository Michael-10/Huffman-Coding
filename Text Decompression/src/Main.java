import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Main {

	public static final String FILENAME = "Sam McGee.txt"; 
	public static HashMap<String, Integer> frequencyMap;
	public static HashMap<String, Node> map;
	public static int numBits = 0;
	public static int totalBits = 0;

	public static void main(String[] args) {
		String text = readFile(FILENAME);
		countChars(text);
		PriorityQueue<Map.Entry<String, Integer>> pq = createPQ(frequencyMap);
		Node root = createTree(pq);
		setBits(root);
		System.out.println("Number of bits: " + numBits);
		System.out.println("Total number of bits if bit-string was fixed: " + totalBits);
	}

	private static void setBits(Node root) {
		if (root == null) {
			return;
		}
		setBitsHelper(root.getLeftChild(), "", "0");
		setBitsHelper(root.getRightChild(), "", "1");
	}

	private static void setBitsHelper(Node subRoot, String bitString, String bit) {
		if (subRoot == null) {
			return;
		}
		subRoot.setBitCode(bitString + bit);
		if (subRoot.getLeftChild() == null && subRoot.getRightChild() == null) {
			numBits += subRoot.getBitCode().length() * frequencyMap.get(subRoot.getEntry().getKey());
			totalBits += frequencyMap.get(subRoot.getEntry().getKey()) * 8;
		}
		
		setBitsHelper(subRoot.getLeftChild(), subRoot.getBitCode(), "0");
		setBitsHelper(subRoot.getRightChild(), subRoot.getBitCode(), "1");
	}

	private static Node createTree(PriorityQueue<Entry<String, Integer>> pq) {
		map = new HashMap<>();
		Entry<String, Integer> entry1 = null;
		Entry<String, Integer> entry2 = null;
		Entry<String, Integer> parent = null;
		Node n1 = null;
		Node n2 = null;
		Node n3 = null;
		
		while (pq.size() > 1) {
			entry1 = pq.poll();
			entry2 = pq.poll();
			if (map.containsKey(entry1.getKey())) { 
				n1 = map.get(entry1.getKey());
			} else { 
				n1 = new Node(entry1);
				map.put(entry1.getKey(), n1);
			}
			
			if (map.containsKey(entry2.getKey())) {
				n2 = map.get(entry2.getKey());
			} else {
				n2 = new Node(entry2);
				map.put(entry2.getKey(), n2);
			}

			parent = new AbstractMap.SimpleEntry<String, Integer>(entry1.getKey() + entry2.getKey(), entry1.getValue() +
					entry2.getValue());

			n3 = new Node(parent, n1, n2);
			n3.setLeftChild(n1);
			n3.setRightChild(n2);
			
			map.put(parent.getKey(), n3);
			pq.offer(parent);
		}
		
		int maxFrequency = 0;

		for(Entry<String, Node> entry : map.entrySet()){ 
			if (entry.getValue().getEntry().getValue() > maxFrequency) {
				maxFrequency = entry.getValue().getEntry().getValue();
			} // end if statement
		} // end for loop
		
		return n3;
	}

	private static PriorityQueue<Entry<String, Integer>> createPQ(HashMap<String, Integer> map) {
		PriorityQueue<Map.Entry<String, Integer>> pq = 
				new PriorityQueue<Map.Entry<String, Integer>>(new Comparator<Map.Entry<String, Integer>>() {
					
					@Override
					public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
						return o1.getValue() - o2.getValue();
					}			
		});
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			pq.offer(entry);
		}
		return pq;
	}

	private static void countChars(String text) {
		frequencyMap = new HashMap<String, Integer>();
		for (int i = 0; i < text.length(); i++) {
			Character c = text.charAt(i);
			String cString = c.toString();
			
			Integer val = frequencyMap.get(cString);
			if (val != null) {
				frequencyMap.put(cString, new Integer(val + 1));
			} else {
				frequencyMap.put(cString, new Integer(1));
			}
		} // end for loop
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
