package JavaRace.process;

import java.util.Scanner;


public class MenuController {
    private ParticipanteService participanteService;
    private TiempoService tiempoService;
    private ClasificacionService clasificacionService;
    private ReporteService reporteService;
    private EstadisticasService estadisticasService;
    private BusquedaService busquedaService;
    private ConsultaService consultaService;
    private Scanner scanner;

    public MenuController(ParticipanteService pService, TiempoService tService, ClasificacionService cService, ReporteService rService) {
        participanteService = pService;
        tiempoService = tService;
        clasificacionService = cService;
        reporteService = rService;
    }

    public void setEstadisticasService(EstadisticasService eService) {
        estadisticasService = eService;
    }

    public void setBusquedaService(BusquedaService bService) {
        busquedaService = bService;
    }

    public void setConsultaService(ConsultaService qService) {
        consultaService = qService;
    }

    public void setScanner(Scanner sc) {
        scanner = sc;
    }


    public void manejarOpcion(int opcion) {

        switch (opcion) {
            case 1:
                participanteService.registrarParticipante(scanner);
                break;
            case 2:
                tiempoService.registrarTiempo(scanner);
                break;
            case 3:
                clasificacionService.calcularClasificacion();
                break;
            case 4:
                reporteService.guardarEnArchivo();
                break;
            case 5:
                participanteService.mostrarTodosLosParticipantes();
                break;
            case 6:
                clasificacionService.mostrarClasificacionFinal();
                break;
            case 7:
                participanteService.eliminarParticipante(scanner);
                break;
            case 8:
                System.out.printf("Mejor tiempo: %.2f segundos%n", estadisticasService.encontrarMejorTiempo());
                break;
            case 9:
                System.out.printf("Peor tiempo: %.2f segundos%n", estadisticasService.encontrarPeorTiempo());
                break;
            case 10:
                System.out.printf("Tiempo promedio: %.2f segundos%n", estadisticasService.calcularTiempoPromedioGeneral());
                break;
            case 11:
                System.out.printf("Participantes con tiempo: %d%n", estadisticasService.contarParticipantesConTiempo());
                break;
            case 12:
                estadisticasService.calcularDiferenciaTiempo();
                break;
            case 13:
                busquedaService.iniciarBusqueda(scanner);
                break;
            case 14:
                consultaService.iniciarConsulta(scanner);
                break;
            case 0:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }
}