package tech.inovasoft.inevolving.ms.categories;

import jakarta.persistence.ElementCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.implementation.CategoryRepositoryImplementation;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepository;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepositoryJpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryRepositorySuccessTest {

    @Mock
    private CategoryRepositoryJpa categoryRepositoryJpa;

    @InjectMocks
    private CategoryRepositoryImplementation categoryRepositoryImplementation;

    @Test
    public void saveCategory() {
        // Given
        var category = new Category(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "categoryName",
                "categoryDescription",
                new ArrayList<>()
        );

        // When
        when(categoryRepositoryJpa.save(category)).thenReturn(category);
        var result = categoryRepositoryImplementation.saveCategory(category);

        // Then
        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getIdUser(), result.getIdUser());
        assertEquals(category.getCategoryName(), result.getCategoryName());

        verify(categoryRepositoryJpa).save(category);
    }

}
