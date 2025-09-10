package main.projects.src_ProyectoButacas.process;

import java.io.IOException;

import cinema.Validate.Validate;

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

    public static void dataEntry(String[] movieSchedule) throws IOException {
        String text;
        System.out.println("\nEl cine esta abierto desde las 12:00 PM hasta las 10:30 PM");
        if (movieSchedule != null) {
            for (int i = 0; i < movieSchedule.length; i++) {
                text = "- Ingresa cual es el horario[" + (i+1) + "] disponible el dia de hoy. En formato (hh:mm PM): ";
                movieSchedule[i] = Validate.valSchedule(text);
            }
        }
    }
    
    public static void quantProcess(String[]usersQuant,int[][][] cinemaMatrix, String[] movieSchedule,int[]idRow) throws IOException {
        String text,name="";
        

        for (int i = 0; i < usersQuant.length; i++) {
            text = "- Ingrese el nombre del usuario [" + (i + 1) + "] en la fila: ";
            usersQuant[i] = Validate.valString(name,text);
            text="- Ingrese la cedula del usuario [" + (i + 1) + "] en la fila: ";
            idRow[i]= Validate.valInt(text,35000000,400000);
            purchaseProcessStart(cinemaMatrix,movieSchedule,usersQuant[i]);
        }

    }

    public static void purchaseProcessStart(int[][][] cinemaMatrix, String[] movieSchedule,String user) throws IOException {
        int band, x;
        int movieOption;
        String text, text2;
        
        if (cinemaMatrix != null && movieSchedule != null) {
            while (true) {
                text="Cliente en fila: "+user;
                System.out.println(text);
                text = "\n- Ingrese [0] para iniciar el proceso de compra del cliente / Ingrese [1] para pasar al siguiente: ";
                band = Validate.valInt(text, 1, 0);  // VALIDAR INT
                if (band == 0) {
                    System.out.println("\n_____________________INICIO DEL PROCESO DE COMPRA_____________________");
                    System.out.println("- Indique a cual de estos peliculas el cliente desea asistir");
                    x = 0;
                    for (int ctrl = 0; ctrl < cinemaMatrix.length; ctrl++) {
                        text2 = " + Ingrese [" + (++x) + "] Si desea asistir a la pelicula [" + MOVIE_LISTINGS[ctrl] + "].";
                        System.out.println(text2);
                    }
                    text = "- Ingrese el numero de la opcion escogida por el cliente: ";
                    movieOption = Validate.valInt(text, 7);
                    switch (movieOption) {
                        case 1, 2, 3, 4, 5, 6, 7:

                            purchaseProcessMiddle(cinemaMatrix, movieSchedule, movieOption);
                            break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    public static void purchaseProcessMiddle(int[][][] cinemaMatrix, String[] movieSchedule, int movieOption) throws IOException {
        String text, text2;
        int x = 0, showTimeOption;

        if (cinemaMatrix != null && movieSchedule != null) {
            for (int ctrl = 0; ctrl < cinemaMatrix[0].length; ctrl++) {
                text2 = " + Ingrese [" + (++x) + "] Si desea asistir a [" + MOVIE_LISTINGS[movieOption - 1] + "] en el HORARIO de las [" + movieSchedule[ctrl] + "].";
                System.out.println(text2);
            }
            text = "- Ingrese el numero de la opcion escogida por el cliente: ";
            showTimeOption = Validate.valInt(text, cinemaMatrix[0].length); //VALIDAR 6 HORARIOS MAX POR DIA
            switch (showTimeOption) {
                case 1, 2, 3, 4, 5, 6:
                    purchaseProcessFinal(cinemaMatrix,  movieOption, showTimeOption);
                    break;
            }
        }
    }

    public static void purchaseProcessFinal(int[][][] cinemaMatrix, int movieOption, int showTimeOption) throws IOException {
        String text, text2;
        int x, k, ticketPrice = 0;
        int option, numbersOfTicket, availableSeats, cicleDuration;

        if (cinemaMatrix != null) {
            availableSeats = Validate.valUnsoldSeats1(cinemaMatrix, movieOption, showTimeOption);
            System.out.println("- Hay solo " + availableSeats + " butacas disponibles");
            text = " + Ingrese cuantas entradas el cliente desea: ";
            numbersOfTicket = Validate.valInt(text, availableSeats);
            x = 0;
            for (String ageClassification : AGE_CLASSIFICATIONS) {
                text2 = " + Ingrese [" + (++x) + "] Si la entrada es para un " + ageClassification;
                System.out.println(text2);
            }
            x = 0;
            k = (cinemaMatrix[0][0].length - availableSeats);
            cicleDuration = numbersOfTicket + k;
            for (; k < cicleDuration; k++) {
                text = "- Ingrese el numero de la opcion escogida para la entrada [" + (++x) + "]: ";
                option = Validate.valInt(text, 3, 1);
                if (option == 1) {
                    ticketPrice = 3;
                } else if (option == 2) {
                    ticketPrice = 2;
                } else if (option == 3) {
                    ticketPrice = 1;
                }
                cinemaMatrix[movieOption - 1][showTimeOption - 1][k] = ticketPrice;
            }
        }
    }

    public static void calcRevenueLosses(int[][][] cinemaMatrix, int[][] revenue, int[] losses) {
        int sum;

        if (cinemaMatrix != null && revenue != null) {
            for (int i = 0; i < cinemaMatrix.length; i++) {
                for (int j = 0; j < cinemaMatrix[0].length; j++) {
                    sum=0;
                    for (int k = 0; k < cinemaMatrix[0][0].length; k++) {
                        sum += cinemaMatrix[i][j][k];
                    }
                    losses[i] += Validate.valUnsoldSeats2(cinemaMatrix, i, j);
                    revenue[i][j] = sum;
                    revenue[i][cinemaMatrix[0].length] += revenue[i][j];
                }
            }
        }
    }


}
