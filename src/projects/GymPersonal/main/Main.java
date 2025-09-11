package projects.GymPersonal.main;

// Mauricio Maestre - 30229181
// Miguel Maestre - 29750704

import projects.GymPersonal.helpers.ConsultDataMain;
import projects.GymPersonal.process.ProcessMain;
import projects.GymPersonal.validateItem.Validate;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static int[][][] gymRevenueArray;
    public static int[][] revenue;
    public static int[] losses,nutritionRevenueArray;
    public static String[][] gymMembersDatabase;
    public static String[] workoutTimes;
    public static int trainingTimesQuant, spotsQuant;
    public static String router;
    public static final int CLASS_QUANT = 9;
    static int rand = (int) (Math.random() * 100) + 1;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String text;

        //Detectamos automaticamente donde se encuentra la ruta donde queremos crear el archivo
        router = Paths.get("").toRealPath()+"/src/GymPersonal/storage";
        router  = Validate.utilDirectory(router)+"/";


        // Pedimos los datos para la inicializacion
        text = "- Ingrese cuantos HORARIOS estaran disponibles por sesion de entrenamiento: ";
        trainingTimesQuant = Validate.valInt(text, 1, 8);

        text = "- Ingrese cuantas personas podran participar en cada clase: ";
        spotsQuant =  Validate.valInt(text, 5, 10);


        // Instanciamos los arreglos
        gymRevenueArray = new int[CLASS_QUANT][trainingTimesQuant][spotsQuant];
        revenue = new int[CLASS_QUANT][(trainingTimesQuant +1)];
        nutritionRevenueArray = new int[5];
        losses = new int[CLASS_QUANT];
        workoutTimes = new String[trainingTimesQuant];
        gymMembersDatabase = new String[Validate.valEnrolledUsersQuant("GymMembersDatabase.txt")][8];


        // Desarrollo
        ConsultDataMain.consultData();
        ProcessMain.process();


        // Eliminamos las instancias
        gymRevenueArray = null;
        losses = null;
        revenue = null;
        workoutTimes = null;
        gymMembersDatabase = null;
        scanner.close();
    }
}