package es.diplock.examples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.diplock.examples.entities.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
}
