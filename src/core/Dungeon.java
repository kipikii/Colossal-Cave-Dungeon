package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Probe {
    public static ArrayList<Probe> probeList = new ArrayList<Probe>();

    private int lifespanInt;
    private int eggsInt;
    private Room currentRoom;
    private Dungeon dungeon;
    private int id;
    private float eggChance;

    private static int roomId = 0;
    private static int globalProbeId = 0;

    Probe(Dungeon dungeon, Room startRoom){
        this(dungeon, startRoom, 3, 0, .5f);
    }

    Probe(Dungeon dungeon, Room startRoom, int lifespan, int egg, float eggChance){
        this.lifespanInt = lifespan;
        this.eggsInt = egg;
        this.dungeon = dungeon;
        this.currentRoom = startRoom;
        this.id = globalProbeId;
        this.eggChance = eggChance;
        globalProbeId++;
        probeList.add(this);
    }

    public static ArrayList<Probe> getProbes(){return probeList;}
    public int getLifespan(){return lifespanInt;}
    public int getEggCount(){return eggsInt;}
    public Room getRoom(){return currentRoom;}
    public Dungeon getDungeon(){return dungeon;}
    public int getId(){return id;}
    public float getEggChance(){return eggChance;}

    public void setLifespan(int num){lifespanInt = num;}
    public void setEggCount(int num){eggsInt = num;}
    public void setRoom(Room room){currentRoom = room;}
    public void setDungeon(Dungeon dungeon){this.dungeon = dungeon;}
    public void setEggChance(float eggChance){this.eggChance = eggChance;}

    public static void createProbe(Dungeon dungeon, Room startRoom){
        new Probe(dungeon, startRoom);
    }

    public String toString(){
        return String.format("ID: %d, Room: %s, Life: %d, Eggs: %d", id, getRoom().getName(), lifespanInt, eggsInt);
    }

    public static void createProbe(Dungeon dungeon, Room startRoom, int lifespan, int egg){
        new Probe(dungeon, startRoom, lifespan, egg, .5f);
    }

    public void step(){
        ArrayList<Integer> navOptionIntegers = new ArrayList<Integer>();
        ArrayList<Room> connections = this.currentRoom.getConnections();
        for (int index = 0; index < connections.size(); index++){
            if (connections.get(index) == null){
                navOptionIntegers.add(index);
            }
        }
        if (navOptionIntegers.isEmpty()){return;}
        int randInt = (int)(Math.random() * navOptionIntegers.size());
        Room roomToAdd = new Room(""+roomId);
        roomToAdd.position = currentRoom.position.translate(randInt);
        boolean changed = false;
        for (Room room : dungeon.getRooms()){
            if (room.position.equals(roomToAdd.position)){
                roomToAdd = room;
                changed = true;
            }
        }
        if (!changed){roomId++;}
        currentRoom.connectRooms(roomToAdd, navOptionIntegers.get(randInt));
        // lay eggs with chance
        if ((Math.random() >= .5 || lifespanInt <= eggsInt) && eggsInt > 0 && currentRoom != dungeon.getStartRoom()){
            eggsInt--;
            createProbe(dungeon, currentRoom, lifespanInt, 0);
        }
        this.currentRoom = roomToAdd;
        lifespanInt--;
        // probe dies if lifespan is 0
        if (this.lifespanInt <= 0){
            getProbes().remove(this);
        }
    }
}

public class Dungeon {
    private Room startRoom;
    private ArrayList<Room> roomList;

    public Dungeon(){
        this.startRoom = new Room("Dungeon Entrance");
        this.startRoom.position = new Vector3();
        this.roomList = new ArrayList<Room>(List.of(this.startRoom));
    }

    public Room getStartRoom(){return startRoom;}
    public ArrayList<Room> getRooms(){return roomList;}

    public static Dungeon generateDungeon(){
        return generateDungeon(1, 3, 0, .5f, 0);
    }
    
    public static Dungeon generateDungeon(int startingProbes, int probeLife, int probeEggs, float eggChance, int warps){
        Scanner scan = new Scanner(System.in);
        Helpers.addInputScanner(scan);

        Dungeon dungeon = new Dungeon();
        for (int i = startingProbes; i > 0; i--){
            Probe.createProbe(dungeon, dungeon.getStartRoom(), probeLife, probeEggs);
        }
        while (!Probe.probeList.isEmpty()){
            for (Probe temp : new ArrayList<>(Probe.getProbes())){
                temp.step();
                System.out.println(temp);
            }
        }

        return dungeon;
    }

    public static void main(String[] args){
        Dungeon dungeon = Dungeon.generateDungeon(3, 3, 1, 0.5f, 0);
        Room.deployPlayer(dungeon.getStartRoom());
    }
}
