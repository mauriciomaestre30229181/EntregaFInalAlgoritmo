package src_restaurant.process;

import src_restaurant.models.Diner;
import src_restaurant.models.Item;
import src_restaurant.models.Table;
import src_restaurant.types.Queue;
import src_restaurant.types.Stack;

public class Process {

    // ----------------------------------------------------------------------------------------------------------------------------- INITS
    public static void initMatrix (Table[] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                Table table = new Table(i + 1, 0, false);
                m[i] = table;
            }
        }
    }

    public static void initMatrix (Diner[] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                Diner diner = new Diner(0);
                m[i] = diner;
            }
        }
    }

    public static void initMatrix (Item[] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                Item item = new Item(0, 0.00);
                m[i] = item;
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------------------------------- INVOICE
    public static void buildInvoiceHeader (StringBuilder invoice, int tableNumber) {
        invoice.append("Factura\n");
        invoice.append("Mesa: ").append(tableNumber).append("\n\n");
    }

    public static void buildClientInvoiceHeader (StringBuilder invoice, String name) {
        invoice.append("Cliente: ").append(name.toLowerCase()).append("\n");
        invoice.append("-----------------------------------------------------------\n");
    }

    public static double buildInvoiceBody(StringBuilder invoice, Item item) {
        String name = item.getName();
        int quant = item.getQuantity();
        double price = item.getPrice();

        double summary = quant * price;
        invoice.append(name).append(" - Cantidad: ").append(quant).append(" - Precio: $").append(price)
            .append(" - Subtotal: $").append(summary).append("\n");

        return summary;
    }

    public static void buildClientInvoiceFooter (StringBuilder invoice, String name, double personTotal) {
        invoice.append("Total de ").append(name).append(": $").append(personTotal).append("\n\n");
    }

    public static void buildInvoiceFooter (StringBuilder invoice, double invoiceTotal) {
        invoice.append("-----------------------------------------------------------\n");
        invoice.append("Total General de la Mesa: $").append(invoiceTotal);
    }

    // ----------------------------------------------------------------------------------------------------------------------------- SEARCH
    public static String searchNameInText (String text, String name) {
        // Caso base: si el texto está vacío
        if (text.isEmpty()) {
            return null;
        }

        // Buscar el inicio de la sección del cliente actual
        int sectionStart = text.indexOf("Cliente: ");
        if (sectionStart == -1) {
            return null; // No hay más clientes
        }

        // Buscar el final del nombre del cliente actual
        int endName = text.indexOf("\n", sectionStart);
        String currentClient = text.substring(sectionStart + 9, endName).trim();

        // Buscar el inicio de la siguiente sección
        int nextSectionStart = text.indexOf("Cliente: ", endName);

        // Extraer la sección completa del cliente actual
        String clientSection = nextSectionStart == -1 ? text.substring(sectionStart) : text.substring(sectionStart, nextSectionStart);

        // Comprobar si es el cliente buscado
        if (currentClient.equals(name)) {
            return clientSection.trim();
        } else {
            // Llamada recursiva con el resto del texto
            String invoiceRest = nextSectionStart == -1 ? "" : text.substring(nextSectionStart);
            return searchNameInText(invoiceRest, name);
        }
    }

    public static String searchTableInText (String text, int table) {
        String[] lines = text.split("\n");
        int[] counters = new int[2]; // [0] = personas, [1] = artículos
        double[] total = new double[1];

        // Buscar la mesa y procesar
        String tableStr = processTable(lines, 0, table, counters, total);
        if (tableStr != null) {
            return String.format(
                "Mesa %s\n" +
                "Nro. de Personas: %d\n" +
                "Total de Items: %d\n" +
                "Total de la Comanda: $%.1f"
            , tableStr, counters[0], counters[1], total[0]);
        }

        return null;
    }

    private static String processTable (String[] lines, int index, int table, int[] counters, double[] total) {
        if (index >= lines.length)
            return null;

        if (lines[index].startsWith("Mesa: ")) {
            int currentTable = Integer.parseInt(lines[index].split(": ")[1].trim());
            if (currentTable == table) {
                String result = processClients(lines, index + 1, counters, total); // Llamada recursiva para procesar el resto del documento
                return (result != null) ? String.valueOf(currentTable) : null; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
            }

            return null;
        }

        String result = processTable(lines, index + 1, table, counters, total); // Llamada recursiva para seguir buscando
        return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
    }

    private static String processClients (String[] lines, int index, int[] counters, double[] total) {
        if (index >= lines.length)
            return "fin";

        if (lines[index].startsWith("Cliente: ")) {
            counters[0]++; // Incrementar contador de personas
            String result = processItems(lines, index + 1, counters, total); // Llamada recursiva para procesar artículos del cliente
            return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
        }

        String result = processClients(lines, index + 1, counters, total); // Llamada recursiva para seguir procesando
        return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
    }

    private static String processItems (String[] lines, int index, int[] counters, double[] total) {
        if (index >= lines.length)
            return "fin";

        if (lines[index].contains("- Subtotal: $")) {
            String subtotalStr = lines[index].split("Subtotal: \\$")[1].split(" ")[0].trim();
            total[0] += Double.parseDouble(subtotalStr);
            counters[1]++; // Incrementar contador de artículos

            String result = processItems(lines, index + 1, counters, total); // Llamada recursiva para procesar siguiente artículo
            return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
        }

        if (lines[index].contains("Total de ")) {
            // Volver a procesar clientes
            // Llamada cruzada entre funciones -> Recursividad indirecta
            return processClients(lines, index + 1, counters, total);
        }

        String result = processItems(lines, index + 1, counters, total); // Llamada recursiva para seguir procesando
        return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
    }

    public static Stack<String> extractClients (String text) {
        Stack<String> result = new Stack<>();
        String[] lines = text.split("\n");

        for (String line : lines) {
            if (line.startsWith("Cliente: ")) {
                String clientName = line.substring(9).trim(); // Extraer el nombre del cliente
                result.push(clientName); // Agregar a la pila
            }
        }

        return result;
    }

    public static Queue<String> extractItems (String text) {
        Queue<String> result = new Queue<>();
        String[] lines = text.split("\n");
        boolean readingClient = false;

        for (String line : lines) {
            if (line.startsWith("Cliente: ")) {
                readingClient = true; // Comienza a procesar los ítems de un cliente
            } else if (line.startsWith("Total de ")) {
                readingClient = false; // Termina de procesar los ítems de un cliente
            } else if (readingClient && line.contains("Cantidad:")) {
                // Extraer el nombre del ítem y la cantidad
                String[] parts = line.split(" - ");
                String nameItem = parts[0].trim();
                String quantItem = parts[1].split(": ")[1].trim(); // Obtener la cantidad
                result.enqueue(nameItem + " x" + quantItem); // Formatear y agregar a la lista
            }
        }

        return result;
    }

    public static void desrefStack (Stack<String> stack) {
        System.out.println();
        while (stack.getSize() > 0) {
            System.out.println("Pop: " + stack.showStack());
            stack.pop();
        }
        System.out.println();
    }

    public static void desrefQueue (Queue<String> queue) {
        System.out.println();
        while (queue.getSize() > 0) {
            System.out.println("Dequeue: " + queue.getPeek());
            queue.dequeue();
        }
        System.out.println();
    }
}