package co.edu.javeriana.jpa_example2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.jpa_example2.model.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

}
