package projects.GymPersonal.process;

import projects.GymPersonal.Nutrition.BookingNutrition;
import projects.GymPersonal.composables.StoreMain;
import projects.GymPersonal.helpers.ConsultData;
import projects.GymPersonal.main.Main;
import projects.GymPersonal.repositories.Nutrition;
import projects.GymPersonal.repositories.Statitics;
import projects.GymPersonal.validateItem.Validate;

import java.io.IOException;


public class Process {

    public static void iniMatrix(int[][][] gymMatrix) {
        if (gymMatrix != null) {
            for (int i = 0; i < gymMatrix.length; i++) {
                for (int j = 0; j < gymMatrix[0].length; j++) {
                    for (int k = 0; k < gymMatrix[0][0].length; k++) {
                        gymMatrix[i][j][k] = 0;
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

    public static void iniArray(String[][] revenueArray) {
        if (revenueArray != null) {
            for (int i = 0; i < revenueArray.length; i++) {
                for (int j = 0; j < revenueArray[0].length; j++) {
                    revenueArray[i][j] = "";
                }
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


    public static void dataEntry(String[] trainingSchedule) throws IOException {
        String text;
        int band=0;
        System.out.println("\nEl gimnasio esta abierto desde las 6:00 AM hasta las 9:00 PM");
        if (trainingSchedule != null) {
            for (int i = 0; i < trainingSchedule.length; i++) {
                text = "- Ingresa cual es el horario[" + (i+1) + "] disponible el dia de hoy. En formato (hh:mm AM/PM): ";
                if(band==0){
                    trainingSchedule[i] = Validate.valSchedule(text);
                    band = 1;
                }else {
                    trainingSchedule[i] = Validate.valSchedule(text,trainingSchedule[i-1]);
                }
            }
        }
    }

    public static void showStartingMenu() {
        System.out.println("\n      ======================= MENU PRINCIPAL ========================    ");
        System.out.println("Indique que desea el cliente: ");
        System.out.println("- Ingrese [1] => Si desea RESERVAR un puesto en una clase de entrenamiento.");
        System.out.println("- Ingrese [2] => Si desea ORDENAR un alimento/bebida el dia de hoy.");
        System.out.println("- Ingrese [3] => Si desea REALIZAR una busqueda especifica.");
        System.out.println("- Ingrese [4] => Si desea SALIR del sistema.");
    }

    public static void execGymSoftware( int[][][] gymMatrix) throws IOException {
        boolean isContinuing = true;
        Statitics statitics = null;
        if(gymMatrix!=null) {
            while (isContinuing) {
                showStartingMenu();
                int option = Validate.valInt("Selecciona una opcion: ", 1, 4);

                switch (option) {
                    case 1:
                        BookingTraining.trainingStart(Main.gymRevenueArray, Main.workoutTimes, Main.gymMembersDatabase);
                        try {
                            statitics = new Statitics(Main.gymRevenueArray, Main.revenue, Main.losses);
                        } catch (IllegalArgumentException e) {
                            Validate.addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                            System.out.println("- ERROR: "+e.getMessage());
                        }
                        statitics.calcRevenueLosses();
                        break;
                    case 2:
                        BookingNutrition.nutritionStart(Main.nutritionRevenueArray, Main.gymMembersDatabase);
                        break;
                    case 3:
                        ConsultData.search();
                        break;
                    case 4:
                        isContinuing = false;
                        break;
                }
            }
        }
    }







}
