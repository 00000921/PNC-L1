package service;

import model.Cita;
import model.Doctor;
import model.Paciente;
import repository.CitaRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DataPersistence;

public class CitaService {
    private CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    public void agendarCita(Date fecha, String hora, Paciente paciente, Doctor doctor, String especialidad) {
        Cita nuevaCita = new Cita(fecha, hora, paciente, doctor, especialidad);
        citaRepository.save(nuevaCita);
    }

    public boolean eliminarCita(int id) {
            List<Cita> citas = DataPersistence.cargarCitas();
            for (Cita cita : citas) {
                if (cita.getId() == id) {
                    citas.remove(cita);
                    DataPersistence.guardarCitas(citas);
                    return true;
                }
            }
            return false;
        }

    public List<Cita> getCitasByDoctor(String codigoDoctor) {
        List<Cita> todasLasCitas = DataPersistence.cargarCitas();
        List<Cita> citasDoctor = new ArrayList<>();

        for (Cita cita : todasLasCitas) {
            if (cita.getDoctor().getCodigo().equalsIgnoreCase(codigoDoctor)) {
                citasDoctor.add(cita);
            }
        }
        return citasDoctor;
    }

    public boolean verificarDisponibilidad(Doctor doctor, Date fecha, String hora) {
        for (Cita cita : citaRepository.findAll()) {
            if (cita.getDoctor().getCodigo().equals(doctor.getCodigo()) &&
                    cita.getFecha().equals(fecha) && cita.getHora().equals(hora)) {
                return false; // El doctor ya tiene una cita a esa hora
            }
        }
        return true; // El doctor está disponible
    }
}