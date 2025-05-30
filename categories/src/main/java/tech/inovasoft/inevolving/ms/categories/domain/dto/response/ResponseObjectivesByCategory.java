package tech.inovasoft.inevolving.ms.categories.domain.dto.response;

import java.util.List;

public record ResponseObjectivesByCategory(
        ResponseCategoryDTO category,
        List<ResponseObjectiveDTO> objectives
) {
}
