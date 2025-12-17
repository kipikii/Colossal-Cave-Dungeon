import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Question {
    String question;
    ArrayList<String> allowed;
    private static Scanner scan = new Scanner(System.in);

    public Question(String question, ArrayList<String> allowed){
        this.question = question;
        this.allowed = allowed;
    }

    public String promptUser(){
        return promptUser(false, true);
    }

    public String promptUser(boolean caseSensitive){
        return promptUser(caseSensitive, true);
    }

    public String promptUser(boolean caseSensitive, boolean correctToOriginal){
        ArrayList<String> modifiedAllowed = new ArrayList<String>();
        for (String eachString : allowed){
            modifiedAllowed.add(new String(eachString));
        }
        if (!caseSensitive){
            int index = 0;
            for (String string : modifiedAllowed){
                modifiedAllowed.set(index++, string.toLowerCase());
            }
        } 
        String userInput = "";
        while (!modifiedAllowed.contains(userInput)){
            if (userInput.equals("")){
                System.out.println(question);
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

    // example usage
    public static void main(String[] args){
        Question q1 = new Question("What is your username?", new ArrayList<>(
            List.of("Kipikii", "Sordem")));
        String sample = q1.promptUser(false);
        System.out.println("Your username is " + sample);
    }
}