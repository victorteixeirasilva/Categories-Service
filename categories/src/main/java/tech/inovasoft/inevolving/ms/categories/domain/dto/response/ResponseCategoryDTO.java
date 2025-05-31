package tech.inovasoft.inevolving.ms.categories.domain.dto.response;

import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

import java.util.UUID;

public record ResponseCategoryDTO(
        UUID id,
        String categoryName,
        String categoryDescription
) {
    public ResponseCategoryDTO(Category category) {
        this(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryDescription()
        );
    }
}
