package es.diplock.examples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.diplock.examples.entities.Talla;

@Repository
public interface TallaRepository extends JpaRepository<Talla, Integer> {
}
