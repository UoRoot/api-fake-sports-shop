package es.diplock.examples.service.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.dtos.product.ProductSearchCriteriaDTO;
import es.diplock.examples.dtos.product.SaveProductDTO;
import es.diplock.examples.entities.Brand;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Product;
import es.diplock.examples.entities.SizeEntity;
import es.diplock.examples.entities.Subcategory;
import es.diplock.examples.enums.GenderEnum;
import es.diplock.examples.mappers.ProductMapper;
import es.diplock.examples.mappers.ProductMapperImpl;
import es.diplock.examples.repositories.BrandRepository;
import es.diplock.examples.repositories.ColorRepository;
import es.diplock.examples.repositories.ProductRepository;
import es.diplock.examples.repositories.SizeRepository;
import es.diplock.examples.repositories.SubcategoryRepository;
import es.diplock.examples.specification.ProductSpecification;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private SizeRepository sizeRepository;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private BrandRepository brandRepository;

    private ProductMapper productMapper;

    private ProductServiceImpl productService;

    @Nested
    @DisplayName("Successful testing of service layer methods")
    class SuccessTests {

        private List<Subcategory> subcategories;
        private List<Brand> brands;
        private List<Color> colors;
        private List<SizeEntity> sizes;
        private List<Product> products;

        @BeforeEach
        void setUp() {
            productMapper = new ProductMapperImpl();
            productService = new ProductServiceImpl(productRepository, colorRepository, sizeRepository,
                    subcategoryRepository, brandRepository, productMapper);

            subcategories = new ArrayList<>();
            subcategories.add(new Subcategory(1, "T-Shirts"));
            subcategories.add(new Subcategory(2, "Vest"));
            subcategories.add(new Subcategory(3, "Shorts"));
            subcategories.add(new Subcategory(4, "Pants"));

            brands = new ArrayList<>();
            brands.add(new Brand(1, "Puma", "image.url"));
            brands.add(new Brand(2, "Nike", "image.url"));
            brands.add(new Brand(3, "Adidas", "image.url"));
            brands.add(new Brand(4, "Under Armour", "image.url"));

            colors = new ArrayList<>();
            colors.add(new Color(1, "Red"));
            colors.add(new Color(2, "Blue"));
            colors.add(new Color(3, "Black"));
            colors.add(new Color(4, "White"));

            sizes = new ArrayList<>();
            sizes.add(new SizeEntity(1, "S"));
            sizes.add(new SizeEntity(2, "M"));
            sizes.add(new SizeEntity(3, "L"));
            sizes.add(new SizeEntity(4, "XL"));

            Product product1 = new Product(
                    1L,
                    "Camiseta de algodón",
                    "Camiseta deportiva clásica de algodón, ideal para entrenamientos de baja intensidad.",
                    "image.jpg",
                    new BigDecimal("19.99"),
                    100,
                    GenderEnum.MALE,
                    Set.of(sizes.get(1), sizes.get(2)), // Tallas M y L
                    Set.of(colors.get(3)), // Color Blanco
                    brands.get(2), // Addidas
                    subcategories.get(0)); // T-Shirts

            Product product2 = new Product(
                    2L,
                    "Leggins de compresión",
                    "Leggins de compresión para running, con tecnología de secado rápido y soporte muscular.",
                    "image.jpg",
                    new BigDecimal("59.99"),
                    30,
                    GenderEnum.FEMALE,
                    Set.of(sizes.get(0), sizes.get(1), sizes.get(2)), // Tallas S, M y L
                    Set.of(colors.get(1), colors.get(2)), // Colores Azul y Negro
                    brands.get(1), // Nike
                    subcategories.get(1)); // Vest

            Product product3 = new Product(
                    3L,
                    "Gorra de running ajustable",
                    "Gorra deportiva ajustable con visera curva, ideal para proteger del sol durante tus carreras.",
                    "image.jpg",
                    new BigDecimal("15.99"),
                    200,
                    GenderEnum.UNISEX,
                    Set.of(), // Sin tallas
                    Set.of(colors.get(0), colors.get(2), colors.get(3)), // Colores Rojo, Negro y Blanco
                    brands.get(0), // Puma
                    subcategories.get(2)); // Shorts

            Product product4 = new Product(
                    4L,
                    "Gorra de running ajustable",
                    "Gorra deportiva ajustable con visera curva, ideal para proteger del sol durante tus carreras.",
                    "image.jpg",
                    new BigDecimal("15.99"),
                    200,
                    GenderEnum.MALE,
                    Set.of(sizes.get(2), sizes.get(3)), // L & XL
                    Set.of(colors.get(0), colors.get(2), colors.get(3)), // Colores Rojo, Negro y Blanco
                    brands.get(0), // Puma
                    subcategories.get(3)); // Pants

            products = Arrays.asList(product1, product2, product3, product4);
        }

        @Test
        @DisplayName("Should return a list of products")
        void testFindAllProducts_Success() {
            // Given
            ProductSearchCriteriaDTO criteria = new ProductSearchCriteriaDTO(
                    1,
                    2,
                    null,
                    null,
                    "male",
                    "price",
                    "asc");

            // When
            when(productRepository.findAll(any(ProductSpecification.class), any(Pageable.class)))
                    .thenReturn(new PageImpl<>(List.of(products.get(0), products.get(3))));

            Page<ProductDTO> actualProducts = productService.findAllProducts(criteria);

            // Then
            assertAll(
                    () -> assertNotNull(actualProducts),
                    () -> assertEquals(2, actualProducts.getSize()),
                    () -> assertEquals(1L, actualProducts.getContent().get(0).getId()),
                    () -> assertEquals(4L, actualProducts.getContent().get(1).getId()));

            // Verify
            verify(productRepository).findAll(any(ProductSpecification.class), any(Pageable.class));
        }

        @Test
        @DisplayName("Should I return a product by ID")
        void testFindProductById_Success() {
            // Given
            Long id = 1L;

            // When
            Optional<Product> optionalProduct = products.stream().filter(p -> p.getId().equals(id)).findFirst();
            when(productRepository.findById(any(Long.class))).thenReturn(optionalProduct);

            ProductDTO productDTO = productService.findProductById(id);

            // Then
            assertAll(
                    () -> assertNotNull(productDTO),
                    () -> assertEquals(1L, productDTO.getId()));
            
            // Verify
            verify(productRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("I should save a product")
        void testSaveProduct_Success() {
            // Given
            SaveProductDTO productDTO = SaveProductDTO.builder()
                    .name("Pantalon Deportivo")
                    .description("Pantalon deportivo cómodo y flexible, ideal para actividades físicas.")
                    .imageUrl("image.jpg")
                    .price(new BigDecimal("45.00"))
                    .stockQuantity(50)
                    .gender("male")
                    .sizesIds(Arrays.asList(2, 3))
                    .colorsIds(Arrays.asList(2))
                    .brandId(4)
                    .subcategoryId(2)
                    .build();
            
            // When
            // mock related entities
            Subcategory mockSubcategory = subcategories.get(1); // Vends
            Brand mockBrand = brands.get(3); // Under Armour
            List<SizeEntity> mockSizes = List.of(sizes.get(2), sizes.get(1)); // M and L
            List<Color> mockColors = List.of(colors.get(1)); // Blue

            when(subcategoryRepository.findById(2)).thenReturn(Optional.of(mockSubcategory));
            when(brandRepository.findById(4)).thenReturn(Optional.of(mockBrand));
            when(colorRepository.findAllById(anyList())).thenReturn(mockColors);
            when(sizeRepository.findAllById(anyList())).thenReturn(mockSizes);

            // mock save
            when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
                Product savedProduct2 = invocation.getArgument(0);
                savedProduct2.setId(4L);
                return savedProduct2;
            });

            ProductDTO actualDTO = productService.saveProduct(productDTO);

            // Then
            assertAll(
                () -> assertNotNull(actualDTO),
                () -> assertEquals(4L, actualDTO.getId()),
                () -> assertEquals(GenderEnum.MALE.getDescription(), actualDTO.getGender()),
                () -> assertEquals(4, actualDTO.getBrandId()),
                () -> assertEquals(2, actualDTO.getSubcategoryId()),
                () -> assertTrue(actualDTO.getColorsIds().containsAll(List.of(2))),
                () -> assertTrue(actualDTO.getSizesIds().containsAll(List.of(2, 3))));

            // Verify
            verify(subcategoryRepository, times(1)).findById(2);
            verify(brandRepository, times(1)).findById(4);
            verify(colorRepository, times(1)).findAllById(anyList());
            verify(sizeRepository, times(1)).findAllById(anyList());
            verify(productRepository, times(1)).save(any(Product.class));
        }

        @Test
        @DisplayName("Debería eliminar un producto")
        void testDeleteProduct_Success() {
            Long id = 1L;
            Optional<Product> optionalProduct = products.stream().filter(p -> p.getId().equals(id)).findFirst();

            when(productRepository.findById(id)).thenReturn(optionalProduct);

            productService.deleteProduct(id);

            verify(productRepository, times(1)).delete(optionalProduct.get());
        }

        @Test
        @DisplayName("Debería actualizar un producto")
        void testUpdateProduct_Success() {
            Long id = 1L;
            SaveProductDTO updateDTO = SaveProductDTO.builder()
                    .name("Updated T-Shirt")
                    .description("An updated comfortable T-shirt.")
                    .imageUrl("updated-image.jpg")
                    .price(new BigDecimal("25.00"))
                    .stockQuantity(75)
                    .gender("male")
                    .sizesIds(List.of(1, 2))
                    .colorsIds(List.of(1, 3))
                    .brandId(1)
                    .subcategoryId(1)
                    .build();

            Product product = products.get(0);

            //When
            when(productRepository.findById(id)).thenReturn(Optional.of(product));

            // mock related entities
            Brand mockBrand = brands.get(0);
            List<Color> mockColors = Arrays.asList(colors.get(0), colors.get(2));
            List<SizeEntity> mockSizes = Arrays.asList(sizes.get(0), sizes.get(1));

            when(brandRepository.getReferenceById(1)).thenReturn(mockBrand);
            when(colorRepository.findAllById(anyList())).thenReturn(mockColors);
            when(sizeRepository.findAllById(anyList())).thenReturn(mockSizes);


            // mock save
            when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
                return invocation.getArgument(0);
            });

            ProductDTO actual = productService.updateProduct(id, updateDTO);

            // Then
            assertAll(
                    () -> assertNotNull(actual),
                    () -> assertEquals(id, actual.getId()),
                    () -> assertEquals("Updated T-Shirt", actual.getName()),
                    () -> assertEquals(new BigDecimal("25.00"), actual.getPrice()),
                    () -> assertEquals(75, actual.getStockQuantity()),
                    () -> assertTrue(actual.getSizesIds().containsAll(List.of(1, 2))),
                    () -> assertTrue(actual.getColorsIds().containsAll(List.of(1, 3))),
                    () -> assertEquals(1, actual.getBrandId()),
                    () -> assertEquals(1, actual.getSubcategoryId()));

            // Verify
            verify(productRepository).findById(id);
            verify(brandRepository).getReferenceById(1);
            verify(colorRepository).findAllById(List.of(1, 3));
            verify(sizeRepository).findAllById(List.of(1, 2));
            verify(productRepository).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("Tests de Error")
    class ErrorTests {

        @Test
        @DisplayName("Debería lanzar excepción cuando el producto no se encuentra")
        void testFindProductById_NotFound() {
            // Configurar los mocks para lanzar ResourceNotFoundException cuando el producto
            // no exista
            // productService.findProductById(id) debería lanzar ResourceNotFoundException
        }

        @Test
        @DisplayName("Debería lanzar excepción cuando no hay productos")
        void testFindAllProducts_NoContent() {
            // Configurar los mocks para lanzar NoContentException cuando no haya productos
            // productService.findAllProducts() debería lanzar NoContentException
        }

        @Test
        @DisplayName("Debería lanzar excepción cuando hay un error al guardar un producto")
        void testSaveProduct_Error() {
            // Configurar los mocks para lanzar una excepción cuando haya un error al
            // guardar un producto
            // productService.saveProduct(createProductDTO) debería lanzar
            // SomeOtherException
        }

        @Test
        @DisplayName("Debería lanzar excepción cuando el producto a actualizar no se encuentra")
        void testUpdateProduct_NotFound() {
            // Configurar los mocks para lanzar ResourceNotFoundException cuando el producto
            // a actualizar no exista
            // productService.updateProduct(productDTO) debería lanzar
            // ResourceNotFoundException
        }

        @Test
        @DisplayName("Debería lanzar excepción cuando el producto a eliminar no se encuentra")
        void testDeleteProduct_NotFound() {
            // Configurar los mocks para lanzar ResourceNotFoundException cuando el producto
            // a eliminar no exista
            // productService.deleteProduct(id) debería lanzar ResourceNotFoundException
        }
    }
}
