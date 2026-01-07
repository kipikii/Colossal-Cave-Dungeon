package core;
import java.util.HashMap;

public class Entity {
    private String nameString;
    private int currHPInt;
    private int maxHPInt;
    private HashMap<Item, Integer> inventoryHashMap;

    public Entity(String name, int hp){
        this.nameString = name;
        this.currHPInt = hp;
        this.currHPInt = hp;
        this.inventoryHashMap = new HashMap<Item, Integer>();
    }

    public String getName(){return nameString;}
    public int getHP(){return currHPInt;}
    public int getMaxHP(){return maxHPInt;}
    public HashMap<Item, Integer> getInventory(){return inventoryHashMap;}

    public void setName(String name){this.nameString = name;}
    public void setHP(int hp){this.currHPInt = hp;}
    public void setMaxHP(int hp){this.maxHPInt = hp;}
    public void setInventory(HashMap<Item, Integer> inventory){this.inventoryHashMap = inventory;}

    public void updateInventory(Item item, int amount){
        if (inventoryHashMap.containsKey(item)){
            inventoryHashMap.put(item, (Integer)(inventoryHashMap.get(item)+amount));
        } else {
            inventoryHashMap.put(item, amount);
        }
        if (inventoryHashMap.get(item) < 0){
            inventoryHashMap.remove(item);
        }
    }
}
