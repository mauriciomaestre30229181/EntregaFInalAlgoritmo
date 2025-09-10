//Francesco Moncini 31.461.113
//Eudrey Itriago 31.550.918
//Jonathan Roca 31.755.191
//Eyleen Berrio 31.909.062

package cinema;

import cinema.Process.ProcessMain;

public class Main {
    public static void main(String[] args) {
    ProcessMain.initializeSystem();
    ProcessMain.loadPreviousData();
    ProcessMain.runMainMenu();
    ProcessMain.saveBeforeExit();
    ProcessMain.cleanUp();
    }
}