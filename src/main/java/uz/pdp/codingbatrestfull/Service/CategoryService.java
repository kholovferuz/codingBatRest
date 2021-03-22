package uz.pdp.codingbatrestfull.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatrestfull.DTO.CategoryDTO;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.Entity.Category;
import uz.pdp.codingbatrestfull.Entity.Language;
import uz.pdp.codingbatrestfull.Repository.CategoryRepository;
import uz.pdp.codingbatrestfull.Repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;

    // READ all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // READ category by id
    public Category getOneCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    // CREATE new category
    public Result addCategory(CategoryDTO categoryDTO) {
        boolean exists = categoryRepository.existsByName(categoryDTO.getName());
        if (exists){
            return new Result("This category already exists",false);
        }

        Category newCategory = new Category();
        newCategory.setName(categoryDTO.getName());
        newCategory.setDescription(categoryDTO.getDescription());

        // call language
        List<Language> languages = languageRepository.findAllById(categoryDTO.getLanguagesId());
        for (Language language : languages) {
            if (language.getId()==null){
                return new Result("Language with the id "+language.getId()+"is not found",false);
            }
        }
        newCategory.setLanguage(languages);

        categoryRepository.save(newCategory);
        return new Result("Category added", true);

    }

    // UPDATE category by id
    public Result updateCategory(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            boolean exists = categoryRepository.existsByName(categoryDTO.getName());
            if (exists){
                return new Result("This category already exists",false);
            }

            Category editedCategory = optionalCategory.get();
            editedCategory.setName(categoryDTO.getName());
            editedCategory.setDescription(categoryDTO.getDescription());

            // call language
            List<Language> languages = languageRepository.findAllById(categoryDTO.getLanguagesId());
            for (Language language : languages) {
                if (language.getId()==null){
                    return new Result("Language with the id "+language.getId()+"is not found",false);
                }
            }
            editedCategory.setLanguage(languages);

            categoryRepository.save(editedCategory);
            return new Result("Category updated", true);

        }
        return new Result("Category with this id is not found", false);
    }

    // DELETE category by id
    public Result deleteCategoryById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return new Result("Category deleted", true);
        }
        return new Result("Category with this id is not found", false);
    }
}
