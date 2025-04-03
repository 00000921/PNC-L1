package view;

import controller.CitaController;
import model.Doctor;
import model.Paciente;
import service.CitaService;

import java.util.Scanner;

public class Menu {
    private CitaController citaController;
    private Scanner scanner = new Scanner(System.in);

    public Menu(CitaService citaService) {
        this.citaController = new CitaController(citaService);
    }

    public void mostrarMenu(Doctor doctor1, Doctor doctor2, Paciente paciente1, Paciente paciente2) {
        int option;
        do {
            System.out.println("\n--- Sistema de Citas Médicas ---");
            System.out.println("1. Agendar cita");
            System.out.println("2. Listar citas");
            System.out.println("3. Ver citas por doctor");
            System.out.println("4. Cancelar cita");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    citaController.agendarCita(doctor1, doctor2, paciente1, paciente2);
                    break;
                case 2:
                    citaController.listarCitas();
                    break;
                case 3:
                    citaController.verCitasPorDoctor();
                    break;
                case 4:
                    citaController.cancelarCita();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (option != 5);

        scanner.close();
    }
}
