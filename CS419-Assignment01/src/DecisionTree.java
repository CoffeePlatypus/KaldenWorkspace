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
	private final char[][] OPTS={{'b','c','x','f','k','s'},								// cap shape
								 {'f','g','y','s'},									// cap surface
								 {'n','b','c','g','r','p','u','e','w','y' },		// cap color
								 {'t','f'},											// bruises
								 {'a','l','c','y','f','m','n','p','s'},				// odor
								 {'a','d','f','n'},									// gill attachment
								 {'c','w','d'},										// gill spacing
								 {'b','n'},											// gill size
								 {'k','n','b','h','g','r','o','p','u','e','w','y'}, // gill color
								 {'e','t'},											// stalk shape
								 {'b','c','u','e','z','r'},							// stalk root
								 {'f','y','k','s'},									// stalk surface above ring
								 {'f','y','k','s'},									// stalk surface below ring
								 {'n','b','c','g','o','p','e','w','y'},				// stalk cover above ring
								 {'n','b','c','g','o','p','e','w','y'},				// stalk cover below ring
								 {'p','u'},											// veil type
								 {'n','o','w','y'},									// veil color
								 {'n','o','t'},										// ring number
								 {'c','e','f','l','n','p','s','z'},					// ring type
								 {'k','n','b','h','r','o','u','w','y'},				// spore print color
								 {'a','c','n','s','v','y'},							// population
								 {'g','l','m','p','u','w','d'}};					// habitat
	
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
		
		int attribute = findImportant(atts, examples);
		atts.remove(attribute);
		DecisionNode nodey = new DecisionNode(attribute);
		// comment out when you figure out attribute picking
//		for(int i = 0; i<OPTS[attribute].length; i++) {
//			char attrValue = OPTS[attribute][i];
//			nodey.addChild(attrValue, learning(filterShrooms(examples,attribute,attrValue), atts, examples));
//		}
		return nodey;
		//TODO add recursion
	}
	
	private HashSet<Mushroom> filterShrooms(HashSet<Mushroom> ex, int attr, char attrValue) {
		HashSet<Mushroom> shrooms = new HashSet<Mushroom>();
		Iterator<Mushroom> musherator = ex.iterator();
		while(musherator.hasNext()) {
			Mushroom temp = musherator.next();
			if(temp.getAttribute(attr) == attrValue) {
				shrooms.add(temp);
			}
		}
		return shrooms;
	}
	
	private int findImportant(HashSet<Integer> attrs, HashSet<Mushroom> examples) {
		//TODO find a way to find important attribute
		// Gain(a) = 1 - entropy?
		//build array of entropy of attributes
		double [] entropies = new double[22];
		double [] remainers = new double[22];
		double [] gain = new double[22];
		Iterator<Integer> atterator = attrs.iterator();
		while(atterator.hasNext()) {
			int attr = atterator.next();
			System.out.println("Attr: "+attr);
			int [][] attrDis = countAttrDistribution(attr, examples);
//			printArray(attrDis);
			double entropy = 0;
			double rem = 0;
			for(int i = 0; i<attrDis.length; i++) {
				System.out.print("\t["+attrDis[i][1]+" | "+ (attrDis[i][0] - attrDis[i][1])+"] / "+attrDis[i][0] +" = ");
				if(attrDis[i][0] != 0) {
					double p = attrDis[i][1] / (double)attrDis[i][0];
					double pent = (p == 0? 0 : p * Math.log10(p));
					double nent = ( 1-p == 0 ? 0: (1-p) * Math.log10(1-p));
					
					
					System.out.println(pent+nent);
					entropy -= pent + nent;
					
					System.out.println(attrDis[i][0]/(double)examples.size());
					rem += (attrDis[i][0] / (double)examples.size()) * pent;
				}else {
					System.out.println(0);
				}
				
//				System.out.println(Math.log(p));
				
			}
			System.out.println("Attr "+attr+" has entropy "+entropy);
			System.out.println("Attr "+attr+" has rem "+rem);
			entropies[attr] = entropy;
			remainers[attr] = rem;
			gain[attr] = 1 - rem;
			
			
		}
		printArray(entropies);
		printArray(remainers);
		printArray(gain);
		return 0;
	}
	
	private int[][] countAttrDistribution(int attr, HashSet<Mushroom> ex) {
//		System.out.println("Couting distribution of attribue");
		Iterator<Mushroom> musherator = ex.iterator();
		HashMap<Character, Integer> charIntMap = new HashMap<Character, Integer>();
		for(int i = 0; i<OPTS[attr].length; i++) {
			charIntMap.put(OPTS[attr][i], i);
		}
//		System.out.println("Mapped CHars");
		int [][] attrDis = new int[OPTS[attr].length][2];
		while(musherator.hasNext()) {
			
			Mushroom shroom = musherator.next();
//			System.out.println("shroom:" + shroom);
			int index = charIntMap.get(shroom.getAttribute(attr));
//			System.out.println("index of attr "+index);
			attrDis[index][0]++;
			if(shroom.isPoisonous()) attrDis[index][1]++;
		}
//		System.out.println("counted distribution");
		return attrDis;
	}
	
	public void printArray(double [] arr) {
		for(int i = 0; i<arr.length; i++ ) {
			if(i % 5 == 0) System.out.println();
			System.out.print(arr[i]+ ((arr[i] == 0)? "\t\t\t": "\t"));
		}
		System.out.println();
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
		
		public void addChild(char attr, DecisionNode n) {
			children.put(attr, n);
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
