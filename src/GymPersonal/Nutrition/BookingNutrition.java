package GymPersonal.Nutrition;

import GymPersonal.helpers.ConsultData;
import GymPersonal.validateItem.Validate;

import java.io.IOException;

import static GymPersonal.process.BookingTraining.*;

public class BookingNutrition {
    public static int creditCostPerItem;
    public static String userNutritionPlan, selectedItem;
    public static String[][] bookedNutritionArray;
    public static String[] selectedNutritionType = new String[5];


    public static void nutritionStart(int[] nutritionArray, String[][] database) throws IOException {
        int band;
        String text;

        if (nutritionArray != null && database != null) {
            while (true) {
                System.out.println("\n================= ORDEN DE COMIDAS, SNACKS Y BEBIDAS NUTRITIVAS =================");
                System.out.println("- Ingrese [1] para INICIAR el proceso de tomar orden del siguiente cliente en fila. " +
                        "\n- Ingrese [2] si quiere SALIR del proceso de ordenar.");
                text = "Selecciona una opcion: ";
                band = Validate.valInt(text, 1, 2);  // VALIDAR INT
                if (band != 1) break;
                System.out.println("\n  ~~~~~ INICIO DEL PROCESO DE RESERVA DE COMIDAS, SNACKS Y BEBIDAS NUTRITIVAS  ~~~~        ");
                text = "- Indique el numero de cedula del usuario: ";
                userDatabaseIndex = Validate.validateId(text, database);
                userName = database[userDatabaseIndex][0];
                userId = database[userDatabaseIndex][1];
                nutritionGetType(nutritionArray,database);
            }
        }
    }

    public static void nutritionGetType(int[] nutritionArray, String[][] database) throws IOException {
        boolean isValid=true;
        String[] HEALTHY_MEALS = {
                "Ensalada de pollo a la plancha con quinoa", "Bowl de arroz con salmon y esparragos", "Omelette con vegetales y waffles de avena",
                "Revoltillo con vegetales y panquecas de platano", "Wrap integral de pavo con vegetales"};
        String[] HEALTHY_DRINKS = {
                "Batido de Proteina: Whey", "Merengada de Proteina: Elite", "Jugo verde: Detox", "Yogurt de fruta natural", "Agua" };
        String[] HEALTHY_SNACKS = {
                "Barra de proteina", "Bowl de yogurt con frutos rojos y acai", "Galleta de avenas y frutos secos",
                "Manzanas troceadas con crema de mani", "Tortillas de coco y yuca"};

        if (nutritionArray != null && database != null) {
            while (true) {
                showMenuTypes();
                int menuOption=Validate.valInt("- Indique el numero de la opcion escogida por el cliente: ", 4);
                switch (menuOption) {
                    case 1:
                        selectedNutritionType = HEALTHY_MEALS;
                        creditCostPerItem = 3;
                        break;
                    case 2:
                        selectedNutritionType = HEALTHY_DRINKS;
                        creditCostPerItem = 2;
                        break;
                    case 3:
                        selectedNutritionType = HEALTHY_SNACKS;
                        creditCostPerItem = 1;
                        break;
                    case 4:
                        return;
                }
                nutritionGetItem(nutritionArray, database);
            }

        }
    }

    public static void showMenuTypes(){
        System.out.println("\n- Indique el TIPO (comida, bebida o snack) de lo que el usuario [" + userName + "] desea: ");
        System.out.println(
                "  + Ingrese [1] Si desea una COMIDA saludable." +
                "\n  + Ingrese [2] Si desea una BEBIDA saludable." +
                "\n  + Ingrese [3] Si desea un SNACK saludable." +
                "\n  + Ingrese [4] si quiere SALIR del proceso de ordenar.");
    }

    public static void nutritionGetItem(int[] nutritionArray, String[][] database) throws IOException {
        String text;
        int  nutritionOption;

        if (nutritionArray != null && database != null) {
            showNutritionMenu(selectedNutritionType,0);
            text = "- Ingrese el numero de la opcion escogida por el cliente: ";
            nutritionOption = Validate.valInt(text, 5);
            selectedItem = selectedNutritionType[nutritionOption-1];
            userNutritionPlan = getUserNutritionPlan(database);
            bookedNutritionArray = ConsultData.readFile("BookedNutritions.txt", 5, false);
            nutritionFinalBooking(nutritionArray, database);
        }
    }

    public static void nutritionFinalBooking(int[] nutritionArray, String[][] database) throws IOException {
        if (nutritionArray != null && database!=null) {
            userId = database[userDatabaseIndex][1];
            userGenre = database[userDatabaseIndex][2];
            userNutritionPlan = getUserNutritionPlan(database);
            PaymentNutrition.paymentNutritionFlow(database, nutritionArray);
        }
    }

    public static String getUserNutritionPlan(String[][] database){
        String nutritionPlan ="";
        if(database != null) {
            switch (database[userDatabaseIndex][5]) {
                case "PCD":
                    nutritionPlan = "Plan de Nutricion Diario";
                    break;
                case "PCM":
                    nutritionPlan ="Plan de Nutricion Mensual";
                    break;
                case "PCS":
                    nutritionPlan = "Plan de Nutricion Semanal";
                    break;
                case "NP":
                    nutritionPlan = "No posee Plan de Nutricion";
                    break;
            }
        }
        return nutritionPlan;
    }

    // Recursión directa no final — aún hay código después del llamado
    public static void showNutritionMenu(String[]array, int ctrl) {
        if (ctrl >= array.length) return;
        String text2 = "  + Ingrese [" + (ctrl+1) + "] Si desea un [" + array[ctrl] + "].";
        System.out.println(text2);
        showNutritionMenu(array, ctrl + 1);
    }

    public static String[][] addRegistrationToNutritArray(String[][] bookedUsers) {
        String[][] newLine = new String[bookedUsers.length + 1][6];
        for (int i = 0; i < bookedUsers.length; i++) {
            newLine[i] = bookedUsers[i];
        }
        newLine[bookedUsers.length] = new String[]{userName, userId, userGenre, userNutritionPlan, selectedItem};
        return newLine;
    }

}
