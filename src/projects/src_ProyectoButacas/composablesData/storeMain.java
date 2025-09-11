package src_ProyectoButacas.composablesData;

import src_ProyectoButacas.objects.*;

public class storeMain {
    public static void store(ArchiveUtil storage,int[]id,String[]names,int[][][]cinemaMatrix,int[][]revenue,int[]losses){
        boolean band=false;

        storeArchive.storeName(names,storage,band);
        storeArchive.storeId(id,  storage, band);
        storeArchive.storeShowCase(cinemaMatrix,revenue,losses,storage);

    }
}
