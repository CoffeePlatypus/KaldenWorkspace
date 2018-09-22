import java.util.Scanner;

public class W2_PolygonalPark {

	public static void main(String[] args) {
		Scanner rin = new Scanner(System.in);
		int tests = rin.nextInt();
		for(int i = 0; i<tests; i++) {
			int houses = rin.nextInt();
			double houseArea = 0;
			int width = 0;
			int height = 0;
			int cornerCount = 0;
			for(int j = 0; j<houses; j++) {
				String houseType = rin.next();
				int houseLength = rin.nextInt();
				if(cornerCount <= 1) {
					width += houseLength;
				}
				if(houseType.equals("T")) {
					double tri= Math.sqrt(Math.pow(houseLength, 2) + Math.pow(houseLength/2, 2));
					System.out.println("Tri area: " + tri);
					houseArea+=tri;
				}else {
					if(houseType.equals("C")) {
						cornerCount++;
						if(cornerCount == 3) {
							height += houseLength;
						}
					}
					houseArea += houseLength * 2; 
				}
				if(cornerCount == 2) {
					height += houseLength;
				}
			}
			System.out.println(height);
			System.out.println(width);
			System.out.println((height * width) - houseArea);
		}
		rin.close();
	}

}
