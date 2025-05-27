package tech.inovasoft.inevolving.ms.categories.domain.dto.response;

import java.util.List;
import java.util.UUID;

public record ResponseCategoriesDTO(
        UUID idUser,
        List<ResponseCategoryDTO> categories
) {
}
