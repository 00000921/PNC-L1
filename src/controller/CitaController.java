package controller;

import model.Cita;
import model.Doctor;
import model.Paciente;
import service.CitaService;
import utils.DataPersistence;
import utils.GenerateCode;
import utils.Utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class CitaController {
    private CitaService citaService;
    private List<Doctor> doctores;
    private List<Paciente> pacientes;
    private List<Cita> citas;
    private Scanner scanner;
    private DataPersistence dataPersistence;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
        this.dataPersistence = new DataPersistence();
        this.doctores = dataPersistence.cargarDoctores();
        this.pacientes = dataPersistence.cargarPacientes();
        this.citas = dataPersistence.cargarCitas();
        this.scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }

    private int generarIdCita() {
        return citas.size() + 1;
    }

    public void agendarCita() {
        if (doctores.isEmpty()) {
            System.out.println("⚠️ No hay doctores registrados. Agregue uno primero.");
            return;
        }
        if (pacientes.isEmpty()) {
            System.out.println("⚠️ No hay pacientes registrados. Agregue uno primero.");
            return;
        }

        int pacienteIndex = seleccionarPaciente();
        if (pacienteIndex == -1) return;

        Paciente pacienteSeleccionado = pacientes.get(pacienteIndex);
        int doctorIndex = seleccionarDoctor();
        if (doctorIndex == -1) return;

        Doctor doctorSeleccionado = doctores.get(doctorIndex);
        String fechaStr = solicitarFecha();
        if (fechaStr == null) return;
        String hora = solicitarHora();
        if (hora == null) return;

        try {
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
            if (!citaService.verificarDisponibilidad(doctorSeleccionado, fecha, hora)) {
                System.out.println("❌ El doctor ya tiene una cita agendada a esa hora.");
                return;
            }
            Cita nuevaCita = new Cita(fecha, hora, pacienteSeleccionado, doctorSeleccionado, doctorSeleccionado.getEspecialidad());
            nuevaCita.setId(generarIdCita());
            citas.add(nuevaCita);
            dataPersistence.guardarCitas(citas);
            System.out.println("✅ Cita agendada con éxito.");
        } catch (Exception e) {
            System.out.println("❌ Error al agendar la cita: " + e.getMessage());
        }
    }

    private int seleccionarPaciente() {
        int pacienteIndex = -1;
        System.out.println("\n--- Seleccione un Paciente ---");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getNombre() + " " + pacientes.get(i).getApellido());
        }

        while (true) {
            System.out.print("Seleccione el paciente: ");
            try {
                pacienteIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (pacienteIndex >= 0 && pacienteIndex < pacientes.size())
                    break;
                else
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Por favor, ingrese un número.");
            }
        }
        return pacienteIndex;
    }

    private int seleccionarDoctor() {
        int doctorIndex = -1;
        System.out.println("\n--- Seleccione un Doctor ---");
        for (int i = 0; i < doctores.size(); i++) {
            System.out.println((i + 1) + ". " + doctores.get(i).getNombre() + " " + doctores.get(i).getApellido() + " - " + doctores.get(i).getEspecialidad() + " - " + doctores.get(i).getCodigo());
        }

        while (true) {
            System.out.print("Seleccione el doctor: ");
            try {
                doctorIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (doctorIndex >= 0 && doctorIndex < doctores.size())
                    break;
                else
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Por favor, ingrese un número.");
            }
        }
        return doctorIndex;
    }

    private String solicitarFecha() {
        String fechaStr;
        do {
            System.out.print("Ingrese la fecha de la cita (yyyy-MM-dd): ");
            fechaStr = scanner.nextLine();
            if (!Utils.isFutureDate(fechaStr)) {
                System.out.println("❌ Fecha no válida. Debe ser mayor a hoy. Formato correcto: yyyy-MM-dd.");
            }
        } while (!Utils.isFutureDate(fechaStr));
        return fechaStr;
    }

    private String solicitarHora() {
        String hora;
        do {
            System.out.print("Ingrese la hora de la cita (ejemplo: 10:00 AM): ");
            hora = scanner.nextLine();
            if (!Utils.isValidTime(hora)) {
                System.out.println("❌ Hora no válida. Use el formato 10:00 AM.");
            }
        } while (!Utils.isValidTime(hora));
        return hora;
    }

    public void cancelarCita() {
        System.out.println("\n--- Citas Registradas ---");
        citas = dataPersistence.cargarCitas(); // Cargar citas desde el JSON
        int citaId = -1;

        if (citas.isEmpty()) {
            System.out.println("⚠️ No hay citas registradas.");
        } else {
            System.out.printf("%-5s %-20s %-10s %-20s %-20s %-10s\n", "ID", "Fecha", "Hora", "Paciente", "Doctor", "Estado");
            System.out.println("-".repeat(100));

            for (Cita cita : citas) {
                System.out.printf("%-5s %-20s %-10s %-20s %-20s %-10s\n",
                        cita.getId(),
                        new SimpleDateFormat("yyyy-MM-dd").format(cita.getFecha()),
                        cita.getHora(),
                        cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido(),
                        cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido(),
                        cita.isLlego() ? "Asistido" : "No asistido");
            }
        }

        while (true) {
            try {
                System.out.print("Ingrese el ID de la cita a cancelar: ");
                citaId = scanner.nextInt(); // Obtener el ID
                scanner.nextLine(); // Limpiar entrada
                final int idToRemove = citaId; // Variable final
                if (citaService.eliminarCita(citaId)) {
                    citas.removeIf(cita -> cita.getId() == idToRemove); // Usar la variable final
                    dataPersistence.guardarCitas(citas); // Guardar cambios
                    System.out.println("✅ Cita eliminada con éxito.");
                } else {
                    System.out.println("❌ No se encontró la cita para eliminar.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada no válida. Debe ingresar un número.");
                scanner.nextLine(); // Limpiar la entrada incorrecta
            }
        }
    }

    public List<Cita> listarCitas() {
        System.out.println("\n--- Citas Registradas ---");
        citas = dataPersistence.cargarCitas(); // Cargar citas desde el JSON

        if (citas.isEmpty()) {
            System.out.println("⚠️ No hay citas registradas.");
        } else {
            System.out.printf("%-5s %-20s %-10s %-20s %-20s %-10s\n", "ID", "Fecha", "Hora", "Paciente", "Doctor", "Estado");
            System.out.println("-".repeat(100));

            for (Cita cita : citas) {
                System.out.printf("%-5s %-20s %-10s %-20s %-20s %-10s\n",
                        cita.getId(),
                        new SimpleDateFormat("yyyy-MM-dd").format(cita.getFecha()),
                        cita.getHora(),
                        cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido(),
                        cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido(),
                        cita.isLlego() ? "Asistido" : "No asistido");
            }

            // Después de listar las citas, preguntar si se desea cambiar el estado de llegada
            cambiarEstadoCita();
        }
        return citas;
    }

    private void cambiarEstadoCita() {
        System.out.print("\n¿Desea cambiar el estado de llegada de alguna cita? (s/n): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el ID de la cita: ");
            int citaId;
            try {
                citaId = Integer.parseInt(scanner.nextLine());
                for (Cita cita : citas) {
                    if (cita.getId() == citaId) {
                        cita.setLlego(!cita.isLlego()); // Cambiar el estado
                        dataPersistence.guardarCitas(citas); // Guardar cambios en el archivo
                        System.out.println("✅ El estado de la cita ha sido actualizado.");
                        return;
                    }
                }
                System.out.println("❌ No se encontró una cita con ese ID.");
            } catch (NumberFormatException e) {
                System.out.println("❌ ID inválido. Debe ser un número.");
            }
        }
    }

    public void verCitasPorDoctor() {
        System.out.print("Ingrese el código del doctor: ");
        String codigoDoctor = scanner.nextLine();

        Doctor doctorEncontrado = null;
        for (Doctor doc : doctores) {
            if (doc.getCodigo().equalsIgnoreCase(codigoDoctor)) {
                doctorEncontrado = doc;
                break;
            }
        }

        if (doctorEncontrado == null) {
            System.out.println("❌ No se encontró un doctor con ese código.");
            return;
        }

        // Cargar las citas desde el JSON
        citas = dataPersistence.cargarCitas();

        List<Cita> citasDoctor = citaService.getCitasByDoctor(codigoDoctor);
        if (citasDoctor.isEmpty()) {
            System.out.println("⚠️ No hay citas asignadas a este doctor.");
        } else {
            System.out.println("\n--- Citas del Doctor " + doctorEncontrado.getNombre() + " " + doctorEncontrado.getApellido() + " ---");
            System.out.printf("%-5s %-20s %-10s %-20s %-20s %-10s\n", "ID", "Fecha", "Hora", "Paciente", "Doctor", "Estado");
            System.out.println("-".repeat(100));

            for (Cita cita : citasDoctor) {
                System.out.printf("%-5s %-20s %-10s %-20s %-20s %-10s\n",
                        cita.getId(),
                        new SimpleDateFormat("yyyy-MM-dd").format(cita.getFecha()),
                        cita.getHora(),
                        cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido(),
                        cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido(),
                        cita.isLlego() ? "Asistido" : "No asistido");
            }
        }
    }

    public void agregarDoctor() {
        System.out.println("\n--- Agregar Nuevo Doctor ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        String dui = pedirDUI();

        String fechaReclutamiento;
        do {
            System.out.print("Fecha de Reclutamiento (YYYY-MM-DD): ");
            fechaReclutamiento = scanner.nextLine();
            if (!Utils.isValidFormat(fechaReclutamiento) || !Utils.isDateValid(fechaReclutamiento)) {
                System.out.println("❌ Fecha de reclutamiento no válida. Debe ser igual o anterior a hoy.");
            }
        } while (!Utils.isValidFormat(fechaReclutamiento) || !Utils.isDateValid(fechaReclutamiento));

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        String codigo = new GenerateCode().generarCodigo();
        Doctor nuevoDoctor = new Doctor(nombre, apellido, dui, fechaReclutamiento, especialidad, codigo);
        doctores.add(nuevoDoctor);
        dataPersistence.guardarDoctores(doctores);
        System.out.println("✅ Doctor agregado con éxito. Código asignado: " + codigo);
    }

    public void agregarPaciente() {
        System.out.println("\n--- Agregar Nuevo Paciente ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        String fechaNacimiento;
        do {
            System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
            fechaNacimiento = scanner.nextLine();
            if (!Utils.isValidFormat(fechaNacimiento) || !Utils.isDateValid(fechaNacimiento)) {
                System.out.println("❌ Fecha de nacimiento no válida. Debe ser igual o anterior a hoy.");
            }
        } while (!Utils.isValidFormat(fechaNacimiento) || !Utils.isDateValid(fechaNacimiento));

        String dui;
        do {
            dui = pedirDUI(); // Validar el DUI para asegurar que sea correcto
        } while (!Utils.validarDUI(dui));

        Paciente nuevoPaciente = new Paciente(nombre, apellido, dui, fechaNacimiento);
        pacientes.add(nuevoPaciente);
        dataPersistence.guardarPacientes(pacientes);
        System.out.println("✅ Paciente agregado con éxito.");
    }

    private String pedirDUI() {
        String dui;
        do {
            System.out.print("DUI (formato 00000000-0): ");
            dui = scanner.nextLine();
            if (!Utils.validarDUI(dui)) {
                System.out.println("❌ Formato de DUI inválido. Intente nuevamente.");
            }
        } while (!Utils.validarDUI(dui));
        return dui;
    }
}