import java.util.ArrayList;
import java.util.Collections;

public class Room {
    private String nameString;
    private ArrayList<Room> connectedRooms; // ArrayList length of six for Up, North, West, Down, South, East
    private ArrayList<Entity> inRoomEntities;
    private ArrayList<Item> inRoomItems;
    private String descriptionString;

    public Room(String name){
        this(name, new ArrayList<Room>(Collections.nCopies(6, (Room) null)), new ArrayList<Entity>(), new ArrayList<Item>(), "You're in " + name + ".");
    }
    
    public Room(String name, ArrayList<Room> connections){
        this(name, connections, new ArrayList<Entity>(), new ArrayList<Item>(), "You're in " + name + ".");
    }

    public Room(String name, ArrayList<Room> connections, ArrayList<Entity> entities, ArrayList<Item> items, String description){
        this.nameString = name;
        this.connectedRooms = connections;
        this.inRoomEntities = entities;
        this.inRoomItems = items;
        this.descriptionString = description;
    }

    public String getName(){return nameString;}
    public ArrayList<Room> getConnections(){return connectedRooms;}
    public ArrayList<Entity> getEntities(){return inRoomEntities;}
    public ArrayList<Item> getItems(){return inRoomItems;}
    public String getDescription(){return descriptionString;}

    public void setName(String name){nameString = name;}
    public void setConnections(ArrayList<Room> rooms){connectedRooms = rooms;}
    public void setEntities(ArrayList<Entity> entities){inRoomEntities = entities;}
    public void setItems(ArrayList<Item> items){inRoomItems = items;}
    public void setDescription(String description){descriptionString = description;}

    public static int directionToInt(String direction){
        int index;
        direction = direction.toLowerCase();
        switch(direction){
            case "u":
            case "up":
                index = 0;
                break;
            case "n":
            case "north":
                index = 1;
                break;
            case "w":
            case "west":
                index = 2;
                break;
            case "d":
            case "down":
                index = 3;
                break;
            case "s":
            case "south":
                index = 4;
                break;
            case "e":
            case "east":
                index = 5;
                break;
            default:
                return -1;
        }
        return index;
    }

    public static String intToDirection(int number){
        switch (number){
            case 0:
                return "Up";
            case 1:
                return "North";
            case 2:
                return "West";
            case 3:
                return "Down";
            case 4:
                return "South";
            case 5:
                return "East";
            default:
                System.out.println("Invalid integer used in intToDirection");
                return "INVALID INTEGER";
        }
    }

    public void connectRooms(Room other, int index){
        int otherIndex = (index + 3) % 6;
        this.getConnections().set(index, other);
        other.getConnections().set(otherIndex, this);
    }

    public void connectRooms(Room other, String direction){
        int index = directionToInt(direction);
        int otherIndex = (index + 3) % 6;
        this.getConnections().set(index, other);
        other.getConnections().set(otherIndex, this);
    }

    public void connectRoomsDirect(Room other, int index, int otherIndex){
        this.getConnections().set(index, other);
        other.getConnections().set(otherIndex, this);
    }

    public void connectRoomsDirect(Room other, String direction, String otherDirection){
        int index = directionToInt(direction);
        int otherIndex = directionToInt(otherDirection);
        this.getConnections().set(index, other);
        other.getConnections().set(otherIndex, this);
    }

    public String toString(){
        ArrayList<String> connectionNames = new ArrayList<String>();
        int index = 0;
        String directionString;
        for (Room room : this.getConnections()){
            if (room != null){
                switch (index) {
                    case 0:
                        directionString = "Up: ";
                        break;
                    case 1:
                        directionString = "North: ";
                        break;
                    case 2:
                        directionString = "West: ";
                        break;
                    case 3:
                        directionString = "Down: ";
                        break;
                    case 4:
                        directionString = "South: ";
                        break;
                    case 5:
                        directionString = "East: ";
                        break;
                    default:
                        continue;
                }
                connectionNames.add(directionString + "\""+room.getName()+"\"");
            }
            index++;
        }
        return String.format("\"%s\" is connected to %s, and contains %s", this.getName(), connectionNames, this.getEntities());
    }

