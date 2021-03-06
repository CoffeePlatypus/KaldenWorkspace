import java.util.Scanner;

public class W1_GroupReverse {

	public static void main(String[] args) {
		Scanner rin = new Scanner(System.in);
		int g = rin.nextInt();
		
		while(g != 0) {
			String s = rin.next();
			g = s.length() / g;
			for(int i = 0; i < s.length(); i += g) {
				System.out.print(reverse(s.substring(i, i + g)));
			}
			System.out.println();
			g = rin.nextInt();
		}
		rin.close();
	}
	
	public static String reverse(String s) {
		return (s.length() < 2) ? s : s.charAt(s.length() - 1) + reverse(s.substring(1, s.length()-1)) +s.charAt(0);
	}
}