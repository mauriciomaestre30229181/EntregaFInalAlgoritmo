package process;

import helpers.Validate;
import java.util.NoSuchElementException;
import java.util.Scanner;
import repositories.CustomQueue;
import repositories.CustomStack;
import repositories.Participante;
import repositories.ParticipanteRepository;
import util.ManejoArchivos;

public class ConsultaService {

    private ParticipanteRepository participanteRepository;
    // TIPOS DE DATOS CORREGIDOS: Ahora usan CustomQueue y CustomStack
    private CustomQueue<Participante> colaDeConsultas;
    private CustomStack<Participante> pilaParaGuardar;

    public ConsultaService(ParticipanteRepository repo) {
        this.participanteRepository = repo;
        // INICIALIZACIÓN CORREGIDA
        this.colaDeConsultas = new CustomQueue<>();
        this.pilaParaGuardar = new CustomStack<>();
    }

    public void iniciarConsulta(Scanner scanner) {
        System.out.println("\n=== CONSULTAR Y AGRUPAR DATOS DE PARTICIPANTES ===");

        realizarConsultas(scanner);
        procesarColaAPila();
        guardarDatosDePila(scanner);
    }

    private void realizarConsultas(Scanner scanner) {
        String continuar;
        do {
            new ParticipanteService(participanteRepository).mostrarParticipantesDisponibles();
            int numero = Validate.validarEntero(scanner, "Ingrese el número del participante a consultar y encolar: ");
            Participante p = participanteRepository.findByNumero(numero);

            if (p != null) {
                System.out.println("Encolando participante: " + p.getNombrePiloto());
                colaDeConsultas.enqueue(p);
            } else {
                System.out.println("Participante no encontrado.");
            }

            System.out.print("¿Desea consultar y encolar otro participante? (S/N): ");
            continuar = scanner.nextLine();
        } while (continuar.equalsIgnoreCase("S"));
    }

    private void procesarColaAPila() {
        if (colaDeConsultas.isEmpty()) {
            System.out.println("La cola de consultas está vacía. No hay datos para procesar.");
            return;
        }

        System.out.println("\n--- Procesando Consultas ---");
        System.out.println("Desencolando, mostrando y apilando los datos:");

        try {
            while (!colaDeConsultas.isEmpty()) {
                Participante p = colaDeConsultas.dequeue();
                System.out.println("Mostrando y Apilando: " + p.toString());
                pilaParaGuardar.push(p);
            }
        } catch (NoSuchElementException e) {
            System.err.println("Error: Se intentó desencolar de una cola vacía.");
        }
    }

    private void guardarDatosDePila(Scanner scanner) {
        if (pilaParaGuardar.isEmpty()) {
            System.out.println("La pila está vacía. No hay datos para guardar.");
            return;
        }

        System.out.println("\n--- Guardando Datos Agrupados ---");
        String nombreBase = Validate.validarTexto(scanner, "Ingrese un nombre base para el archivo de reporte (e.g., consulta_pilotos): ");

        StringBuilder contenido = new StringBuilder();
        contenido.append("=== Reporte de Consulta Agrupada ===\n");
        contenido.append("Datos extraídos de la pila en orden LIFO (Last-In, First-Out):\n");
        contenido.append("-".repeat(80)).append("\n");

        while (!pilaParaGuardar.isEmpty()) {
            Participante p = pilaParaGuardar.pop();
            contenido.append(p.toString()).append("\n");
        }

        contenido.append("-".repeat(80)).append("\n");
        contenido.append("Fin del reporte.\n");

        try {
            ManejoArchivos.guardarConsulta(contenido.toString(), nombreBase);
        } catch (Exception e) {
            System.err.println("Error crítico al guardar el archivo de consulta: " + e.getMessage());
        }
    }
}
