package es.diplock.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.diplock.examples.dtos.producto.CreateProductoDTO;
import es.diplock.examples.dtos.producto.ProductoDTO;
import es.diplock.examples.entities.Categoria;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Producto;
import es.diplock.examples.entities.Talla;
import es.diplock.examples.enums.GeneroEnum;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.repositories.CategoriaRepository;
import es.diplock.examples.repositories.ColorRepository;
import es.diplock.examples.repositories.ProductoRepository;
import es.diplock.examples.repositories.TallaRepository;
import es.diplock.examples.service.Producto.ProductoServiceImpl;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

	@Mock
	private ProductoRepository productoRepository;

	@Mock
	private ColorRepository colorRepository;

	@Mock
	private TallaRepository tallaRepository;

	@Mock
	private CategoriaRepository categoriaRepository;

	@InjectMocks
	private ProductoServiceImpl productoService;

	private CreateProductoDTO createProductoDTO;

	@BeforeEach
	void setUp() {
		createProductoDTO = CreateProductoDTO.builder()
				.nombre("Producto Test")
				.descripcion("Descripción del producto test")
				.precio(new BigDecimal("100.00"))
				.cantidadStock(10)
				.genero("Unisex")
				.tallasIds(Arrays.asList(1, 2))
				.coloresIds(Arrays.asList(1, 2))
				.categoriaId(1)
				.build();
	}

	@Test
	void testSaveProduct_Success() {
		Categoria categoria = new Categoria(1, "Categoría Test");
		Set<Color> colores = new HashSet<>(Arrays.asList(new Color(1, "Rojo"), new Color(2, "Azul")));
		Set<Talla> tallas = new HashSet<>(Arrays.asList(new Talla(1, "S"), new Talla(2, "M")));

		when(categoriaRepository.findById(anyInt())).thenReturn(Optional.of(categoria));
		when(colorRepository.findAllById(anyList())).thenReturn(new ArrayList<>(colores));
		when(tallaRepository.findAllById(anyList())).thenReturn(new ArrayList<>(tallas));
		when(productoRepository.save(any(Producto.class))).thenReturn(Producto.builder()
				.id(1L)
				.nombre("Producto Test")
				.descripcion("Descripción del producto test")
				.precio(new BigDecimal("100.00"))
				.cantidadStock(10)
				.genero(GeneroEnum.UNISEX)
				.tallas(tallas)
				.colores(colores)
				.categoria(categoria)
				.build());

		ProductoDTO result = productoService.saveProduct(createProductoDTO);

		assertNotNull(result);
		assertEquals("Producto Test", result.nombre());
		assertEquals(new BigDecimal("100.00"), result.precio());
		assertEquals(10, result.cantidadStock());
		assertEquals("Unisex", result.genero());
		assertEquals(2, result.tallas().size());
		assertEquals(2, result.colores().size());

		verify(categoriaRepository, times(1)).findById(anyInt());
		verify(colorRepository, times(1)).findAllById(anyList());
		verify(tallaRepository, times(1)).findAllById(anyList());
		verify(productoRepository, times(1)).save(any(Producto.class));
	}

	@Test
	void testSaveProduct_CategoriaNotFound() {
		when(categoriaRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> productoService.saveProduct(createProductoDTO));

		verify(categoriaRepository, times(1)).findById(anyInt());
		verify(colorRepository, times(0)).findAllById(anyList());
		verify(tallaRepository, times(0)).findAllById(anyList());
		verify(productoRepository, times(0)).save(any(Producto.class));
	}

	@Test
	void testSaveProduct_ColorNotFound() {
		Categoria categoria = new Categoria(1, "Categoría Test");
		Set<Color> colores = new HashSet<>(Collections.singletonList(new Color(1, "Rojo"))); // Faltan colores

		when(categoriaRepository.findById(anyInt())).thenReturn(Optional.of(categoria));
		when(colorRepository.findAllById(anyList())).thenReturn(new ArrayList<>(colores));

		assertThrows(ResourceNotFoundException.class, () -> productoService.saveProduct(createProductoDTO));

		verify(categoriaRepository, times(1)).findById(anyInt());
		verify(colorRepository, times(1)).findAllById(anyList());
		verify(tallaRepository, times(0)).findAllById(anyList());
		verify(productoRepository, times(0)).save(any(Producto.class));
	}

	@Test
	void testSaveProduct_TallaNotFound() {
		Categoria categoria = new Categoria(1, "Categoría Test");
		Set<Color> colores = new HashSet<>(Arrays.asList(new Color(1, "Rojo"), new Color(2, "Azul")));
		Set<Talla> tallas = new HashSet<>(Collections.singletonList(new Talla(1, "S"))); // Faltan tallas

		when(categoriaRepository.findById(anyInt())).thenReturn(Optional.of(categoria));
		when(colorRepository.findAllById(anyList())).thenReturn(new ArrayList<>(colores));
		when(tallaRepository.findAllById(anyList())).thenReturn(new ArrayList<>(tallas));

		assertThrows(ResourceNotFoundException.class, () -> productoService.saveProduct(createProductoDTO));

		verify(categoriaRepository, times(1)).findById(anyInt());
		verify(colorRepository, times(1)).findAllById(anyList());
		verify(tallaRepository, times(1)).findAllById(anyList());
		verify(productoRepository, times(0)).save(any(Producto.class));
	}
}