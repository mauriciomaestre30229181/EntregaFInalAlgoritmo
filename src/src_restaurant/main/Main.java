package src_restaurant.main;

import src_restaurant.restaurant.RestaurantMain;
import src_restaurant.utils.LoggerUtil;

public class Main {

    public static void main(String[] args) {
        try {
            LoggerUtil.log("=== INICIANDO SISTEMA ===");
            RestaurantMain.init();
            LoggerUtil.log("=== FINALIZANDO SISTEMA ===");
        } catch (Exception e) {
            LoggerUtil.logError("Error fatal en el sistema: " + e.getMessage());
            System.out.println("Ha ocurrido un error fatal. Revisa los logs para mas informacion.");
        }
    }
}