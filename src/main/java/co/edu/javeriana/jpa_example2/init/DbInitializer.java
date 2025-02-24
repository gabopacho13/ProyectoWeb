package co.edu.javeriana.jpa_example2.init;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.javeriana.jpa_example2.model.Company;
import co.edu.javeriana.jpa_example2.model.Person;
import co.edu.javeriana.jpa_example2.repository.CompanyRepository;
import co.edu.javeriana.jpa_example2.repository.PersonRepository;
import jakarta.transaction.Transactional;

@Component
public class DbInitializer implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Person person = personRepository.save(new Person("5567" + i, "Carla" + i, "Carlson" + 1));
            persons.add(person);
        }

        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Company company = companyRepository.save(new Company("compay" + i, "1234" + i));
            companies.add(company);
        }

        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            person.getCompanies().add(companies.get(i % 3));
            personRepository.save(person);
        }


        List<Person> retrievedPersons = personRepository.findAll();

        for (Person person : retrievedPersons) {
            // System.out.println(person.getFirstName() + " " + person.getLastName());
            log.info("{} {}", person.getFirstName(), person.getLastName());
            for (Company company : person.getCompanies()) {
                log.info("{}", company.getName());
            }

        }

    }

}
