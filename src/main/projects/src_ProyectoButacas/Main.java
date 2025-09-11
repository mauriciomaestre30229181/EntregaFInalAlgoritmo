package src_ProyectoButacas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import src_ProyectoButacas.composablesData.*;
import src_ProyectoButacas.helpers.consultMain;
import src_ProyectoButacas.objects.ArchiveUtil;
import src_ProyectoButacas.process.processMain;
import src_ProyectoButacas.validateItem.Validate;
import src_ProyectoButacas.validateItem.ValidateOBJ;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String text="";
        String archiveName = "InformeVentasCine";
        ArchiveUtil storage=null;
        int[][][] cinema;
        int[][] revenue;
        int[] losses;
        String[] movieTimes;
        String[] usersRow;
        int[] idRow;
        int showTimesQuant, seatsQuant, usersQuant;
        int moviesQuant = 7;

        String router=Paths.get("").toRealPath().toString()+"/src/cine/storage/";

        try {
            storage= new ArchiveUtil(router);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println("-ERROR-STORAGE-: ["+e.getMessage()+"]");
        }

        if (storage.directoriesExist()) {
            text="\n¿Desea realizar una busqueda o realizar una compra? \nBusqueda \nCompra";
            System.out.println(text);
            String option=Validate.valOptionCompare(text, scanner.nextLine().trim().toLowerCase(), "busqueda", "compra");
            
            text=Objects.equals(option, "busqueda")   
            ?"Que desea buscar? nombre, cedula?":null;
        }

        if (text!=null) {
            System.out.println(text);
            String option=ValidateOBJ.valOption(text, scanner.nextLine(),scanner);
            consultMain.consultDataMain(option.toLowerCase(), storage);
        }
        
        // Pedimos los datos para la inicializacion
        

        text = "- Ingrese cuantos HORARIOS estaran disponibles por pelicula: ";
        showTimesQuant = Validate.valInt(text, 6, 4);

        text = "- Ingrese cuantos asientos tiene cada sala: ";
        seatsQuant =  Validate.valInt(text, 20, 10);

        text= "- Ingrese cuantos usuarios hay en la fila";
        usersQuant = Validate.valInt(text, 4, 1);
        // Instanciamos los arreglos
        usersRow= new String[usersQuant];
        idRow= new int[usersQuant];
        cinema = new int[moviesQuant][showTimesQuant][seatsQuant];
        revenue = new int[moviesQuant][(showTimesQuant+1)];
        losses = new int[moviesQuant];
        movieTimes = new String[showTimesQuant];

        // Inicializamos los arreglos
        processMain.processIni(revenue, cinema, losses, movieTimes, usersRow,idRow);
        

        // Desarrollo
        processMain.proccessData(movieTimes, cinema, revenue, losses, router, usersRow,idRow);
        storeMain.store(storage,idRow,usersRow,cinema,revenue,losses);

        // Eliminamos las instancias
        cinema = null;
        revenue = null;
        movieTimes = null;
        scanner.close();
    }
}
