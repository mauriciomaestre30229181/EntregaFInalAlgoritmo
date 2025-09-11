package src_restaurant.restaurant;

import java.io.File;
import java.util.Scanner;

import src_restaurant.models.Diner;
import src_restaurant.models.Item;
import src_restaurant.models.Table;
import src_restaurant.process.Process;
import src_restaurant.process.ProcessMain;
import src_restaurant.utils.LoggerUtil;

public class RestaurantMain {

    // CONSTANTS
    private static final int MAX_TABLES = 30;
    private static final int MAX_PERSONS_BY_TABLE = 6;
    private static final int MAX_ITEMS_BY_PERSON = 3;

    // MAIN ARRAYS
    private static Table[] tablesNew = new Table[MAX_TABLES];

    public static void init () {
        ProcessMain.initAllArrays(tablesNew);
        showMenu();
        ProcessMain.clearAllArrays(tablesNew);
    }

    private static void showMenu () {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE RESTAURANTE ===");
            System.out.println("1. Agregar comanda");
            System.out.println("2. Ver estado de todas las mesas");
            System.out.println("3. Buscar información por nombre");
            System.out.println("4. Buscar información por mesa");
            System.out.println("5. Buscar clientes por factura");
            System.out.println("6. Buscar items por factura");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opt = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
            System.out.println(); // Salto de linea

            switch (opt) {
                case 1:
                    addOrder(scanner);
                    break;
                case 2:
                    showTables();
                    break;
                case 3:
                    searchInfo(scanner, "NAME");
                    break;
                case 4:
                    searchInfo(scanner, "TABLE");
                    break;
                case 5:
                    searchInfo(scanner, "CLIENT");
                    break;
                case 6:
                    searchInfo(scanner, "ITEM");
                    break;
                case 7:
                    exit = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

    }

    private static void addOrder (Scanner scanner) {
        Table table = Restaurant.setTableNumber(scanner, tablesNew, MAX_TABLES);
        int persons = Restaurant.setPersonsInTable(scanner, table, MAX_PERSONS_BY_TABLE);

        Diner[] diners = new Diner[persons];
        Process.initMatrix(diners);
        Restaurant.namesInTable(scanner, diners);

        for (int i = 0; i < persons; i++) {
            Diner diner = diners[i];
            int products = Restaurant.setItemsPerson(scanner, diner, MAX_ITEMS_BY_PERSON);

            Item[] items = new Item[products];
            Process.initMatrix(items);

            for (int j = 0; j < products; j++) {
                Item item = items[j];
                System.out.println("\nItem #" + (j + 1) + " - " + diner.getName().toUpperCase());

                Restaurant.setItemName(scanner, item, j);
                Restaurant.setItemQuant(scanner, item, j);
                Restaurant.setItemPrice(scanner, item, j);
            }

            diner.setItems(items);
            items = null;
        }

        table.setDiners(diners);
        diners = null;

        Restaurant.generateInvoice(table);
        table = null;
    }

    private static void showTables () {
        for (int i = 0; i < tablesNew.length; i++) {
            Restaurant.showStatusTable(tablesNew[i]);
        }
    }

    private static void searchInfo (Scanner scanner, String criteria) {
        String error_msg = "";
        File[] invoices = Restaurant.getTotalInvoices();

        if (invoices != null && invoices.length > 0) {
            switch (criteria) {
                case "NAME":
                    Restaurant.searchInfoByName(scanner, invoices);
                    break;
                case "TABLE":
                    Restaurant.searchInfoByTable(scanner, invoices, MAX_TABLES);
                    break;
                case "CLIENT":
                    Restaurant.searchInfoByClient(scanner, invoices);
                    break;
                case "ITEM":
                    Restaurant.searchInfoByItem(scanner, invoices);
                    break;
                default:
                    error_msg = "Termino de busqueda invalido";
                    break;
            }
        } else {
            error_msg = "No hay facturas generadas";
        }

        if (!error_msg.equals("")) {
            LoggerUtil.logWarn(error_msg);
            System.out.println(error_msg.toUpperCase());
        }
    }
}