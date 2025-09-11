package ElectricBill.arr;

import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Paths;

import ElectricBill.arr.Process.MainProcess;
import ElectricBill.arr.Repositories.ArchiveUtil;
import ElectricBill.arr.Repositories.Stadistics;
import ElectricBill.arr.Repositories.User;



//Alumnos:
//Juan Vargas 31.139.590
//Emilio Chinchilla 31.471.587
//Oscar wu 30.913.513



public class Main {
    public static void main(String[] args) throws IOException {

        String customersnames;
        String [] userArray;
        double [][][] customers;
        double [][] average;
        double [] consume;
        double [] finalprice;
        int casa = 0, months =0, client = 0 ;
        Scanner enter = new Scanner(System.in);

       
        /*customers = new double[casa][months][client];
        average = new double [casa][2];
        cost = new double [casa];
        consume = new String[2];
        finalprice = new double [casa];*/

        

        

        String router = Paths.get("").toRealPath().toString()+"/projects/ElectricBill/arr/storage";

        ArchiveUtil archiveUtil = new ArchiveUtil(router);


        int elementsUser = ElectricBill.arr.validate.Validations.countArchiveElements(archiveUtil, "user.txt");


        userArray = new String[elementsUser];

        int elementsConsume = ElectricBill.arr.validate.Validations.countArchiveElements(archiveUtil, "kilowatts.txt");
        consume = new double[elementsConsume];

        String text = "\n Ingrese su nombre de usuario : ";
        System.out.println(text);
        String userName = enter.next();
        text = "Ingrese su contraseña : ";
        System.out.println(text);
        String password = enter.next();

        userName = ElectricBill.arr.validate.Validations.valSubName(userName,text);
        ElectricBill.arr.validate.Validations.utilValSubName(password);

        User user = new User(userName, router,password, userArray);

        Stadistics stadistics = new Stadistics(archiveUtil);
        

        
        MainProcess.Process(user,stadistics,archiveUtil,router);

        customersnames = null;
        average = null;
        customers = null;
        consume = null;
        finalprice = null;
        userArray = null;

    }
}

