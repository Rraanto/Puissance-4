package Tools;

public class Functions {
    public static int countInstances(int element, int[] table) {
        int numberOfInstances = 0;
        for(int i=0; i<table.length; i++) {
            if (table[i] == element) {
                numberOfInstances++;
            }
        }
        return numberOfInstances;
    }

    // appends an element (integer) to a given array of same type (integer)
    public static void appendElement(int element, int[] table) {
        
    }

    //tests 
    public static void main(String[] args) {
        int[] list = {1, 2, 3, 4};
        System.out.println(list.length);
        appendElement(5, list);
        System.out.println(list.length);
    }
} 
