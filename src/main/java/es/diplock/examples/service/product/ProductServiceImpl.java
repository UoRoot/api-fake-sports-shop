package es.diplock.examples.service.product;

import java.util.HashSet;
import java.util.List;
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
        return productRepository.findById(id)
                .map(ProductMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    public ProductDTO saveProduct(CreateProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Set<Color> colors = new HashSet<>(colorRepository.findAllById(productDTO.getColorsIds()));
        if (colors.size() != productDTO.getColorsIds().size()) {
            throw new ResourceNotFoundException("One or more colors not found");
        }

        Set<SizeEntity> sizes = new HashSet<>(sizeRepository.findAllById(productDTO.getSizesIds()));
        if (sizes.size() != productDTO.getSizesIds().size()) {
            throw new ResourceNotFoundException("One or more sizes not found");
        }

        Product product = productRepository.save(ProductMapper.toEntity(productDTO, category, brand, colors, sizes));
        return ProductMapper.toDto(product);
    }

    public void updateProduct(ProductDTO productDTO) {
    };

    public void deleteProduct(Long id) {
    }

}
