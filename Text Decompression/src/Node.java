import java.util.Map.Entry;

public class Node {
	public Entry<String, Integer> keyVal;
	public Node leftChild;
	public Node rightChild;
	public String bitCode;
	
	public Node() {
		keyVal = null;
		leftChild = null;
		rightChild = null;
		bitCode = "";
	}
	
	public Node(Entry<String, Integer> entry) {
		keyVal = entry;
		leftChild = null;
		rightChild = null;
		bitCode = "";
	}
	
	public Node(Entry<String, Integer> entry, Node n1, Node n2) {
		keyVal = entry;
		leftChild = n1;
		rightChild = n2;
		bitCode = "";
	}
	
	public Entry<String, Integer> getEntry() { return keyVal; }
		
	public void setLeftChild(Node lc) { leftChild = lc; }
	
	public Node getLeftChild() { return leftChild; }
	
	public void setRightChild(Node rc) { rightChild = rc; }

	public Node getRightChild() { return rightChild; }

	public void setBitCode(String bc) {	bitCode = bc; }
	
	public String getBitCode() { return bitCode; }
}
