package repository;

import model.Cita;
import java.util.ArrayList;
import java.util.List;

public class CitaRepository {
    private List<Cita> citas;

    public CitaRepository() {
        this.citas = new ArrayList<>();
    }

    public void save(Cita cita) {
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