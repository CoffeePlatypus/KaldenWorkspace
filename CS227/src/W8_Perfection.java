import java.util.ArrayList;
import java.util.Scanner;

public class W8_Perfection {

	public W8_Perfection() {
		
	}
	
	public static void main(String [] args) {
		W8_Perfection d = new W8_Perfection();
		Scanner rin = new Scanner(System.in);
		int n = rin.nextInt();
		System.out.println("PERFECTION OUTPUT");
		while(n != 0) {
			System.out.printf("%5d  %s\n",n, d.getPerfection(n, d.sum(d.findProperDivisors(n))));
			n = rin.nextInt();
		}
		rin.close();
		System.out.println("END OF OUTPUT");
	}
	
	public ArrayList<Integer> findProperDivisors(int n) {
		ArrayList<Integer> divisors = new ArrayList<Integer> ();
		if(n == 1) return divisors;
		divisors.add(1);
		for(int i = 2; i < Math.sqrt(n); i++) {
			if(n%i == 0) {
				divisors.add(i);
				divisors.add(n/i);
			}
		}
		return divisors;
	}
	
	public int sum(ArrayList<Integer> num) {
		int sum = 0;
		for(int i = 0; i<num.size(); i++) {
			sum+= num.get(i);
		}
		return sum;
	}
	
	public String getPerfection(int n, int sum) {
		if(n == sum) {
			return "PERFECT";
		}else if(sum < n) {
			return "DEFICIENT";
		}
		return "ABUNDANT";
	}
}
