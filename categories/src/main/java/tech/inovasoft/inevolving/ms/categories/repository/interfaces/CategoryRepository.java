package tech.inovasoft.inevolving.ms.categories.repository.interfaces;

import org.springframework.stereotype.Repository;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

import java.util.UUID;

@Repository
public interface CategoryRepository {

    Category saveCategory(Category newCategory);

    ResponseCategoryAndNewObjectiveDTO addObjectiveToCategory(UUID idUser, RequestAddObjectiveToCategoryDTO requestDTO);

    Category findCategoryByIdAndIdUser(UUID id, UUID idUser);

    ResponseObjectiveDTO findObjectiveByIdAndIdUser(UUID uuid, UUID idUser);
}
