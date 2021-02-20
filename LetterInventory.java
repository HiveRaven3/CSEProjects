//Sam McCormick
//TA: Oscar Perez
//The LetterInventory class represents a set of methods and an object that alphebetizes 
//and keeps track of how many letters are in inputted strings. 

import java.util.*;

public class LetterInventory 
{
    public static final int ALPHA_COUNTER = 26; //number of letters in alphabet. 
    private int letterCount[]; 
    private int size;

    //post: constructs a LetterInventory of the letters (as lowercase) inputted in
    //      the String data. 
    public LetterInventory(String data)
    {
        data = data.toLowerCase();
        letterCount = new int[ALPHA_COUNTER];
        for(int i = 0; i < data.length(); i++)
        {
            if(data.charAt(i) >= 'a' && data.charAt(i) <= 'z')
            {
                size++;
                letterCount[(data.charAt(i) - 'a')]++;
            }
        }
    }

    //pre: throws exception of not a letter
    //post: returns the value of how many of the given char letter there are. 
    public int get(char letter)
    {
        letter = Character.toLowerCase(letter);
        if(letter >= 'a' && letter <= 'z')
        {
            return letterCount[letter - 'a'];
        }
        throw new IllegalArgumentException();
    }

    //pre: exception if not both a letter AND positive value.
    //post: sets the amount of the parameter char letter to the int value given.
    public void set(char letter, int value)
    {
        letter = Character.toLowerCase(letter);
        if(letter < 'a' || letter > 'z' || value < 0)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            size = size - letterCount[letter - 'a'];
            letterCount[letter - 'a'] = value;
            size += value;
        }
    }

    //post: returns the character count of the String. 
    public int size()
    {
        return size;
    }

    //post: returns true if empty, returns false if not empty
    public boolean isEmpty()
    {
        return size() == 0;
    }

    //pre: takes in a LetterInventory to add on. 
    //post: returns a new LetterInventory that contains all the letters of 
    //      the two original inventories.  
    public LetterInventory add(LetterInventory other)
    {
        LetterInventory plusInven = new LetterInventory("");
        for(int i = 0; i < ALPHA_COUNTER; i++)
        {
            plusInven.letterCount[i] = this.letterCount[i] + other.letterCount[i];
        }
        plusInven.size = this.size + other.size;
        return plusInven;
    }
    
    //pre: takes in a LetterInventory to subtract. 
    //post: returns a new LetterInventory of the letters left over after  
    //      removing letters from the current inventory based on the paramter other 
    //      LetterInventory. Returns null if the amount of letters left over in any place 
    //      would be negative. 
    public LetterInventory subtract(LetterInventory other)
    {
        LetterInventory minusInven = new LetterInventory("");
        for(int i = 0; i < ALPHA_COUNTER; i++)
        {
            minusInven.letterCount[i] = this.letterCount[i] - other.letterCount[i];
            if(minusInven.letterCount[i] < 0 )
            {
                return null;
            }
        }
        minusInven.size = this.size - other.size;
        return minusInven;
    }

    //post: returns the organized string between 2 brackets. 
    public String toString()
    {
        String theString = "";
        for(int i = 0; i < ALPHA_COUNTER; i++)
        {
            for(int j = 0; j < letterCount[i]; j++)
            {
                theString += (char) ('a' + i);
            }
        }
        return "[" + theString + "]";
    }
}
