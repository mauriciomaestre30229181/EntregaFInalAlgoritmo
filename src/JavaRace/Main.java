package JavaRace;

import JavaRace.process.processMain;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Java Race Timer...");
        processMain sistema = new processMain();
        sistema.iniciar();
    }
}