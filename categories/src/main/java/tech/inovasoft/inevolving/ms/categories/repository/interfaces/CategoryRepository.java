package tech.inovasoft.inevolving.ms.categories.repository.interfaces;

import org.springframework.stereotype.Repository;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.ErrorInExternalServiceException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundCategoryInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository {

    Category saveCategory(Category newCategory) throws DataBaseException;

    ResponseCategoryAndNewObjectiveDTO addObjectiveToCategory(UUID idUser, RequestAddObjectiveToCategoryDTO requestDTO) throws ErrorInExternalServiceException, DataBaseException;

    Category findCategoryByIdAndIdUser(UUID id, UUID idUser) throws DataBaseException, NotFoundCategoryInDatabaseException;

    ResponseObjectiveDTO findObjectiveByIdAndIdUser(UUID uuid, UUID idUser);

    ResponseMessageDTO removeObjectiveToCategory(UUID idObjective, UUID idCategory);

    ResponseMessageDTO removeCategory(Category category);

    List<Category> getCategories(UUID idUser);

    List<UUID> getObjectivesByCategory(UUID idCategory, UUID idUser);

}
