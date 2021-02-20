//Sam McCormick
//TA: Oscar Perez
//The Guitar37  class uses code from  the guitar class to create 
//sounds based on keys pressed. 
import java.util.*;

public class Guitar37 implements Guitar {
    public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
    private GuitarString[] keys; //keeps track of the keys
    private int count; //counts how many time tic is called

    //constructs a guitar object with each of the notes. 
    public Guitar37()
    {
        keys = new GuitarString[KEYBOARD.length()];
        for(int i = 0; i < KEYBOARD.length(); i++)
        {
            keys[i] = new GuitarString(440 *(Math.pow(2, ((i - 24.0) / 12.0))));
        }
    }
    
    //checks that the note is on the keyboard.
    //@param keystroke - the provided key
    //true if valid, false if not.
    public boolean hasString(char keystroke)
    {
        return KEYBOARD.indexOf(keystroke) != -1; //goes to -1 if not a valid index.
    }

    //"plucks" the string for the input
    //@param keystroke - the key to play
    //throws IllegalArgumentException if character is not on keyboard
    public void pluck(char keystroke)
    {
        if(!hasString(keystroke))
        {
            throw new IllegalArgumentException();
        }
        keys[KEYBOARD.indexOf(keystroke)].pluck();
    }

    //performs the advancement of one "tic"
    public void tic()
    {  
        count++;
        for(int i = 0; i < KEYBOARD.length(); i++)
        {
            keys[i].tic();
        }
    }

    //returns how many tics have passed
    public int time()
    {
        return count;
    }

    //creates the conglommeration of all the sounds
    //returns the total sounds added
    public double sample()
    {
        double totalSound = 0.0;
        for(int i = 0; i < KEYBOARD.length(); i++)
        {
            totalSound += keys[i].sample();
        }
        return totalSound;
    }

    //plays the note given in the parameter by calling pluck, but will ignore pitches outside
    //of range 110 Hz to 880Hz inclusive.
    //@param sound - the note to call for
    public void playNote(int sound)
    {
        sound += 24;
        if(0 <= sound && sound < 38)
        {
            keys[sound].pluck();
        }
    }
}
