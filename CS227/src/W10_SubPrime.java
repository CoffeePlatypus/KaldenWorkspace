import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class W10_SubPrime {
	public static void main(String[] args) throws FileNotFoundException {
		W10_SubPrime d = new W10_SubPrime();
		Scanner scan = new Scanner(new FileReader("subprime.in.txt"));
		int numBanks = scan.nextInt();
		int numDebters = scan.nextInt();
		int count = 0;
		while(numBanks!= 0 && numDebters != 0) {
			d.subprime(scan, numBanks, numDebters);
			numBanks = scan.nextInt();
			numDebters = scan.nextInt();

		}
		scan.close();
	}
	
	
	
	public void subprime(Scanner scan, int numBanks, int numDebters) {
		int [] reserves = new int[numBanks];
		for(int i = 0; i<numBanks; i++) {
			reserves[i] = scan.nextInt();
		}
		ArrayList<int[]> transactions = new ArrayList<int[]> ();
		for(int i = 0; i<numDebters; i++) {
			int [] t = {scan.nextInt() -1, scan.nextInt()-1, scan.nextInt()};
			transactions.add(t);
		}
		
		for(int i =0; i<transactions.size(); i++) {
			int [] t = transactions.get(i);
			if(reserves[t[0]] >= t[2]) {
				reserves[t[0]] -= t[2];
				reserves[t[1]] += t[2];
			}else if(isOwed(transactions.subList(i, transactions.size()), t[0])){
				int dif =  t[2] - reserves[t[0]];
				reserves[t[1]] += reserves[t[0]];
				reserves[t[0]] = 0;
				t[2] = dif;
				transactions.add(t);
			}else {
				System.out.println("N");
				return;
			}
		}
		System.out.println("S");
	}
	
	public boolean isOwed(List<int[]> trans, int debted) {
		for(int i = 0; i < trans.size(); i++) {
			int [] t = trans.get(i);
			if(t[1] == debted) {
				return true;
			}
		}
		return false;
	}
	
}
