package tech.inovasoft.inevolving.ms.categories.domain.dto.request;

import java.util.UUID;

public record RequestAddObjectiveToCategoryDTO(
        UUID idCategory,
        UUID idObjective
) {
}