    public static void deployPlayer(Room startingRoom){
        Room currentRoom = startingRoom;
        Question asker;
        String questionString;
        int loopingInt;
        ArrayList<String> availableDirectionsArrayList;
        String userInputString = "sample";
        while (!userInputString.equals("quit")){
            questionString = currentRoom.getDescription()+"\nAvailable directions: ";
            loopingInt = 0;
            availableDirectionsArrayList = new ArrayList<>();
            for (Room givenRoom : currentRoom.getConnections()){
                if (givenRoom != null){
                    availableDirectionsArrayList.add(intToDirection(loopingInt));
                    questionString = questionString + intToDirection(loopingInt) + ", ";
                }
                loopingInt++;
            }
            questionString = questionString.substring(0, questionString.length() - 2);
            questionString += "\nWhich way would you like to go?";
            availableDirectionsArrayList.add("quit");
            asker = new Question(questionString, availableDirectionsArrayList);
            userInputString = asker.promptUser();
            if (userInputString.equals("quit")){continue;}
            currentRoom = currentRoom.getConnections().get(directionToInt(userInputString));
        }
        
    }

    public static void main(String[] args){
        Room garageRoom = new Room("Garage");
        Room kitchenRoom = new Room("Kitchen");
        Room livingRoom = new Room("Living Room");
        Room southEntrance = new Room("South-end of the Entrance");
        Room centerEntrance = new Room("Center of the Entrance");
        Room northEntrance = new Room("North-end of the Entrance");
        Room formalLivingRoom = new Room("Formal Living Room");
        Room tvRoom = new Room("TV Room");
        Room officeRoom = new Room("Office");
        Room diningRoom = new Room("Dining Room");
        Room masterBedRoom = new Room("Master Bedroom");
        Room masterCloset = new Room("Master Bedroom Closet");
        Room masterBathRoom = new Room("Master Bedroom");
        Room laundryRoom = new Room("Laundry Room");
        Room guestHallRoom = new Room("Guest Room Hall");
        Room guestHallBathRoom = new Room("Guest Hall Bathroom");
        Room couchRoom = new Room("Couch Room");
        Room guestBedRoom = new Room("Guest Bedroom");
        Room guestBedRoomBathRoom = new Room("Guest Bedroom Bathroom");
        Room childBedRoom = new Room("Child's Bedroom");
        Room childBedRoomBathRoom = new Room("Child's Bathroom");
        Room childCloset = new Room("Child's Closet");

        southEntrance.connectRooms(centerEntrance, "n");
        southEntrance.connectRooms(tvRoom, "w");
        southEntrance.connectRooms(formalLivingRoom, "e");
        formalLivingRoom.getConnections().set(1, kitchenRoom);
        centerEntrance.connectRooms(officeRoom, "w");
        centerEntrance.connectRooms(northEntrance, "n");
        centerEntrance.getConnections().set(5, formalLivingRoom);
        northEntrance.connectRooms(masterBedRoom, "w");
        northEntrance.connectRooms(kitchenRoom, "e");
        northEntrance.connectRooms(diningRoom, "n");
        masterBedRoom.connectRoomsDirect(masterCloset, "e", "n");
        masterBedRoom.connectRooms(masterBathRoom, "n");
        kitchenRoom.connectRooms(garageRoom, "s");
        kitchenRoom.connectRooms(laundryRoom, "e");
        kitchenRoom.connectRooms(livingRoom, "n");
        livingRoom.connectRooms(guestHallRoom, "e");
        livingRoom.connectRooms(childBedRoom, "n");
        diningRoom.connectRooms(livingRoom, "e");
        guestHallRoom.connectRooms(couchRoom, "n");
        guestHallRoom.connectRooms(guestHallBathRoom, "e");
        guestHallRoom.connectRooms(guestBedRoom, "s");
        guestBedRoom.connectRoomsDirect(guestBedRoomBathRoom, "e", "s");
        childBedRoom.connectRooms(childBedRoomBathRoom, "e");
        childBedRoomBathRoom.connectRooms(childCloset, "s");

        deployPlayer(southEntrance);
    }
}
