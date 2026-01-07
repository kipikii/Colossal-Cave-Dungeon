package core;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import interactables.LeverInteractable;

public class Room {
    private String nameString;
    private ArrayList<Room> connectedRooms; // ArrayList length of six for Up, North, West, Down, South, East
    private ArrayList<String> requirementPassages;
    private ArrayList<Entity> inRoomEntities;
    private ArrayList<Item> inRoomItems;
    private ArrayList<Interactable> inRoomInteractables;
    private String descriptionString;

    public Room(String name){
        this(name, new ArrayList<Room>(Collections.nCopies(6, (Room) null)), new ArrayList<String>(Collections.nCopies(6, (String) null)),new ArrayList<Entity>(), new ArrayList<Item>(), new ArrayList<Interactable>(), "You're in " + name + ".");
        this.descriptionString = "You're in " + name + ".";
    }

    public Room(String name, String description){
        this(name, new ArrayList<Room>(Collections.nCopies(6, (Room) null)), new ArrayList<>(Collections.nCopies(6, (String) null)), new ArrayList<Entity>(), new ArrayList<Item>(), new ArrayList<Interactable>(), description);
    }

    public Room(String name, ArrayList<String> requirements){
        this(name, new ArrayList<Room>(Collections.nCopies(6, (Room) null)), requirements, new ArrayList<Entity>(), new ArrayList<Item>(), new ArrayList<Interactable>(), "You're in " + name + ".");
    }

    public Room(String name, ArrayList<Item> items, int one){
        this(name, new ArrayList<Room>(Collections.nCopies(6, (Room) null)), new ArrayList<String>(Collections.nCopies(6, (String) null)), new ArrayList<Entity>(), items, new ArrayList<Interactable>(), "");
    }

    public Room(String name, ArrayList<Room> connections, ArrayList<String> requirementPassages, ArrayList<Entity> entities, ArrayList<Item> items, ArrayList<Interactable> interactables, String description){
        this.nameString = name;
        this.connectedRooms = connections;
        this.requirementPassages = requirementPassages;
        this.inRoomEntities = entities;
        this.inRoomItems = items;
        this.inRoomInteractables = interactables;
        this.descriptionString = "You're in " + name + ". " + description;
    }

    public String getName(){return nameString;}
    public ArrayList<Room> getConnections(){return connectedRooms;}
    public ArrayList<String> getConnectionRequirements(){return requirementPassages;}
    public ArrayList<Entity> getEntities(){return inRoomEntities;}
    public ArrayList<Item> getItems(){return inRoomItems;}
    public ArrayList<Interactable> getInteractables(){return inRoomInteractables;}
    public String getDescription(){return descriptionString;}

    public void setName(String name){nameString = name;}
    public void setConnections(ArrayList<Room> rooms){connectedRooms = rooms;}
    public void setConnectionRequirements(ArrayList<String> booleans){requirementPassages = booleans;}
    public void setEntities(ArrayList<Entity> entities){inRoomEntities = entities;}
    public void setItems(ArrayList<Item> items){inRoomItems = items;}
    public void setInteractables(ArrayList<Interactable> interactables){inRoomInteractables = interactables;}
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

    // makes a Euclidean connection between two rooms
    public void connectRooms(Room other, int index){
        int otherIndex = (index + 3) % 6;
        if (this.getConnections().get(index) != null || other.getConnections().get(otherIndex) != null){
            System.out.println(String.format("Prevented override of established pathway between %s and %s",
        this.getName(), other.getName()));
        return;
        }
        this.getConnections().set(index, other);
        other.getConnections().set(otherIndex, this);
    }

    public void connectRooms(Room other, String direction){
        int index = directionToInt(direction);
        connectRooms(other, index);
    }

    // makes a potentially non-Euclidean connection between two rooms
    public void connectRooms(Room other, int index, int otherIndex){
        if (this.getConnections().get(index) != null || other.getConnections().get(otherIndex) != null){
            System.out.println(String.format("Prevented override of established pathway between %s and %s",
        this.getName(), other.getName()));
        return;
        }
        this.getConnections().set(index, other);
        other.getConnections().set(otherIndex, this);
    }

    public void connectRooms(Room other, String direction, String otherDirection){
        int index = directionToInt(direction);
        int otherIndex = directionToInt(otherDirection);
        connectRooms(other, index, otherIndex);
    }

    // these methods create one-way connections
    public void connectRoomsOneWay(Room other, String direction){
        int index = directionToInt(direction);
        this.getConnections().set(index, other);
    }

    public void connectRoomsOneWay(Room other, int index){
        this.getConnections().set(index, other);
    }

    // creates a passage that can only be opened under a boolean (VariableManager)
    public void connectRoomsConditional(Room other, int index, String booleanCond){
        int otherIndex = (index + 3) % 6;
        connectRooms(other, index);
        this.getConnectionRequirements().set(index, booleanCond);
        this.getConnectionRequirements().set(otherIndex, booleanCond);
    }

    public void connectRoomsConditional(Room other, String direction, String booleanCond){
        int index = directionToInt(direction);
        this.connectRoomsConditional(other, index, booleanCond);
    }

