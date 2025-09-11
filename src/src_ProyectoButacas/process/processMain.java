package src_ProyectoButacas.process;

import java.io.IOException;


public class processMain {
    public static void processIni(int[][]revenue, int[][][]cinema,int[]losses,String[]movies, String[] users,int[]id){
        Process.iniMatrix(cinema);
        Process.iniArray(revenue);
        Process.iniArray(losses);
        Process.iniArray(movies);
        Process.iniArray(users);
        Process.iniArray(id);
    }

    public static void proccessData(String[] movies, int[][][] cinema, int[][] revenue, int[] losses, String router,String[]users,int[]id) throws IOException {
        Process.dataEntry(movies);
        Process.quantProcess(users,cinema, movies,id);
        Process.calcRevenueLosses(cinema, revenue, losses);
    }
}
