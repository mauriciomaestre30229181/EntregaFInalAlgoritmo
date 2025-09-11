package JavaRace.process;

import JavaRace.composables.ReportGenerator;
import JavaRace.repositories.CustomQueue;
import JavaRace.repositories.CustomStack;
import JavaRace.repositories.Participante;

public class OperacionesService {
    private CustomStack<String> pilaOperaciones;
    private CustomQueue<String> colaReportes;

    /**
     * Precondición: Ninguna
     * Postcondición: Se crea un servicio de operaciones con pila and cola vacías
     */
    public OperacionesService() {
        this.pilaOperaciones = new CustomStack<>();
        this.colaReportes = new CustomQueue<>();
    }

    /**
     * Precondición: El parámetro operacion no debe ser nulo
     * Postcondición: La operación es agregada a la pila de operaciones
     * @param operacion La operación a registrar
     */
    public void registrarOperacion(String operacion) {
        this.pilaOperaciones.push(operacion);
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna y remueve la última operación de la pila, o un mensaje si está vacía
     * @return La última operación o mensaje de pila vacía
     */
    public String deshacerUltimaOperacion() {
        try {
            return this.pilaOperaciones.pop();
        } catch (IllegalArgumentException e) {
            return "No hay operaciones para deshacer";
        }
    }

    /**
     * Precondición: El parámetro tipoReporte no debe ser nulo
     * Postcondición: El tipo de reporte es agregado a la cola de reportes
     * @param tipoReporte El tipo de reporte a encolar
     */
    public void encolarReporte(String tipoReporte) {
        this.colaReportes.enqueue(tipoReporte);
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna y remueve el siguiente reporte de la cola, o un mensaje si está vacía
     * @return El siguiente reporte o mensaje de cola vacía
     */
    public String procesarSiguienteReporte() {
        try {
            return this.colaReportes.dequeue();
        } catch (IllegalArgumentException e) {
            return "No hay reportes en cola";
        }
    }

    /**
     * Precondición: Los parámetros participantes y posiciones no deben ser nulos
     * Postcondición: Todos los reportes en cola son generados y mostrados en consola
     * @param participantes Array de participantes
     * @param posiciones Array de posiciones
     */
    public void generarReportesEnCola(Participante[] participantes, int[] posiciones) {
        while (!this.colaReportes.isEmpty()) {
            String tipoReporte = this.procesarSiguienteReporte();
            String reporte = "";

            switch (tipoReporte) {
                case "detallado":
                    reporte = ReportGenerator.generateDetailedRaceResultsReport(participantes, posiciones);
                    break;
                case "lista":
                    reporte = ReportGenerator.generateParticipantListReport(participantes);
                    break;
                case "tiempos":
                    reporte = ReportGenerator.generateRaceTimesSummaryReport(participantes);
                    break;
            }

            System.out.println("Reporte generado: " + tipoReporte);
            System.out.println(reporte);
        }
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna true si hay operaciones en la pila, false en caso contrario
     * @return true si hay operaciones, false en caso contrario
     */
    public boolean hayOperaciones() {
        return !this.pilaOperaciones.isEmpty();
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna true si hay reportes en la cola, false en caso contrario
     * @return true si hay reportes, false en caso contrario
     */
    public boolean hayReportesEnCola() {
        return !this.colaReportes.isEmpty();
    }
}