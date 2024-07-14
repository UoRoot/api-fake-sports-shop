package es.diplock.examples.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "nombre is required")
    @Size(min = 3, max = 100, message = "nombre debe tener entre 3 a 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "apellido is required")
    @Size(min = 3, max = 100, message = "apellido debe tener entre 3 a 100 caracteres")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Size(min = 3, max = 100, message = "direccion debe tener entre 3 a 255 caracteres")
    @Column(name = "direccion", length = 255)
    private String direccion;

    @Size(min = 3, max = 100, message = "codigo postal debe tener entre 6 a 10 caracteres")
    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @NotBlank(message = "nombre is required")
    @Email
    @Size(min = 3, max = 100, message = "correo electronico no debería tener más de 255 caracteres")
    @Column(name = "correo_electronico", nullable = false, unique = true, length = 255)
    private String correoElectronico;

    @Pattern(regexp = "^[0-9]{9}$", message = "ingresa un numero de teléfono validado")
    @Column(name = "telefono", length = 15)
    private String telefono;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Venta> ventas;
}