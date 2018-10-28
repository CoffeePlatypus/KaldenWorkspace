import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNet {
	private Layer [] net;
	private ArrayList<Point> examples;
	private boolean debug = false;
	
	public NeuralNet(int inputPointLen, int [] layerLens, ArrayList<Point> ex) {
		examples = ex;
		net = new Layer[layerLens.length];
		net[0] = new Layer(inputPointLen, layerLens[0]);
		for(int i = 1; i<layerLens.length; i++) {
			net[i] = new Layer(layerLens[i-1], layerLens[i]);
		}
		if (debug) {
			System.out.println("Input lenght: "+ inputPointLen);
			System.out.println("Output lenght: "+layerLens[layerLens.length -1]);
		}
	}
	
	public void backPropLearning() {
		for (int j = 0; j <1000; j++) {
			Point point = examples.get(j%examples.size());
			
			if (debug) System.out.println("put point into input layer");
			net[0].calculateOutput(point.getData());
			if (debug) System.out.println(net[0]);
			
			for(int i = 1; i<net.length; i++) {
				Layer l = net[i];
				if(debug) System.out.printf("\nPropigating output down layer [%d] - with %d perceptrons\n", i, l.getOutputLength());
				l.calculateOutput(net[i-1].getOutput());
				if(debug) System.out.println(l);
			}
			
			double maxError = net[net.length-1].calcuateOutputError(point.getClassification());
			net[net.length-1].updateWeights();
			if (debug) System.out.println("--updated--\n"+net[net.length-1]);
			
			for(int i = net.length-2; i>=0; i--) {
				if (debug) System.out.printf("\nPropigate error backwards up layer [%d]\n",i);
				Layer l = net[i];
				l.calculateHiddenError(net[i+1].getErrors());
	
				l.updateWeights();
				if (debug) System.out.println("--updated--\n"+l);
			}
			System.out.printf("Iteration [%d] Max Errror: %f\n", j,maxError);
			if(maxError < .01 ) {
				return;
			}
		}
	}
	
	private boolean testPoint(Point p) {
		System.out.println(p);
		net[0].calculateOutput(p.getData());
		System.out.println("Ouput layer 1 "+net[0]);
		for(int i = 1; i<net.length; i++) {
			Layer l = net[i];
			System.out.println(l);
			l.calculateOutput(net[i-1].getOutput());
		}
//		net[net.length-1].calculateOutput(net[net.length-2].getOutput());
		double [] output = net[net.length-1].getOutput();
		System.out.println(Arrays.toString(output));
		double max = output [0];
		int index = 0;
		for(int i = 0; i<output.length; i++) {
			if(output[i]> max) {
				max = output[i];
				index = i;
			}
		}
		System.out.println(max+" : "+index);
		return p.getClassification()[index] == 1.0;
	}
	
	public double testData() {
		System.out.println("Test Data...");
		double correctCount = 0.0;
		for(int i = 0; i<2; i++) {
			Point p = examples.get(i);
			if(testPoint(p)) {
				System.out.println("correct");
				correctCount++;
			}else {
				System.out.println("incorrect");
			}
		}
		
		System.out.println("Accuracy: "+correctCount/examples.size());
		
		return (correctCount/examples.size())*10;
	}

}
