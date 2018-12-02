
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
public class W10_ForeignExchange {	
	public static void main(String [] args) {
		HashMap<Integer, LinkedList<Integer>> reqs = new HashMap<Integer, LinkedList<Integer>>();
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		while(num != 0) {
			for(int i = 0; i<num; i++) {
				int origin = scan.nextInt();
				int dest = scan.nextInt();
				if(reqs.containsKey(dest) && reqs.get(dest).contains(origin)) {
					LinkedList<Integer> listy = reqs.get(dest);
					listy.removeFirstOccurrence(origin);
					if(listy.isEmpty()) {
						reqs.remove(dest);
					}
				}else {
					if(reqs.containsKey(origin)) {
						reqs.get(origin).add(dest);
					}else {
						LinkedList<Integer> newlist = new LinkedList<Integer>();
						newlist.add(dest);
						reqs.put(origin, newlist);
					}
				}
			}
			if(reqs.isEmpty()) {
				System.out.println("YES");
			}else {
				System.out.println("NO");
			}
			reqs = new HashMap<Integer, LinkedList<Integer>>();
			num = scan.nextInt();
		}
		scan.close();
	}
}