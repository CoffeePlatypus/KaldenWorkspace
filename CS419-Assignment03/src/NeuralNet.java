import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNet {
	private Layer[] net;
	private boolean debug = false;
	private int res;

	/* Neural net constructor
	 */
	public NeuralNet(int[] layerLens, int r) {
		res = r;
		net = new Layer[layerLens.length];
		net[0] = new Layer(new double[layerLens[0]]);
		for (int i = 1; i < layerLens.length; i++) {
			net[i] = new Layer(layerLens[i - 1], layerLens[i]);
		}
		if (debug) {
			System.out.println("Input lenght: " + layerLens[0]);
			System.out.println("Output lenght: " + layerLens[layerLens.length - 1]);
		}
	}
	
	/* Neural net constructor for loading neural net
	 */
	public NeuralNet(int numLayers, int r) {
		res = r;
		net = new Layer[numLayers];
	}
	
	public String getRes() {
		return res == 5 ? "0"+res : res+"";
	}
	
	public void setLayer(int i, Perceptron [] p) {
		net[i] = new Layer(p);
	}

	public void backPropLearning(ArrayList<Point> examples) {
		for (int j = 0; j < 1000*examples.size(); j++) {
			Point point = examples.get(j % examples.size());
//			System.out.println("Train Point: "+point);

			if (debug)
				System.out.println("put point into input layer");
			net[0] = new Layer(point.getData());

			for (int i = 1; i < net.length; i++) {
				Layer l = net[i];
				if (debug)
					System.out.printf("\nPropigating output down layer [%d] - with %d perceptrons\n", i,
							l.getOutputLength());
				l.calculateOutput(net[i - 1].getOutput());
				if (debug)
					System.out.println(l);
			}
			double maxError = net[net.length - 1].calcuateOutputError(point.getClassification());
//			System.out.println("\to:"+Arrays.toString(net[net.length-1].getOutput()));
//			System.out.println("\te:"+Arrays.toString(net[net.length-1].getErrors()));
			net[net.length - 1].deltaUpdateWeights(net[net.length - 2].getOutput());
			if (debug)
				System.out.println("--updated--\n" + net[net.length - 1]);

			for (int i = net.length - 2; i > 0; i--) {
				if (debug)
					System.out.printf("\nPropigate error backwards up layer [%d]\n", i);
				Layer l = net[i];
				l.calculateHiddenError(net[i + 1].getDeltas());

				l.deltaUpdateWeights(net[i-1].getOutput());
				if (debug)
					System.out.println("--updated--\n" + l);
			}
			if (maxError < .01) {
				return;
			}
		}
	}

	private int testPoint(Point p) {
		// System.out.println("Input Point: "+p);
		net[0] = new Layer(p.getData());
//		System.out.println("Point data : Output layer [0] :"+Arrays.toString(net[0].getOutput()));
		for (int i = 1; i < net.length; i++) {
			Layer l = net[i];
			l.calculateOutput(net[i - 1].getOutput());
//			System.out.printf("Output layer [%d] : %s\n",i,Arrays.toString(l.getOutput()));
		}
		double[] output = net[net.length - 1].getOutput();
//		System.out.println(p.getClassificationIndex()+ " : Output: " + Arrays.toString(output));
		double max = output[0];
		int index = 0;
		for (int i = 0; i < output.length; i++) {
			if (output[i] > max) {
				max = output[i];
				index = i;
			}
		}
		return index;
	}

	public double testData(ArrayList<Point> examples) {
		int[] stats = new int[net[net.length - 1].getOutputLength()];
		int[] pre = new int[net[net.length - 1].getOutputLength()];
		int[] correct = new int[net[net.length - 1].getOutputLength()];
		int[] wrong = new int[net[net.length - 1].getOutputLength()];
		double correctCount = 0.0;
		for (int i = 0; i < examples.size(); i++) {
			Point p = examples.get(i);
			stats[p.getClassificationIndex()]++;
			int classed = testPoint(p);
//			System.out.println("point: " + p + ", class: " + classed);
			if (p.getClassificationIndex() == classed) {
				correctCount++;
				correct[classed]++;
			}else {
				wrong[classed]++;
			}
			pre[classed]++;
//			System.out.println("================================");
		}
		System.out.println("Data stats    : " + Arrays.toString(stats));
		System.out.println("Guessed total : " + Arrays.toString(pre));
		System.out.println("Guessed right : " + Arrays.toString(correct));
		System.out.println("Guessed wrong : " + Arrays.toString(wrong));
		System.out.println("Accuracy: " + correctCount / examples.size() * 100 + "%");

		return (correctCount / examples.size()) * 100;
	}
	
	public String write() {
		String s = res+"\n"+net.length+"\n"+net[0].getOutputLength()+"\n";
		 for(int i = 1; i<net.length; i++) {
			 s += net[i].write();
		 }
		return s;
	}

}
