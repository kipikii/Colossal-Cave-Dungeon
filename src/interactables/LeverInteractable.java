package interactables;
import core.VariableManager;
import core.Interactable;
import java.util.ArrayList;
import java.util.List;
import core.Room;

public class LeverInteractable extends Interactable {
    private String boolString;
    private String interactOnDescString;
    private String interactOffDescString;
    
    public LeverInteractable(String name, ArrayList<String> possibleNames, String boolString){
        super(name, "There is a "+name+ " here.", possibleNames);
        this.boolString = boolString; 
        this.interactOffDescString = "You flip the lever from ON to OFF.";
        this.interactOnDescString = "You flip the lever from OFF to ON.";
    }

    public LeverInteractable(String name, String description, String interactOnDescription, String interactOffDescription, ArrayList<String> possibleNames, String boolString){
        super(name, description, possibleNames);
        this.boolString = boolString;
        this.interactOnDescString = interactOnDescription;
        this.interactOffDescString = interactOffDescription;
    }

    public void interact(){
        if (VariableManager.getBool(boolString)){
            System.out.println(interactOffDescString);
        } else {
            System.out.println(interactOnDescString);
        }
        VariableManager.flipBool(boolString);
    }

    public String toString(){
        return String.format("%s is %b", getName(), VariableManager.getBool(boolString));
    }

    public static void main(String[] args){
        Room r1 = new Room("Room 1");
        Room r2 = new Room("Room 2");
        LeverInteractable lever = new LeverInteractable("Lever", new ArrayList<>(List.of("Lever")), "Lever");
        VariableManager.instantiateNew("Lever", false);
        r1.getInteractables().add(lever);
        r1.connectRooms(r2, "n");
        r1.getConnectionRequirements().set(1, "Lever");
        Room.deployProbe(r1);
    }
}
