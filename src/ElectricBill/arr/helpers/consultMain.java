package ElectricBill.arr.helpers;

import java.io.FileNotFoundException;

import ElectricBill.arr.Repositories.ArchiveUtil;
import ElectricBill.arr.Repositories.MunicipalityClientFinder;
import ElectricBill.arr.Repositories.MunicipalityConsumptionStats;
import ElectricBill.arr.Repositories.Stadistics;



public class consultMain {



   public static void main(String router, ArchiveUtil archiveUtil) {
    String text ="Escriba el numero por el tipo de busqueda que desea realizar  \n 1. General \n 2. Clientes por municipio \n 3. Consumo por municipio";
    int option =ElectricBill.arr.validate.Validations.valMaxvalues(text, 3);
    switch (option) {
        case 1:
            Stadistics stadistics = new Stadistics(archiveUtil);
            stadistics.saveTableFormatted(archiveUtil, "estaditico", 0.47);
        case 2:
        try {
            MunicipalityClientFinder finder = new MunicipalityClientFinder(router);
            finder.generateMunicipalityReport(); 
            finder.closeScanner();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Error: No se pudieron encontrar los archivos necesarios");
            System.out.println("Detalle: " + e.getMessage());
        }
        break;

        case 3:
        try {
            MunicipalityConsumptionStats stats = new MunicipalityConsumptionStats(router);
            stats.generateMunicipalityStats();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Error: Archivos no encontrados");
        }
        break;

        default:
            break;
    }
    }
            

        
     
 }
    

 
    

