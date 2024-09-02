package es.diplock.examples.service.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
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

            when(productMapper.toDTO(any(Product.class))).thenAnswer(invocation -> {
                Product product = invocation.getArgument(0);
                return toDTO(product);
            });
        }

        @Test
        @DisplayName("Should return a list of products")
        void testFindAllProducts_Success() {
            ProductSearchCriteriaDTO criteria = new ProductSearchCriteriaDTO(
                    1,
                    2,
                    null,
                    null,
                    "male",
                    "price",
                    "asc");

            when(productRepository.findAll(any(ProductSpecification.class), any(Pageable.class)))
                    .thenReturn(new PageImpl<>(List.of(products.get(0), products.get(3))));
            
            Page<ProductDTO> productDTOs = productService.findAllProducts(criteria);

            assertAll(
                    () -> assertNotNull(productDTOs),
                    () -> assertEquals(2, productDTOs.getSize()));
        }

        @Test
        @DisplayName("Should I return a product by ID")
        void testFindProductById_Success() {
            Long id = 1L;
            Optional<Product> optionalProduct = products.stream().filter(p -> p.getId().equals(id)).findFirst();

            when(productRepository.findById(id)).thenReturn(optionalProduct);

            ProductDTO productDTO = productService.findProductById(id);

            assertAll(
                    () -> assertNotNull(productDTO),
                    () -> assertEquals(id, productDTO.id()),
                    () -> assertEquals(toDTO(optionalProduct.get()), productDTO));
        }

        @Test
        @DisplayName("I should save a product")
        void testSaveProduct_Success() {

            // Arrange
            SaveProductDTO productDTO = SaveProductDTO.builder()
                    .name("Pantalon Deportivo")
                    .description("Pantalon deportivo cómodo y flexible, ideal para actividades físicas.")
                    .imageUrl("image.jpg")
                    .price(new BigDecimal("45.00"))
                    .stockQuantity(50)
                    .gender("Male")
                    .sizesIds(Arrays.asList(2, 3))
                    .colorsIds(Arrays.asList(2))
                    .brandId(4)
                    .subcategoryId(2)
                    .build();
            Subcategory mockSubcategory = subcategories.get(1); // Vends
            Brand mockBrand = brands.get(3); // Under Armour
            Set<SizeEntity> mockSizes = Set.of(sizes.get(1), sizes.get(2)); // M and L
            Set<Color> mockColors = Set.of(colors.get(1)); // Blue
            Product mockProductMapper = new Product(
                    null,
                    "Pantalon Deportivo",
                    "Pantalon deportivo cómodo y flexible, ideal para actividades físicas.",
                    "image.jpg",
                    new BigDecimal("45.00"),
                    50,
                    GenderEnum.MALE,
                    null,
                    null,
                    null,
                    null);
            Product savedProduct = new Product(
                    4L,
                    "Pantalon Deportivo",
                    "Pantalon deportivo cómodo y flexible, ideal para actividades físicas.",
                    "image.jpg",
                    new BigDecimal("45.00"),
                    50,
                    GenderEnum.MALE,
                    mockSizes,
                    mockColors,
                    mockBrand,
                    mockSubcategory);

            setupMocks(Optional.of(mockSubcategory), Optional.of(mockBrand), List.copyOf(mockColors),
                    List.copyOf(mockSizes));
            when(productMapper.saveToEntity(productDTO)).thenReturn(mockProductMapper);
            when(productRepository.save(mockProductMapper)).thenReturn(savedProduct);

            ProductDTO actualDTO = productService.saveProduct(productDTO);
            ProductDTO expectedDTO = new ProductDTO(
                    4L,
                    "Pantalon Deportivo",
                    "Pantalon deportivo cómodo y flexible, ideal para actividades físicas.",
                    "image.jpg",
                    new BigDecimal("45.00"),
                    50,
                    "Male",
                    Arrays.asList(2, 3),
                    Arrays.asList(2),
                    2,
                    4);

            assertAll(
                    () -> assertNotNull(actualDTO),
                    () -> assertEquals(expectedDTO, actualDTO),
                    () -> assertEquals(2, actualDTO.sizesIds().size()),
                    () -> assertEquals(1, actualDTO.colorsIds().size()));

            verifyRepositories(1, 1, 1, 1, 1);
        }

        @Test
        @DisplayName("Debería eliminar un producto")
        void tetDeleteProduct_Success() {
           
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
                    .gender("Male")
                    .sizesIds(Arrays.asList(1, 2))
                    .colorsIds(Arrays.asList(1, 3))
                    .brandId(1)
                    .subcategoryId(1)
                    .build();

            Product existingProduct = products.get(0);

            // Mock findById
            when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));

            // Mock updateEntityFromDto
            doNothing().when(productMapper).updateEntityFromDto(eq(updateDTO), any(Product.class));
            
            // Mock related entities
            Subcategory mockSubcategory = subcategories.get(0);
            Brand mockBrand = brands.get(0);
            List<Color> mockColors = Arrays.asList(colors.get(0), colors.get(2));
            List<SizeEntity> mockSizes = Arrays.asList(sizes.get(0), sizes.get(1));

            when(subcategoryRepository.findById(1)).thenReturn(Optional.of(mockSubcategory));
            when(brandRepository.findById(1)).thenReturn(Optional.of(mockBrand));
            when(colorRepository.findAllById(Arrays.asList(1, 3))).thenReturn(mockColors);
            when(sizeRepository.findAllById(Arrays.asList(1, 2))).thenReturn(mockSizes);

            // Mock save
            when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
                Product savedProduct = invocation.getArgument(0);
                savedProduct.setId(id);
                return savedProduct;
            });

            // Act
            ProductDTO result = productService.updateProduct(id, updateDTO);

            // Assert
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(id, result.id()),
                    () -> assertEquals("Updated T-Shirt", result.name()),
                    () -> assertEquals(new BigDecimal("25.00"), result.price()),
                    () -> assertEquals(75, result.stockQuantity()),
                    () -> assertEquals(2, result.sizesIds().size()),
                    () -> assertEquals(2, result.colorsIds().size()),
                    () -> assertEquals(1, result.brandId()),
                    () -> assertEquals(1, result.subcategoryId())
            );

            // Verify
            verify(productRepository).findById(id);
            verify(productMapper).updateEntityFromDto(eq(updateDTO), any(Product.class));
            verify(subcategoryRepository).findById(1);
            verify(brandRepository).findById(1);
            verify(colorRepository).findAllById(Arrays.asList(1, 3));
            verify(sizeRepository).findAllById(Arrays.asList(1, 2));
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

    private static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        List<Integer> sizesIds = mapSizesToSizeIds(product.getSizes());
        List<Integer> colorsIds = mapColorsToColorIds(product.getColors());
        Integer subcategoryId = product.getSubcategory().getId();
        Integer brandId = product.getBrand().getId();
        Long id = product.getId();
        String name = product.getName();
        String description = product.getDescription();
        String imageUrl = product.getImageUrl();
        BigDecimal price = product.getPrice();
        Integer stockQuantity = product.getStockQuantity();
        String gender = mapGenderEnumToGenderString(product.getGender());

        ProductDTO productDTO = new ProductDTO(id, name, description, imageUrl, price, stockQuantity, gender, sizesIds,
                colorsIds, subcategoryId, brandId);

        return productDTO;
    }

    private static Subcategory mapIdToSubcategory(Integer id) {
        if (id == null)
            return null;
        return Subcategory.builder().id(id).build();
    }

    private static Brand mapIdToBrand(Integer id) {
        if (id == null)
            return null;
        return Brand.builder().id(id).build();
    }

    private static Set<SizeEntity> mapSizeIdsToSizes(List<Integer> ids) {
        if (ids == null)
            return null;
        return ids.stream()
                .map(id -> SizeEntity.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    private static List<Integer> mapSizesToSizeIds(Set<SizeEntity> sizes) {
        if (sizes == null)
            return null;
        return sizes.stream()
                .map(size -> size.getId().intValue())
                .collect(Collectors.toList());
    }

    private static Set<Color> mapColorIdsToColors(List<Integer> ids) {
        if (ids == null)
            return null;
        return ids.stream()
                .map(id -> Color.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    private static List<Integer> mapColorsToColorIds(Set<Color> colors) {
        if (colors == null)
            return null;
        return colors.stream()
                .map(color -> color.getId().intValue())
                .collect(Collectors.toList());
    }

    private static GenderEnum mapGenderStringToGenderEnum(String gender) {
        return GenderEnum.getEnum(gender);
    }

    private static String mapGenderEnumToGenderString(GenderEnum gender) {
        return gender.getDescription();
    }

    private void verifyRepositories(int subcategoryTimes, int brandTimes, int colorTimes, int sizeTimes,
            int productTimes) {
        verify(subcategoryRepository, times(subcategoryTimes)).findById(anyInt());
        verify(brandRepository, times(brandTimes)).findById(anyInt());
        verify(colorRepository, times(colorTimes)).findAllById(anyList());
        verify(sizeRepository, times(sizeTimes)).findAllById(anyList());
        verify(productRepository, times(productTimes)).save(any(Product.class));
    }

    private void setupMocks(Optional<Subcategory> subcategory, Optional<Brand> brand, List<Color> colors,
            List<SizeEntity> sizes) {
        if (subcategory != null)
            when(subcategoryRepository.findById(anyInt())).thenReturn(subcategory);
        if (brand != null)
            when(brandRepository.findById(anyInt())).thenReturn(brand);
        if (colors != null)
            when(colorRepository.findAllById(anyList())).thenReturn(colors);
        if (sizes != null)
            when(sizeRepository.findAllById(anyList())).thenReturn(sizes);
    }

    private void updateEntityFromDto(SaveProductDTO dto, Product entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getGender() != null ) {
            entity.setGender( mapGenderStringToGenderEnum( dto.getGender() ) );
        }
        if ( dto.getImageUrl() != null ) {
            entity.setImageUrl( dto.getImageUrl() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
        if ( dto.getStockQuantity() != null ) {
            entity.setStockQuantity( dto.getStockQuantity() );
        }
    }

}
