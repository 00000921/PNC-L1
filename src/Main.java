import model.Cita;
        import model.Doctor;
        import model.Paciente;
        import repository.CitaRepository;
        import service.CitaService;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;
        import java.util.Scanner;

        public class Main {
            private static CitaService citaService;
            private static Scanner scanner = new Scanner(System.in);

            public static void main(String[] args) {
                // Inicializar el repositorio y el servicio
                CitaRepository citaRepository = new CitaRepository();
                citaService = new CitaService(citaRepository);

                // Crear algunos doctores y pacientes para las pruebas
                Doctor doctor1 = new Doctor("Mundo", "Chagas", "12345678-9", "2021-01-01", "Medicina General", "ZNH-001-MD-Q1");
                Doctor doctor2 = new Doctor("Maria", "Lopez", "87654321-0", "2021-01-01", "Pediatria", "ZNH-002-MD-Q2");

                // Crear pacientes
                Paciente paciente1 = new Paciente("Juan", "Pérez", "01234567-8", "2010-01-01");
                Paciente paciente2 = new Paciente("Ana", "García", "87654321-0", "2015-05-05");

                // Menú principal
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
                    scanner.nextLine(); // Consume el salto de línea

                    switch (option) {
                        case 1:
                            agendarCita(doctor1, doctor2, paciente1, paciente2);
                            break;
                        case 2:
                            listarCitas();
                            break;
                        case 3:
                            verCitasPorDoctor();
                            break;
                        case 4:
                            cancelarCita();
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

            private static void agendarCita(Doctor doctor1, Doctor doctor2, Paciente paciente1, Paciente paciente2) {
                System.out.print("Ingrese la fecha de la cita (dd/MM/yyyy): ");
                String fechaStr = scanner.nextLine();
                System.out.print("Ingrese la hora de la cita (ejemplo: 10:00 AM): ");
                String hora = scanner.nextLine();
                System.out.print("Seleccione el paciente (1. Juan, 2. Ana): ");
                int pacienteOption = scanner.nextInt();
                scanner.nextLine(); // Consume el salto de línea
                Paciente pacienteSeleccionado = (pacienteOption == 1) ? paciente1 : paciente2;

                System.out.print("Seleccione el doctor (1. Mundo, 2. Maria): ");
                int doctorOption = scanner.nextInt();
                scanner.nextLine(); // Consume el salto de línea
                Doctor doctorSeleccionado = (doctorOption == 1) ? doctor1 : doctor2;

                try {
                    // Crear un objeto Date basado en la fecha ingresada
                    Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                    citaService.agendarCita(fecha, hora, pacienteSeleccionado, doctorSeleccionado, doctorSeleccionado.getEspecialidad());
                    System.out.println("Cita agendada con éxito.");
                } catch (Exception e) {
                    System.out.println("Error al agendar la cita: " + e.getMessage());
                }
            }

            private static void listarCitas() {
                System.out.println("Citas del día:");
                citaService.listarCitas().forEach(cita -> System.out.println(cita));
            }

            private static void verCitasPorDoctor() {
                System.out.print("Ingrese el código del doctor: ");
                String codigoDoctor = scanner.nextLine();
                List<Cita> citas = citaService.getCitasByDoctor(codigoDoctor);
                if (citas == null || citas.isEmpty()) {
                    System.out.println("No hay citas asignadas a este doctor.");
                } else {
                    System.out.println("Citas del doctor " + codigoDoctor + ":");
                    citas.forEach(cita -> System.out.println(cita));
                }
            }

            private static void cancelarCita() {
                System.out.print("Ingrese el ID de la cita a cancelar: ");
                int citaId = scanner.nextInt();
                if (citaService.cancelarCita(citaId)) {
                    System.out.println("Cita cancelada con éxito.");
                } else {
                    System.out.println("No se encontró la cita para cancelar.");
                }
            }
        }