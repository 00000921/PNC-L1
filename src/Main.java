import model.Doctor;
import model.Paciente;
import repository.CitaRepository;
import service.CitaService;
import view.Menu;


public class Main {
    public static void main(String[] args) {
        CitaRepository citaRepository = new CitaRepository();
        CitaService citaService = new CitaService(citaRepository);

        Doctor doctor1 = new Doctor("Mundo", "Chagas", "12345678-9", "2021-01-01", "Medicina General", "ZNH-001-MD-Q1");
        Doctor doctor2 = new Doctor("Maria", "Lopez", "87654321-0", "2021-01-01", "Pediatria", "ZNH-002-MD-Q2");

        Paciente paciente1 = new Paciente("Juan", "Pérez", "01234567-8", "2010-01-01");
        Paciente paciente2 = new Paciente("Ana", "García", "87654321-0", "2015-05-05");

        Menu menu = new Menu(citaService);
        menu.mostrarMenu(doctor1, doctor2, paciente1, paciente2);
    }
}
