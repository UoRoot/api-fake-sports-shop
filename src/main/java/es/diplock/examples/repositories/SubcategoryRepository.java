package es.diplock.examples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.diplock.examples.entities.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer>{
    
}
