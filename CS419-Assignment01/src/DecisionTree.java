import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* A decision tree for predicting if Mushrooms are poisonous
 * @author Julia Froegel
 */
public class DecisionTree {
	private HashSet<Mushroom> testingSet;
	private DecisionNode root;
	
	/* Constructs a new DecisionTree
	 * @param ts - set of mushrooms a subset of which will be used for construting the decision tree
	 */
	public DecisionTree(HashSet<Mushroom> ts) {
		testingSet = ts;
		root = null;
	}
	
	/* Builds tree structure and root for the DecisionTree
	 * @param n - size to use for the testing set
	 * @return root DecisionNode of tree
	 */
	public DecisionNode buildTree(int n) {
		HashSet<Mushroom> subset = testingSet;//getTestSubset(int n); //TODO uncomment
		HashSet<Integer> atts = getAttributes();
		System.out.println(atts);
		root = learning(subset, atts, subset);
		System.out.println(root);
		return root;
	}
	
	/* Follows recursive decision tree learning algorithm for building tree
	 * @param examples - set of mushrooms examples to use for learning
	 * @param atts - set of attributes to use to divide the examples based on whether or not they are poisonious
	 * @param pexamples - set of mushrooms from the previous call?
	 */
	private DecisionNode learning(HashSet<Mushroom> examples, HashSet<Integer> atts, HashSet<Mushroom> pexamples ) {
		if(examples.isEmpty()) return pluralityValue(pexamples);
		else if (hasSameClassification(examples)) return getClassification(examples);
		else if (atts.isEmpty()) return pluralityValue(pexamples);
		return new DecisionNode(-3);
		//TODO add recursion
	}
	
	/* determines the pluralityValue of a set of Mushrooms
	 * @param shrooms - set of mushrooms
	 * @return DecisionNode with attribute value -1 if most of the mushrooms are edible or -2 if most are poisonous 
	 */
	private DecisionNode pluralityValue(HashSet<Mushroom> shrooms) {
		int pcount = 0;
		int ecount = 0;
		Iterator<Mushroom> musherator = shrooms.iterator();
		while(musherator.hasNext()) {
			Mushroom temp = musherator.next();
			if(temp.isPoisonous()) {
				pcount++;
			}else {
				ecount++;
			}
		}
		return new DecisionNode(pcount >= ecount ? -2 : -1);
	}
	
	/* determines if all the Mushrooms are in the same classification
	 * @param - shrooms list of mushrooms
	 * @return <CODE>true</CODE> if all the mushrooms have the same classification
	 */
	private boolean hasSameClassification(HashSet<Mushroom> shrooms) {
		int pcount = 0;
		int ecount = 0;
		Iterator<Mushroom> musherator = shrooms.iterator();
		while(musherator.hasNext()) {
			Mushroom temp = musherator.next();
			if(temp.isPoisonous()) {
				pcount++;
			}else {
				ecount++;
			}
		}
		return pcount == 0 || ecount == 0;
	}
	
	/* get classification of a set of mushrooms that all have the same classification
	 * @param shrooms - list of mushrooms
	 * @return DecisionNode with attribute value -1 if the mushrooms are edible or -2 if they are poisonous or -3 if error
	 */
	private DecisionNode getClassification(HashSet<Mushroom> shrooms) {
		Iterator<Mushroom> musherator = shrooms.iterator();
		if(musherator.hasNext()) {
			return new DecisionNode(musherator.next().isPoisonous()? -2 : -1);
		}
		return new DecisionNode(-3); // error?
	}
	
	/* make a testSet that is a subset of TestingSet
	 * @param n - size of testing subset to make
	 * @return set of mushrooms that is a subset of Testing Set
	 */
	private HashSet<Mushroom> getTestSubset(int n) {
		Iterator<Mushroom> seterator = testingSet.iterator();
		HashSet<Mushroom> sub = new HashSet<Mushroom>();
		int count = 0;
		while(seterator.hasNext() && count++ < n) {
			sub.add(seterator.next());
		}
		return sub;
	}
	
	/* make a set or integers that correspond to all of the attributes
	 */
	private HashSet<Integer> getAttributes() {
		HashSet<Integer> atts = new HashSet<Integer>();
		for(int i = 0; i<22; i++) {
			atts.add(i);
		}
		return atts;
	}
	
	/* node in decision tree
	 * attribute corresponds to mushroom attribute to determine which leaf to go to or toxicity if it is a leaf
	 * children are in a hashmap reached by possible attribute char values
	 */
	public class DecisionNode{
		int attribute; // -1 edible or -2 poisonous
		HashMap<Character,DecisionNode> children;
		
		/* Create DecisionNode for attribute a
		 */
		private DecisionNode(int a) {
			attribute = a;
			children = new HashMap<Character,DecisionNode>();
		}
		
		public boolean isLeaf() {
			return attribute < 0;
		}
		
		public String toString() {
			String s = "Printing Decision Node\n";
			if(isLeaf()) {
				s += attribute == -1? "Edible" : "Poisonious";
			}else {
				s += "Attribute: "+attribute +"\n";
				s += "Num Children: "+children.size();
			}
			return s;
		}
			
	}
	
}
