package tech.inovasoft.inevolving.ms.categories.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.ErrorInExternalServiceException;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepository;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepositoryJpa;
import tech.inovasoft.inevolving.ms.categories.service.client.ObjectiveServiceClient;

import java.util.List;
import java.util.UUID;

public class CategoryRepositoryImplementation implements CategoryRepository {

    @Autowired
    private CategoryRepositoryJpa categoryRepositoryJpa;

    @Autowired
    private ObjectiveServiceClient objectiveServiceClient;

    /**
     * @description - Save category in database | Salva a categoria no banco de dados.
     * @param newCategory - category to save | Categoria a ser salva.
     * @return - category saved | Categoria salva.
     * @throws DataBaseException - database error | Erro no banco de dados.
     */
    @Override
    public Category saveCategory(Category newCategory) throws DataBaseException {
        try {
            return categoryRepositoryJpa.save(newCategory);
        } catch (Exception e) {
            //TODO: Desenvolver teste
            throw new DataBaseException("save");
        }
    }

    /**
     * @description - Add objective to category | Adiciona um objetivo a uma categoria
     * @param idUser - user id | Id do usuário
     * @param requestDTO - request to add objective to category | Requisição para adicionar um objetivo a uma categoria
     * @return - response with category and new objective | Resposta com a categoria e o novo objetivo
     * @throws ErrorInExternalServiceException - error in external service | Erro no serviço externo
     * @throws DataBaseException - database error | Erro no banco de dados
     */
    @Override
    public ResponseCategoryAndNewObjectiveDTO addObjectiveToCategory(
            UUID idUser,
            RequestAddObjectiveToCategoryDTO requestDTO
    ) throws ErrorInExternalServiceException, DataBaseException {
        var category = categoryRepositoryJpa.findById(requestDTO.idCategory()); //TODO: Refatorar para o metodo findCategoryByIdAndIdUser
        ResponseObjectiveDTO objective;

        try {
            objective = objectiveServiceClient.getObjectiveById(requestDTO.idObjective());
        } catch (Exception e) {
            throw new ErrorInExternalServiceException("objectiveServiceClient.getObjectiveById");
        }

        category.get().getObjectives().add(objective.idObjective());

        saveCategory(category.get());

        return new ResponseCategoryAndNewObjectiveDTO(category.get(), objective);
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
