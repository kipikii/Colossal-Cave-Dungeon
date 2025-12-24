package core;
import java.util.ArrayList;

public class Entity {
    private String nameString;
    private int currHPInt;
    private int maxHPInt;
    private ArrayList<Item> inventoryArrayList;

    public Entity(String name, int hp){
        this.nameString = name;
        this.currHPInt = hp;
        this.currHPInt = hp;
        this.inventoryArrayList = new ArrayList<Item>();
    }

    public String getName(){return nameString;}
    public int getHP(){return currHPInt;}
    public int getMaxHP(){return maxHPInt;}
    public ArrayList<Item> getInventory(){return inventoryArrayList;}

    public void setName(String name){this.nameString = name;}
    public void setHP(int hp){this.currHPInt = hp;}
    public void setMaxHP(int hp){this.maxHPInt = hp;}
    public void setInventory(ArrayList<Item> inventory){this.inventoryArrayList = inventory;}

    
}
