package core;
import java.util.ArrayList;

public class Interactable {
    private String nameString;
    private String percieveDescString;
    private ArrayList<String> possibleNames;
    
    public Interactable(String name, ArrayList<String> possibleNames){
        this(name, String.format("There is a %s here.", name), possibleNames);
    }

    public Interactable(String name, String description, ArrayList<String> possibleNames){
        this.nameString = name;
        this.percieveDescString = description;
        this.possibleNames = possibleNames;
    }

    public String getName(){return nameString;}
    public String getDescription(){return percieveDescString;}
    public ArrayList<String> getPossible(){return possibleNames;}

    public void setName(String name){nameString = name;}
    public void setDescription(String description){percieveDescString = description;}
    public void setPossible(ArrayList<String> possible){possibleNames = possible;}

    public void interact(){
        System.out.println("You interact with the "+nameString+". Nothing happens.");
    }

    public static void main(String[] args){
        
    }
}
