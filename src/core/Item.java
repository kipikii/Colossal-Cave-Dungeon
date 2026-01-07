package core;
import java.util.ArrayList;
import java.util.List;

public class Item {
    private String nameString;
    private String descriptionString;
    public ArrayList<String> possibleNames;

    public Item(String name){
        this(name, String.format("There is a %s here.", name), new ArrayList<String>(List.of(name)));
    }

    public Item(String name, ArrayList<String> possible){
        this(name, String.format("There is a %s here.", name), possible);
    }

    public Item(String name, String desc, ArrayList<String> possible){
        nameString = name;
        descriptionString = desc;
        possibleNames = possible;
    }

    public String getName(){return nameString;}
    public String getDescription(){return descriptionString;}
    public ArrayList<String> getPossible(){return possibleNames;}

    public void setName(String name){nameString = name;}
    public void setDescription(String desc){descriptionString = desc;}
    public void setPossible(ArrayList<String> possible){possibleNames = possible;}
}
