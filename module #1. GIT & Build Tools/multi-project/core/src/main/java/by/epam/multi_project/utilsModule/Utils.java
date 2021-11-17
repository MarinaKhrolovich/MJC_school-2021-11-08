package by.epam.multi_project.utilsModule;

import createLibraryUtils.Library;

public class Utils {

    public static boolean isAllPositiveNumbers(String... str) {

        for (String st : str) {

            if (!Library.isPositiveNumber(st)) {

                return false;
            }
        }

        return true;

    }
}
