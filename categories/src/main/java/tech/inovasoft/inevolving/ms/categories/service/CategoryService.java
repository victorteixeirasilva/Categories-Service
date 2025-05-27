package tech.inovasoft.inevolving.ms.categories.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestUpdateCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoriesDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
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
    public Category addCategory(UUID idUser, RequestCategoryDTO dto) {
        var category = new Category(idUser, dto);
        return categoryRepository.saveCategory(category);
    }

    public ResponseCategoryAndNewObjectiveDTO addObjectiveToCategory(UUID idUser, RequestAddObjectiveToCategoryDTO dto) {
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    public ResponseMessageDTO removeObjectiveToCategory(UUID idUser, UUID idCategory, UUID idObjective) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    public ResponseMessageDTO removeCategory(UUID idUser, UUID idCategory) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    public Category updateCategory(UUID idUser, UUID idCategory, RequestUpdateCategoryDTO dto) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    public ResponseCategoriesDTO getCategories(UUID idUser) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }


    public Category getObjectivesByCategory(UUID idUser, UUID idCategory) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }
}
