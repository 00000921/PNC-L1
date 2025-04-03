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

    public void mostrarMenu() {
        int option;
        do {
            System.out.println("\n--- Sistema de Citas Médicas ---");
            System.out.println("1. Agregar Doctor");
            System.out.println("2. Agregar Paciente");
            System.out.println("3. Agendar Cita");
            System.out.println("4. Listar Citas");
            System.out.println("5. Ver Citas por Doctor");
            System.out.println("6. Cancelar Cita");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    citaController.agregarDoctor();
                    break;
                case 2:
                    citaController.agregarPaciente();
                    break;
                case 3:
                    citaController.agendarCita();
                    break;
                case 4:
                    citaController.listarCitas();
                    break;
                case 5:
                    citaController.verCitasPorDoctor();
                    break;
                case 6:
                    citaController.cancelarCita();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (option != 7);

        scanner.close();
    }
}
