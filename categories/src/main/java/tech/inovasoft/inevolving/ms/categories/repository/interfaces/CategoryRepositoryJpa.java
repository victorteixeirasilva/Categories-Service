package tech.inovasoft.inevolving.ms.categories.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;

import java.util.UUID;

public interface CategoryRepositoryJpa extends JpaRepository<Category, UUID> {
}
