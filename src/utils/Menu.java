package utils;

import controller.CitaController;
import service.CitaService;
import java.util.Scanner;

public class Menu {
    private CitaController citaController;
    private Scanner scanner = new Scanner(System.in);

    public Menu(CitaService citaService) {
        this.citaController = new CitaController(citaService);
    }

    public void mostrarMenu() {
        int option = 0;
        boolean salir = false;

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

            try {
                option = Integer.parseInt(scanner.nextLine());
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
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor ingrese un número del 1 al 8.");
            }
        } while (!salir);

        scanner.close();
    }


}