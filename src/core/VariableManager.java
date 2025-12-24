package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariableManager {
    private static Map<String, Boolean> boolMap = new HashMap<>();
    private static Map<String, Integer> intMap = new HashMap<>();

    public static Integer getInt(String name){
        return intMap.get(name);
    }

    public static Boolean getBool(String name){
        return boolMap.get(name);
    }

    public static void instantiateNew(String name, int number){
        intMap.put(name, Integer.valueOf(number));
    }

    public static void instantiateNew(String name, boolean bool){
        boolMap.put(name, Boolean.valueOf(bool));
    }

    public static void flipBool(String name){
        Set<String> set = boolMap.keySet();
        if (!set.contains(name)){
            instantiateNew(name, false);
        }
        boolMap.put(name, (Boolean)!boolMap.get(name));
    }

    public static void incrementInt(String name, int number){
        Set<String> set = intMap.keySet();
        if (!set.contains(name)){
            instantiateNew(name, 0);
        }
        intMap.put(name, (Integer)intMap.get(name)+number);
    }
}
