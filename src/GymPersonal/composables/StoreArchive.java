package GymPersonal.composables;

import GymPersonal.helpers.ConsultData;
import GymPersonal.process.BookingTraining;
import GymPersonal.process.Process;
import GymPersonal.repositories.*;
import GymPersonal.validateItem.Validate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Arrays;


public class StoreArchive {
    static int rand = (int) (Math.random() * 100) + 1;

    public static void rewriteDataBase(ArchiveUtil archiveUtil, String fileName, String[][] lines)  {
        String content = String.join(",", lines[0]);
        archiveUtil.setCreateArchive(content, fileName, true, false);

        for (int i = 1; i < lines.length; i++) {
            content = String.join(",", lines[i]);
            archiveUtil.setCreateArchive(content, fileName, true, true);
        }
    }


    public static void storeRegistrationToFile(String[][] bookedUsersNew, String fileName) throws IOException {
        if(bookedUsersNew!=null) {
            String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage";
            router = Validate.utilDirectory(router) + "/"+fileName;
            try (PrintWriter pw = new PrintWriter(new FileWriter(router))) {
                for (String[] addUser : bookedUsersNew) {
                    pw.println(String.join(",", addUser));
                }
            }
        }
    }


    public static void storeDetailedUserRegistration(User user, Training training, String fileName) throws IOException {
        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
        ArchiveUtil util = new ArchiveUtil(router);
        writeFormattedHeader(util, fileName);
        writeRegistTemplate(util, user, training, fileName);
        util.setCreateArchive("╚══════════════════════════════════════════════════════════════╝", fileName, true);
        System.out.println("=> Busqueda realizada correctamente! Guardado en el archivo: "+fileName+"\n");

    }


    public static void storeDetailedUserOrder(User user, Nutrition nutrition, String fileName) throws IOException {
        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
        ArchiveUtil util = new ArchiveUtil(router);
        writeFormattedHeader(util, fileName);
        writeOrderTemplate(util, user, nutrition,fileName);
        util.setCreateArchive("╚══════════════════════════════════════════════════════════════╝", fileName, true);
        System.out.println("=> Busqueda realizada correctamente! Guardado en el archivo: "+fileName);
    }


    public static void writeGrupalResultsFiles(String fileName, String[] lines, int matchesQuant, boolean isOrder) throws IOException {
        if(lines!=null) {
            User user=null;
            String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
            ArchiveUtil util = new ArchiveUtil(router);
            writeFormattedHeader(util, fileName);
            for (int i = 0; i < matchesQuant; i++) {
                String[][] line;
                if(!isOrder) line= new String[2][6];
                else  line= new String[2][5];
                Process.iniArray(line);
                ConsultData.updateFileHeaders(line, isOrder);
                String[] data = lines[i].split(",", -1);
                line[1]= data;
                try {
                    user = new User(line, 1);
                } catch (IllegalArgumentException e) {
                    Validate.addError(e + "/" + e.getMessage() + "/" + e.getLocalizedMessage());
                    System.out.println("- ERROR: " + e.getMessage());
                }

                if (matchesQuant == 1) {
                    doObjectWriting(user, fileName, isOrder);
                } else {
                    doObjectWriting(user, fileName, isOrder);
                    util.setCreateArchive("║                                                              ║", fileName, true);
                }
            }
            util.setCreateArchive("╚══════════════════════════════════════════════════════════════╝", fileName, true);
            System.out.println("=> Busqueda realizada correctamente! Guardado en el archivo: " + fileName + ".txt");
        }
    }

    public static void doObjectWriting(User user, String fileName, boolean isOrder) throws IOException {
        Training training=null;
        Nutrition nutrition=null;
        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
        ArchiveUtil util = new ArchiveUtil(router);

        if (!isOrder) {
            try { training = new Training(user.getDatabase(), 1); } catch (IllegalArgumentException e) {
                Validate.addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.println("- ERROR: "+e.getMessage());
            }
            writeRegistTemplate(util, user, training, fileName);
        } else{
            try { nutrition = new Nutrition(user.getDatabase(),1); } catch (IllegalArgumentException e) {
                Validate.addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.println("- ERROR: "+e.getMessage());
            }
            writeOrderTemplate(util,  user, nutrition, fileName);
        }
    }

