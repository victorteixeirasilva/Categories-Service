package tech.inovasoft.inevolving.ms.categories.domain.dto.response;

import java.sql.Date;
import java.util.UUID;

public record ResponseObjectiveDTO(
        UUID id,
        String nameObjective,
        String descriptionObjective,
        String statusObjective,
        Date completionDate,
        UUID idUser
) {
}
