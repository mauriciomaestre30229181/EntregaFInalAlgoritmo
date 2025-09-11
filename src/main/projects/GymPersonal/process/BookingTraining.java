package main.projects.GymPersonal.process;

import main.projects.GymPersonal.validateItem.Validate;
import main.projects.GymPersonal.helpers.ConsultData;
import java.io.IOException;


public class BookingTraining {
    public static int classOption, scheduleOption, userDatabaseIndex,  availableSpots;
    public static String userId, userGenre, userName, userTrainingPlan, selectedSchedule;
    public static String[][] bookedUsersArray;
    public static final String[] CLASS_LISTINGS = {
            "Pilates", "Boxeo Fit", "Funcionales",
            "TRX", "Body Power", "Cardio Step",
            "Power Bike", "Yoga", "Reformer: Pilates"
    };

    public static void trainingStart(int[][][] gymMatrix,String[] trainingSchedule, String[][] database) throws IOException {
        int band;
        String text;

        if (gymMatrix != null && database != null) {
            while (true) {
                System.out.println("\n================= RESERVA DE PUESTOS EN CLASE DE ENTRANAMIENTO =================");
                System.out.println("- Ingrese [1] para INICIAR el proceso de reserva del siguiente cliente en fila. " +
                        "\n- Ingrese [2] si quiere SALIR del proceso de reserva.");
                text = "Selecciona una opcion: ";
                band = Validate.valInt(text, 1, 2);  // VALIDAR INT
                if (band != 1) break;
                System.out.println("\n  ~~~~~ INICIO DEL PROCESO DE RESERVA DE ESPACIO EN CLASE DE ENTRENAMIENTO ~~~~        ");
                text = "- Indique el numero de cedula del usuario: ";
                userDatabaseIndex = Validate.validateId(text, database);
                userName = database[userDatabaseIndex][0];
                userId= database[userDatabaseIndex][1];
                bookingClassFlow(gymMatrix, trainingSchedule,database);
            }
        }
    }


    public static void bookingClassFlow(int[][][] GymMatrix, String[] trainingSchedule, String[][] database) throws IOException {
        int x;
        String text, text2;

        if (GymMatrix != null && trainingSchedule != null && database != null) {
            System.out.println("- Indique a cual de estas clases de entrenamiento el usuario \"" + userName +"\" desea asistir: ");
            x = 0;
            for (int ctrl = 0; ctrl < GymMatrix.length; ctrl++) {
                text2 = "  + Ingrese [" + (++x) + "] Si desea asistir a la clase de [" + CLASS_LISTINGS[ctrl] + "].";
                System.out.println(text2);
            }
            text = "- Ingrese el numero de la opcion escogida por el cliente: ";
            classOption = Validate.valInt(text, 9);
            switch (classOption) {
                case 1, 2, 3, 4, 5, 6, 7, 8, 9:
                    bookingScheduleFlow(GymMatrix, trainingSchedule,database);
                    break;
            }
        }
    }

    public static void bookingScheduleFlow(int[][][] GymMatrix, String[] trainingSchedule, String[][] database) throws IOException {
        String text;
        int x = 1, ctrl = 0;

        if (GymMatrix != null && trainingSchedule != null && database != null) {
            do {
                System.out.println("- Indique en que horario de los siguientes el cliente desea asistir:");
                showScheduleOptionRec(x, trainingSchedule, ctrl);
                text = "- Ingrese el numero de la opcion escogida por el cliente: ";
                scheduleOption = Validate.valInt(text, GymMatrix[0].length);
                selectedSchedule = trainingSchedule[scheduleOption - 1];
                bookedUsersArray = ConsultData.readFile("BookedUsers.txt", 6, true);
            } while (!Validate.isValidRegistration(bookedUsersArray));
            switch (scheduleOption) {
                case 1, 2, 3, 4, 5, 6, 7, 8:
                    bookingSpotFlow(GymMatrix, database);
                    break;
            }
        }
    }

    // Recursividad directa
    public static void showScheduleOptionRec(int i, String[] trainingSchedule, int pos) {
        if (trainingSchedule != null) {
            if (pos >= trainingSchedule.length) return;
            String text = "  + Ingrese [" + (i) + "] Si desea asistir a [" + CLASS_LISTINGS[classOption - 1] + "] en el HORARIO de las [" + trainingSchedule[pos] + "].";
            System.out.println(text);
            i++;
            showScheduleOptionRec(i, trainingSchedule, pos + 1);
        }
    }


    public static String getUserTrainingPlan(String[][] database){
        String trainingPlan ="";
        if(database != null) {
            switch (database[userDatabaseIndex][3]) {
                case "PED":
                    trainingPlan = "Plan de Entrenamiento Diario";
                    break;
                case "PMG":
                    trainingPlan ="Plan de Entrenamiento Mensual General";
                    break;
                case "PME":
                    trainingPlan = "Plan de Entrenamiento Mensual Estudiantil";
                    break;
                case "PMT":
                    trainingPlan = "Plan de Entrenamiento Mensual Tercera Edad";
                    break;
            }
        }
        return trainingPlan;
    }

    public static void bookingSpotFlow(int[][][] gymMatrix, String[][] database) throws IOException {
        if (gymMatrix != null && database!=null) {
            userId = database[userDatabaseIndex][1];
            userGenre = database[userDatabaseIndex][2];
            userTrainingPlan = getUserTrainingPlan(database);
            availableSpots = Validate.valUnsoldSpots1(gymMatrix, classOption, scheduleOption);
            System.out.print("\nHay solo " + availableSpots + " espacios disponibles en la clase de [");
            System.out.println(CLASS_LISTINGS[classOption - 1] + " a las " + selectedSchedule + "]");
            PaymentTraining.paymentClassFlow(database, gymMatrix);
        }
    }


    public static String[][] addRegistrationToArray(String[][] bookedUsers) {
        String[][] newLine = new String[bookedUsers.length + 1][6];
        for (int i = 0; i < bookedUsers.length; i++) {
            newLine[i] = bookedUsers[i];
        }
        newLine[bookedUsers.length] = new String[]{userName, userId, userGenre, userTrainingPlan, CLASS_LISTINGS[classOption - 1], selectedSchedule};
        return newLine;
    }


}
