package JavaRace.repositories;

import JavaRace.process.OptionalTiempo;

public class Participante {
    private String nombrePiloto;
    private int edadPiloto;
    private int numeroParticipante;
    private String marcaVehiculo;
    private int añoVehiculo;
    private Patrocinador patrocinador;
    private TiempoCarrera tiempoCarrera;

    public Participante(String nombrePiloto, int edad, int numero, String marca) {
        this.nombrePiloto = nombrePiloto;
        edadPiloto = edad;
        numeroParticipante = numero;
        marcaVehiculo = marca;
    }

    public void setAñoVehiculo(int año) {
        añoVehiculo = año;
    }

    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
    }

    public String getNombrePiloto() {
        return nombrePiloto;
    }

    public int getEdadPiloto() {
        return edadPiloto;
    }

    public int getNumeroParticipante() {
        return numeroParticipante;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public int getAñoVehiculo() {
        return añoVehiculo;
    }

    public String getNombrePatrocinador() {
        return (patrocinador != null) ? patrocinador.getNombre() : "N/A";
    }

    public Patrocinador getPatrocinador() {
        return patrocinador;
    }

    public OptionalTiempo getTiempoCarrera() {
        return new OptionalTiempo(tiempoCarrera);
    }

    public void setTiempoCarrera(TiempoCarrera tiempo) {
        tiempoCarrera = tiempo;
    }

    public double getTiempoTotal() {
        return tiempoCarrera != null ? tiempoCarrera.getTiempoTotal() : 0.0;
    }

    public String toString() {
        return "Participante{" +
                "nombrePiloto='" + nombrePiloto + '\'' +
                ", edadPiloto=" + edadPiloto +
                ", numeroParticipante=" + numeroParticipante +
                ", marcaVehiculo='" + marcaVehiculo + '\'' +
                ", añoVehiculo=" + añoVehiculo +
                ", patrocinador=" + (patrocinador != null ? patrocinador.getNombre() : "N/A") +
                ", tiempoCarrera=" + (tiempoCarrera != null ? tiempoCarrera.getTiempoTotal() : "N/A") +
                '}';
    }
}