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
        System.out.println("            MENÚ DE PROYECTOS");
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

        // Agregar los 8 proyectos
        lista.insert(new FinalProject("Proyecto Gym", () -> {
            try {
                projects.GymPersonal.main.Main.main(new String[0]);
            } catch (Exception e) {
                System.out.println("Error ejecutando el proyecto Gym: " + e.getMessage());
            }
        }));

        // Proyecto 2
        lista.insert(new FinalProject("Calculadora Científica", () -> {
            System.out.println("Ejecutando calculadora científica...");
            // Simular ejecución
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Calculadora finalizada.");
        }));

        // Proyecto 3
        lista.insert(new FinalProject("Gestor de Tareas", () -> {
            System.out.println("Ejecutando gestor de tareas...");
            // Simular ejecución
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Gestor de tareas finalizado.");
        }));

        // Proyecto 4
        lista.insert(new FinalProject("Sistema de Reservas", () -> {
            System.out.println("Ejecutando sistema de reservas...");
            // Simular ejecución
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Sistema de reservas finalizado.");
        }));

        // Proyecto 5
        lista.insert(new FinalProject("Juego de Memoria", () -> {
            System.out.println("Ejecutando juego de memoria...");
            // Simular ejecución
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Juego de memoria finalizado.");
        }));

        // Proyecto 6
        lista.insert(new FinalProject("Clima Mundial", () -> {
            System.out.println("Ejecutando aplicación de clima...");
            // Simular ejecución
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Aplicación de clima finalizada.");
        }));

        // Proyecto 7
        lista.insert(new FinalProject("Traductor Simple", () -> {
            System.out.println("Ejecutando traductor...");
            // Simular ejecución
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Traductor finalizado.");
        }));

        // Proyecto 8
        lista.insert(new FinalProject("Gestor de Contraseñas", () -> {
            System.out.println("Ejecutando gestor de contraseñas...");
            // Simular ejecución
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Gestor de contraseñas finalizado.");
        }));

        return lista;
    }


    @Override
    public String toString() {
        return name;
    }
}
