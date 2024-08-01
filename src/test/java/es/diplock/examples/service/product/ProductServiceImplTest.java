package es.diplock.examples.service.product;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import es.diplock.examples.dtos.product.CreateProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.entities.Brand;
import es.diplock.examples.entities.Category;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Product;
import es.diplock.examples.entities.SizeEntity;
import es.diplock.examples.enums.GenderEnum;
import es.diplock.examples.mappers.ProductMapper;
import es.diplock.examples.repositories.BrandRepository;
import es.diplock.examples.repositories.CategoryRepository;
import es.diplock.examples.repositories.ColorRepository;
import es.diplock.examples.repositories.ProductRepository;
import es.diplock.examples.repositories.SizeRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

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

    @Nested
    @DisplayName("Successful testing of service layer methods")
    class SuccessTests {

        private static List<Category> categories;
        private static List<Brand> brands;
        private static List<Color> colors;
        private static List<SizeEntity> sizes;
        private static List<Product> products;

        @BeforeAll
        static void setUp() {

            /*
             * Creando instancias estáticas para simular los repositorios
             */
            categories = new ArrayList<>();
            categories.add(new Category(1, "Prendas superiores"));
            categories.add(new Category(2, "Prendas inferiores"));
            categories.add(new Category(3, "Calzado"));
            categories.add(new Category(4, "Accesorios"));

            brands = new ArrayList<>();
            brands.add(new Brand(1, "Puma"));
            brands.add(new Brand(2, "Nike"));
            brands.add(new Brand(3, "Adidas"));
            brands.add(new Brand(4, "Under Armour"));

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
                    new BigDecimal("19.99"),
                    100,
                    GenderEnum.MALE,
                    Set.of(sizes.get(1), sizes.get(2)), // Tallas M y L
                    Set.of(colors.get(3)), // Color Blanco
                    categories.get(0), // Prendas superiores
                    brands.get(2));

            Product product2 = new Product(
                    2L,
                    "Leggins de compresión",
                    "Leggins de compresión para running, con tecnología de secado rápido y soporte muscular.",
                    new BigDecimal("59.99"),
                    30,
                    GenderEnum.FEMALE,
                    Set.of(sizes.get(0), sizes.get(1), sizes.get(2)), // Tallas S, M y L
                    Set.of(colors.get(1), colors.get(2)), // Colores Azul y Negro
                    categories.get(1), // Prendas inferiores
                    brands.get(1) // Nike
            );

            Product product3 = new Product(
                    3L,
                    "Gorra de running ajustable",
                    "Gorra deportiva ajustable con visera curva, ideal para proteger del sol durante tus carreras.",
                    new BigDecimal("15.99"),
                    200,
                    GenderEnum.UNISEX,
                    Set.of(), // Sin tallas
                    Set.of(colors.get(0), colors.get(2), colors.get(3)), // Colores Rojo, Negro y Blanco
                    categories.get(3), // Accesorios
                    brands.get(0) // Puma
            );

            products = Arrays.asList(product1, product2, product3);

        }

        @Test
        @DisplayName("Should return a list of products")
        void testFindAllProducts_Success() {
            when(productRepository.findAll()).thenReturn(products);

            List<ProductDTO> productDTOs = productService.findAllProducts();

            assertAll(
                    () -> assertNotNull(productDTOs),
                    () -> assertEquals(3, productDTOs.size()));
        }

        @Test
        @DisplayName("Should I return a product by ID")
        void testFindProductById_Success() {
            Long id = 1L;
            Optional<Product> product = products.stream()
                    .filter(p -> p.getId().equals(id)).findFirst();

            when(productRepository.findById(id)).thenReturn(product);

            ProductDTO productDTO = productService.findProductById(id);

            assertAll(
                    () -> assertNotNull(productDTO),
                    () -> assertEquals(id, productDTO.id()),
                    () -> assertEquals(ProductMapper.toDto(product.get()), productDTO));
        }

        @Test
        @DisplayName("I should save a product")
        void testSaveProduct_Success() {
            Category actualCategory = categories.get(1); // Prendas superiores
            Brand actualBrand = brands.get(3); // Under Armour
            Set<SizeEntity> actualSizes = Set.of(sizes.get(1), sizes.get(2)); // M and L
            Set<Color> actualColors = Set.of(colors.get(1)); // Blue

            setupMocks(Optional.of(actualCategory), Optional.of(actualBrand), new ArrayList<>(actualColors),
                    new ArrayList<>(actualSizes));
            when(productRepository.save(any(Product.class))).thenReturn(new Product(
                    4L,
                    "Pantalon Deportivo",
                    "Pantalon deportivo cómodo y flexible, ideal para actividades físicas.",
                    new BigDecimal("45.00"),
                    50,
                    GenderEnum.MALE,
                    actualSizes,
                    actualColors,
                    actualCategory,
                    actualBrand));

            CreateProductDTO createProductDTO = CreateProductDTO.builder()
                    .name("Pantalon Deportivo")
                    .description("Pantalon deportivo cómodo y flexible, ideal para actividades físicas.")
                    .price(new BigDecimal("45.00"))
                    .stockQuantity(50)
                    .gender("Male")
                    .sizesIds(Arrays.asList(2, 3))
                    .colorsIds(Arrays.asList(2))
                    .categoryId(2)
                    .brandId(4)
                    .build();

            ProductDTO actualDto = productService.saveProduct(createProductDTO);
            ProductDTO expectedDto = new ProductDTO(
                    4L,
                    "Pantalon Deportivo",
                    "Pantalon deportivo cómodo y flexible, ideal para actividades físicas.",
                    new BigDecimal("45.00"),
                    50,
                    "Male",
                    new HashSet<>(Arrays.asList(2, 3)),
                    new HashSet<>(Arrays.asList(2)),
                    2,
                    4);

            assertAll(
                    () -> assertNotNull(actualDto),
                    () -> assertEquals(expectedDto, actualDto),
                    () -> assertEquals(2, actualDto.sizesIds().size()),
                    () -> assertEquals(1, actualDto.colorsIds().size()));

            verifyRepositories(1, 1, 1, 1, 1);
        }

        @Test
        @DisplayName("Debería eliminar un producto")
        void tetDeleteProduct_Success() {
            // Configurar los mocks para eliminar un producto por ID
            // productService.delseteProduct(id) debería eliminar el producto correctamente
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
        @DisplayName("Debería actualizar un producto")
        void testUpdateProduct_Success() {
            // Configurar los mocks para actualizar un producto
            // productService.updateProduct(productDTO) debería actualizar el producto
            // correctamente
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
