package projects.GymPersonal.process;

import projects.GymPersonal.main.Main;

import java.io.IOException;

public class ProcessMain {
    public static void process() throws IOException {
        // Inicializamos los arreglos
        Process.iniMatrix(Main.gymRevenueArray);
        Process.iniArray(Main.workoutTimes);
        Process.iniArray(Main.nutritionRevenueArray);
        Process.iniArray(Main.revenue);
        Process.iniArray(Main.losses);


        // Desarrollo
        Process.dataEntry(Main.workoutTimes);
        Process.execGymSoftware(Main.gymRevenueArray);
    }
}
