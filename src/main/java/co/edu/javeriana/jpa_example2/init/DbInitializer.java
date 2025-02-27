package co.edu.javeriana.jpa_example2.init;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.InventarioCaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.JugadorRepository;
import co.edu.javeriana.jpa_example2.repository.PartidaRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoCiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioCiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.ServiciosCompradosRepository;
import co.edu.javeriana.jpa_example2.repository.TransaccionRepository;
import jakarta.transaction.Transactional;

@Component
public class DbInitializer implements CommandLineRunner {

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private InventarioCaravanaRepository inventarioCaravanaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ProductoCiudadRepository productoCiudadRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ServicioCiudadRepository servicioCiudadRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServiciosCompradosRepository serviciosCompradosRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Lista para almacenar las ciudades
        List<Ciudad> ciudades = new ArrayList<>();

        // Crear 5 ciudades como ejemplo
        for (int i = 0; i < 20; i++) {
            Ciudad ciudad = ciudadRepository.save(new Ciudad("Ciudad_" + i, 100 + i * 10)); // Nombre y impuesto_entrada variables
            ciudades.add(ciudad);
        }

        /*
         * List<Person> persons = new ArrayList<>();
         * for (int i = 0; i < 100; i++) {
         * Person person = personRepository.save(new Person("5567" + i, "Carla" + i,
         * "Carlson" + 1));
         * persons.add(person);
         * }
         * 
         * List<Company> companies = new ArrayList<>();
         * for (int i = 0; i < 100; i++) {
         * Company company = companyRepository.save(new Company("compay" + i, "1234" +
         * i));
         * companies.add(company);
         * }
         * 
         * for (int i = 0; i < persons.size(); i++) {
         * Person person = persons.get(i);
         * person.getCompanies().add(companies.get(i % 3));
         * personRepository.save(person);
         * }
         * 
         * 
         * List<Person> retrievedPersons = personRepository.findAll();
         * 
         * for (Person person : retrievedPersons) {
         * // System.out.println(person.getFirstName() + " " + person.getLastName());
         * log.info("{} {}", person.getFirstName(), person.getLastName());
         * for (Company company : person.getCompanies()) {
         * log.info("{}", company.getName());
         * }
         * 
         * }
         */
    }

}
