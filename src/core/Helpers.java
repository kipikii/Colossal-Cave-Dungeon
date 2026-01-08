package core;

import java.util.ArrayList;
import java.util.Scanner;

public class Helpers {
    public static Scanner scan;

    public static void addInputScanner(Scanner scanner){
        scan = scanner;
    }

    public static String promptUser(String question, ArrayList<String> allowed, boolean caseSensitive, boolean correctToOriginal){
        ArrayList<String> modifiedAllowed = new ArrayList<String>();
        for (String eachString : allowed){
            modifiedAllowed.add(new String(eachString));
        }
        if (!caseSensitive){
            for (int i = 0; i < modifiedAllowed.size(); i++){
                String string = modifiedAllowed.get(i);
                modifiedAllowed.set(i, string.toLowerCase());
            }
        } 
        String userInput = "";
        while (!modifiedAllowed.contains(userInput)){
            if (userInput.equals("")){
                System.out.println("\n"+question);
            }
            System.out.print("> ");
            userInput = scan.nextLine();
            if (!caseSensitive){userInput = userInput.toLowerCase();}
        }
        if (correctToOriginal){
            if (!allowed.contains(userInput)){
                userInput = userInput.substring(0,1).toUpperCase() + userInput.substring(1);
            }
        }
        return userInput;
    }
}
