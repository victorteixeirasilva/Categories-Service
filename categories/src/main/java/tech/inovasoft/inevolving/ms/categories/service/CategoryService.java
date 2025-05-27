package tech.inovasoft.inevolving.ms.categories.service;

import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

import java.util.UUID;

@Service
public class CategoryService {

    public Category addCategory(UUID idUser) {
        //TODO: RED
        //TODO: GREEN
        //TODO: BLUE
        return null;
    }

    public ResponseCategoryAndNewObjectiveDTO addObjectiveToCategory(UUID idUser, RequestAddObjectiveToCategoryDTO dto) {
        //TODO: RED
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
}
