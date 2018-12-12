import java.util.Scanner;

public class Drought {
	
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		
		int width = scan.nextInt();
		int n = scan.nextInt();
		int [] lengths = new int[n];
		for(int i = 0; i< n; i++) {
			lengths[i] = scan.nextInt();
		}
		
		int [] minh = new int [n];
		minh[0] = scan.nextInt();
		for(int i = 1; i<n+1; i++ ) {
			int t = scan.nextInt();
			if(minh[i-1] < t) {
				minh[i-1] = t;
			}
			if(i<n) {
				minh[i] = t;
			}
		}
		
		int sum = 0;
		for(int i = 0; i<n; i++) {
			sum += width * lengths[i] * minh[i];
		}
	}

}
