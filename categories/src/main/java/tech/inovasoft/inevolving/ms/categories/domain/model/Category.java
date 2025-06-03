package tech.inovasoft.inevolving.ms.categories.domain.model;

import jakarta.persistence.*;
import lombok.*;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestCategoryDTO;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID idUser;
    private String categoryName;
    private String categoryDescription;
    @ElementCollection(fetch = FetchType.EAGER)
    List<UUID> objectives;

    public Category(UUID idUser, RequestCategoryDTO requestDTO) {
        this.idUser = idUser;
        this.categoryName = requestDTO.categoryName();
        this.categoryDescription = requestDTO.categoryDescription();
    }
}
