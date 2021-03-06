import java.util.Scanner;

public class W3_LeapBirthdays {

	public static void main(String[] args) {
		Scanner rin = new Scanner(System.in);
		int t = rin.nextInt();
		for(int i = 0; i < t; i++) {
			int day = rin.nextInt();
			int month = rin.nextInt();
			int year = rin.nextInt();
			int qyear = rin.nextInt();
			if(day == 29 && month == 2 && isLeapYear(year)) {
				//born on leap year
				int count = 0;
				for(int q = year + 4; q <= qyear; q+=4) {
					if(isLeapYear(q)) {
						count++;
					}
				}
				System.out.println("Case "+(i+1)+": "+count);
			}else {
				// not born on leap year
				System.out.println("Case "+(i+1)+": "+(qyear - year));
			}
		}
		rin.close();
	}
	
	public static boolean isLeapYear(int year) {
		if(year % 400 == 0) return true;
		else if (year % 100 == 0) return false;
		else if (year % 4 == 0) return true;
		else return false;
	}
}
