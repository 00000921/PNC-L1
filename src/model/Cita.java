package model;

import java.util.Date;

public class Cita {
    private int id;
    private Date fecha;
    private String hora;
    private Paciente paciente;
    private Doctor doctor;
    private String especialidad;
    private boolean llego;

    public Cita(Date fecha, String hora, Paciente paciente, Doctor doctor, String especialidad) {
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.doctor = doctor;
        this.especialidad = especialidad;
        this.llego = false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isLlego() {
        return llego;
    }

    public void setLlego(boolean llego) {
        this.llego = llego;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", hora='" + hora + '\'' +
                ", paciente=" + paciente.getNombre() + " " + paciente.getApellido() +
                ", doctor=" + doctor.getNombre() + " " + doctor.getApellido() +
                ", especialidad='" + especialidad + '\'' +
                ", llego=" + llego +
                '}';
    }
}