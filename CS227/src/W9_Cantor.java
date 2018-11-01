
public class W9_Cantor {
	
	public static void main(String [] args) {
	
		int n = 5;
		int count = n;
		for(int i = n; i < 2+n; i++) {
			System.out.println("----------");
			for(int j = i-1; j >= 1 ; j--) {
				System.out.println(count++ +" : "+j +"/"+(i-j));
			}
		}
	}
}
