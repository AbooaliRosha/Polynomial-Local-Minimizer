
public class SDGSS extends SteepestDescent {
	private final double _PHI_ = (1. + Math.sqrt(5)) / 2.; // phi ratio
	private double maxStep; // Armijo max step size
	private double minStep; // Armijo beta parameter
	private double delta; // Armijo delta parameter

	// constructors
	public SDGSS() {
		this.maxStep = 1.0;
		this.minStep = 0.001;
		this.delta = 0.001;
	}

	public SDGSS(double maxStep, double minStep, double delta) {
		this.maxStep = maxStep;
		this.minStep = minStep;
		this.delta = delta;
	}

	// getters
	public double getMaxStep() {
		return this.maxStep;
	}

	public double getMinStep() {
		return this.minStep;
	}

	public double getDelta() {
		return this.delta;
	}

	// setters
	public void setMaxStep(double a) {
		this.maxStep = a;
	}

	public void setMinStep(double a) {
		this.minStep = a;
	}

	public void setDelta(double a) {
		this.delta = a;
	}

	// other methods
	@Override
	public double lineSearch(Polynomial P, double[] x) // step size from GSS
	{
		double[] dir = new double[x.length]; //direction
		
		//finding the direction useing gradient
		for (int i = 0; i < x.length; i++) {
			dir[i] = -1 * P.gradientNorm(x);
		}
		double c = this.minStep + (this.maxStep - this.minStep) / _PHI_; //calculation c 

		return GSS(this.minStep, this.maxStep, c, x, dir, P);

	}

	public void set(double a, double e, int i) // set variables that are in SD class
	{
		setX0(e);
		setEps(a);
		setMaxIter(i);
	}

	public void print() // print parameters
	{
		System.out.println("GSS maximum step size: " + this.maxStep);
		System.out.println("GSS minimum step size: " + this.minStep);
		System.out.println("GSS delta: " + this.delta);
	}

	private double GSS(double a, double b, double c, double[] x, double[] dir, Polynomial P) {

		double[] a_ = new double[P.getN()]; // left
		double[] b_ = new double[P.getN()]; // right
		double[] c_ = new double[P.getN()]; // c value
		double[] d_ = new double[P.getN()]; // direction

		a_ = direction(P, x, a);
		b_ = direction(P, x, b);
		c_ = direction(P, x, c);
		d_ = P.gradient(x);

		while (Math.abs(b - a) > this.delta) { //while the interval is bigger than then delta, continue 

			if (Math.abs(c - a) < Math.abs(b - c)) // right interval is bigger
			{
				double y_val = b - ((b - c) / _PHI_);
				double[] y = new double[P.getN()];
				y = direction(P, x, y_val);

				// setting direction
				for (int i = 0; i < P.getN(); i++) {
					d_[i] = d_[i] * -1;
				}

				if (P.f(y) < P.f(b_) && P.f(y) < P.f(c_)) // min is in a-c
				{
					return GSS(c, b, y_val, x, d_, P);
				}

				if (P.f(y) > P.f(c_)) // min is in c-b
				{

					return GSS(a, y_val, c, x, d_, P);

				}

			} else if (Math.abs(c - a) > Math.abs(b - c))// left interval is larger
			{

				double y_val = a + (c - a) / _PHI_;
				double[] y = new double[P.getN()];
				y = direction(P, x, y_val);

				for (int i = 0; i < P.getN(); i++) {
					d_[i] = d_[i] * -1;
				}

				if (P.f(y) < P.f(a_) && P.f(y) < P.f(c_)) // min is in a-c
				{
					return GSS(a, c, y_val, x, d_, P);

				}

				if (P.f(y) > P.f(c_))// min is in c-b
				{
					return GSS(y_val, b, c, x, d_, P);
				}

			}

			if (P.f(c_) > P.f(a_) || P.f(c_) > P.f(b_)) // special case
			{
				if (P.f(a_) >= P.f(b_)) {
					return b;
				} else if (P.f(a_) < P.f(b_)) {
					return a;
				}
			}

		}

		return (b + a) / 2.0; // return midpoint

	}

}
