package repositories;


public class TiempoCarrera {
    private double vuelta1;
    private double vuelta2;
    private double vuelta3;
    private double tiempoTotal;

    public TiempoCarrera(double v1, double v2, double v3) {
        if (v1 < 0 || v2 < 0 || v3 < 0) {
            throw new IllegalArgumentException("Los tiempos de vuelta no pueden ser negativos.");
        }
        vuelta1 = v1;
        vuelta2 = v2;
        vuelta3 = v3;
        tiempoTotal = calcularTiempoTotal();
    }

    public double getTiempoTotal() {
        return tiempoTotal;
    }

    private double calcularTiempoTotal() {
        return vuelta1 + vuelta2 + vuelta3;
    }

    public static String formatearTiempo(double segundos) {
        if (segundos <= 0) {
            return "00:00.00";
        }

        int minutos = (int) (segundos / 60);
        double segundosRestantes = segundos % 60;

        return String.format("%02d:%05.2f", minutos, segundosRestantes);
    }
}