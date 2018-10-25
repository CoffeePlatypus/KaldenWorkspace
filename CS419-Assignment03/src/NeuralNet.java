import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNet {
	private Layer [] net;
	private ArrayList<Point> examples;
	
	public NeuralNet(int inputPointLen, int [] layerLens, ArrayList<Point> ex) {
		examples = ex;
		net = new Layer[layerLens.length];
		net[0] = new Layer(inputPointLen, layerLens[0]);
		for(int i = 1; i<layerLens.length; i++) {
			net[i] = new Layer(layerLens[i-1], layerLens[i]);
		}
	}
	
	public void backPropLearning() {
		Point p = examples.get(0);
		
		for(int i = 0; i<net.length; i++) {
			Layer l = net[i];
			System.out.println("get output");
			System.out.println(Arrays.toString(l.getOutput(p.getData())));
			System.out.println("\nget error");
			System.out.println(Arrays.toString(l.calcuateError(p.getClassification())));
			System.out.println(l);
			l.updateWeights();
			System.out.println(l);
		}
	}

}
