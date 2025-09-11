package JavaRace.repositories;

public interface ParticipanteRepository {
    void save(Participante participante);
    Participante findByNumero(int numeroParticipante);
    Participante[] findAll();
    void update(Participante participante);
    void delete(int numeroParticipante);
}