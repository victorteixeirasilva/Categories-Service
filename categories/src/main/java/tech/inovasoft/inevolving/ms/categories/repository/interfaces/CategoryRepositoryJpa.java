package tech.inovasoft.inevolving.ms.categories.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryJpa extends JpaRepository<Category, UUID> {

    Optional<Category> findByIdAndIdUser(UUID idCategory, UUID idUser);

    List<Category> findAllByIdUser(UUID idUser);

}
