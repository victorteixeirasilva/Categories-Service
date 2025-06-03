package tech.inovasoft.inevolving.ms.categories.service.client.dto;

import java.util.UUID;

public record RequestCreateObjectiveDTO(
        String nameObjective,
        String descriptionObjective,
        UUID idUser
) {
}
