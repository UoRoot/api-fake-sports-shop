package es.diplock.examples.service.product;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.product.CreateProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.entities.Brand;
import es.diplock.examples.entities.Category;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Product;
import es.diplock.examples.entities.SizeEntity;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.mappers.ProductMapper;
import es.diplock.examples.repositories.BrandRepository;
import es.diplock.examples.repositories.CategoryRepository;
import es.diplock.examples.repositories.ColorRepository;
import es.diplock.examples.repositories.ProductRepository;
import es.diplock.examples.repositories.SizeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ColorRepository colorRepository;

    private final SizeRepository sizeRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO findProductById(Long id) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()) {
            return optional.map(ProductMapper::toDto).get();
        }

        return null;
    }

    public ProductDTO saveProduct(CreateProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Set<Color> colors = getColors(productDTO.getColorsIds());
        Set<SizeEntity> sizes = getSizes(productDTO.getSizesIds());

        Product product = productRepository.save(ProductMapper.toEntity(productDTO, category, brand, colors, sizes));
        return ProductMapper.toDto(product);
    }

    public void updateProduct(ProductDTO productDTO) {
    };

    public void deleteProduct(Long id) {
    }

    private Set<Color> getColors(List<Integer> colorIds) {
        Set<Color> colors = new HashSet<>(colorRepository.findAllById(colorIds));
        if (colors.size() != colorIds.size()) {
            throw new ResourceNotFoundException("One or more colors not found");
        }
        return colors;
    }

    private Set<SizeEntity> getSizes(List<Integer> sizeIds) {
        Set<SizeEntity> sizes = new HashSet<>(sizeRepository.findAllById(sizeIds));
        if (sizes.size() != sizeIds.size()) {
            throw new ResourceNotFoundException("One or more sizes not found");
        }
        return sizes;
    }

}
