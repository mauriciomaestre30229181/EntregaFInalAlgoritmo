package projects.GymPersonal.helpers;

import projects.GymPersonal.main.Main;
import projects.GymPersonal.process.Process;

import java.io.IOException;


public class ConsultDataMain {
    public static void consultData() throws IOException {
        Process.iniArray(Main.gymMembersDatabase);

        // Desarrollo
        ConsultData.readGymDatabaseFile(Main.gymMembersDatabase, "GymMembersDatabase.txt");
    }
}
