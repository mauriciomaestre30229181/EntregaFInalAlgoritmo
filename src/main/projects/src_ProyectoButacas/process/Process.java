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

    public static void iniArray(String[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                array[i] = "";
            }
        }
    }

    public static final String[] MOVIE_LISTINGS = {
            "Avatar", "Top gun: Maverick", "Avengers: End Game",
            "Oppenheimer", "Titanic", "Mission: Impossible",
            "Rapidos y Furiosos"
    };

    public static final String[] AGE_CLASSIFICATIONS = {
            "Adulto General => Precio: 3$", "Niño [Hasta los 14 años] =>  Precio: 2$", "Adulto de la Tercera Edad => Precio: 1$"
    };

}
