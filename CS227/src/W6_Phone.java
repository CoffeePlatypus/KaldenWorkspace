import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class W6_Phone {
	HashMap<Character, Integer> keyboard;
	
	public W6_Phone() {
		keyboard = new HashMap<Character, Integer>();
		// 65 - 90
		char c = 65;
		for(int i = 2; i <10; i++) {
			for(int j = 0; j<3; j++) {
				if(c == 81) c++;
				keyboard.put(c++, i);
			}
		}
	}
	
	public static void main(String [] args) throws NumberFormatException, IOException {
		W6_Phone p = new W6_Phone();
		BufferedReader rin = new BufferedReader(new InputStreamReader(System.in));
		int tests = Integer.parseInt(rin.readLine());
		for(int i = 0; i < tests; i++) {
			rin.readLine();
			p.buildDB(rin);
		}
	}
	
	public void buildDB(BufferedReader rin) throws NumberFormatException, IOException {
//		HashMap<String,Integer> couunt;
// TODO finish
		int size = Integer.parseInt(rin.readLine());
		for(int i =0; i<size; i++) {
			String [] number = rin.readLine().split("");
			int count = 0;
			for(int j = 0; j<number.length; j++) {
				if(keyboard.containsKey(number[j].charAt(0))) {
					number[j] = keyboard.get(number[j].charAt(0)) +"";
					count++;
				}else if(number[j].equals("-") && count != 3) {
					number[j] = "";
				}else {
					count++;
				}
			}
			String num = String.join("", number);
			System.out.println(num);
		}
		
	}
}
