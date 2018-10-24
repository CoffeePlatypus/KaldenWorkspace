
public class NeuralNet {
	private Layer [] net;
	
	public NeuralNet(int outerInLen, int [] layerLens) {
		net = new Layer[layerLens.length];
		net[0] = new Layer(outerInLen, layerLens[0]);
		for(int i = 1; i<layerLens.length; i++) {
			net[i] = new Layer(layerLens[i-1], layerLens[i]);
		}
	}
}
