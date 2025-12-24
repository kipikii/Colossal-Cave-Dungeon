package core;
public class Item {
    private String nameString;

    public Item(String name){
        nameString = name;
    }

    public String getName(){return nameString;}

    public String setName(){return nameString;}
}
