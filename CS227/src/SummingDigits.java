import java.util.Scanner;

public class SummingDigits {
	
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		while(n != 0) {
			while(n > 9) {
				int sum = 0;
				while(n > 0) {
					sum += n % 10;
					n = n / 10;
				}
				n = sum;
			}
			System.out.println(n);
			n = scan.nextInt();
		}
		scan.close();
	}
}
