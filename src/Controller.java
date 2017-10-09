
public class Controller {

	private Neuron[][] n;
	private double[][] w;
	private static final double phi = 0.01;
	private static final double alpha = 0.1;
	private static final double lambda = alpha / phi;

	/*
	 * Initialize network with custom and random weights
	 */

	public Controller(int out, int in, double... weights) {
		n = new Neuron[2][];
		n[0] = new Neuron[in];
		n[1] = new Neuron[out];
		w = new double[in][];
		for(int i = 0; i < in; i++) {
			n[0][i] = new Neuron();
			w[i] = new double[out];
			for(int j = 0; j < out; j++) {
				if(weights.length > (i * out + j)) {
					w[i][j] = weights[i * out + j];
				} else {
					w[i][j] = Math.random();
				}
			}
		}
		for(int i = 0; i < out; i++) {
			n[1][i] = new Neuron(Math.random());
		}
	}

	public double[] think(double[] input) {
		double[] fin = new double[n[1].length];
		for(int i = 0; i < input.length; i ++) {
			n[0][i].value = input[i];
		}
		for(int i = 0; i < n[1].length; i++) {
			n[1][i].value = 0;
			for (int j = 0; j <= w[0].length; j++) {
				n[1][i].value += n[0][j].value * w[j][i] - n[1][i].theta;
			}
			n[1][i].value = step(n[1][i].value);
			fin[i] = n[1][i].value;
		}
		for(int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				w[i][j] += phi * n[1][j].value * (lambda * n[0][i].value - w[i][j]);
			}
		}
		return fin;
	}

	private double step(double value) {
		return value > 0 ? 1 : 0;
	}

	@Override
	public String toString() {
		String fin = "";
		for(int i = 0; i < n[0].length; i++) {
			fin += "Input Neuron " + i + "a: " + n[0][i] + "\n";
			for(int j = 0; j < w[i].length; j++) {
				fin += i + "a -> " + j + "b : " + w[i][j] + "\n";
			}
		}
		for(int i = 0; i < n[1].length; i++) {
			fin += "Output Neuron " + i + "b: " + n[1][i] + "\n";
		}
		return fin;
	}
}