    public static void writeFormattedHeader(ArchiveUtil archiveUtil, String fileName) throws IOException{
        archiveUtil.setCreateArchive("\n╔══════════════════════════════════════════════════════════════╗", fileName, true,false);
        archiveUtil.setCreateArchive("║                       DATOS SOLICITADOS                      ║", fileName, true);
        archiveUtil.setCreateArchive("╠══════════════════════════════════════════════════════════════╣", fileName, true);
    }


    public static void writeRegistTemplate(ArchiveUtil archiveUtil, User user, Training training, String fileName) {
        archiveUtil.setCreateArchive(String.format("║  USUARIO: %-20s                               ║", user.getName().trim()), fileName, true);
        archiveUtil.setCreateArchive(String.format("║  CEDULA: %-18s                                  ║",user.getId().trim()), fileName, true);
        archiveUtil.setCreateArchive(String.format("║  PLAN: %-42s            ║", training.getPlan().trim()), fileName, true);
        archiveUtil.setCreateArchive(String.format("║  CLASE A LA QUE ASISTIO: %-18s                  ║", training.getTrainingClass().trim()), fileName, true);
        archiveUtil.setCreateArchive(String.format("║  HORARIO A LA QUE ASISTIO: %-18s                ║",training.getSchedule().trim()), fileName, true);
    }

    public static void writeOrderTemplate(ArchiveUtil archiveUtil, User user, Nutrition nutrition, String fileName) throws IOException{
        archiveUtil.setCreateArchive(String.format("║  USUARIO: %-21s                              ║", user.getName().trim()), fileName, true);
        archiveUtil.setCreateArchive(String.format("║  CEDULA: %-22s                              ║",user.getId().trim()), fileName, true);
        archiveUtil.setCreateArchive(String.format("║  PLAN: %-50s    ║", nutrition.getPlan().trim()), fileName, true);
        archiveUtil.setCreateArchive(String.format("║  ORDEN: %-52s ║", nutrition.getItem().trim()), fileName, true);
    }

    public static void writeRegistrationReport(Statitics statitics, String route) throws IOException {
        String text;
        int band, sum;
        int[][][] gymMatrix =statitics.getGymMatrix();
        int[][] revenue = statitics.getRevenue();
        int[] losses = statitics.getLosses();

        if (gymMatrix != null && revenue != null && losses != null) {
            ArchiveUtil archiveUtil = new ArchiveUtil(route);
            String fileName = "InformeVentasGym_"+rand;
            for (int i = 0; i < gymMatrix.length; i++) {
                text = String.format("%s", BookingTraining.CLASS_LISTINGS[i]);
                archiveUtil.setCreateArchive(text, fileName, false, true);
                band = 0;
                sum = 0;
                for (int j = 0; j < gymMatrix[0].length; j++) {
                    if (band == 0) {
                        for (int y = 0; y < gymMatrix[0].length; y++) {
                            sum += gymMatrix[0][0].length - Validate.valUnsoldSpots2(gymMatrix, i, y);
                        }
                        band = 1;
                    }
                }
                text = String.format(",%d", revenue[i][gymMatrix[0].length]);
                archiveUtil.setCreateArchive(text, fileName, false, true);
                text = String.format(",%d", (losses[i] * 2));
                archiveUtil.setCreateArchive(text, fileName, false, true);
                text = statitics.getPercentage(sum);
                archiveUtil.setCreateArchive(text, fileName, false, true);
            }
        }
        System.out.println("  >>>> BUSQUEDA COMPLETADA CON EXITO: Puede ver su informe del día  <<<<");
    }

}
