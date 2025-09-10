package src_ProyectoButacas.composablesData;

import src_ProyectoButacas.validateItem.*;
import src_ProyectoButacas.objects.*;
import java.time.LocalDateTime;

import main.projects.src_ProyectoButacas.objects.ArchiveUtil;

public class storeArchive {
    static int rand=(int)(Math.random()*100 + 1);

    public static void storeName(String[]names, ArchiveUtil storage,boolean check){
        LocalDateTime actualDateTime = LocalDateTime.now();
        String safeDateTime = actualDateTime.toString().replace(":", "-").replace(".", "-");
        String nameRoute=(!check)? "nameStore_"+safeDateTime+"_serial"+rand : "nameStore";

        String text="";

        if (names!=null) {
            for(int i=0;i<names.length;i++){
                text+=String.valueOf(names[i]);
                if (i<names.length-1) {
                    text+="/";
                }
            }
            storage.setCreateArchive(text, nameRoute, true); 

        }   
        actualDateTime = null;
    }

    public static void storeErrors(String error,ArchiveUtil storage){
        
        String nameRoute="ErrorSystem.log";
        storage.setCreateArchive(error, nameRoute, true);
    }

    public static void storeId(int[]ids,ArchiveUtil storage,boolean check){
        LocalDateTime actualDateTime = LocalDateTime.now();
        String safeDateTime = actualDateTime.toString().replace(":", "-").replace(".", "-");
        String nameRoute=(!check)? "idStore_"+safeDateTime+"_serial"+rand : "nameStore";

        String text="";

        if (ids!=null) {
            for(int i=0;i<ids.length;i++){
                text+=String.valueOf(ids[i]);
                if (i<ids.length-1) {
                    text+="/";
                }
            }
            storage.setCreateArchive(text, nameRoute, true); 

        }   
        actualDateTime = null;

    }
    public static final String[] MOVIE_LISTINGS = {
            "Avatar", "Top gun: Maverick", "Avengers: End Game",
            "Oppenheimer", "Titanic", "Mission: Impossible",
            "Rapidos y Furiosos"
    };
}
