package JavaRace.process;

import JavaRace.helpers.Validate;
import JavaRace.helpers.errorLog;
import JavaRace.repositories.Participante;
import JavaRace.repositories.ParticipanteRepository;
import JavaRace.repositories.TiempoCarrera;
import java.util.Scanner;

public class TiempoService {
    private ParticipanteRepository participanteRepository;

    public TiempoService(ParticipanteRepository repo) {
        participanteRepository = repo;
    }

    private int getParticipantNumberForTimeRegistration(Scanner scanner) {
        new ParticipanteService(participanteRepository).mostrarParticipantesDisponibles();
        return Validate.validarEntero(scanner, "Ingrese número de participante: ");
    }

    private Participante validateAndGetParticipant(int numeroParticipante, Scanner scanner) {
        Participante participante = participanteRepository.findByNumero(numeroParticipante);

        if (participante == null) {
            System.out.println("Error: Participante no encontrado.");
            return null;
        }

        if (participante.getTiempoCarrera().isPresent()) {
            System.out.println("Este participante ya tiene tiempos registrados. ¿Desea sobrescribirlos? (S/N)");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("S")) {
                return null;
            }
        }
        return participante;
    }

    private TiempoCarrera requestLapTimes(Scanner scanner) {
        double vuelta1 = Validate.validarTiempo(scanner, "Tiempo vuelta 1 (segundos): ");
        double vuelta2 = Validate.validarTiempo(scanner, "Tiempo vuelta 2 (segundos): ");
        double vuelta3 = Validate.validarTiempo(scanner, "Tiempo vuelta 3 (segundos): ");
        return new TiempoCarrera(vuelta1, vuelta2, vuelta3);
    }

    private void updateAndSaveParticipantTimes(Participante participante, TiempoCarrera tiempos) {
        participante.setTiempoCarrera(tiempos);
        participanteRepository.update(participante);

        // DEBUG
        System.out.println("Participante actualizado:");
        System.out.println("Número: " + participante.getNumeroParticipante());
        System.out.println("Tiempo total: " + tiempos.getTiempoTotal());
    }

    public void registrarTiempo(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE TIEMPOS ===");

        Participante[] participantes = participanteRepository.findAll();
        if (participantes == null || participantes.length == 0) {
            System.out.println("Error: No hay participantes registrados.");
            return;
        }

        int numeroParticipante = getParticipantNumberForTimeRegistration(scanner);
        Participante participante = validateAndGetParticipant(numeroParticipante, scanner);

        if (participante != null) {
            try {
                System.out.println("Registrando tiempos para: " + participante.getNombrePiloto());
                TiempoCarrera tiempos = requestLapTimes(scanner);
                updateAndSaveParticipantTimes(participante, tiempos);

                // DEBUG: Verificar que el tiempo se guardó
                System.out.println("Tiempo guardado: " + participante.getTiempoCarrera().get().getTiempoTotal());
            } catch (Exception e) {
                System.out.println("Error al registrar tiempos: " + e.getMessage());
                errorLog.logError("Error al registrar tiempos: " + e.getMessage());
            }
        }
    }
}