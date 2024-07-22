package es.diplock.examples.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.CategoryDTO;
import es.diplock.examples.entities.Category;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.mappers.CategoryMapper;
import es.diplock.examples.repositories.CategoryRepository;
import es.diplock.examples.service.BaseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements BaseService<CategoryDTO, Integer> {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO findById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            return CategoryMapper.toDto(optionalCategory.get());
        }

        throw new ResourceNotFoundException("Category not found");
    }

    @Override
    public List<CategoryDTO> findAll() {
        return null;
    }

    @Override
    public CategoryDTO save(CategoryDTO entity) {
        return null;
    }

    @Override
    public CategoryDTO update(Integer id, CategoryDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }

}
