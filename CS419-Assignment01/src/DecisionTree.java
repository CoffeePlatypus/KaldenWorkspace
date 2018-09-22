import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DecisionTree {
	private HashSet<Mushroom> testingSet;
	private DecisionNode root;
	
	public DecisionTree(HashSet<Mushroom> ts) {
		testingSet = ts;
		root = null;
	}
		
	public DecisionNode buildTree(int n) {
		HashSet<Mushroom> subset = testingSet;//getTestSubset(int n); //TODO uncomment
		HashSet<Integer> atts = getAttributes();
		System.out.println(atts);
		root = learning(subset, atts, subset);
		System.out.println(root);
		return root;
	}
	
	private DecisionNode learning(HashSet<Mushroom> examples, HashSet<Integer> atts, HashSet<Mushroom> pexamples ) {
		if(examples.isEmpty()) return pluralityValue(pexamples);
		else if (hasSameClassification(examples)) return getClassification(examples);
		else if (atts.isEmpty()) return pluralityValue(pexamples);
		return new DecisionNode(-3);
		//TODO add recursion
	}
	
	private DecisionNode pluralityValue(HashSet<Mushroom> sub) {
		int pcount = 0;
		int ecount = 0;
		Iterator<Mushroom> musherator = sub.iterator();
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
	
	private DecisionNode getClassification(HashSet<Mushroom> shrooms) {
		Iterator<Mushroom> musherator = shrooms.iterator();
		if(musherator.hasNext()) {
			return new DecisionNode(musherator.next().isPoisonous()? -2 : -1);
		}
		return new DecisionNode(-3); // error?
	}
	
	private HashSet<Mushroom> getTestSubset(int n) {
		Iterator<Mushroom> seterator = testingSet.iterator();
		HashSet<Mushroom> sub = new HashSet<Mushroom>();
		int count = 0;
		while(seterator.hasNext() && count++ < n) {
			sub.add(seterator.next());
		}
		return sub;
	}
	
	private HashSet<Integer> getAttributes() {
		HashSet<Integer> atts = new HashSet<Integer>();
		for(int i = 0; i<22; i++) {
			atts.add(new Integer(i));
		}
		return atts;
	}
	
	public class DecisionNode{
		int attribute; // -1 edible -2 poisonious
		HashMap<Character,DecisionNode> children;
		
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
