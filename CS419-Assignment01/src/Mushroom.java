
public class Mushroom {
	private char [] attributes;
	private boolean isPoisonous;
	
	public Mushroom(String line) {
		attributes = new char[22];
		for(int i = 0; i<22; i++) {
			attributes[i] = line.charAt(i * 2);
		}
		isPoisonous = line.charAt(44) == 'p';
	}
	
	public boolean isPoisonous() {
		return isPoisonous;
	}
	
	public char getAttribute(int attribute) {
		return attributes[attribute];
	}
	
	public String toString() {
		String s = isPoisonous ? "Poisonous :" : "Edible    :";
		for(int i = 0; i<22; i++) {
			s += " " + attributes[i];
		}
		return s;
	}
}
