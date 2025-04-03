package repository;

import model.Cita;
import java.util.ArrayList;
import java.util.List;

public class CitaRepository {
    private List<Cita> citas;
    private int idCounter;

    public CitaRepository() {
        this.citas = new ArrayList<>();
        this.idCounter = 1;
    }

    public void save(Cita cita) {
        cita.setId(idCounter++);
        citas.add(cita);
    }

    public List<Cita> findAll() {
        return citas;
    }

    public Cita findById(int id) {
        for (Cita cita : citas) {
            if (cita.getId() == id) {
                return cita;
            }
        }
        return null;
    }

    public void delete(Cita cita) {
        citas.remove(cita);
    }
}