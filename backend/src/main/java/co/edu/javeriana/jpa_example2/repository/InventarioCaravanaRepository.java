package co.edu.javeriana.jpa_example2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.jpa_example2.model.InventarioCaravana;

@Repository
public interface InventarioCaravanaRepository extends JpaRepository<InventarioCaravana, Long> {

}
