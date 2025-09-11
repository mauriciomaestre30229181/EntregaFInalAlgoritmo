package src_restaurant.process;

import java.io.File;
import java.util.Scanner;

import src_restaurant.models.Diner;
import src_restaurant.models.Item;
import src_restaurant.models.Table;
import src_restaurant.types.Queue;
import src_restaurant.types.Stack;
import src_restaurant.utils.ArchiveUtil;
import src_restaurant.utils.FileManager;
import src_restaurant.utils.LoggerUtil;

public class ProcessMain {

    public static void initAllArrays(Table[] tables) {
        LoggerUtil.log("INICIO: Inicializando arreglos");
        Process.initMatrix(tables);
        LoggerUtil.log("FIN: Inicializando arreglos");
    }

    public static void clearAllArrays(Table[] tables) {
        LoggerUtil.log("INICIO: Desreferenciando arreglos");
        tables = null;
        LoggerUtil.log("FIN: Desreferenciando arreglos");
    }

    public static String buildInvoiceText(Table table) {
        Diner[] diners = table.getDiners();
        StringBuilder invoice = new StringBuilder();
        double invoiceTotal = 0;

        Process.buildInvoiceHeader(invoice, table.getNumber());
        for (int personIndex = 0; personIndex < table.getPersonsInTable(); personIndex++) {
            Diner diner = diners[personIndex];
            if (diner != null) {
                String personName = diner.getName();
                Item[] items = diner.getItems();
                double personTotal = 0;

                Process.buildClientInvoiceHeader(invoice, personName);
                for (int itemIndex = 0; itemIndex < diner.getNumberOfItems(); itemIndex++) {
                    Item item = items[itemIndex];
                    if (item != null) {
                        double summary = Process.buildInvoiceBody(invoice, item);
                        personTotal += summary;
                    }
                }

                Process.buildClientInvoiceFooter(invoice, personName, personTotal);
                invoiceTotal += personTotal;
            }
        }

        Process.buildInvoiceFooter(invoice, invoiceTotal);
        return invoice.toString();
    }

    public static String searchNameInFile (File file, String name) {
        String invoiceText = FileManager.getTextFromFile(file);
        String searchResult = Process.searchNameInText(invoiceText, name);
        return searchResult;
    }

    public static String searchTableInFile (File file, int table) {
        String invoiceText = FileManager.getTextFromFile(file);
        String searchResult = Process.searchTableInText(invoiceText, table);
        return searchResult;
    }

    public static String searchClientsInFile (File file) {
        String invoiceText = FileManager.getTextFromFile(file);

        Stack<String> clients = Process.extractClients(invoiceText);
        String result = clients.toString();
        Process.desrefStack(clients);

        return result;
    }

    public static String searchItemsInFile (File file, ArchiveUtil util) {
        String fileName = file.getName();
        Scanner fileScanner = util.getArchive(fileName);
        if (fileScanner == null) {
            LoggerUtil.logWarn("El archivo " + fileName + " no existe.");
            System.out.println("El archivo " + fileName + " no existe.");
            return null;
        }

        StringBuilder content = new StringBuilder();
        while (fileScanner.hasNextLine())
            content.append(fileScanner.nextLine()).append("\n");
        fileScanner.close();

        Queue<String> items = Process.extractItems(content.toString());
        String result = items.toString();
        Process.desrefQueue(items);

        return result;
    }
}