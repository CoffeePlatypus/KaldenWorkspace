import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* A decision tree for predicting if Mushrooms are poisonous
 * @author Julia Froegel
 */
public class DecisionTree {
	private HashSet<Mushroom> trainingSet;
	private DecisionNode root;
	private boolean debug;
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
		trainingSet = ts;
		root = null;
		debug = false;
	}
	
	/* Builds tree structure and root for the DecisionTree
	 * @param n - size to use for the testing set
	 * @return root DecisionNode of tree
	 */
	public DecisionNode buildTree(int n) {
		HashSet<Mushroom> subset = getTestSubset(n); //TODO uncomment
		HashSet<Integer> atts = getAttributes();
		root = learning(subset, atts, subset);
		return root;
	}
	
	public boolean predictPoisonious(Mushroom mushy) {
		DecisionNode temp = root;
		while(!temp.isLeaf()) {
			temp = temp.getChild(mushy.getAttribute(temp.getAttrNum()));
		}
		return temp.isPoisonious();
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
		for(int i = 0; i<OPTS[attribute].length; i++) {
			char attrValue = OPTS[attribute][i];
			nodey.addChild(attrValue, learning(filterShrooms(examples,attribute,attrValue), atts, examples));
//			nodey.addChild(attrValue, learning(filterShrooms(examples,attribute,attrValue), new HashSet<Integer>(), new HashSet<Mushroom>()));
		}
		return nodey;
	}
	
	private HashSet<Mushroom> filterShrooms(HashSet<Mushroom> ex, int attr, char attrValue) {
		HashSet<Mushroom> shrooms = new HashSet<Mushroom>();
		Iterator<Mushroom> musherator = ex.iterator();
//		System.out.println("finding " + attr+" = "+attrValue);
		while(musherator.hasNext()) {
			Mushroom temp = musherator.next();
			if(temp.getAttribute(attr) == attrValue) {
				shrooms.add(temp);
//				System.out.println("\t"+temp);
			}
		}
		return shrooms;
	}
	
	/* Hopefully finds the most important attribute
	 * @param attrs
	 */
	private int findImportant(HashSet<Integer> attrs, HashSet<Mushroom> examples) {
		double [] entropies = new double[22];
		double [] gains = new double[22];
		Iterator<Integer> atterator = attrs.iterator();
		while(atterator.hasNext()) {
			int attr = atterator.next();
//			if(debug) System.out.println("Attr: "+attr);
			//// Count which mushrooms have which attributes
			int [][] attrDis = countAttrDistribution(attr, examples); 
			//// attrDis[x][0] = num with attrType; attrDis[x][1] =num with attrType that are poisonous
			double entropy = 0;
			double rem = 0;
			for(int i = 0; i<attrDis.length; i++) {
				if(debug) System.out.print("\t["+attrDis[i][1]+" | "+ (attrDis[i][0] - attrDis[i][1])+"] / "+attrDis[i][0] +" = ");
				if(attrDis[i][0] != 0) {
					double p = attrDis[i][1] / (double)attrDis[i][0];
					double pent = (p == 0? 0 : p * Math.log10(p));
					double nent = ( 1-p == 0 ? 0: (1-p) * Math.log10(1-p));
					entropy -= pent + nent;
					if(debug) {
						System.out.print(pent+nent);
						System.out.print("\t"+attrDis[i][0]/(double)examples.size()+"\n");
					}
					rem += (attrDis[i][0] / (double)examples.size()) * pent;
				}else {
//					System.out.println(0);
				}
				
			}
			if(debug) {
				System.out.println("Attr "+attr+" has entropy "+entropy);
				System.out.println("Attr "+attr+" has rem "+rem);
			}
			// store entropy and gain
			entropies[attr] = entropy;
			gains[attr] = 1 + rem;
		}
		if(debug) {
			printArray(entropies);
			printArray(gains);
		}		
		// find largest gain
		atterator = attrs.iterator();
		if(atterator.hasNext()) {
			int attr = atterator.next();
			double gain = gains[attr];
			while(atterator.hasNext()) {
				int temp = atterator.next();
				if(gains[temp] > gain) {
					gain = gains[temp];
					attr = temp;
				}
			}
			System.out.println("most important attr: "+attr);
			return attr;
		}else {
			System.out.println("No attrs error?");
			return -1; //error?
		}
	}
	
	/* Counts the attributeType distribution for an Attribute
	 * @param attr - int representing an Attribute
	 * @param ex -set of mushrooms
	 * @return - int[][] where [i][0] = num of mushrooms with attribute type && [i][1] = num of [i][0] that are poisonous  
	 */
	private int[][] countAttrDistribution(int attr, HashSet<Mushroom> ex) {
		Iterator<Mushroom> musherator = ex.iterator();
		HashMap<Character, Integer> charIntMap = new HashMap<Character, Integer>();
		for(int i = 0; i<OPTS[attr].length; i++) {
			charIntMap.put(OPTS[attr][i], i);
		}
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
//		System.out.println("Pcount: "+pcount+"\nEcount: "+ecount);
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
	
	/* make a testSet that is a subset of trainingSet
	 * @param n - size of testing subset to make
	 * @return set of mushrooms that is a subset of Testing Set
	 */
	private HashSet<Mushroom> getTestSubset(int n) {
		Iterator<Mushroom> seterator = trainingSet.iterator();
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
	
	public String toString() {
		String s = "";
		printTree(root);
		return s;
	}
	
	private String printTree(DecisionNode n) {
		if(n == null) return "";
		String s = n.toString();
		if(!n.isLeaf()) {
			for(int i = 0; i<n.numChildren(); i++) {
				s+= "\n\t- "+OPTS[n.getAttrNum()][i]+" - "+printTree(n.getChild(OPTS[n.getAttrNum()][i]));
			}
		}
		return s;
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
		
		public boolean isPoisonious() {
			return attribute == -2;
		}
		
		public int numChildren() {
			return children.size();
		}
		
		public int getAttrNum() {
			return attribute;
		}
		
		public DecisionNode getChild(char attribute) {
			return children.get(attribute);
		}
		
		public String toString() {
			String s = "";//"Printing Decision Node\n";
			if(isLeaf()) {
				s += attribute == -1? "Edible" : "Poisonious";
			}else {
				s += "Attr: "+attribute +"  --  ";
				s += "Num Children: "+children.size();
			}
			return s;
		}
			
	}
	
}
