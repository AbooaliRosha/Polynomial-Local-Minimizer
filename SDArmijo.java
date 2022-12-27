
public class SDArmijo extends SteepestDescent {
	private double maxStep; // Armijo max step size
	private double beta; // Armijo beta parameter
	private double tau; // Armijo tau parameter
	private int K; // Armijo max no. of iterations

	// constructors
	public SDArmijo() {
		this.maxStep = 1.0;
		this.beta = 0.0001;
		this.tau = 0.5;
		this.K = 10;

	}

	public SDArmijo(double maxStep, double beta, double tau, int K) {
		this.maxStep = maxStep;
		this.beta = beta;
		this.tau = tau;
		this.K = K;
	}

	// getters
	public double getMaxStep() {
		return this.maxStep;
	}

	public double getBeta() {
		return this.beta;
	}

	public double getTau() {
		return this.tau;
	}

	public int getK() {
		return this.K;
	}

	// setters
	public void setMaxStep(double a) {
		this.maxStep = a;
	}

	public void setBeta(double a) {
		this.beta = a;
	}

	public void setTau(double a) {
		this.tau = a;
	}

	public void setK(int a) {
		this.K = a;
	}

	// other methods
	@Override
	public double lineSearch(Polynomial P, double[] x) // Armijo line search
	{

		double alpha = this.maxStep;
		double gradient[];
		double left[] = new double[x.length];
		double right;
		int counter = 0;

		gradient = P.gradient(x);

		for (int i = 0; i < x.length; i++) {
			left[i] = x[i] - alpha * gradient[i];
		}

		right = P.f(x) - alpha * this.beta * Math.pow(P.gradientNorm(x), 2.0);

		while (!(P.f(left) <= right)) {
			alpha = alpha * this.tau;
			counter++;

			for (int i = 0; i < x.length; i++) {
				left[i] = x[i] - alpha * gradient[i];
			}

			right = P.f(x) - alpha * this.beta * Math.pow(P.gradientNorm(x), 2.0);

			if (counter == this.K) {
				return -1.507;

			}
		}

		return alpha;

	}

	public void set(double a, int i, double e) {
		setEps(a);
		setMaxIter(i);
		setX0(e);
	}

	public void print() // print parameters
	{

		System.out.println("Armijo maximum step size: " + this.maxStep);
		System.out.println("Armijo beta: " + this.beta);
		System.out.println("Armijo tau: " + this.tau);
		System.out.println("Armijo maximum iterations: " + this.K);
	}

}
