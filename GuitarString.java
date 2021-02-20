//Sam McCormick
//TA: Oscar Perez
//The GuitarString class works as a reference to what a single 
//guitar string would do at a given frequency. 
import java.util.*;

public class GuitarString {
private Queue<Double> ring;
public static final double DECAY_FACTOR = 0.996;

    //this constructs the guitarString object at a given frequency, with a ring buffer.
    //if the ring buffer would be of a size smaller than 0 or tyhe frequency is less than or 
    //equal to 0, an IllegalArgumentException is thrown. 
    public GuitarString(double frequency)
    {
        ring = new LinkedList<Double>();
        int sizeN = (int)(Math.round(StdAudio.SAMPLE_RATE / frequency));
        if(frequency <= 0 || sizeN < 2)
        {
            throw new IllegalArgumentException();
        }
        for(int i = 0; i <sizeN; i++)
        {
            ring.add(0.0);
        }

    }
    
    //also constructs a guitarString, but with all the values of the ring buffer given in 
    //an array as a parameter. if there are less than 2 elements, an IllegalArgumentException is thrown.  
    public GuitarString(double[] init)
    {
        ring = new LinkedList<Double>();
        if(init.length < 2)
        {
            throw new IllegalArgumentException();
        }
        for(double n : init)
        {
            ring.add(n);
        }
    }

    //this method simulates "plucking" the guitar string, and fills the ring buffer with values from -0.5 to 0.5.
    public void pluck()
    {
        for(int i = 0; i < ring.size(); i++)
        {
            double randomN = Math.random() - 0.5; 
            //Math.random returns inclusive 0.0 to 1.0
            ring.remove();
            ring.add(randomN);
        }
    }

    //this method applioes the "Karplus-Strong" update one time. Multiplies the average of the first two values 
    //by the decay factor, and deletes the first value.
    public void tic()
    {
        double firstVal = ring.remove();
        double secondVal = ring.peek();
        double average = ((firstVal + secondVal) / 2); 
        ring.add(average * DECAY_FACTOR);
    }

    //this returns the first value of the ring buffer as a double. 
    public double sample()
    {
        return ring.peek();
    }
}
