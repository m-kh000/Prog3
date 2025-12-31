package utils;

import java.io.IOException;

public class IDInitializer {
    private static int[] allIDs;
    
    static {
        try {
            allIDs = FileUtils.readIDs();
        } catch (IOException e) {
            allIDs = new int[] {1, 1, 1, 1};
            FileUtils.log(e);
        }
    }

    public static int getItemsGlobalID() {
        return allIDs[0];
    }
    public static int getProductsGlobalID() {
        return allIDs[1];
    }
    public static int getTasksGlobalID() {
        return allIDs[2];
    }
    public static int getProductlinesGlobalID() {
        return allIDs[3];
    }
}
