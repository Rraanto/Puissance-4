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
}
