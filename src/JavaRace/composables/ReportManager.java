package JavaRace.composables;

import JavaRace.helpers.errorLog;
import java.io.IOException;
import JavaRace.repositories.Participante;
import JavaRace.util.ManejoArchivos;


public class ReportManager {

    private static void saveSingleReport(String reportContent, String fileName) throws IOException {
        ManejoArchivos.guardarReporteGenerico(reportContent, fileName);
        System.out.println("Reporte '" + fileName + "' guardado exitosamente.");
    }


    private static void handleNextReportGeneration(Participante[] participantes, int[] posiciones, String[] remainingReportNames, int reportIndex) {
        if (reportIndex >= remainingReportNames.length || remainingReportNames[reportIndex] == null) {
            return; // Caso base
        }

        String currentReportName = remainingReportNames[reportIndex];
        handleReportProcessingAndSaving(currentReportName, participantes, posiciones, remainingReportNames, reportIndex + 1);
    }

    private static void handleReportProcessingAndSaving(String reportName, Participante[] participantes, int[] posiciones, String[] remainingReportNames, int nextIndex) {
        try {
            String reportContent = "";
            String fileName = "";

            if ("detailedResults".equals(reportName)) {
                reportContent = ReportGenerator.generateDetailedRaceResultsReport(participantes, posiciones);
                fileName = "reporte_resultados_detallado";
            } else if ("participantList".equals(reportName)) {
                reportContent = ReportGenerator.generateParticipantListReport(participantes);
                fileName = "reporte_lista_participantes";
            } else if ("timesSummary".equals(reportName)) {
                reportContent = ReportGenerator.generateRaceTimesSummaryReport(participantes);
                fileName = "reporte_resumen_tiempos";
            } else {
                System.err.println("Tipo de reporte desconocido: " + reportName);
                errorLog.logError("Tipo de reporte desconocido: " + reportName);
            }

            if (!reportContent.isEmpty()) {
                saveSingleReport(reportContent, fileName);
            }

        } catch (IOException e) {
            System.err.println("Error al generar reporte '" + reportName + "': " + e.getMessage());
            errorLog.logError("Error al generar reporte '" + reportName + "': " + e.getMessage());
        }

        handleNextReportGeneration(participantes, posiciones, remainingReportNames, nextIndex);
    }

}