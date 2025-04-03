package controller;

import model.Cita;
import model.Doctor;
import model.Paciente;
import service.CitaService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CitaController {
    private CitaService citaService;
    private Scanner scanner;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
        this.scanner = new Scanner(System.in);
    }

    public void agendarCita(Doctor doctor1, Doctor doctor2, Paciente paciente1, Paciente paciente2) {
        System.out.print("Ingrese la fecha de la cita (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        System.out.print("Ingrese la hora de la cita (ejemplo: 10:00 AM): ");
        String hora = scanner.nextLine();
        System.out.print("Seleccione el paciente (1. Juan, 2. Ana): ");
        int pacienteOption = scanner.nextInt();
        scanner.nextLine();
        Paciente pacienteSeleccionado = (pacienteOption == 1) ? paciente1 : paciente2;

        System.out.print("Seleccione el doctor (1. Mundo, 2. Maria): ");
        int doctorOption = scanner.nextInt();
        scanner.nextLine();
        Doctor doctorSeleccionado = (doctorOption == 1) ? doctor1 : doctor2;

        try {
            Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
            citaService.agendarCita(fecha, hora, pacienteSeleccionado, doctorSeleccionado, doctorSeleccionado.getEspecialidad());
            System.out.println("Cita agendada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al agendar la cita: " + e.getMessage());
        }
    }

    public void listarCitas() {
        System.out.println("Citas del día:");
        citaService.listarCitas().forEach(System.out::println);
    }

    public void verCitasPorDoctor() {
        System.out.print("Ingrese el código del doctor: ");
        String codigoDoctor = scanner.nextLine();
        List<Cita> citas = citaService.getCitasByDoctor(codigoDoctor);
        if (citas.isEmpty()) {
            System.out.println("No hay citas asignadas a este doctor.");
        } else {
            System.out.println("Citas del doctor " + codigoDoctor + ":");
            citas.forEach(System.out::println);
        }
    }

    public void cancelarCita() {
        System.out.print("Ingrese el ID de la cita a cancelar: ");
        int citaId = scanner.nextInt();
        scanner.nextLine();
        if (citaService.cancelarCita(citaId)) {
            System.out.println("Cita cancelada con éxito.");
        } else {
            System.out.println("No se encontró la cita para cancelar.");
        }
    }
}
