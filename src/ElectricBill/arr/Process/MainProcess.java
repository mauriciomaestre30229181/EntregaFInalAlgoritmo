package ElectricBill.arr.Process;

import ElectricBill.arr.composable.mainStoreArchive;
import ElectricBill.arr.helpers.consultMain;
import ElectricBill.arr.validate.Validations;

import java.io.FileNotFoundException;

import ElectricBill.arr.Repositories.ArchiveUtil;
import ElectricBill.arr.Repositories.MunicipalityClientFinder;
import ElectricBill.arr.Repositories.Stadistics;
import ElectricBill.arr.Repositories.User;
public class MainProcess{


    public static void Process(User user, Stadistics stadistics, ArchiveUtil archiveUtil, String router) throws FileNotFoundException {
        int option = 0;
        String text = "";

        text = "escriba que acción quiere tomar: -pagar, -consultar , -salir";
        System.out.println(text);
        option = Validations.valOption(text);

       if (!(option == 0) && !(option > 4)){
        if (option == 1) {
            
           mainStoreArchive.store(user, archiveUtil);
        }
        else if(option == 2){
           ElectricBill.arr.helpers.consultMain.main(router,archiveUtil);
        }
            
        else if (option == 3){
            
            text = "saliendo del programa";
            System.out.println(text);
        }
        
       }
       else{
        text = "esa opción no existe";
        System.out.println(text);
       }
    }


    public static double payElectricBill(User user){
        String userName = user.getUserName();
        double kilowattHours = user.getKilowattHours();
        int  municipality = user.getOption();
        double cost = Process.calculatePrice(kilowattHours, municipality);
        return cost;

       /*  double cost = 0.0;
        Process.iniMatrix(customers);
        Process.iniAverage(average);
       // Process.initNames(customersnames);
    //Process.iniDouble(cost);
        Process.initNames(consume);
        //Process.iniDouble(finalprice);

        consultMain.main(customers);
        Process.calculateAverage(customers,average);
       cost = Process.calculatePrice(customers,cost);
       // Process.hig_LowConsume(cost,customersnames,consume);
       // Process.calculaterMunipality(cost,finalprice,customersnames);
        try {
            mainStoreArchive.store(customersnames, consume, customers, average, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }


}
