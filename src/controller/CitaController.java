package controller;

import model.Cita;
import model.Doctor;
import model.Paciente;
import service.CitaService;
import utils.GenerateCode;
import utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void agendarCita() {
        if (doctores.isEmpty()) {
            System.out.println("⚠️ No hay doctores registrados. Agregue uno primero.");
            return;
        }
        if (pacientes.isEmpty()) {
            System.out.println("⚠️ No hay pacientes registrados. Agregue uno primero.");
            return;
        }

        int pacienteIndex = -1;
        System.out.println("\n--- Seleccione un Paciente ---");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getNombre() + " " + pacientes.get(i).getApellido());
        }

        while (true) {
            System.out.print("Seleccione el paciente: ");
            try {
                pacienteIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (pacienteIndex >= 0 && pacienteIndex < pacientes.size()) break;
                else System.out.println("❌ Opción inválida. Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Por favor, ingrese un número.");
            }
        }

        Paciente pacienteSeleccionado = pacientes.get(pacienteIndex);

        int doctorIndex = -1;
        System.out.println("\n--- Seleccione un Doctor ---");
        for (int i = 0; i < doctores.size(); i++) {
            System.out.println((i + 1) + ". " + doctores.get(i).getNombre() + " " + doctores.get(i).getApellido() + " - " + doctores.get(i).getEspecialidad());
        }

        while (true) {
            System.out.print("Seleccione el doctor: ");
            try {
                doctorIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (doctorIndex >= 0 && doctorIndex < doctores.size()) break;
                else System.out.println("❌ Opción inválida. Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Por favor, ingrese un número.");
            }
        }

        Doctor doctorSeleccionado = doctores.get(doctorIndex);

        String fechaStr;
        do {
            System.out.print("Ingrese la fecha de la cita (yyyy-MM-dd): ");
            fechaStr = scanner.nextLine();
            if (!Utils.isFutureDate(fechaStr)) {
                System.out.println("❌ Fecha no válida. Debe ser mayor a hoy. Formato correcto: yyyy-MM-dd.");
            }
        } while (!Utils.isFutureDate(fechaStr));

        String hora;
        do {
            System.out.print("Ingrese la hora de la cita (ejemplo: 10:00 AM): ");
            hora = scanner.nextLine();
            if (!Utils.isValidTime(hora)) {
                System.out.println("❌ Hora no válida. Use el formato 10:00 AM.");
            }
        } while (!Utils.isValidTime(hora));

        try {
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
            citaService.agendarCita(fecha, hora, pacienteSeleccionado, doctorSeleccionado, doctorSeleccionado.getEspecialidad());
            System.out.println("✅ Cita agendada con éxito.");
        } catch (Exception e) {
            System.out.println("❌ Error al agendar la cita: " + e.getMessage());
        }
    }


    public void listarCitas() {
        System.out.println("\n--- Citas Registradas ---");
        List<Cita> citas = citaService.listarCitas();
        if (citas.isEmpty()) {
            System.out.println("⚠️ No hay citas registradas.");
        } else {
            for (Cita cita : citas) {
                System.out.println("Cita ID: " + cita.getId());
                System.out.println("Paciente: " + cita.getPaciente().getNombre());
                System.out.println("Doctor: " + cita.getDoctor().getNombre());
                System.out.println("Fecha: " + cita.getFecha());
                System.out.println("Hora: " + cita.getHora());
                System.out.println("Estado: (HACE FALTA AGREGAR)");
                System.out.println("---------------------------------");
            }
        }
    }


    public void verCitasPorDoctor() {
        System.out.print("Ingrese el código del doctor: ");
        String codigoDoctor = scanner.nextLine();
        List<Cita> citas = citaService.getCitasByDoctor(codigoDoctor);
        if (citas.isEmpty()) {
            System.out.println("⚠️ No hay citas asignadas a este doctor.");
        } else {
            System.out.println("\n--- Citas del Doctor " + codigoDoctor + " ---");
            for (Cita cita : citas) {
                System.out.println("Cita ID: " + cita.getId());
                System.out.println("Paciente: " + cita.getPaciente().getNombre());
                System.out.println("Fecha: " + cita.getFecha());
                System.out.println("Hora: " + cita.getHora());
                System.out.println("Estado: (HACE FALTA AGREGAR)" );
                System.out.println("---------------------------------");
            }
        }
    }

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

        int edad = calcularEdad(fechaNacimiento);
        String dui = (edad < 18) ? "00000000-0" : pedirDUI();

        Paciente nuevoPaciente = new Paciente(nombre, apellido, dui, fechaNacimiento);
        pacientes.add(nuevoPaciente);
        System.out.println("✅ Paciente agregado con éxito.");
    }

    private int calcularEdad(String fechaNacimiento) {
        try {
            Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
            int birthYear = birthDate.getYear() + 1900; // Ajuste para obtener año correcto
            int currentYear = java.time.Year.now().getValue();
            return currentYear - birthYear;
        } catch (Exception e) {
            System.out.println("❌ Error al calcular la edad: " + e.getMessage());
            return 0; // Si hay un error, devuelve 0
        }
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

    public List<Doctor> getDoctores() {
        return doctores;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
}