    // creates a potentially non-Euclidean passage that can only be opened under a boolean (VariableManager)
    public void connectRoomsConditional(Room other, int index, String booleanCond, int otherIndex){
        this.connectRooms(other, index, otherIndex);
        this.getConnectionRequirements().set(index, booleanCond);
        other.getConnectionRequirements().set(otherIndex, booleanCond);
    }

    public void connectRoomsConditional(Room other, String direction, String booleanCond, String otherDirection){
        this.connectRoomsConditional(other, directionToInt(direction), booleanCond, directionToInt(otherDirection));
    }

    public void addInteractable(Interactable interactable){
        this.getInteractables().add(interactable);
    }

    public String toString(){
        ArrayList<String> connectionNames = new ArrayList<String>();
        ArrayList<String> entityNames = new ArrayList<String>();
        ArrayList<String> itemNames = new ArrayList<String>();
        ArrayList<String> interactableNames = new ArrayList<String>();
        int index = 0;
        String directionString;
        for (Room room : this.getConnections()){
            if (room != null){
                directionString = intToDirection(index) + ": ";
                connectionNames.add(directionString + "\""+room.getName()+"\"");
            }
            index++;
        }
        for (Entity entity : this.getEntities()){entityNames.add(entity.getName());}
        for (Item item : this.getItems()){itemNames.add(item.getName());}
        for (Interactable interactable : this.getInteractables()){interactableNames.add(interactable.getName());}
        return String.format("\"%s\" is connected to %s, contains %s entities, %s interactables, %s items", this.getName(), connectionNames, entityNames, interactableNames, itemNames);
    }

    public static void deployProbe(Room startingRoom){
        System.out.println("You can quit at any time by typing \"quit\" into the console when the game says \"You're in...\".");
        Room currentRoom = startingRoom;
        String questionString;
        int loopingInt;
        ArrayList<String> availableOptionsArrayList;
        ArrayList<String> availableInteractsArrayList;
        ArrayList<String> availableItemsArrayList;
        String userInputString = "";
        int pathwayCount;
        int interactableCount;
        int itemCount;

        Entity playerEntity = new Entity("You", 10 );

        while (!userInputString.equals("quit")){
            pathwayCount = 0;
            interactableCount = 0;
            itemCount = 0;
            questionString = currentRoom.getDescription();
            loopingInt = 0;
            availableOptionsArrayList = new ArrayList<>();
            availableInteractsArrayList = new ArrayList<>();
            availableItemsArrayList = new ArrayList<>();
            // add available directions as options for selection as well as their shortened versions
            for (Room givenRoom : currentRoom.getConnections()){
                String req = currentRoom.getConnectionRequirements().get(loopingInt);
                if (givenRoom != null && (req == null || Boolean.TRUE.equals(VariableManager.getBool(req)))){
                    if (pathwayCount <= 0){
                        questionString += "\nAvailable directions: ";
                    }
                    availableOptionsArrayList.add(intToDirection(loopingInt));
                    availableOptionsArrayList.add(intToDirection(loopingInt).substring(0, 1));
                    questionString = questionString + intToDirection(loopingInt) + ", ";
                    pathwayCount++;
                }
                loopingInt++;
            }
            if (pathwayCount > 0){questionString = questionString.substring(0, questionString.length() - 2);}
            // check the number of interactables and add their contents for later
            for (Interactable interactable : currentRoom.getInteractables()){
                questionString += "\n"+interactable.getDescription();
                for (String option : interactable.getPossible()){
                    availableInteractsArrayList.add(option);
                }
                interactableCount++;
            }
            // do the same for items
            for (Item item : currentRoom.getItems()){
                questionString += "\n"+item.getDescription();
                for (String option : item.getPossible()){
                    availableItemsArrayList.add(option);
                }
                itemCount++;
            }
            questionString += "\nWhat would you like to do?";
            availableOptionsArrayList.add("quit");
            availableOptionsArrayList.addAll(List.of("i", "interact", "g", "get"));
            userInputString = Helpers.promptUser(questionString, availableOptionsArrayList, false, false);
            if (userInputString.equals("quit")){
                continue;
            // case for interaction
            } else if (userInputString.equals("i") || userInputString.equals("interact")){
                if (interactableCount == 1){
                    currentRoom.getInteractables().get(0).interact();
                } else if (interactableCount < 1) {
                    System.out.println("There isn't anything to interact with.");
                    continue;
                } else {
                    userInputString = Helpers.promptUser("Which object do you want to interact with?", availableInteractsArrayList, false, false);
                    // check each interactable in the room to see if they are the desired object, then interact
                    for (Interactable interactable : currentRoom.getInteractables()){
                        if (interactable.getPossible().contains(userInputString)){interactable.interact();}
                    }
                }
            // case for item pickups
            } else if (userInputString.equals("g") || userInputString.equals("get")){
                if (itemCount == 1){
                    System.out.println(String.format("You took the %s.", currentRoom.getItems().get(0).getName()));
                    playerEntity.updateInventory(currentRoom.getItems().get(0), 1);
                    currentRoom.getItems().remove(currentRoom.getItems().get(0));
                } else if (itemCount < 1){
                    System.out.println("There isn't anything to pick up.");
                    continue;
                } else {
                    userInputString = Helpers.promptUser("What would you like to pick up?",
                    availableItemsArrayList, false, false);
                    int index = 0;
                    for (Item item : currentRoom.getItems()){
                        if (item.getPossible().contains(userInputString)){
                            playerEntity.updateInventory(item, 1);
                            currentRoom.getItems().remove(currentRoom.getItems().get(index));
                        }
                        index++;
                    }
                }
            // else must be a direction
            } else {
                currentRoom = currentRoom.getConnections().get(directionToInt(userInputString));
            }
            System.out.println();
        }
        
    }

