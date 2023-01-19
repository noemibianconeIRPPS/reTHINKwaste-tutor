package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Category;
import it.cnr.rethinkwaste.model.assessment.Question;
import it.cnr.rethinkwaste.repository.assessment.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category findAllByQuestionsContaining(Question question) {
        return categoryRepository.findAllByQuestionsContaining(question);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

}
