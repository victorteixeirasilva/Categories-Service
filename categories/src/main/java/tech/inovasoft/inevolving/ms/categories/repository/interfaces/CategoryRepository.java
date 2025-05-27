package tech.inovasoft.inevolving.ms.categories.repository.interfaces;

import org.springframework.stereotype.Repository;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

@Repository
public interface CategoryRepository {

    Category saveCategory(Category newCategory);

}
