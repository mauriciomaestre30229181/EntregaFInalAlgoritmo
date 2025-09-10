package main.projects.src_ProyectoButacas.helpers;

import src.main.projects.src_ProyectoButacas.validateItem.*;
import src.main.projects.src_ProyectoButacas.objects.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;


public class consultData {
    public static String optionSelect(String optionSelect) throws IOException {
        String text="";
        Scanner enter=new Scanner(System.in);

        if (Objects.equals(optionSelect,"nombre")) {
            text="Ingrese el NOMBRE del cliente a buscar: ";
            System.out.println(text);
            return Validate.valString(optionSelect,text); 
        }else if (Objects.equals(optionSelect,"cedula")) {
            text="Ingrese la CEDULA del cliente a buscar: ";
            return Integer.toString(Validate.valInt(text,35000000));
        }
        return null;
    }
}
