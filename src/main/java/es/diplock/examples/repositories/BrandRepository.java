package es.diplock.examples.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import es.diplock.examples.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
