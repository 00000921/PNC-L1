package controller;

import model.Cita;
import model.Doctor;
import model.Paciente;
import service.CitaService;
import view.GenerateCode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CitaController {
    private CitaService citaService;
    private List<Doctor> doctores;
    private List<Paciente> pacientes;
    private Scanner scanner;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
        this.doctores = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    // Función para agendar cita sin datos quemados
    public void agendarCita() {
        if (doctores.isEmpty()) {
            System.out.println("⚠️ No hay doctores registrados. Agregue uno primero.");
            return;
        }
        if (pacientes.isEmpty()) {
            System.out.println("⚠️ No hay pacientes registrados. Agregue uno primero.");
            return;
        }

        System.out.println("\n--- Seleccione un Paciente ---");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getNombre() + " " + pacientes.get(i).getApellido());
        }
        System.out.print("Seleccione el paciente: ");
        int pacienteIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        if (pacienteIndex < 0 || pacienteIndex >= pacientes.size()) {
            System.out.println("❌ Opción inválida.");
            return;
        }
        Paciente pacienteSeleccionado = pacientes.get(pacienteIndex);

        System.out.println("\n--- Seleccione un Doctor ---");
        for (int i = 0; i < doctores.size(); i++) {
            System.out.println((i + 1) + ". " + doctores.get(i).getNombre() + " " + doctores.get(i).getApellido() + " - " + doctores.get(i).getEspecialidad());
        }
        System.out.print("Seleccione el doctor: ");
        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        if (doctorIndex < 0 || doctorIndex >= doctores.size()) {
            System.out.println("❌ Opción inválida.");
            return;
        }
        Doctor doctorSeleccionado = doctores.get(doctorIndex);

        System.out.print("Ingrese la fecha de la cita (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        System.out.print("Ingrese la hora de la cita (ejemplo: 10:00 AM): ");
        String hora = scanner.nextLine();

        try {
            Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
            citaService.agendarCita(fecha, hora, pacienteSeleccionado, doctorSeleccionado, doctorSeleccionado.getEspecialidad());
            System.out.println("✅ Cita agendada con éxito.");
        } catch (Exception e) {
            System.out.println("❌ Error al agendar la cita: " + e.getMessage());
        }
    }

    // Función para listar citas
    public void listarCitas() {
        System.out.println("\n--- Citas Registradas ---");
        List<Cita> citas = citaService.listarCitas();
        if (citas.isEmpty()) {
            System.out.println("⚠️ No hay citas registradas.");
        } else {
            citas.forEach(System.out::println);
        }
    }

    // Función para ver citas por doctor
    public void verCitasPorDoctor() {
        System.out.print("Ingrese el código del doctor: ");
        String codigoDoctor = scanner.nextLine();
        List<Cita> citas = citaService.getCitasByDoctor(codigoDoctor);
        if (citas.isEmpty()) {
            System.out.println("⚠️ No hay citas asignadas a este doctor.");
        } else {
            System.out.println("\n--- Citas del Doctor " + codigoDoctor + " ---");
            citas.forEach(System.out::println);
        }
    }

    // Función para cancelar cita
    public void cancelarCita() {
        System.out.print("Ingrese el ID de la cita a cancelar: ");
        int citaId = scanner.nextInt();
        scanner.nextLine();
        if (citaService.cancelarCita(citaId)) {
            System.out.println("✅ Cita cancelada con éxito.");
        } else {
            System.out.println("❌ No se encontró la cita para cancelar.");
        }
    }

    // Función para agregar un nuevo doctor
    public void agregarDoctor() {
        System.out.println("\n--- Agregar Nuevo Doctor ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("DUI: ");
        String dui = scanner.nextLine();

        System.out.print("Fecha de Reclutamiento (YYYY-MM-DD): ");
        String fechaReclutamiento = scanner.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        String codigo = new GenerateCode().generarCodigo();

        Doctor nuevoDoctor = new Doctor(nombre, apellido, dui, fechaReclutamiento, especialidad, codigo);
        doctores.add(nuevoDoctor);

        System.out.println("✅ Doctor agregado con éxito. Código asignado: " + codigo);
    }

    // Función para agregar un nuevo paciente
    public void agregarPaciente() {
        System.out.println("\n--- Agregar Nuevo Paciente ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
        String fechaNacimiento = scanner.nextLine();

        int edad = calcularEdad(fechaNacimiento);
        String dui = (edad < 18) ? "00000000-0" : pedirDUI();

        Paciente nuevoPaciente = new Paciente(nombre, apellido, dui, fechaNacimiento);
        pacientes.add(nuevoPaciente);

        System.out.println("✅ Paciente agregado con éxito.");
    }

    private String pedirDUI() {
        System.out.print("DUI: ");
        return scanner.nextLine();
    }

    private int calcularEdad(String fechaNacimiento) {
        int añoNacimiento = Integer.parseInt(fechaNacimiento.substring(0, 4));
        int añoActual = java.time.Year.now().getValue();
        return añoActual - añoNacimiento;
    }

    public List<Doctor> getDoctores() {
        return doctores;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
}
