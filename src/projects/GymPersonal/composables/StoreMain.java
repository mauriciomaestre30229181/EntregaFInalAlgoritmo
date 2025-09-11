package projects.GymPersonal.composables;

import projects.GymPersonal.main.Main;
import projects.GymPersonal.repositories.Statitics;
import projects.GymPersonal.validateItem.Validate;

import java.io.IOException;

public class StoreMain {
    public static void store() throws IOException {
        // Desarrollo
        Statitics statitics=null;
        try {
            statitics = new Statitics(Main.gymRevenueArray, Main.revenue, Main.losses);
        } catch (IllegalArgumentException e) {
            Validate.addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
            System.out.println("- ERROR: "+e.getMessage());
        }
        StoreArchive.writeRegistrationReport(statitics, Main.router);
    }

}
