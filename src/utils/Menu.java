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
            System.out.println("6. Administrar Citas");
            System.out.println("7. Salir");
            System.out.println("8. Mundo salva vidas"); // Nueva opción
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
                        gestionarCitas();
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema...");
                        salir = true;
                        break;
                    case 8:
                        System.out.println("🦸‍♂️ Mundo salva vidas!"); // Mensaje al seleccionar esta opción
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

    private void gestionarCitas() {
        System.out.println("Elija una opción");
        int optCita = 0;
        boolean regresar = false;
        do {
            try {
                System.out.println("1. Marcar Llegada");
                System.out.println("2. Cancelar Cita");
                System.out.println("3. Regresar");
                optCita = Integer.parseInt(scanner.nextLine());
                switch (optCita) {
                    case 1:
                        Utils.marcarLlegada(citaController);
                        break;
                    case 2:
                        citaController.cancelarCita();
                        break;
                    case 3:
                        regresar = true;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor digite opción 1 o 2");
            }
        } while (!regresar);
    }
}