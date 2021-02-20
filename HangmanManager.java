//Sam McCormick
//TA: Oscar Perez
//The HangmanManager class manages the words, guesses, and correct/incorrect answers
//the user has made for the evil Hangman game. 
import java.util.*;

public class HangmanManager 
{
    private Set<String> words;
    private Set<Character> guesses;
    private int guessesLeft;
    private String pattern; 

    //constructs a hangmanmanager object which takes a dictionary of words, a length the word should be, and 
    //the amount of incorrect guesses the player has. Will throw IllegalArgumentException if the word 
    //is less than 1 letter or they have less than 0 wrong guesses. 
    public HangmanManager(Collection<String> dictionary, int length, int max)
    {
        if(length < 1 || max < 0)
        {
            throw new IllegalArgumentException();
        }
        words = new TreeSet<String>();
        guesses = new TreeSet<Character>();
        guessesLeft = max;
        pattern = "";
        for(int i = 0; i < length; i++)
        {
            pattern += "-"; 
            if(i + 1 != length)
            {
                pattern += " ";
            }
        }
        for(String n : dictionary)
        {
            if(!(n.length() > length) && !(n.length() < length))
            {
                words.add(n);
            }
        }
    }

    //this returns the set of words that is being considered still by the program, 
    //as they fit the pattern so far.  
    public Set<String> words()
    {
        return words;
    }

    //this returns the number of incorrect guesses the player has left. 
    public int guessesLeft()
    {
        return guessesLeft;
    }

    //returns the characters that the player has guessed so far. 
    public Set<Character> guesses()
    {
        return guesses;
    }

    //returns the string of the pattern of the correct answer, with dashes excluding the characters that the 
    //player has not yet guessed. throws an IllegalStateException if there are no words available to guess. 
    public String pattern()
    {
        if(words.isEmpty())
        {
            throw new IllegalStateException();
        }
        return pattern;
    }

    //this method records that the player has made a guess, and throws an IllegalStateException if the amount
    //of incorrect guesses possible is less than 1, or if no words are possible. 
    //If they have guessed the letter before, an IllegalArgumentException is thrown. 
    //This method also works to group words on if they can be created with the current pattern. 
    //Also updates the current patter of words and words being considered, as well as reducing available guesses if
    //the user is incorrect. 
    //Returns the amount of times the guessed letter occurs in the answer after the player's guess. 
    public int record(char guess)
    {
        if(guessesLeft < 1 || words.isEmpty())
        {
            throw new IllegalStateException();
        }
        if(guesses.contains(guess))
        {
            throw new IllegalArgumentException();
        }
        guesses.add(guess);
        int maxSize = 0;
        Map<String, Set<String>> map = new TreeMap<String, Set<String>>();
        for(String n : words)
        {
            String currPattern = patternHelp(guess, n);
            if (!map.containsKey(currPattern)) //this is straight out of lecture
            {
                Set<String> familyWords = new TreeSet<String>();
                map.put(currPattern, familyWords);
            }
            map.get(currPattern).add(n);
        }
        for (String currPattern : map.keySet()) 
        {
            if (map.get(currPattern).size() > maxSize) 
            {
                maxSize = maxSizeCalculator(map, currPattern);
            }
        }    
        return countGuess(guess);
    } 


    //this method calculates and returns the maximum size of the options available
    //as well as updating the words being considered. 
    private int maxSizeCalculator(Map<String, Set<String>> map, String currPattern)
    {
        int maxSize = 0;
        words.clear();
        Set<String> wordsToAdd = map.get(currPattern);
        words.addAll(wordsToAdd);
        pattern = currPattern;
        return map.get(currPattern).size();
    }

    //this method returns the pattern after including the player's guessed letter. 
    private String patternHelp(char guess, String word)
    {
        int indexOfPattern = 0;
        String currPattern = "";
        for(int i = 0; i < word.length(); i++)
        {
            if(word.charAt(i) != guess)
            {
                currPattern += pattern.substring(indexOfPattern, indexOfPattern + 1);
            }
            else
            {
                currPattern += guess;
            }
            if(indexOfPattern + 1 < pattern.length())
            {
                currPattern += " ";
            }
            indexOfPattern += 2;
        }
        return currPattern;
    }

    //this method returns the amount of times the guessed character occurs in 
    //the correct answer possibilities. 
    private int countGuess(char guess)
    {
        int occurences = 0;
        for (int i = 0; i < pattern.length(); i++) 
        {
            if (pattern.charAt(i) == guess) 
            {
                occurences++;
            }
        }
        if (occurences == 0) 
        {
            guessesLeft--;
        }
        return occurences;
    }
}
