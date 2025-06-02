package tech.inovasoft.inevolving.ms.categories.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryJpa extends JpaRepository<Category, UUID> {
    //TODO: Query JPQL
    Optional<Category> findByIdAndIdUser(UUID idCategory, UUID idUser);

    //TODO: Query JPQL
    List<Category> findAllByIdUser(UUID idUser);
}
