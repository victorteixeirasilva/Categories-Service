package tech.inovasoft.inevolving.ms.categories.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestUpdateCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoriesDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectivesByCategory;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepository;

import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * @description - Creates a new goal category for the user. | Cria uma nova categoria de objetivos para o usuário.
     * @param idUser - User id | Id do usuário
     * @param dto - DTO (Data Transfer Object) with Category information | DTO (Objeto de transferência de dados) com Informações da categoria
     * @return - Category created | Categoria criada
     */
    public Category addCategory(
            UUID idUser,
            RequestCategoryDTO dto
    ) {
        var category = new Category(idUser, dto);
        return categoryRepository.saveCategory(category);
    }

    /**
     * @description - Adds an objective to a category | Adiciona um objetivo a uma categoria
     * @param idUser - User id | Id do usuário
     * @param dto - DTO (Data Transfer Object) with Objective information | DTO (Objeto de transferência de dados) com Informações do objetivo
     * @return - Category and new objective | Categoria e novo objetivo
     */
    public ResponseCategoryAndNewObjectiveDTO addObjectiveToCategory(
            UUID idUser,
            RequestAddObjectiveToCategoryDTO dto
    ) {
        var category = categoryRepository.findCategoryByIdAndIdUser(dto.idCategory(), idUser);

        var objective = categoryRepository.findObjectiveByIdAndIdUser(dto.idObjective(), idUser);

        return categoryRepository.addObjectiveToCategory(idUser, dto);
    }

    /**
     * @description - Removes an objective from a category | Remove um objetivo de uma categoria
     * @param idUser - User id | Id do usuário
     * @param idCategory - Category id | Id da categoria
     * @param idObjective - Objective id | Id do objetivo
     * @return - Success message | Retorna uma mensagem de sucesso
     */
    public ResponseMessageDTO removeObjectiveToCategory(
            UUID idUser,
            UUID idCategory,
            UUID idObjective
    ) {
        var category = categoryRepository.findCategoryByIdAndIdUser(idCategory, idUser);

        var objective = categoryRepository.findObjectiveByIdAndIdUser(idObjective, idUser);

        return categoryRepository.removeObjectiveToCategory(idObjective, idCategory);
    }

    /**
     * @description - Removes a category | Remove uma categoria
     * @param idUser - User id | Id do usuário
     * @param idCategory - Category id | Id da categoria
     * @return - Success message | Retorna uma mensagem de sucesso
     */
    public ResponseMessageDTO removeCategory(
            UUID idUser,
            UUID idCategory
    ) {
        var category = categoryRepository.findCategoryByIdAndIdUser(idCategory, idUser);

        return categoryRepository.removeCategory(category);
    }

    /**
     * @description - Updates a category | Atualiza uma categoria
     * @param idUser - User id | Id do usuário
     * @param idCategory - Category id | Id da categoria
     * @param dto - DTO (Data Transfer Object) with Category information | DTO (Objeto de transferência de dados) com Informações da categoria
     * @return - Category updated | Categoria atualizada
     */
    public Category updateCategory(
            UUID idUser,
            UUID idCategory,
            RequestUpdateCategoryDTO dto
    ) {
        var category = categoryRepository.findCategoryByIdAndIdUser(idCategory, idUser);

        category.setCategoryName(dto.categoryName());
        category.setCategoryDescription(dto.categoryDescription());

        return categoryRepository.saveCategory(category);
    }

    public ResponseCategoriesDTO getCategories(
            UUID idUser
    ) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }


    public ResponseObjectivesByCategory getObjectivesByCategory(
            UUID idUser,
            UUID idCategory
    ) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }
}
