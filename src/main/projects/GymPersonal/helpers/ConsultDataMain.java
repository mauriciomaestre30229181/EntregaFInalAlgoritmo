package GymPersonal.helpers;

import GymPersonal.main.Main;
import GymPersonal.process.Process;

import java.io.IOException;


public class ConsultDataMain {
    public static void consultData() throws IOException {
        Process.iniArray(Main.gymMembersDatabase);

        // Desarrollo
        ConsultData.readGymDatabaseFile(Main.gymMembersDatabase, "GymMembersDatabase.txt");
    }
}
