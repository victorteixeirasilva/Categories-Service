package tech.inovasoft.inevolving.ms.categories.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepository;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepositoryJpa;

import java.util.List;
import java.util.UUID;

public class CategoryRepositoryImplementation implements CategoryRepository {

    @Autowired
    private CategoryRepositoryJpa categoryRepositoryJpa;

    @Override
    public Category saveCategory(Category newCategory) {
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    @Override
    public ResponseCategoryAndNewObjectiveDTO addObjectiveToCategory(UUID idUser, RequestAddObjectiveToCategoryDTO requestDTO) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    @Override
    public Category findCategoryByIdAndIdUser(UUID id, UUID idUser) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    @Override
    public ResponseObjectiveDTO findObjectiveByIdAndIdUser(UUID uuid, UUID idUser) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    @Override
    public ResponseMessageDTO removeObjectiveToCategory(UUID idObjective, UUID idCategory) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    @Override
    public ResponseMessageDTO removeCategory(Category category) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    @Override
    public List<Category> getCategories(UUID idUser) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return List.of();
    }

    @Override
    public List<UUID> getObjectivesByCategory(UUID idCategory, UUID idUser) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return List.of();
    }
}
