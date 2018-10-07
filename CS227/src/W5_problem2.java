import java.util.Scanner;

public class W5_problem2 {
	
	public static void main(String [] args) {
//		Scanner rin = new Scanner(System.in);
//		int t = rin.nextInt();
		int t = 100;
		int n = 0;
		for(int i = 0; i<t; i++) {
			System.out.println(i+" - "+Math.pow(66, i)%100);
		}
	}
}
