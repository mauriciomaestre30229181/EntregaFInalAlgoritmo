package JavaRace.composables;

import JavaRace.repositories.Participante;
import JavaRace.repositories.TiempoCarrera;

public class ReportGenerator {


    private static String repeatCharNonTail(char c, int count) {
        if (count <= 0) {
            return "";
        }
        return c + repeatCharNonTail(c, count - 1);
    }


    private static void appendReportHeader(StringBuilder report, String title, String columns) {
        report.append("=== ").append(title).append(" ===\n");
        report.append(columns).append("\n");
        report.append(repeatCharNonTail('-', 80)).append("\n");
    }

    private static void getSortedParticipantsByPosition(Participante[] participantes, int[] posiciones, Participante[] sortedResult, int currentIndex) {
        if (currentIndex >= participantes.length) {
            return; // Caso base
        }

        Participante current = participantes[currentIndex];
        if (current != null && current.getTiempoCarrera().isPresent() &&
                current.getNumeroParticipante() <= posiciones.length &&
                posiciones[current.getNumeroParticipante() - 1] > 0) {

            int position = posiciones[current.getNumeroParticipante() - 1];
            if (position <= sortedResult.length) {
                sortedResult[position - 1] = current;
            }
        }

        getSortedParticipantsByPosition(participantes, posiciones, sortedResult, currentIndex + 1);
    }

    private static StringBuilder buildDetailedReportBodyRecursively(StringBuilder report, Participante[] sortedParticipants, int[] posiciones, int currentIndex) {
        if (currentIndex >= sortedParticipants.length) {
            return report; // Caso base
        }

        Participante p = sortedParticipants[currentIndex];
        if (p != null && p.getTiempoCarrera().isPresent()) {
            TiempoCarrera tiempo = p.getTiempoCarrera().get();
            String posicion = String.valueOf(currentIndex + 1);
            String piloto = p.getNombrePiloto();
            double tiempoTotal = tiempo.getTiempoTotal();
            String estado = determineRaceStatus(tiempoTotal);
            String vehiculo = p.getMarcaVehiculo();
            String patrocinador = p.getNombrePatrocinador();

            report.append(String.format("%-3s | %-12s | %-8s | %-9s | %-8s | %-12s%n",
                    posicion, piloto, TiempoCarrera.formatearTiempo(tiempoTotal),
                    estado, vehiculo, patrocinador));
        }

        return buildDetailedReportBodyRecursively(report, sortedParticipants, posiciones, currentIndex + 1);
    }

    private static String determineRaceStatus(double time) {
        return checkExcellentStatus(time);
    }

    private static String checkExcellentStatus(double time) {
        if (time < 180.0) {
            return "Excelente";
        }
        return checkGoodStatus(time);
    }

    private static String checkGoodStatus(double time) {
        if (time < 240.0) {
            return "Bueno";
        }
        return checkRegularStatus(time);
    }

    private static String checkRegularStatus(double time) {
        return "Regular";
    }


    public static String generateDetailedRaceResultsReport(Participante[] participantes, int[] posiciones) {
        StringBuilder report = new StringBuilder();
        appendReportHeader(report, "REPORTE DETALLADO DE RESULTADOS DE CARRERA",
                "POS | PILOTO       | TIEMPO   | ESTADO    | VEHÍCULO | PATROCINADOR");

        Participante[] sortedParticipants = new Participante[25]; // Máximo 25 participantes
        getSortedParticipantsByPosition(participantes, posiciones, sortedParticipants, 0);

        buildDetailedReportBodyRecursively(report, sortedParticipants, posiciones, 0);
        report.append(repeatCharNonTail('-', 80)).append("\n");
        return report.toString();
    }

    private static StringBuilder buildParticipantListReportBodyRecursively(StringBuilder report, Participante[] participantes, int currentIndex) {
        if (currentIndex >= participantes.length) {
            return report; // Caso base
        }

        Participante p = participantes[currentIndex];
        if (p != null) {
            report.append(String.format("%-3d | %-12s | %-4d | %-8s | %-4d | %-12s%n",
                    p.getNumeroParticipante(),
                    p.getNombrePiloto(),
                    p.getEdadPiloto(),
                    p.getMarcaVehiculo(),
                    p.getAñoVehiculo(),
                    p.getNombrePatrocinador()));
        }

        return buildParticipantListReportBodyRecursively(report, participantes, currentIndex + 1);
    }

    public static String generateParticipantListReport(Participante[] participantes) {
        StringBuilder report = new StringBuilder();
        appendReportHeader(report, "REPORTE DE LISTA DE PARTICIPANTES",
                "Nº  | PILOTO       | EDAD | VEHÍCULO | AÑO  | PATROCINADOR");

        buildParticipantListReportBodyRecursively(report, participantes, 0);
        report.append(repeatCharNonTail('-', 80)).append("\n");
        return report.toString();
    }

    private static StringBuilder buildRaceTimesSummaryReportBodyRecursively(StringBuilder report, Participante[] participantes, int currentIndex) {
        if (currentIndex >= participantes.length) {
            return report; // Caso base
        }

        Participante p = participantes[currentIndex];
        if (p != null && p.getTiempoCarrera().isPresent() && p.getTiempoTotal() > 0) {
            TiempoCarrera tiempo = p.getTiempoCarrera().get();
            report.append(String.format("%-3d | %-12s | %-23.2f | %s%n",
                    p.getNumeroParticipante(),
                    p.getNombrePiloto(),
                    tiempo.getTiempoTotal(),
                    TiempoCarrera.formatearTiempo(tiempo.getTiempoTotal())));
        }

        return buildRaceTimesSummaryReportBodyRecursively(report, participantes, currentIndex + 1);
    }

    public static String generateRaceTimesSummaryReport(Participante[] participantes) {
        StringBuilder report = new StringBuilder();
        appendReportHeader(report, "REPORTE DE RESUMEN DE TIEMPOS DE CARRERA",
                "Nº  | PILOTO       | TIEMPO TOTAL (segundos) | TIEMPO FORMATEADO");

        buildRaceTimesSummaryReportBodyRecursively(report, participantes, 0);
        report.append(repeatCharNonTail('-', 80)).append("\n");
        return report.toString();
    }
}