import java.util.ArrayList;

public class Stats 
{
	public double doubleAve(ArrayList<Double> a) 
	{
		double sum = 0;
		for(int i=0;i<a.size();i++) 
		{
			sum += a.get(i);
		}
		return sum / a.size();
	}
	public double intAve(ArrayList<Integer> a) 
	{
		double sum = 0;
		for(int i=0;i<a.size();i++) 
		{
			sum += a.get(i);
		}
		return sum / a.size();
		
	}
	public double doubleMax(ArrayList<Double> a) 
	{
		double max = a.get(0);  
        
        for (int i = 0; i < a.size(); i++) 
        {  
              
           if(a.get(i) > max) 
           {
        	   max = a.get(i);
           }
                 
        }  
        return max;
	}
	public int intMax(ArrayList<Integer> a) 
	{
        int max = a.get(0);  
        
        for (int i = 0; i < a.size(); i++) 
        {  
              
           if(a.get(i) > max) 
           {
        	   max = a.get(i);
           }
                 
        }  
        return max;
	}
	public double doubleMin(ArrayList<Double> a) 
	{
        double min = a.get(0);  
        
        for (int i = 0; i < a.size(); i++) 
        {  
              
           if(a.get(i) < min) 
           {
        	   min = a.get(i);
           }
                 
        }  
        return min;
	}
	public int intMin(ArrayList<Integer> a) 
	{
        int min = a.get(0);  
        
        for (int i = 0; i < a.size(); i++) 
        {  
              
           if(a.get(i) < min) 
           {
        	   min = a.get(i);
           }
                 
        }  
        return min;
	}
	public double doubleSD(ArrayList<Double> a) 
	{
		double mean = doubleAve(a);
		double sum = 0;
		
		for(int i = 0;i<a.size();i++) 
		{
			 sum += (Math.pow((a.get(i)-mean),2));
		}
		
		int n = a.size() - 1;
		double final_r = Math.sqrt(sum / n);
		
		return final_r;
		
	}
	public double intSD(ArrayList<Integer> a) 
	{
		double mean = intAve(a);
		double sum = 0;
		
		for(int i = 0;i<a.size();i++) 
		{
			sum += (Math.pow((a.get(i)-mean),2));
		}
		
		int n = a.size() - 1;
		double final_r = Math.sqrt(sum / n);
		
		return final_r;
	}
	

}
