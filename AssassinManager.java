//Sam McCormick
//TA: Oscar Perez
//The AssassinManager class keeps track of the order of the assassins and members of
//the graveyard for the client program to access. 
import java.util.*;

public class AssassinManager {

    private AssassinNode killFront;
    private AssassinNode graveFront;
    
    //this constructs the AssassinManager object, using a list of names
    //that are bound each other to create the kill ring. 
    //there has to be at least one name and the name cannot be null,
    //or an IllegalArgumentException will be thrown. 
    public AssassinManager(List<String> names)
    {
        if(names.size() == 0 || names == null)
        {
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < names.size(); i++)
        {
            AssassinNode newAssassin = new AssassinNode(names.get(i));
            if(killFront == null)
            {
                killFront = newAssassin;
            } 
            else 
            {
                AssassinNode current = killFront;
                while(current.next != null)
                {
                    current = current.next;
                }
                current.next = newAssassin;
         }
      }
    }  


    //this method prints the current kill ring of survivors, with one relationship per line 
    //indented a small amount. 
    //this WILL report that someone is stalking themself if they are the winner. 
    public void printKillRing()
    {
        AssassinNode current = killFront;
        while(current != null)
        {
            if(current.next != null)
            {
                System.out.println("    " + current.name + " is stalking " + current.next.name);
            }
            else
            {
                System.out.println("    " + current.name + " is stalking " + killFront.name);        
            }
            current = current.next;
        }
    }

    //this method prints the members of the graveyard, with the same format as the killRing but
    //with "killed by" instead of "is stalking". 
    //if the graveyard is empty, there is nothing printed.
    //The order printed is the first killed is last on the list. 
    public void printGraveyard()
    {
        AssassinNode current = graveFront;
        while(current != null)
        {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }

    //this method returns true if the kill ring contains the given name. 
    //returns false if the name is not in the kill ring. Names are case insensitive. 
    public boolean killRingContains(String name)
    {
        AssassinNode current = killFront;
        return nameContained(current, name);
    }

    //this method returns true if the graveyard contains the given name. 
    //returns false if the name is not in the graveyard. Names are case insensitive. 
    public boolean graveyardContains(String name)
    {
        AssassinNode current = graveFront;
        return nameContained(current, name);
    }

    //this method returns true if there is only one person alive, and false
    //if there is more than one person.
    public boolean gameOver()
    {
        return killFront.next == null;
    }

    //this method returns the string name for the winner, and returns null if the game is not over.
    public String winner()
    {
        if(gameOver())
        {
            return killFront.name;
        }
        else
        {
            return null;
        }
    }

    //this method "kills" the person of the given string name, and moves them to the graveyard. 
    //The order of the graveyard follows as the first person killed at the end of the list. 
    //this method leaves the kill ring in the current order, with the dead person removed. 
    public void kill(String name)
    {
        if(gameOver())
        {
            throw new IllegalStateException(); //precedence per spec over argument exception
        }
        if(!killRingContains(name))
        {
            throw new IllegalArgumentException();
        } 
        
        AssassinNode killLead = killFront; 
        AssassinNode deadGuy = graveFront;
        if (killLead.name.equalsIgnoreCase(name)) //if the first person of ring is killed
        {
            deadGuy = killLead;
            while(killLead.next != null) 
            {
               killLead = killLead.next;
            }
            killFront = killFront.next;
        } 
        else 
        {
             while(!killLead.next.name.equalsIgnoreCase(name)) //if any other person is killed
            {
                killLead = killLead.next;
            }
            deadGuy = killLead.next;
            killLead.next = killLead.next.next;
        }
        deadGuy.next = graveFront;
        graveFront = deadGuy;
        deadGuy.killer = killLead.name;     
    }   
    
    //this method checks inside of each ring, depending on the parameter AssassinNode given, for the 
    //name given and retuirns true if it is there, false if not t
    private boolean nameContained(AssassinNode current, String name)
    {
        while(current != null)
        {
            if(current.name.equalsIgnoreCase(name))
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
