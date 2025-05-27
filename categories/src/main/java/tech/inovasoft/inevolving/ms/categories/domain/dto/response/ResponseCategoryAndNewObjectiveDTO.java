package tech.inovasoft.inevolving.ms.categories.domain.dto.response;

import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

import java.util.UUID;

public record ResponseCategoryAndNewObjectiveDTO(
        String message,
        UUID idCategory,
        UUID idUser,
        String categoryName,
        String categoryDescription,
        ResponseObjectiveDTO objective
) {
    public ResponseCategoryAndNewObjectiveDTO(Category category, ResponseObjectiveDTO objective) {
        this(
             "Objective add successfully",
             category.getId(),
             category.getIdUser(),
             category.getCategoryName(),
             category.getCategoryDescription(),
             objective
        );

    }
}
