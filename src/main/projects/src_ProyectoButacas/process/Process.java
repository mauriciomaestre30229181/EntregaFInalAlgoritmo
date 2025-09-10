package main.projects.src_ProyectoButacas.process;

public class Process {
    public static void iniMatrix(int[][][] cinema) {
        if (cinema != null) {
            for (int i = 0; i < cinema.length; i++) {
                for (int j = 0; j < cinema[0].length; j++) {
                    for (int k = 0; k < cinema[0][0].length; k++) {
                        cinema[i][j][k] = 0;
                    }
                }
            }
        }
    }

    public static void iniArray(int[][] revenueArray) {
        if (revenueArray != null) {
            for (int i = 0; i < revenueArray.length; i++) {
                for (int j = 0; j < revenueArray[0].length; j++) {
                    revenueArray[i][j] = 0;
                }
            }
        }
    }


    public static void iniArray(int[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                array[i] = 0;
            }
        }
    }

}
