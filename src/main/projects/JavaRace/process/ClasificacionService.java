package process;

import repositories.Participante;
import repositories.ParticipanteRepository;
import repositories.TiempoCarrera;

public class ClasificacionService {
    private ParticipanteRepository participanteRepository;
    private int[] posicionesFinales;

    public ClasificacionService(ParticipanteRepository repo) {
        participanteRepository = repo;
        posicionesFinales = new int[26]; // Índice 0 no se usa, 1-25 para participantes
    }



    public void calcularClasificacion() {
        System.out.println("\n=== CALCULANDO CLASIFICACIÓN FINAL ===");
        posicionesFinales = new int[26]; // Índices 1-25 para participantes

        Participante[] todosParticipantes = participanteRepository.findAll();
        if (todosParticipantes == null) {
            System.out.println("Error: No hay participantes registrados.");
            return;
        }

        int participantesValidos = 0;
        for (Participante p : todosParticipantes) {
            if (p != null && p.getTiempoCarrera().isPresent() && p.getTiempoTotal() > 0) {
                participantesValidos++;
            }
        }

        if (participantesValidos == 0) {
            System.out.println("Error: Ningún participante tiene tiempos registrados.");
            return;
        }

        Participante[] participantesConTiempos = new Participante[participantesValidos];
        int index = 0;
        for (Participante p : todosParticipantes) {
            if (p != null && p.getTiempoCarrera().isPresent() && p.getTiempoTotal() > 0) {
                participantesConTiempos[index++] = p;
            }
        }

        for (int i = 0; i < participantesConTiempos.length - 1; i++) {
            for (int j = 0; j < participantesConTiempos.length - i - 1; j++) {
                if (participantesConTiempos[j].getTiempoTotal() > participantesConTiempos[j+1].getTiempoTotal()) {
                    Participante temp = participantesConTiempos[j];
                    participantesConTiempos[j] = participantesConTiempos[j+1];
                    participantesConTiempos[j+1] = temp;
                }
            }
        }


        for (int i = 0; i < participantesConTiempos.length; i++) {
            int numParticipante = participantesConTiempos[i].getNumeroParticipante();
            posicionesFinales[numParticipante] = i + 1; // Posición basada en 1

            // Debug: Mostrar asignación
            System.out.printf("Participante %d - Tiempo: %.2f - Posición: %d%n",
                    numParticipante,
                    participantesConTiempos[i].getTiempoTotal(),
                    i + 1);
        }

        System.out.println("¡Clasificación calculada exitosamente!");
    }

    private void displayParticipantLineRecursively(Participante[] participantes, int index) {
        if (index >= participantes.length || participantes[index] == null) {
            return; // Caso base
        }

        Participante p = participantes[index];
        if (posicionesFinales[p.getNumeroParticipante()] > 0) {
            if (p.getTiempoCarrera().isPresent()) {
                TiempoCarrera t = p.getTiempoCarrera().get();
                System.out.printf("%2d  | %-12s | %-8s | %-8s | %-12s%n",
                        posicionesFinales[p.getNumeroParticipante()],
                        p.getNombrePiloto(),
                        p.getMarcaVehiculo(),
                        TiempoCarrera.formatearTiempo(t.getTiempoTotal()),
                        p.getNombrePatrocinador());
            }
        }

        displayParticipantLineRecursively(participantes, index + 1);
    }

    public void mostrarClasificacionFinal() {
        System.out.println("\n=== CLASIFICACIÓN FINAL ===");

        Participante[] todosParticipantes = participanteRepository.findAll();
        if (todosParticipantes == null) {
            System.out.println("No hay participantes registrados.");
            return;
        }

        System.out.println("POS | PILOTO       | VEHÍCULO | TIEMPO   | PATROCINADOR");
        System.out.println("-".repeat(70));
        displayParticipantLineRecursively(todosParticipantes, 0);
    }

    public int[] getPosicionesFinales() {
        int[] copia = new int[posicionesFinales.length];
        System.arraycopy(posicionesFinales, 0, copia, 0, posicionesFinales.length);
        return copia;
    }
}