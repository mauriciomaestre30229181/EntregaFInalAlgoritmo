package repositories; // <-- ASEGÚRATE de que este paquete sea 'repositories'

import java.time.LocalDate;

public class Patrocinador {
    private String nombre;
    private String industria;
    private int anioFundacion;

    public Patrocinador(String nombre, String industria, int anioFundacion) {
        setNombre(nombre);
        setIndustria(industria);
        setAnioFundacion(anioFundacion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del patrocinador no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getIndustria() {
        return industria;
    }

    public void setIndustria(String industria) {
        if (industria == null || industria.trim().isEmpty()) {
            throw new IllegalArgumentException("La industria del patrocinador no puede estar vacía.");
        }
        this.industria = industria;
    }

    public int getAnioFundacion() {
        return anioFundacion;
    }

    public void setAnioFundacion(int anioFundacion) {
        int anioActual = LocalDate.now().getYear();
        if (anioFundacion < 1800 || anioFundacion > anioActual) {
            throw new IllegalArgumentException("El año de fundación debe ser realista (entre 1800 y " + anioActual + ").");
        }
        this.anioFundacion = anioFundacion;
    }

    @Override
    public String toString() {
        return "Patrocinador{" +
                "nombre='" + nombre + '\'' +
                ", industria='" + industria + '\'' +
                ", anioFundacion=" + anioFundacion +
                '}';
    }
}
