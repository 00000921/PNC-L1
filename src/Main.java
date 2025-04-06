import repository.CitaRepository;
import service.CitaService;
import utils.Menu;

public class Main {
    public static void main(String[] args) {
        CitaRepository citaRepository = new CitaRepository();
        CitaService citaService = new CitaService(citaRepository);

        Menu menu = new Menu(citaService);
        menu.mostrarMenu();
    }
}