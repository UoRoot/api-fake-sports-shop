package es.diplock.examples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.diplock.examples.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
