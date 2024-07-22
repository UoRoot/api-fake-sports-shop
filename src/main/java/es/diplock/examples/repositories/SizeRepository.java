package es.diplock.examples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.diplock.examples.entities.SizeEntity;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {
}
