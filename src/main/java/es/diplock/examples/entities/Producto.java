package es.diplock.examples.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import es.diplock.examples.enums.GeneroEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto extends Base {

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 100, message = "El nombre del producto debe tener entre 3 y 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener un máximo de 10 dígitos enteros y 2 decimales")
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "La cantidad en stock debe ser mayor o igual a 0")
    @Column(name = "cantidad_stock", nullable = false)
    private Integer cantidadStock;

    @NotNull(message = "El género es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false, length = 1)
    private GeneroEnum genero;

    // @NotNull(message = "El género es obligatorio")
    // @Pattern(regexp = "M|F|U", message = "El género debe ser 'M', 'F' o 'U'")
    // @Column(name = "genero", nullable = false, length = 1)
    // private String genero;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "producto_talla", joinColumns = @JoinColumn(name = "producto_id"), inverseJoinColumns = @JoinColumn(name = "talla_id"))
    private Set<Talla> tallas = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "producto_color", joinColumns = @JoinColumn(name = "producto_id"), inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colores = new HashSet<>();

    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<DetalleVenta> detallesVenta;

}
