package src.main.projects.src_ProyectoButacas;

public class ValidateOBJ{
    public static String valOption(String text, String option, Scanner scanner) throws IOException {
        
        if (!Validate.valString(option)) {
            System.out.println("ERROR - [No se permiten caracteres especiales o numeros]");
            System.out.println(text);
            option=scanner.nextLine().trim().toLowerCase();
            return valOption(text, option,scanner);
        }else{
            return option;
        }

    }
}