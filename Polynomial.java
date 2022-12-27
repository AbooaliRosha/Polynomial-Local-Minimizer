
public class Polynomial 
{
	private int n ; // no. of variables
	private int degree ; // degree of polynomial
	private double [][] coefs ; // coefficients
	
	// constructors
	public Polynomial ()
	{
	    this.n = 2;
		this.degree = 2;
	}
	public Polynomial ( int n , int degree , double [][] coefs )
	{
		this.n = n;
		this.degree = degree;
		this.coefs = coefs;
	}
	
	// getters
	public int getN () 
	{
		return n;
	}
	public int getDegree () 
	{
		return degree;
	}
	public double [][] getCoefs ()
	{
		return coefs;
	}
	
	// setters
	public void setN ( int a )
	{
	    this.n = a;
	}
	public void setDegree ( int a )
	{
	    this.degree = a;
	}
	public void setCoef ( int j , int d , double a )
	{
		this.coefs[j][d+1] = a;
	}
	
	 // other methods
	boolean isSet = false;
	public void init () // init member arrays to correct size
	{
		for(int i=0;i<this.n;i++)
	    {
	        for(int k=0; k<this.degree+1;k++)
	        {
	            this.coefs[i][k] = coefs[i][k];
	            
	        }
	    }
	    isSet = true;
	}
	public double f ( double [] x ) // calculate function value at point x
	{ 
		int counter = this.degree;
	    double sum = 0;
	    
	    for(int i = 0; i< this.n; i++)
	    {
	    	counter = this.degree;
	        for(int j = 0;j<this.degree+1;j++)
	        {
	            sum = sum +(coefs[i][j] * Math.pow(x[i],counter));
	            counter--;  
	        }
	    }
	    return sum;
	}
	
	public double [] gradient ( double [] x ) { // calculate gradient at point x
		double[] gradient_x = new double[this.n];
	
	    int counter = this.degree;
	    for(int i = 0; i< this.n; i++)
	    {
	        counter = this.degree;
	        for(int j = 0;j<this.degree;j++)
	        {
	            double dr = 0;
	            dr = coefs[i][j] * counter;
	            if(counter==1)
	            {
	                gradient_x[i] = gradient_x[i] + dr;
	            }
	            else
	            {
	            	 gradient_x[i] += dr * Math.pow(x[i],counter-1);
	            }
	            counter--;
	        }
	    }
	    return gradient_x;
	}
	
	public double gradientNorm ( double [] x ) // calculate norm of gradient at point x
	{
		double grNorm = 0;
	    double sum = 0;
	    
	    for(int i=0;i < x.length;i++)
	    {
	        sum = sum + Math.pow(x[i],2);
	        //grNorm = Math.sqrt(sum);
	        
	    }
	    grNorm = Math.sqrt(sum);
	    return grNorm;
	}
	public boolean isSet () // indicate whether polynomial is set
	{
		isSet = false;
	    return false;
	}
	public void print () //print polynomial
	{ 
		int counter = this.degree;
	    System.out.print("f(x) = ");
	    for(int i=0;i<this.n;i++)
	    {
	        counter = this.degree;
	        System.out.print("( ");
	        for(int k = 0; k <this.degree+1;k++)
	        {
	            
	            if(k==this.degree)
	            {
	                System.out.format("%4.2f",this.coefs[i][k]);
	                
	            }
	            else
	            {
	                 System.out.format("%4.2f",this.coefs[i][k]);
	                 System.out.print("x"+(i+1)+"^"+(counter)+" + ");
	                
	            }
	            counter--;
	            
	        }
	        
	        if(i==this.n-1)
	        {
	            System.out.print(" )");
	        }
	        else
	        {
	            System.out.print(" ) + ");
	        }
	
	    }
	       System.out.println() ;   
	        
    }

}
