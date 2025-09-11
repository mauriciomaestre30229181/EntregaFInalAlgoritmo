package JavaRace.process;

import JavaRace.repositories.Participante;
import JavaRace.repositories.ParticipanteRepository;
import JavaRace.helpers.errorLog;
import JavaRace.util.ManejoArchivos;
import JavaRace.config.Storage;

public class ReporteService {
    private ParticipanteRepository participanteRepository;
    private ClasificacionService clasificacionService;

    public ReporteService(ParticipanteRepository repo, ClasificacionService cService) {
        participanteRepository = repo;
        clasificacionService = cService;
    }

    public void guardarEnArchivo() {
        System.out.println("\n=== GUARDANDO DATOS EN ARCHIVO ===");
        Participante[] todosParticipantes = participanteRepository.findAll();
        boolean hayTiempos = false;
        for (Participante p : todosParticipantes) {
            if (p != null && p.getTiempoCarrera().isPresent() && p.getTiempoTotal() > 0) {
                hayTiempos = true;
                break;
            }
        }
        if (!hayTiempos) {
            System.out.println("ERROR: No hay participantes con tiempos registrados.");
            System.out.println("Debe:");
            System.out.println("1. Registrar participantes (Opción 1)");
            System.out.println("2. Registrar tiempos (Opción 2)");
            System.out.println("3. Calcular clasificación (Opción 3)");
            return;
        }
        if (clasificacionService.getPosicionesFinales() == null) {
            System.out.println("Calculando clasificación automáticamente...");
            clasificacionService.calcularClasificacion();
        }
        int[] posiciones = clasificacionService.getPosicionesFinales();
        boolean posicionesValidas = false;
        for (int i = 1; i < posiciones.length; i++) {
            if (posiciones[i] > 0) {
                posicionesValidas = true;
                break;
            }
        }

        if (!posicionesValidas) {
            System.out.println("ERROR: No se pudieron calcular posiciones válidas.");
            return;
        }

        try {
            Storage.ensureStorageDirectoryExists();
            ManejoArchivos.guardarDatos(todosParticipantes, posiciones, Storage.getStoragePath());
            System.out.println("¡Datos guardados exitosamente!");
        } catch (Exception e) {
            System.out.println("ERROR CRÍTICO: " + e.getMessage());
            errorLog.logError("Error al guardar: " + e.getMessage());
        }
    }


}