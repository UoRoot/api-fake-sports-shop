package es.diplock.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.diplock.examples.dtos.product.CreateProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.entities.Brand;
import es.diplock.examples.entities.Category;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Product;
import es.diplock.examples.entities.SizeEntity;
import es.diplock.examples.enums.GenderEnum;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.repositories.BrandRepository;
import es.diplock.examples.repositories.CategoryRepository;
import es.diplock.examples.repositories.ColorRepository;
import es.diplock.examples.repositories.ProductRepository;
import es.diplock.examples.repositories.SizeRepository;
import es.diplock.examples.service.product.ProductServiceImpl;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

	@Nested
	@DisplayName("Metodo saveProduct() de ProductService")
	class whenObtainingProduct {
		@Mock
		private ProductRepository productRepository;

		private ProductServiceImpl productService;
	}

	@Nested
	@DisplayName("Metodo saveProduct() de ProductService")
	class WhenCreatingProduct {
		@Mock
		private ProductRepository productRepository;

		@Mock
		private ColorRepository colorRepository;

		@Mock
		private SizeRepository sizeRepository;

		@Mock
		private CategoryRepository categoryRepository;

		@Mock
		private BrandRepository brandRepository;

		@InjectMocks
		private ProductServiceImpl productService;

		private CreateProductDTO createProductDTO;

		@BeforeEach
		void setUp() {
			createProductDTO = CreateProductDTO.builder()
					.name("Product Test")
					.description("Description of the test product.")
					.price(new BigDecimal("100.00"))
					.stockQuantity(10)
					.gender("Unisex")
					.sizesIds(Arrays.asList(1, 2))
					.colorsIds(Arrays.asList(1, 2))
					.categoryId(1)
					.brandId(1)
					.build();
		}

		private void verifyRepositories(int categoryTimes, int brandTimes, int colorTimes, int sizeTimes,
				int productTimes) {
			verify(categoryRepository, times(categoryTimes)).findById(anyInt());
			verify(brandRepository, times(brandTimes)).findById(anyInt());
			verify(colorRepository, times(colorTimes)).findAllById(anyList());
			verify(sizeRepository, times(sizeTimes)).findAllById(anyList());
			verify(productRepository, times(productTimes)).save(any(Product.class));
		}

		private void setupMocks(Optional<Category> category, Optional<Brand> brand, List<Color> colors,
				List<SizeEntity> sizes) {
			if (category != null)
				when(categoryRepository.findById(anyInt())).thenReturn(category);
			if (brand != null)
				when(brandRepository.findById(anyInt())).thenReturn(brand);
			if (colors != null)
				when(colorRepository.findAllById(anyList())).thenReturn(colors);
			if (sizes != null)
				when(sizeRepository.findAllById(anyList())).thenReturn(sizes);
		}

		@Test
		@DisplayName("Prodando si un producto guardado correctamente")
		void testSaveProduct_Success() {
			Category category = new Category(1, "Category Test");
			Brand brand = new Brand(1, "Brand test");
			Set<Color> colors = new HashSet<>(Arrays.asList(new Color(1, "Red"), new Color(2, "Blue")));
			Set<SizeEntity> sizes = new HashSet<>(Arrays.asList(new SizeEntity(1, "S"), new SizeEntity(2, "M")));

			setupMocks(Optional.of(category), Optional.of(brand), new ArrayList<>(colors), new ArrayList<>(sizes));
			when(productRepository.save(any(Product.class))).thenReturn(Product.builder()
					.id(1L)
					.name("Product Test")
					.description("Description of the test product.")
					.price(new BigDecimal("100.00"))
					.stockQuantity(10)
					.gender(GenderEnum.UNISEX)
					.sizes(sizes)
					.colors(colors)
					.category(category)
					.brand(brand)
					.build());

			ProductDTO result = productService.saveProduct(createProductDTO);

			assertNotNull(result);
			assertEquals("Product Test", result.name());
			assertEquals(new BigDecimal("100.00"), result.price());
			assertEquals(10, result.stockQuantity());
			assertEquals("Unisex", result.gender());
			assertEquals(2, result.sizesIds().size());
			assertEquals(2, result.colorsIds().size());

			verifyRepositories(1, 1, 1, 1, 1);
		}

		@Test
		@DisplayName("En caso de no encontrar una Categoria")
		void testSaveProduct_CategoryNotFound() {
			setupMocks(Optional.empty(), null, null, null);

			assertThrows(ResourceNotFoundException.class, () -> productService.saveProduct(createProductDTO));

			verifyRepositories(1, 0, 0, 0, 0);
		}

		@Test
		@DisplayName("En caso de no encontrar una Marca")
		void testSaveProduct_BrandNotFound() {
			Category category = new Category(1, "Category Test");

			setupMocks(Optional.of(category), Optional.empty(), null, null);

			assertThrows(ResourceNotFoundException.class, () -> productService.saveProduct(createProductDTO));

			verifyRepositories(1, 1, 0, 0, 0);
		}

		@Test
		@DisplayName("En caso de no encontrar un Color")
		void testSaveProduct_ColorNotFound() {
			Category category = new Category(1, "Category Test");
			Brand brand = new Brand(1, "Brand test");
			Set<Color> colors = new HashSet<>(Collections.singletonList(new Color(1, "Red"))); // faltan 1 color

			setupMocks(Optional.of(category), Optional.of(brand), new ArrayList<>(colors), null);

			assertThrows(ResourceNotFoundException.class, () -> productService.saveProduct(createProductDTO));

			verifyRepositories(1, 1, 1, 0, 0);
		}

		@Test
		@DisplayName("En caso de no encontrar una Talla")
		void testSaveProduct_SizeNotFound() {
			Category category = new Category(1, "Category Test");
			Brand brand = new Brand(1, "Brand test");
			Set<Color> colors = new HashSet<>(Arrays.asList(new Color(1, "Red"), new Color(2, "Blue"))); // colores
																											// completos
			Set<SizeEntity> sizes = new HashSet<>(Collections.singletonList(new SizeEntity(1, "S"))); // Falta una talla
																										// (size)

			setupMocks(Optional.of(category), Optional.of(brand), new ArrayList<>(colors), new ArrayList<>(sizes));

			assertThrows(ResourceNotFoundException.class, () -> productService.saveProduct(createProductDTO));

			verifyRepositories(1, 1, 1, 1, 0);
		}
	}
}