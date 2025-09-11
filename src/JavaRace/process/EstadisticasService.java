package JavaRace.process;

import JavaRace.helpers.Validate;
import JavaRace.repositories.Participante;
import JavaRace.repositories.ParticipanteRepository;
import java.util.Scanner;

public class EstadisticasService {
    private ParticipanteRepository participanteRepository;

    public EstadisticasService(ParticipanteRepository repo) {
        participanteRepository = repo;
    }

    private double findMinTimeRecursively(Participante[] participantes, int index, double currentMin) {
        if (index >= participantes.length) {
            return (currentMin == Double.MAX_VALUE) ? 0.0 : currentMin; // Caso base
        }

        Participante p = participantes[index];
        if (p != null && p.getTiempoCarrera().isPresent() && p.getTiempoTotal() > 0) {
            currentMin = Math.min(currentMin, p.getTiempoTotal());
        }

        return findMinTimeRecursively(participantes, index + 1, currentMin);
    }

    public double encontrarMejorTiempo() {
        Participante[] participantes = participanteRepository.findAll();
        if (participantes == null || participantes.length == 0) {
            return 0.0;
        }
        return findMinTimeRecursively(participantes, 0, Double.MAX_VALUE);
    }

    private double findMaxTimeRecursively(Participante[] participantes, int index, double currentMax) {
        if (index >= participantes.length) {
            return currentMax; // Caso base
        }

        Participante p = participantes[index];
        if (p != null && p.getTiempoCarrera().isPresent() && p.getTiempoTotal() > 0) {
            currentMax = Math.max(currentMax, p.getTiempoTotal());
        }

        return findMaxTimeRecursively(participantes, index + 1, currentMax);
    }

    public double encontrarPeorTiempo() {
        Participante[] participantes = participanteRepository.findAll();
        if (participantes == null || participantes.length == 0) {
            return 0.0;
        }
        return findMaxTimeRecursively(participantes, 0, 0.0);
    }


    private double[] calculateAverageRecursively(Participante[] participantes, int index, double sum, int count) {
        if (index >= participantes.length) {
            return new double[]{sum, count}; // Caso base
        }

        Participante p = participantes[index];
        if (p != null && p.getTiempoCarrera().isPresent() && p.getTiempoTotal() > 0) {
            sum += p.getTiempoTotal();
            count++;
        }

        return calculateAverageRecursively(participantes, index + 1, sum, count);
    }

    public double calcularTiempoPromedioGeneral() {
        Participante[] participantes = participanteRepository.findAll();
        if (participantes == null || participantes.length == 0) {
            return 0.0;
        }

        double[] result = calculateAverageRecursively(participantes, 0, 0.0, 0);
        return result[1] > 0 ? result[0] / result[1] : 0.0;
    }


    private int countParticipantsWithTimeRecursively(Participante[] participantes, int index, int count) {
        if (index >= participantes.length) {
            return count; // Caso base
        }

        Participante p = participantes[index];
        if (p != null && p.getTiempoCarrera().isPresent() && p.getTiempoTotal() > 0) {
            count++;
        }

        return countParticipantsWithTimeRecursively(participantes, index + 1, count);
    }

    public int contarParticipantesConTiempo() {
        Participante[] participantes = participanteRepository.findAll();
        if (participantes == null || participantes.length == 0) {
            return 0;
        }
        return countParticipantsWithTimeRecursively(participantes, 0, 0);
    }

    private int[] requestParticipantNumbers(Scanner scanner) {
        int numeroParticipante1 = Validate.validarEntero(scanner, "Ingrese número del participante 1: ");
        int numeroParticipante2 = Validate.validarEntero(scanner, "Ingrese número del participante 2: ");
        return new int[]{numeroParticipante1, numeroParticipante2};
    }

    private double[] getParticipantTimes(int num1, int num2) {
        Participante p1 = participanteRepository.findByNumero(num1);
        Participante p2 = participanteRepository.findByNumero(num2);

        double time1 = -1;
        double time2 = -1;

        if (p1 != null && p1.getTiempoCarrera().isPresent()) {
            time1 = p1.getTiempoCarrera().get().getTiempoTotal();
        }

        if (p2 != null && p2.getTiempoCarrera().isPresent()) {
            time2 = p2.getTiempoCarrera().get().getTiempoTotal();
        }

        return new double[]{time1, time2};
    }

    private void displayTimeDifference(int num1, int num2, double diff) {
        System.out.printf("Diferencia de tiempo entre %d y %d: %.2f segundos%n", num1, num2, diff);
    }

    public void calcularDiferenciaTiempo() {
        System.out.println("Calcular diferencia entre dos participantes:");
        Scanner scanner = new Scanner(System.in);
        int[] participantNumbers = requestParticipantNumbers(scanner);
        int num1 = participantNumbers[0];
        int num2 = participantNumbers[1];

        double[] times = getParticipantTimes(num1, num2);
        double time1 = times[0];
        double time2 = times[1];

        if (time1 > 0 && time2 > 0) {
            double diferencia = Math.abs(time1 - time2);
            displayTimeDifference(num1, num2, diferencia);
        } else {
            System.out.println("No se pudo calcular la diferencia. Verifique los números de participante y si tienen tiempos registrados.");
        }
    }
}