    private static void runHouseTest(){
        Room garageRoom = new Room("Garage");
        Room kitchenRoom = new Room("Kitchen");
        Room livingRoom = new Room("Living Room");
        Room southEntrance = new Room("South End of the Entrance");
        Room centerEntrance = new Room("Center of the Entrance");
        Room northEntrance = new Room("North End of the Entrance");
        Room formalLivingRoom = new Room("Formal Living Room");
        Room tvRoom = new Room("TV Room");
        Room officeRoom = new Room("Office");
        Room diningRoom = new Room("Dining Room");
        Room masterBedRoom = new Room("Master Bedroom", "There is a small flap in the ceiling above you that leads up to the attic.");
        Room masterCloset = new Room("Master Bedroom Closet");
        Room masterBathRoom = new Room("Master Bathroom");
        Room laundryRoom = new Room("Laundry Room");
        Room guestHallRoom = new Room("Guest Room Hall");
        Room guestHallBathRoom = new Room("Guest Hall Bathroom", "There is a small flap in the ceiling above you that leads up to the attic.");
        Room couchRoom = new Room("Couch Room");
        Room guestBedRoom = new Room("Guest Bedroom");
        Room guestBedRoomBathRoom = new Room("Guest Bedroom Bathroom");
        Room childBedRoom = new Room("Child's Bedroom");
        Room childBedRoomBathRoom = new Room("Child's Bathroom");
        Room childCloset = new Room("Child's Closet");
        Room westAtticRoom = new Room("West End of the Attic", "There is a small hole below you that leads to the Master Bathroom.");
        Room eastAtticRoom = new Room("East End of the Attic", "There is a small hole below you that leads to the Guest Hall Bathroom.");
        
        Room secretBasement = new Room("Secret Basement", new ArrayList<Item>(List.of(
            new Item("Photo", "A picture of someone dear rests on the ground.", 
            new ArrayList<String>(List.of("Photo"))))), 1);

        LeverInteractable secretBasementLever = new LeverInteractable("Switch", "There is a switch on the wall.", 
        "You flip the switch from OFF to ON. You hear something shift in the distance.", 
        "You flip the switch from ON to OFF. You hear something shift in the distance.", 
        new ArrayList<String>(List.of("Switch", "Pull Switch", "Use Switch", "Flip Switch", "Lever", "Pull Lever", "Use Lever", "Flip Lever")),
        "Basement Door");

        southEntrance.connectRooms(centerEntrance, "n");
        southEntrance.connectRooms(tvRoom, "w");
        southEntrance.connectRooms(formalLivingRoom, "e");
        formalLivingRoom.getConnections().set(1, kitchenRoom);
        centerEntrance.connectRooms(officeRoom, "w");
        centerEntrance.connectRooms(northEntrance, "n");
        centerEntrance.getConnections().set(5, formalLivingRoom);
        northEntrance.connectRooms(masterBedRoom, "w", "s");
        northEntrance.connectRooms(kitchenRoom, "e");
        northEntrance.connectRooms(diningRoom, "n");
        masterBedRoom.connectRooms(masterCloset, "w", "n");
        masterBedRoom.connectRooms(masterBathRoom, "n");
        masterBedRoom.connectRooms(westAtticRoom, "u");
        kitchenRoom.connectRooms(garageRoom, "s");
        kitchenRoom.connectRooms(laundryRoom, "e");

        laundryRoom.addInteractable(secretBasementLever);

        kitchenRoom.connectRooms(livingRoom, "n");
        livingRoom.connectRooms(guestHallRoom, "e");
        livingRoom.connectRooms(childBedRoom, "n");
        diningRoom.connectRooms(livingRoom, "e");
        guestHallRoom.connectRooms(couchRoom, "n");
        guestHallRoom.connectRooms(guestHallBathRoom, "e");
        guestHallRoom.connectRooms(guestBedRoom, "s");
        guestHallBathRoom.connectRooms(eastAtticRoom, "u");
        guestBedRoom.connectRooms(guestBedRoomBathRoom, "e", "s");
        childBedRoom.connectRooms(childBedRoomBathRoom, "e");
        childBedRoomBathRoom.connectRooms(childCloset, "s");
        childCloset.connectRoomsConditional(secretBasement, "d", "Basement Door");
        westAtticRoom.connectRooms(eastAtticRoom, "e");
        deployProbe(kitchenRoom);
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Helpers.addInputScanner(scan);
        runHouseTest();
    }
}
