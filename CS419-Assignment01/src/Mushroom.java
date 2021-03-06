/*
 * A Mushroom is a Mushroom with a char [] of attribues and boolean isPoisonous
 * @author Julia Froegel
 */
public class Mushroom {
	private char [] attributes;
	private boolean isPoisonous;
	
	/*
	 * Creates new mushroom
	 * @param line - string with 23 chars indicating various attributes of a mushrooom
	 */
	public Mushroom(String line) {
		attributes = new char[22];
		for(int i = 0; i<22; i++) {
			attributes[i] = line.charAt(i * 2);
		}
		isPoisonous = line.charAt(44) == 'p';
	}
	
	/*
	 * @return <CODE>true</CODE> if mushroom is poisonous
	 */
	public boolean isPoisonous() {
		return isPoisonous;
	}
	
	/*
	 * @return char [] of mushroom attributes
	 */
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
