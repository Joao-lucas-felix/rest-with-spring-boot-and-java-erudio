package br.com.restwithspringbootandjavaerudio.services;

import br.com.restwithspringbootandjavaerudio.domain.Person;
import br.com.restwithspringbootandjavaerudio.exception.UnfoundResourceExeception;
import br.com.restwithspringbootandjavaerudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;


@Service // diz que é o spring que tem que injetar essa calsse
public class PersonService {
    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonService.class.getName());
    @Autowired
    private PersonRepository repository;

    public Person findById(Long id) {

        logger.info("Finding a Person with id: " + id);
        return repository.findById(id)
                .orElseThrow(
                        () -> new UnfoundResourceExeception("Unfound Resource with this ID")
                );
    }

    public List<Person> findALL() {
        logger.info("finding all people");
        return repository.findAll();
    }

    public Person createPerson(Person p) {
        logger.info("creating a new person");
        return repository.save(p);
    }

    public Person updatePerson(Person person) {
        logger.info("updating a person");
        Person personToUpdate = repository.findById(person.getId())
                .orElseThrow(
                        () -> new UnfoundResourceExeception("Unfound Resource with this ID")
                );
        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setLastName(person.getLastName());
        personToUpdate.setAddress(person.getAddress());
        personToUpdate.setGender(person.getGender());
        return repository.save(personToUpdate);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting the person withs this id: " + id);
        Person personToDelete = repository.findById(id)
                .orElseThrow(
                        () -> new UnfoundResourceExeception("Unfound Resource with this ID")
                );
        repository.delete(personToDelete);
    }

}
