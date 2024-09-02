package es.diplock.examples.service.product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.product.SaveProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.dtos.product.ProductSearchCriteriaDTO;
import es.diplock.examples.entities.Brand;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Product;
import es.diplock.examples.entities.SizeEntity;
import es.diplock.examples.entities.Subcategory;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.mappers.ProductMapper;
import es.diplock.examples.repositories.BrandRepository;
import es.diplock.examples.repositories.ColorRepository;
import es.diplock.examples.repositories.ProductRepository;
import es.diplock.examples.repositories.SizeRepository;
import es.diplock.examples.repositories.SubcategoryRepository;
import es.diplock.examples.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ColorRepository colorRepository;

    private final SizeRepository sizeRepository;

    private final SubcategoryRepository subcategoryRepository;

    private final BrandRepository brandRepository;

    private final ProductMapper productMapper;

    public Page<ProductDTO> findAllProducts(ProductSearchCriteriaDTO criteria) {
        Specification<Product> spec = new ProductSpecification(criteria);
        Page<Product> products = productRepository.findAll(spec, PageRequest.of(criteria.getPage() - 1, criteria.getSize()));
        return products.map(productMapper::toDTO);
    }

    public ProductDTO findProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElse(null);
    }

    public ProductDTO saveProduct(SaveProductDTO productDTO) {
        Subcategory subcategory = findCategoryById(productDTO.getSubcategoryId());
        Brand brand = findBrandById(productDTO.getBrandId());
        Set<Color> colors = new HashSet<>(findColorsByIds(productDTO.getColorsIds()));
        Set<SizeEntity> sizes = new HashSet<>(findSizesByIds(productDTO.getSizesIds()));

        Product product = productMapper.saveToEntity(productDTO);
        product.setSubcategory(subcategory);
        product.setBrand(brand);
        product.setColors(colors);
        product.setSizes(sizes);

        Product savedProduct = productRepository
                .save(product);

        return productMapper.toDTO(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, SaveProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {
                    productMapper.updateEntityFromDto(productDTO, product);
                    setRelatedEntities(product, productDTO);
                    Product updatedProduct = productRepository.save(product);
                    return productMapper.toDTO(updatedProduct);
                })
                .orElse(null);
    };

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        product -> productRepository.delete(product),
                        () -> {
                            throw new ResourceNotFoundException("Product not found with id: " + id);
                        });

    }

    private Subcategory findCategoryById(Integer categoryId) {
        return subcategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private Brand findBrandById(Integer brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
    }

    private List<Color> findColorsByIds(List<Integer> colorIds) {
        List<Color> colors = colorRepository.findAllById(colorIds);
        if (colors.size() != colorIds.size()) {
            throw new ResourceNotFoundException("One or more colors not found");
        }
        return colors;
    }

    private List<SizeEntity> findSizesByIds(List<Integer> sizeIds) {
        List<SizeEntity> sizes = sizeRepository.findAllById(sizeIds);
        if (sizes.size() != sizeIds.size()) {
            throw new ResourceNotFoundException("One or more sizes not found");
        }
        return sizes;
    }

    private void setRelatedEntities(Product product, SaveProductDTO dto) {
        if (dto.getSubcategoryId() != null) {
            Subcategory subcategory = subcategoryRepository.findById(dto.getSubcategoryId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Category not found with id: " + dto.getSubcategoryId()));
            product.setSubcategory(subcategory);
        }
        if (dto.getBrandId() != null) {
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + dto.getBrandId()));
            product.setBrand(brand);
        }
        if (dto.getColorsIds() != null && dto.getColorsIds().size() > 0) {
            product.setColors(new HashSet<>(findColorsByIds(dto.getColorsIds())));
        }

        if (dto.getSizesIds() != null && dto.getSizesIds().size() > 0) {
            product.setSizes(new HashSet<>(findSizesByIds(dto.getSizesIds())));
        }
    }

}
