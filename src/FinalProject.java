import java.util.Scanner;

public class FinalProject {
    private String name;
    private Runnable runnable;

    public FinalProject(String name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
    }

    public String getName() {
        return name;
    }

    public void run() {
        if (runnable != null) {
            runnable.run();
        } else {
            System.out.println("-ERROR: No hay acción definida para este proyecto.");
        }
    }

    public static void showMenu(List<FinalProject> projectsList) {

        System.out.println("\n" + "=".repeat(50));
        System.out.println("                 MENÚ DE PROYECTOS");
        System.out.println("=".repeat(50));
        System.out.println("Proyectos disponibles:");

        for (int i = 0; i < projectsList.getSize(); i++) {
            FinalProject finalProject = projectsList.getNodeByPos(i).getData();
            System.out.printf("%2d. %s\n", (i + 1), finalProject.getName());
        }

        System.out.println("-".repeat(50));
        System.out.println(" 0. Salir");
        System.out.print("Seleccione un proyecto: ");
        System.out.flush();
    }

    public static List<FinalProject> initializeProjects() {
        List<FinalProject> lista = new List<>();

        lista.insert(new FinalProject("Sistema Gimnasio", () -> {
            try {
                GymPersonal.main.Main.main(new String[0]);
            } catch (Exception e) {
                System.out.println("Error ejecutando el proyecto Gym: " + e.getMessage());
            }
        }));

        // Proyecto 2
        lista.insert(new FinalProject("Sistema Factura Electrica", () -> {
            try {
                ElectricBill.arr.Main.main(new String[0]);
            } catch (Exception e) {
                System.out.println("Error ejecutando Sistema Factura Electrica: " + e.getMessage());
            }
        }));

        // Proyecto 3
        lista.insert(new FinalProject("Butacas de Cine", () -> {
            try {
                src_ProyectoButacas.Main.main(new String[0]);
            } catch (Exception e) {
                System.out.println("Error ejecutando Butacas de Cine: " + e.getMessage());
            }
        }));

        // Proyecto 4
        lista.insert(new FinalProject("JavaRace", () -> {
            try {
                JavaRace.Main.main(new String[0]);
            } catch (Exception e) {
                System.out.println("Error ejecutando JavaRace: " + e.getMessage());
            }
        }));

        // Proyecto 5
        lista.insert(new FinalProject("Cinema", () -> {
            try {
                cinema.Main.main(new String[0]);
            } catch (Exception e) {
                System.out.println("Error ejecutando Cinema: " + e.getMessage());
            }
        }));

        // Proyecto 6
        lista.insert(new FinalProject("Sistema Restaurante", () -> {
            try {
                src_restaurant.main.Main.main(new String[0]);
            } catch (Exception e) {
                System.out.println("Error ejecutando el Sistema Restaurante: " + e.getMessage());
            }
        }));

        // Proyecto 7
        lista.insert(new FinalProject("Inventario Camisetas", () -> {
            try {
                src_inventario.arr.Main.main(new String[0]);
            } catch (Exception e) {
                System.out.println("Error ejecutando Inventario Camisetas: " + e.getMessage());
            }
        }));

        return lista;
    }


    @Override
    public String toString() {
        return name;
    }
}
