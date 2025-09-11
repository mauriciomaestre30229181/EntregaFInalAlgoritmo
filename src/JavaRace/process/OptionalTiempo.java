package JavaRace.process;


import JavaRace.repositories.TiempoCarrera;

public class OptionalTiempo {
    private TiempoCarrera tiempo;

    public OptionalTiempo(TiempoCarrera tiempoParam) {
        tiempo = tiempoParam;
    }

    public boolean isPresent() {
        return tiempo != null;
    }

    public TiempoCarrera get() {
        if (tiempo == null) {
            throw new RuntimeException("No hay tiempo presente");
        }
        return tiempo;
    }

    public boolean isEmpty() {
        return tiempo == null;
    }
}