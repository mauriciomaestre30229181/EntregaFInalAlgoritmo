package main.projects.src_ProyectoButacas.helpers;

import src.main.projects.src_ProyectoButacas.validateItem.*;
import src.main.projects.src_ProyectoButacas.objects.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import cinema.Validate.Validate;


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

    public static String[] getDirectoriesPerName(String[] items, Name userDefault){
        
        String[] txt=new String[2];
        Scanner enter=new Scanner(System.in);   
        String text="\nAqui tienes los archivos existentes:¿Cual desea Buscar? \nElija por numero: [1/2/3...]";
        String select="";
        String compare="";

        if (items==null || items.length<1) {
            return null;
        }

        System.out.println(text);   
        for(int i=0;i<items.length;i++){
            System.out.println("no entra al bendito ciclo");
            if (Objects.equals(items[i].split("")[0],"nameStore")) {
                System.out.println("-Posicion ["+(i+1)+"] ["+items[i]+"]");
                
                select=select+(i+1);
            }
        }

        String index = enter.nextLine();
        txt[0]=getItemByIndex(items,index,select);

        if (txt[0]==null) {
            return null;
        }

        String[]serialItem=txt[0].split("_");
        compare=serialItem[serialItem.length-1];
        userDefault.setSerial(compare);

        for (int i=0;i<items.length;i++) {
            if(Objects.equals(items[i].split("")[0],"idStore")){
                String[] serialItem2=items[i].split("_");
                if (Objects.equals(compare,serialItem2[serialItem.length-1])) {
                    txt[1]=items[i];
                }
            }
        }

        return txt[1]==null?null:txt;
    }

    public static String getItemByIndex(String[] items,String index, String select){

        for (int i = 0; i < select.length(); i++) {
            if(select.charAt(i)==index.charAt(0)){
                return items[Integer.parseInt(index)-1];
            }
        }

        return null;
    }
}
