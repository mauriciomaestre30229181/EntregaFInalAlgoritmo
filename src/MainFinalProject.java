import java.util.Scanner;
import java.util.InputMismatchException;

public class MainFinalProject {
    public static void main(String[] args) {
        List<FinalProject> projectsList = FinalProject.initializeProjects();
        Scanner sc = new Scanner(System.in);
        int option = -1;

        do {
            try {
                FinalProject.showMenu(projectsList);
                option = sc.nextInt();
                sc.nextLine(); //

                if (option > 0 && option <= projectsList.getSize()) {
                    FinalProject selectedProject = projectsList.getNodeByPos(option - 1).getData();
                    System.out.println("\n>>> Ejecutando: " + selectedProject.getName() + " <<<\n");

                    // Ejecutar el proyecto
                    selectedProject.run();

                    // Pausa para permitir al usuario ver el resultado
                    System.out.println("\nPresione Enter para continuar...");
                    sc.nextLine();

                } else if (option != 0) {
                    System.out.println("- ERROR: Opción inválida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("- ERROR: Debe ingresar un número válido.");
                sc.nextLine();
                option = -1;
            } catch (Exception e) {
                System.out.println("- ERROR inesperado: " + e.getMessage());
                sc.nextLine();
                option = -1;
            }
        } while (option != 0);
        System.out.println("Fin del Proyecto Final Grupal de Algoritmos.");
        sc.close();
    }

}
