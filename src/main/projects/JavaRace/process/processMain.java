package process;

import repositories.InMemoryParticipanteRepository;
import repositories.ParticipanteRepository;
import java.util.Scanner;
import helpers.Validate;


public class processMain {
    private MenuController menuController;
    private Scanner scanner;

    public processMain() {
        ParticipanteRepository participanteRepository = new InMemoryParticipanteRepository();
        scanner = new Scanner(System.in);

        ParticipanteService participanteService = new ParticipanteService(participanteRepository);
        TiempoService tiempoService = new TiempoService(participanteRepository);
        ClasificacionService clasificacionService = new ClasificacionService(participanteRepository);
        ReporteService reporteService = new ReporteService(participanteRepository, clasificacionService);
        EstadisticasService estadisticasService = new EstadisticasService(participanteRepository);
        BusquedaService busquedaService = new BusquedaService();
        ConsultaService consultaService = new ConsultaService(participanteRepository);

        menuController = new MenuController(participanteService, tiempoService, clasificacionService, reporteService);
        menuController.setEstadisticasService(estadisticasService);
        menuController.setBusquedaService(busquedaService);
        menuController.setConsultaService(consultaService);
        menuController.setScanner(scanner);

        System.out.println("Sistema de Carrera inicializado correctamente.");
    }

    private void mostrarMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MENÚ PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. Registrar participante");
        System.out.println("2. Registrar tiempos");
        System.out.println("3. Calcular clasificación");
        System.out.println("4. Guardar datos en archivo");
        System.out.println("5. Mostrar todos los participantes");
        System.out.println("6. Mostrar clasificación final");
        System.out.println("7. Eliminar Participante");
        System.out.println("8. Encontrar mejor tiempo");
        System.out.println("9. Encontrar peor tiempo");
        System.out.println("10. Calcular tiempo promedio");
        System.out.println("11. Contar participantes con tiempo");
        System.out.println("12. Calcular diferencia de tiempo");
        System.out.println("13. Buscar en reportes de carrera");
        System.out.println("14. Consultar y Agrupar Datos ");
        System.out.println("0. Salir");
        System.out.println("=".repeat(50));
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = Validate.validarEntero(scanner, "Seleccione una opción: ");

            menuController.manejarOpcion(opcion);
            salir = (opcion == 0);
        }
        scanner.close();
    }
}