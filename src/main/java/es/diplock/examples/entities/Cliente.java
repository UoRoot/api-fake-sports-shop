package es.diplock.examples.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clientes")
public class Cliente extends Base {
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    
    @Column(name = "direccion", length = 255)
    private String direccion;
    
    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;
    
    @Column(name = "correo_electronico", nullable = false, unique = true, length = 255)
    private String correoElectronico;
    
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Venta> ventas;
}