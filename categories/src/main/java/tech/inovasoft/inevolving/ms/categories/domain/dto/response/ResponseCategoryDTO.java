package tech.inovasoft.inevolving.ms.categories.domain.dto.response;

import java.util.UUID;

public record ResponseCategoryDTO(
        UUID id,
        String categoryName,
        String categoryDescription
) {
}
