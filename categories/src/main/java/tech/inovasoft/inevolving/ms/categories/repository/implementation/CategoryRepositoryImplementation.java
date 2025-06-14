package tech.inovasoft.inevolving.ms.categories.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.ErrorInExternalServiceException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundCategoryInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundObjectiveInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepository;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepositoryJpa;
import tech.inovasoft.inevolving.ms.categories.service.client.ObjectiveServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
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
    public Category saveCategory(
            Category newCategory
    ) throws
            DataBaseException
    {
        try {
            return categoryRepositoryJpa.save(newCategory);
        } catch (Exception e) {
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
    ) throws
            ErrorInExternalServiceException,
            DataBaseException,
            NotFoundObjectiveInDatabaseException,
            NotFoundCategoryInDatabaseException
    {
        var category = findCategoryByIdAndIdUser(requestDTO.idCategory(), idUser);

        ResponseObjectiveDTO objective = findObjectiveByIdAndIdUser(requestDTO.idObjective(), idUser);

        List<UUID> newObjectives = new ArrayList<>(category.getObjectives());

        newObjectives.add(objective.id());
        category.setObjectives(newObjectives);

        saveCategory(category);

        return new ResponseCategoryAndNewObjectiveDTO(category, objective);
    }

    /**
     * @description - Find category by id | Encontra uma categoria pelo id
     * @param id - category id | Id da categoria
     * @param idUser - user id | Id do usuário
     * @return - category | Categoria
     * @throws DataBaseException - database error | Erro no banco de dados
     * @throws NotFoundCategoryInDatabaseException - category not found in database | Categoria não encontrada no banco de dados
     */
    @Override
    public Category findCategoryByIdAndIdUser(
            UUID id,
            UUID idUser
    ) throws
            DataBaseException,
            NotFoundCategoryInDatabaseException
    {
        Optional<Category> category;
        try {
            category = categoryRepositoryJpa.findByIdAndIdUser(id, idUser);
        } catch (Exception e) {
            throw new DataBaseException("findByIdAndIdUser");
        }

        if (category.isEmpty()) {
            throw new NotFoundCategoryInDatabaseException();
        }

        return category.get();
    }

    /**
     * @description - Find objective by id | Encontra um objetivo pelo id
     * @param uuid - objective id | Id do objetivo
     * @param idUser - user id | Id do usuário
     * @return - objective | objetivo
     * @throws ErrorInExternalServiceException - error in external service | Erro no serviço externo
     * @throws NotFoundObjectiveInDatabaseException - objective not found in database | Objetivo não encontrado no banco de dados
     */
    @Override
    public ResponseObjectiveDTO findObjectiveByIdAndIdUser(
            UUID uuid,
            UUID idUser
    ) throws
            ErrorInExternalServiceException,
            NotFoundObjectiveInDatabaseException
    {
        ResponseEntity<ResponseObjectiveDTO> entity;
        try {
            entity = objectiveServiceClient.getObjectiveById(uuid, idUser);
        } catch (Exception e) {
            throw new ErrorInExternalServiceException("objectiveServiceClient.getObjectiveById");
        }

        if (entity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new NotFoundObjectiveInDatabaseException();
        }

        return new ResponseObjectiveDTO(
                    entity.getBody().id(),
                    entity.getBody().nameObjective(),
                    entity.getBody().descriptionObjective(),
                    entity.getBody().statusObjective(),
                    entity.getBody().completionDate(),
                    entity.getBody().idUser()
                );
    }

    /**
     * @description - Remove objective from category | Remove um objetivo de uma categoria
     * @param idObjective - objective id | Id do objetivo
     * @param idCategory - category id | Id da categoria
     * @param idUser - user id | Id do usuário
     * @return - response with message | Resposta com a mensagem
     * @throws NotFoundCategoryInDatabaseException - category not found in database | Categoria não encontrada no banco de dados
     * @throws DataBaseException - database error | Erro no banco de dados
     */
    @Override
    public ResponseMessageDTO removeObjectiveToCategory(
            UUID idObjective,
            UUID idCategory,
            UUID idUser
    ) throws
            NotFoundCategoryInDatabaseException,
            DataBaseException
    {
        var category = findCategoryByIdAndIdUser(idCategory, idUser);
        List<UUID> objectives = new ArrayList<>(category.getObjectives());
        objectives.remove(idObjective);
        category.setObjectives(objectives);
        saveCategory(category);

        return new ResponseMessageDTO("Objective removed from category successfully");
    }

    /**
     * @description - Remove category | Remove uma categoria
     * @param category - category to remove | Categoria a ser removida
     * @return - response with message | Resposta com a mensagem
     * @throws DataBaseException - database error | Erro no banco de dados
     */
    @Override
    public ResponseMessageDTO removeCategory(
            Category category
    ) throws
            DataBaseException
    {
        try {
            categoryRepositoryJpa.delete(category);
            return new ResponseMessageDTO("Category removed successfully");
        } catch (Exception e) {
            throw new DataBaseException("delete");
        }
    }

    /**
     * @description - Get categories by user id | Obtém as categorias pelo id do usuário
     * @param idUser - user id | Id do usuário
     * @return - categories | Categorias
     * @throws NotFoundCategoryInDatabaseException - category not found in database | Categoria não encontrada no banco de dados
     * @throws DataBaseException - database error | Erro no banco de dados
     */
    @Override
    public List<Category> getCategories(
            UUID idUser
    ) throws
            NotFoundCategoryInDatabaseException,
            DataBaseException
    {
        List<Category> categories;
        try {
            categories = categoryRepositoryJpa.findAllByIdUser(idUser);
        } catch (Exception e) {
            throw new DataBaseException("findAllByIdUser");
        }
        if (categories.isEmpty()) {
            throw new NotFoundCategoryInDatabaseException();
        }
        return categories;
    }

    /**
     * @description - Get objectives by category | Obtém os objetivos pela categoria
     * @param idCategory - category id | Id da categoria
     * @param idUser - user id | Id do usuário
     * @return - objectives | Objetivos
     * @throws NotFoundCategoryInDatabaseException - category not found in database | Categoria não encontrada no banco de dados
     * @throws DataBaseException - database error | Erro no banco de dados
     * @throws ErrorInExternalServiceException - error in external service | Erro no serviço externo
     */
    @Override
    public List<UUID> getObjectivesByCategory(
            UUID idCategory,
            UUID idUser
    ) throws
            NotFoundCategoryInDatabaseException,
            DataBaseException,
            ErrorInExternalServiceException
    {
        var category = findCategoryByIdAndIdUser(idCategory, idUser);
        List<UUID> objectives = new ArrayList<>(category.getObjectives());

        for (UUID id : category.getObjectives()) {
            try {
                var objective = findObjectiveByIdAndIdUser(id, idUser);
            } catch (NotFoundObjectiveInDatabaseException e) {
                objectives.remove(id);
            }
        }

        category.setObjectives(objectives);
        saveCategory(category);

        return objectives;
    }
}
