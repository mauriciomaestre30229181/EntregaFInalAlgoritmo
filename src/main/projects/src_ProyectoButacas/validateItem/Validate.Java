package src.main.projects.src_ProyectoButacas.validateItem;

public class Validate{

    public static int valUnsoldSeats1(int[][][] cinemaMatrix, int movieOption, int showTimeOption){
        int count=0;
        if (cinemaMatrix != null) {
            for (int i = 0; i < cinemaMatrix[0][0].length; i++) {
                if (cinemaMatrix[movieOption - 1][showTimeOption - 1][i] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

     public static int valUnsoldSeats2(int[][][] cinemaMatrix, int x, int j){
        int count=0;
        if (cinemaMatrix != null) {
            for (int z = 0; z < cinemaMatrix[0][0].length; z++) {
                if (cinemaMatrix[x][j][z] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static String valSchedule(String text) throws IOException {
        String schedule;
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        LocalTime minHour = LocalTime.parse("12:00 PM", format);
        LocalTime maxHour = LocalTime.parse("10:30 PM", format);

        while (true){
            try {
                System.out.println(text);
                schedule = scanner.nextLine().toUpperCase(Locale.ROOT).trim();

                LocalTime inputtedHour = LocalTime.parse(schedule, format);
                if (!inputtedHour.isBefore(minHour) && !inputtedHour.isAfter(maxHour) && schedule.endsWith("PM")) {
                    return schedule.replace(" ", "");
                }else {
                    System.out.println("  - ERROR: [Hora inválida, fuera del rango permitido (12:00 PM - 10:30 PM)]");
                }

            } catch (DateTimeParseException e) {
                addError("Formato de horas inválido "+e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.print("  - ERROR: [Formato invalido, utilice el formato hh:mm PM (ej. 04:30 PM) / Verifique que la hora sea real]");
   
            }

        }
    }
}