package utils;

import java.io.IOException;

public class IDInitializer {
    private static class IDHolder {
        private static final int[] allIDs = initializeIDs();

        private static int[] initializeIDs() {
            int[] array;
            try {
                array = FileUtils.readIDs();
            } catch (IOException e) {
                array = new int[] {1, 1, 1, 1};
                FileUtils.log(e);
            }

            return array;
        }
    }
    
    public static int getItemsGlobalID() {
        return IDHolder.allIDs[0];
    }
    public static int getProductsGlobalID() {
        return IDHolder.allIDs[1];
    }
    public static int getTasksGlobalID() {
        return IDHolder.allIDs[2];
    }
    public static int getProductlinesGlobalID() {
        return IDHolder.allIDs[3];
    }
}
