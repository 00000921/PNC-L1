package service;

import model.Cita;
import model.Doctor;
import model.Paciente;
import repository.CitaRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CitaService {
    private CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    // Método para agendar nuevas citas
    public void agendarCita(Date fecha, String hora, Paciente paciente, Doctor doctor, String especialidad) {
        Cita nuevaCita = new Cita(fecha, hora, paciente, doctor, especialidad);
        citaRepository.save(nuevaCita);
    }

    public boolean cancelarCita(int citaId) {
        Cita cita = citaRepository.findById(citaId);
        if (cita != null) {
            citaRepository.delete(cita);
            return true;
        }
        return false;
    }

    public List<Cita> getCitasByDoctor(String codigoDoctor) {
        List<Cita> todasLasCitas = citaRepository.findAll();
        List<Cita> citasDoctor = new ArrayList<>();

        for (Cita cita : todasLasCitas) {
            if (cita.getDoctor().getCodigo().equals(codigoDoctor)) {
                citasDoctor.add(cita);
            }
        }
        return citasDoctor;
    }

    public boolean verificarDisponibilidad(Doctor doctor, Date fecha, String hora) {
        // Implementar lógica para verificar si el doctor está disponible
        return true; // Placeholder
    }
}