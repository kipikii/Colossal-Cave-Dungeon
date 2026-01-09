package core;

public class Vector3 {
    public int x;
    public int y;
    public int z;

    public Vector3(){
        this(0,0,0);
    }

    public Vector3(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString(){
        return String.format("<%d, %d, %d>", x, y, z);
    }

    // given a direction index according to Room.java
    public Vector3 translate(int index){
        switch (index) {
            case 0: return new Vector3(x, y+1, z);
            case 1: return new Vector3(x, y, z+1);
            case 2: return new Vector3(x+1, y, z);
            case 3: return new Vector3(x, y-1, z);
            case 4: return new Vector3(x, y, z-1);
            case 5: return new Vector3(x-1, y, z);
            default: 
                System.out.println("Translation error with Vector3, returning null");
                return null;
        }
    }
}
