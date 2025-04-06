package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder; // Importar GsonBuilder
import com.google.gson.reflect.TypeToken;
import model.Cita;
import model.Doctor;
import model.Paciente;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataPersistence {
    private static final String DOCTORES_FILE = "doctores.json";
    private static final String PACIENTES_FILE = "pacientes.json";
    private static final String CITAS_FILE = "citas.json";

    // Usar GsonBuilder para formateo
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void guardarDoctores(List<Doctor> doctores) {
        try (Writer writer = new FileWriter(DOCTORES_FILE)) {
            gson.toJson(doctores, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar doctores: " + e.getMessage());
        }
    }

    public List<Doctor> cargarDoctores() {
        try (Reader reader = new FileReader(DOCTORES_FILE)) {
            Type doctorListType = new TypeToken<ArrayList<Doctor>>() {}.getType();
            return gson.fromJson(reader, doctorListType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar doctores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarPacientes(List<Paciente> pacientes) {
        try (Writer writer = new FileWriter(PACIENTES_FILE)) {
            gson.toJson(pacientes, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    public List<Paciente> cargarPacientes() {
        try (Reader reader = new FileReader(PACIENTES_FILE)) {
            Type pacienteListType = new TypeToken<ArrayList<Paciente>>() {}.getType();
            return gson.fromJson(reader, pacienteListType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar pacientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarCitas(List<Cita> citas) {
        try (Writer writer = new FileWriter(CITAS_FILE)) {
            gson.toJson(citas, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar citas: " + e.getMessage());
        }
    }

    public List<Cita> cargarCitas() {
        try (Reader reader = new FileReader(CITAS_FILE)) {
            Type citaListType = new TypeToken<ArrayList<Cita>>() {}.getType();
            return gson.fromJson(reader, citaListType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar citas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}