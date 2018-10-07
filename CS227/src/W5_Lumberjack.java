import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class W5_Lumberjack {
	
	public static void main(String [] args) throws FileNotFoundException {
//		Scanner rin = new Scanner(System.in);
		Scanner rin = new Scanner(new FileReader("test.txt"));
		int n = rin.nextInt();
		System.out.println("Lumberjacks:");
		rin.next();
		for(int i = 0; i < n; i++) {
			String line = rin.nextLine();
			Scanner b = new Scanner(line);
			int t = 0;
			boolean asc = false;
			while(b.hasNextInt()) {
				int j = b.nextInt();
				if(t == 0) {
					if(b.hasNextInt()) {
//						System.out.println(j);
						int x = b.nextInt();
						asc = x >= j; 
						t = j;
					}else {
//						System.out.println("Empty break");
						break;
					}
				}else if((j < t && asc) || (j > t && !asc)) {
//					System.out.println(asc+ " "+j +" "+ t);
					break;
				}else {
					t = j;
				}
			}
			if(!b.hasNextInt()) {
				System.out.println("Ordered");
			}else {
				System.out.println("Unordered");
			}
			b.close();
		}
		rin.close();
	}
}
