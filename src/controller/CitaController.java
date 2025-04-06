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
    private DataPersistence dataPersistence; // Instancia de DataPersistence

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
        this.dataPersistence = new DataPersistence(); // Inicializa DataPersistence
        this.doctores = dataPersistence.cargarDoctores(); // Carga doctores
        this.pacientes = dataPersistence.cargarPacientes(); // Carga pacientes
        this.citas = dataPersistence.cargarCitas(); // Carga citas
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

        // Seleccionar paciente
        int pacienteIndex = seleccionarPaciente();
        if (pacienteIndex == -1) return;

        Paciente pacienteSeleccionado = pacientes.get(pacienteIndex);

        // Seleccionar doctor
        int doctorIndex = seleccionarDoctor();
        if (doctorIndex == -1) return;

        Doctor doctorSeleccionado = doctores.get(doctorIndex);

        // Solicitar fecha
        String fechaStr = solicitarFecha();
        if (fechaStr == null) return;

        // Solicitar hora
        String hora = solicitarHora();
        if (hora == null) return;

        // Verificar disponibilidad del doctor
        try {
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
            if (!citaService.verificarDisponibilidad(doctorSeleccionado, fecha, hora)) {
                System.out.println("❌ El doctor ya tiene una cita agendada a esa hora.");
                return;
            }
            Cita nuevaCita = new Cita(fecha, hora, pacienteSeleccionado, doctorSeleccionado, doctorSeleccionado.getEspecialidad());
            citas.add(nuevaCita);
            dataPersistence.guardarCitas(citas); // Guardar citas después de agregar
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
                if (pacienteIndex >= 0 && pacienteIndex < pacientes.size()) break;
                else System.out.println("❌ Opción inválida. Intente nuevamente.");
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
                if (doctorIndex >= 0 && doctorIndex < doctores.size()) break;
                else System.out.println("❌ Opción inválida. Intente nuevamente.");
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
        int citaId = -1;

        while (true) {
            try {
                System.out.print("Ingrese el ID de la cita a cancelar: ");
                citaId = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada no válida. Debe ingresar un número.");
                scanner.nextLine();
            }
        }

        if (citaService.eliminarCita(citaId)) {
            System.out.println("✅ Cita eliminada con éxito.");
        } else {
            System.out.println("❌ No se encontró la cita para eliminar.");
        }
    }

    public List<Cita> listarCitas() {
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
                System.out.println("Estado: " + (cita.isLlego() ? "Confirmada" : "Pendiente"));
                System.out.println("---------------------------------");
            }
        }
        return citas;
    }

    public void verCitasPorDoctor() {
        System.out.print("Ingrese el código del doctor: ");
        String codigoDoctor = scanner.nextLine();

        // Buscar el doctor con el código
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

        List<Cita> citas = citaService.getCitasByDoctor(codigoDoctor);
        if (citas.isEmpty()) {
            System.out.println("⚠️ No hay citas asignadas a este doctor.");
        } else {
            System.out.println("\n--- Citas del Doctor " + doctorEncontrado.getNombre() + " " + doctorEncontrado.getApellido() + " ---");
            for (Cita cita : citas) {
                System.out.println("Cita ID: " + cita.getId());
                System.out.println("Fecha: " + cita.getFecha());
                System.out.println("Especialidad: " + cita.getEspecialidad());
                System.out.println("Doctor Encargado: " + cita.getDoctor().getNombre());
                System.out.println("Paciente: " + cita.getPaciente().getNombre());
                System.out.println("Hora: " + cita.getHora());
                System.out.println("Estado: " + (cita.isLlego() ? "Confirmada" : "Pendiente"));
                System.out.println("---------------------------------");
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
        dataPersistence.guardarDoctores(doctores);  // Guardar doctores después de agregar
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
        dataPersistence.guardarPacientes(pacientes);  // Guardar pacientes después de agregar
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

    public Scanner getScanner() {
        return scanner;
    }

    public List<Doctor> getDoctores() {
        return doctores;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
}