package JavaRace.repositories;

public class InMemoryParticipanteRepository implements ParticipanteRepository {
    private Participante[] participantes;
    private final int MAX_PARTICIPANTES = 25;

    public InMemoryParticipanteRepository() {
        participantes = new Participante[MAX_PARTICIPANTES];
    }

    private int findEmptySlotRecursively(int index) {
        if (index >= participantes.length) {
            return -1; // Caso base - no hay espacio
        }

        if (participantes[index] == null) {
            return index;
        }

        return findEmptySlotRecursively(index + 1);
    }

    private int findParticipantIndexRecursively(int numeroParticipante, int index) {
        if (index >= participantes.length) {
            return -1; // Caso base - no encontrado
        }

        if (participantes[index] != null && participantes[index].getNumeroParticipante() == numeroParticipante) {
            return index;
        }

        return findParticipantIndexRecursively(numeroParticipante, index + 1);
    }

    @Override
    public void save(Participante participante) {
        if (findParticipantIndexRecursively(participante.getNumeroParticipante(), 0) != -1) {
            System.err.println("Error: Participante con número " + participante.getNumeroParticipante() + " ya existe.");
            return;
        }

        int emptySlot = findEmptySlotRecursively(0);
        if (emptySlot != -1) {
            participantes[emptySlot] = participante;
        } else {
            System.err.println("Error: No hay espacio para más participantes.");
        }
    }

    @Override
    public Participante findByNumero(int numeroParticipante) {
        int index = findParticipantIndexRecursively(numeroParticipante, 0);
        return index != -1 ? participantes[index] : null;
    }

    @Override
    public Participante[] findAll() {
        Participante[] copia = new Participante[participantes.length];
        System.arraycopy(participantes, 0, copia, 0, participantes.length);
        return copia;
    }

    @Override
    public void update(Participante updatedParticipante) {
        int index = findParticipantIndexRecursively(updatedParticipante.getNumeroParticipante(), 0);
        if (index != -1) {
            participantes[index] = updatedParticipante;
            System.out.println("DEBUG: Participante " + updatedParticipante.getNumeroParticipante() + " actualizado"); // Debug
        } else {
            System.err.println("Error: Participante a actualizar con número " +
                    updatedParticipante.getNumeroParticipante() + " no encontrado.");
        }
    }

    @Override
    public void delete(int numeroParticipante) {
        int index = findParticipantIndexRecursively(numeroParticipante, 0);
        if (index != -1) {
            participantes[index] = null;
        } else {
            System.err.println("Error: Participante a eliminar con número " + numeroParticipante + " no encontrado.");
        }
    }
}