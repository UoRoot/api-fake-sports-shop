package es.diplock.examples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.diplock.examples.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
