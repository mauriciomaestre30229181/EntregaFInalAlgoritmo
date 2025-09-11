package JavaRace.process;

// IMPORTACIONES CORREGIDAS
import JavaRace.helpers.Validate;
import JavaRace.helpers.errorLog; // <-- Ahora se importa desde 'repositories'
import JavaRace.repositories.Participante;
import JavaRace.repositories.ParticipanteRepository;
import JavaRace.repositories.Patrocinador;
import java.util.Scanner;

public class ParticipanteService {
    private ParticipanteRepository participanteRepository;
    private final int MAX_PARTICIPANTES = 25;

    public ParticipanteService(ParticipanteRepository repo) {
        this.participanteRepository = repo;
    }

    private boolean isMaxParticipantsReached() {
        Participante[] participantes = participanteRepository.findAll();
        return countParticipantsRecursively(participantes, 0) >= MAX_PARTICIPANTES;
    }

    private int countParticipantsRecursively(Participante[] participantes, int index) {
        if (index >= participantes.length) {
            return 0; // Caso base
        }

        int count = (participantes[index] != null) ? 1 : 0;
        return count + countParticipantsRecursively(participantes, index + 1);
    }

    private Participante requestNewParticipantData(Scanner scanner) {
        String nombrePiloto = Validate.validarNombre(scanner, "Ingrese nombre del piloto: ");
        int edadPiloto = Validate.validarEdad(scanner, "Ingrese edad del piloto: ");

        Participante[] participantes = participanteRepository.findAll();
        int numeroParticipante = Validate.validarNumeroParticipante(scanner, "Ingrese número de participante: ", participantes);

        String marcaVehiculo = Validate.validarTexto(scanner, "Ingrese marca del vehículo: ");

        Participante nuevoParticipante = new Participante(nombrePiloto, edadPiloto, numeroParticipante, marcaVehiculo);

        int añoVehiculo = Validate.validarAño(scanner, "Ingrese año del vehículo: ");

        System.out.println("--- Datos del Patrocinador ---");
        String nombrePatrocinador = Validate.validarTexto(scanner, "Ingrese nombre del patrocinador: ");
        String industriaPatrocinador = Validate.validarTexto(scanner, "Ingrese industria del patrocinador: ");
        int anioFundacion = Validate.validarAño(scanner, "Ingrese año de fundación del patrocinador (e.g., 1980): ");
        Patrocinador patrocinador = new Patrocinador(nombrePatrocinador, industriaPatrocinador, anioFundacion);

        nuevoParticipante.setAñoVehiculo(añoVehiculo);
        nuevoParticipante.setPatrocinador(patrocinador);

        return nuevoParticipante;
    }

    public void registrarParticipante(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE PARTICIPANTE ===");

        if (isMaxParticipantsReached()) {
            System.out.println("Error: Máximo de participantes alcanzado.");
            return;
        }

        try {
            Participante nuevoParticipante = requestNewParticipantData(scanner);
            participanteRepository.save(nuevoParticipante);
            System.out.println("¡Participante registrado exitosamente!");
        } catch (Exception e) {
            System.out.println("Error al registrar participante: " + e.getMessage());
            errorLog.logError("Error al registrar participante: " + e.getMessage());
        }
    }

    public void eliminarParticipante(Scanner scanner) {
        System.out.println("\n=== ELIMINAR PARTICIPANTE ===");
        mostrarParticipantesDisponibles();
        int numeroParticipante = Validate.validarEntero(scanner, "Ingrese el número del participante a eliminar: ");

        Participante participante = participanteRepository.findByNumero(numeroParticipante);
        if (participante == null) {
            System.out.println("Error: No se encontró un participante con ese número.");
            return;
        }

        System.out.printf("¿Está seguro que desea eliminar a %s (Nº %d)? (S/N): ", participante.getNombrePiloto(), participante.getNumeroParticipante());
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {
            participanteRepository.delete(numeroParticipante);
            System.out.println("Participante eliminado exitosamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void displayAvailableParticipantsRecursively(Participante[] participantes, int index) {
        if (index >= participantes.length) {
            return; // Caso base
        }

        Participante p = participantes[index];
        if (p != null) {
            System.out.printf("Nº %d - %s%n", p.getNumeroParticipante(), p.getNombrePiloto());
        }

        displayAvailableParticipantsRecursively(participantes, index + 1);
    }

    public void mostrarParticipantesDisponibles() {
        System.out.println("\n--- PARTICIPANTES REGISTRADOS ---");
        Participante[] todosParticipantes = participanteRepository.findAll();

        if (todosParticipantes == null || countParticipantsRecursively(todosParticipantes, 0) == 0) {
            System.out.println("No hay participantes registrados.");
            return;
        }

        displayAvailableParticipantsRecursively(todosParticipantes, 0);
    }

    private void displayAllParticipantsSummaryRecursively(Participante[] participantes, int index) {
        if (index >= participantes.length) {
            return; // Caso base
        }

        Participante p = participantes[index];
        if (p != null) {
            mostrarResumenParticipante(p.getNumeroParticipante());
            System.out.println();
        }

        displayAllParticipantsSummaryRecursively(participantes, index + 1);
    }

    public void mostrarTodosLosParticipantes() {
        System.out.println("\n=== TODOS LOS PARTICIPANTES ===");
        Participante[] todosParticipantes = participanteRepository.findAll();

        if (todosParticipantes == null || countParticipantsRecursively(todosParticipantes, 0) == 0) {
            System.out.println("No hay participantes registrados.");
            return;
        }

        displayAllParticipantsSummaryRecursively(todosParticipantes, 0);
    }

    public void mostrarResumenParticipante(int numeroParticipante) {
        Participante participante = participanteRepository.findByNumero(numeroParticipante);
        if (participante != null) {
            System.out.println("\n--- RESUMEN DEL PARTICIPANTE ---");
            System.out.println("Piloto: " + participante.getNombrePiloto());
            System.out.println("Edad: " + participante.getEdadPiloto());
            System.out.println("Número: " + participante.getNumeroParticipante());
            System.out.println("Vehículo: " + participante.getMarcaVehiculo() + " " + participante.getAñoVehiculo());
            System.out.println("Patrocinador: " + participante.getNombrePatrocinador());
        } else {
            System.out.println("Participante con número " + numeroParticipante + " no encontrado.");
        }
    }
}
