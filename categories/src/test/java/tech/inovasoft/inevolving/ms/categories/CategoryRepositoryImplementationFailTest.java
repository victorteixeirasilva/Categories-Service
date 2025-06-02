package tech.inovasoft.inevolving.ms.categories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.ErrorInExternalServiceException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundCategoryInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundObjectiveInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.implementation.CategoryRepositoryImplementation;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepositoryJpa;
import tech.inovasoft.inevolving.ms.categories.service.client.ObjectiveServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryRepositoryImplementationFailTest {

    @Mock
    private CategoryRepositoryJpa categoryRepositoryJpa;

    @Mock
    private ObjectiveServiceClient objectiveServiceClient;

    @InjectMocks
    private CategoryRepositoryImplementation categoryRepositoryImplementation;

    @Test
    public void saveCategoryDataBaseException() {
        // Given
        var category = new Category(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "categoryName",
                "categoryDescription",
                new ArrayList<>()
        );

        // When
        when(categoryRepositoryJpa.save(category)).thenThrow(new RuntimeException());
        var result = assertThrows(DataBaseException.class, () -> {
            categoryRepositoryImplementation.saveCategory(category);
        });

        // Then
        assertNotNull(result);
        assertEquals("Error in database: save", result.getMessage());

        verify(categoryRepositoryJpa).save(category);
    }

    @Test
    public void findCategoryByIdAndIdUserDataBaseException() {
        // Given
        var idCategory = UUID.randomUUID();
        var idUser = UUID.randomUUID();

        var category = new Category(idCategory, idUser, "CategoryName", "CategoryDescription", new ArrayList<>());

        // When
        when(categoryRepositoryJpa.findByIdAndIdUser(idCategory, idUser))
                .thenThrow(new RuntimeException());
        var result = assertThrows(DataBaseException.class, () -> {
            categoryRepositoryImplementation
                    .findCategoryByIdAndIdUser(idCategory, idUser);
        });

        // Then
        assertNotNull(result);
        assertEquals("Error in database: findByIdAndIdUser", result.getMessage());

        verify(categoryRepositoryJpa).findByIdAndIdUser(idCategory, idUser);
    }

    @Test
    public void findCategoryByIdAndIdUserNotFoundCategoryInDatabaseException() {
        // Given
        var idCategory = UUID.randomUUID();
        var idUser = UUID.randomUUID();

        var category = new Category(idCategory, idUser, "CategoryName", "CategoryDescription", new ArrayList<>());

        // When
        when(categoryRepositoryJpa.findByIdAndIdUser(idCategory, idUser))
                .thenReturn(Optional.empty());
        var result = assertThrows(NotFoundCategoryInDatabaseException.class, () -> {
            categoryRepositoryImplementation
                    .findCategoryByIdAndIdUser(idCategory, idUser);
        });

        // Then
        assertNotNull(result);
        assertEquals("Category not found in database", result.getMessage());

        verify(categoryRepositoryJpa).findByIdAndIdUser(idCategory, idUser);
    }

    @Test
    public void findObjectiveByIdAndIdUserErrorInExternalServiceException() {
        // Given
        var idObjective = UUID.randomUUID();
        var idUser = UUID.randomUUID();
        ResponseEntity<ResponseObjectiveDTO> response = ResponseEntity.ok(new ResponseObjectiveDTO(idObjective, "ObjectiveName", "ObjectiveDescription", "status", null, idUser));
        var expectedObjective = response.getBody();

        // When
        when(objectiveServiceClient.getObjectiveById(idObjective, idUser))
                .thenThrow(new RuntimeException());
        var result = assertThrows(ErrorInExternalServiceException.class, () -> {
            categoryRepositoryImplementation
                    .findObjectiveByIdAndIdUser(idObjective, idUser);
        });

        // Then
        assertNotNull(result);

        assert expectedObjective != null;
        assertEquals("Error in external service: objectiveServiceClient.getObjectiveById", result.getMessage());

        verify(objectiveServiceClient).getObjectiveById(idObjective, idUser);
    }

    @Test
    public void findObjectiveByIdAndIdUserNotFoundObjectiveInDatabaseException() {
        // Given
        var idObjective = UUID.randomUUID();
        var idUser = UUID.randomUUID();
        ResponseEntity<ResponseObjectiveDTO> response = ResponseEntity.ok(new ResponseObjectiveDTO(idObjective, "ObjectiveName", "ObjectiveDescription", "status", null, idUser));
        var expectedObjective = response.getBody();

        // When
        when(objectiveServiceClient.getObjectiveById(idObjective, idUser))
                .thenReturn(ResponseEntity.notFound().build());
        var result = assertThrows(NotFoundObjectiveInDatabaseException.class, () -> {
            categoryRepositoryImplementation
                    .findObjectiveByIdAndIdUser(idObjective, idUser);
        });

        // Then
        assertNotNull(result);

        assert expectedObjective != null;
        assertEquals("Objective not found in database", result.getMessage());

        verify(objectiveServiceClient).getObjectiveById(idObjective, idUser);
    }

    @Test
    public void removeCategoryDataBaseException() {
        // Given
        var category = new Category(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "CategoryName",
                "CategoryDescription",
                List.of(UUID.randomUUID())
        );

        // When
        doThrow(new RuntimeException()).when(categoryRepositoryJpa).delete(category);
        var result = assertThrows(DataBaseException.class, () -> {
            categoryRepositoryImplementation.removeCategory(category);
        });

        // Then
        assertNotNull(result);
        assertEquals("Error in database: delete", result.getMessage());

        verify(categoryRepositoryJpa).delete(category);
    }

    @Test
    public void getCategoriesDataBaseException() {
        // Given
        var idUser = UUID.randomUUID();

        // When
        when(categoryRepositoryJpa.findAllByIdUser(idUser))
                .thenThrow(new RuntimeException());
        var result = assertThrows(DataBaseException.class, () -> {
            categoryRepositoryImplementation.getCategories(idUser);
        });

        // Then
        assertNotNull(result);
        assertEquals("Error in database: findAllByIdUser", result.getMessage());

        verify(categoryRepositoryJpa).findAllByIdUser(idUser);
    }

    @Test
    public void getCategoriesNotFoundCategoryInDatabaseException() {
        // Given
        var idUser = UUID.randomUUID();
        List<Category> categories = new ArrayList<>();

        // When
        when(categoryRepositoryJpa.findAllByIdUser(idUser)).thenReturn(categories);
        var result = assertThrows(NotFoundCategoryInDatabaseException.class, () -> {
            categoryRepositoryImplementation.getCategories(idUser);
        });

        // Then
        assertNotNull(result);
        assertEquals("Category not found in database", result.getMessage());

        verify(categoryRepositoryJpa).findAllByIdUser(idUser);
    }

    @Test
    public void getObjectivesByCategoryNotFoundObjectiveInDatabaseException() throws NotFoundCategoryInDatabaseException, DataBaseException, NotFoundObjectiveInDatabaseException, ErrorInExternalServiceException {
        // Given
        var idCategory = UUID.randomUUID();
        var idUser = UUID.randomUUID();
        var expectedCategory = new Category(
                idCategory,
                idUser,
                "CategoryName",
                "CategoryDescription",
                List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
        );

        // When
        when(categoryRepositoryJpa.findByIdAndIdUser(idCategory, idUser)).thenReturn(Optional.of(expectedCategory));
        ResponseObjectiveDTO mockObjective = new ResponseObjectiveDTO(UUID.randomUUID(), "ObjectiveName", "ObjectiveDescription", "status", null, idUser);
        when(objectiveServiceClient.getObjectiveById(expectedCategory.getObjectives().get(0), idUser))
                .thenReturn(ResponseEntity.ok(mockObjective));
        when(objectiveServiceClient.getObjectiveById(expectedCategory.getObjectives().get(1), idUser))
                .thenReturn(ResponseEntity.notFound().build());
        when(objectiveServiceClient.getObjectiveById(expectedCategory.getObjectives().get(2), idUser))
                .thenReturn(ResponseEntity.ok(mockObjective));
        var result = categoryRepositoryImplementation
                .getObjectivesByCategory(idCategory, idUser);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(categoryRepositoryJpa).findByIdAndIdUser(idCategory, idUser);
    }

}
