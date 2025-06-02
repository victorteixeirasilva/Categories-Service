package tech.inovasoft.inevolving.ms.categories;

import jakarta.persistence.ElementCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.ErrorInExternalServiceException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundCategoryInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundObjectiveInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.implementation.CategoryRepositoryImplementation;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepository;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepositoryJpa;
import tech.inovasoft.inevolving.ms.categories.service.client.ObjectiveServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryRepositorySuccessTest {

    @Mock
    private CategoryRepositoryJpa categoryRepositoryJpa;

    @Mock
    private ObjectiveServiceClient objectiveServiceClient;

    @InjectMocks
    private CategoryRepositoryImplementation categoryRepositoryImplementation;

   @Test
    public void saveCategory() throws DataBaseException {
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

    @Test
    public void addObjectiveToCategory() throws ErrorInExternalServiceException, DataBaseException, NotFoundObjectiveInDatabaseException {
        // Given
        var idUser = UUID.randomUUID();
        var requestDTO = new RequestAddObjectiveToCategoryDTO(UUID.randomUUID(), UUID.randomUUID());
        var objective = new ResponseObjectiveDTO(
                requestDTO.idObjective(),
                "ObjectiveName",
                "ObjectiveDescription",
                "status",
                null,
                idUser
        );
        var expectedDTO = new ResponseCategoryAndNewObjectiveDTO(
                "Objective add successfully",
                requestDTO.idCategory(),
                idUser,
                "CategoryName",
                "CategoryDescription",
                objective

        );
        var oldCategory = new Category(
                requestDTO.idCategory(),
                idUser,
                "CategoryName",
                "CategoryDescription",
                new ArrayList<>()
        );
        var newCategory = new Category(
                requestDTO.idCategory(),
                idUser,
                "CategoryName",
                "CategoryDescription",
                List.of(requestDTO.idObjective())
        );

        // When
        when(categoryRepositoryJpa.findById(requestDTO.idCategory())).thenReturn(Optional.of(oldCategory));
        when(objectiveServiceClient.getObjectiveById(requestDTO.idObjective(), idUser)).thenReturn(ResponseEntity.ok(objective));
        when(categoryRepositoryJpa.save(newCategory)).thenReturn(newCategory);
        var result = categoryRepositoryImplementation.addObjectiveToCategory(idUser, requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(expectedDTO.message(), result.message());
        assertEquals(expectedDTO.idUser(), result.idUser());
        assertEquals(expectedDTO.categoryName(), result.categoryName());
        assertEquals(expectedDTO.categoryDescription(), result.categoryDescription());
        assertEquals(expectedDTO.idCategory(), result.idCategory());
        assertEquals(expectedDTO.objective().idObjective(), result.objective().idObjective());
        assertEquals(expectedDTO.objective().idUser(), result.objective().idUser());
        assertEquals(expectedDTO.objective().nameObjective(), result.objective().nameObjective());
        assertEquals(expectedDTO.objective().descriptionObjective(), result.objective().descriptionObjective());

        verify(categoryRepositoryJpa).findById(requestDTO.idCategory());
        verify(objectiveServiceClient).getObjectiveById(requestDTO.idObjective(), idUser);
        verify(categoryRepositoryJpa).save(newCategory);
    }

    @Test
    public void findCategoryByIdAndIdUser() throws NotFoundCategoryInDatabaseException, DataBaseException {
        // Given
        var idCategory = UUID.randomUUID();
        var idUser = UUID.randomUUID();

        var category = new Category(idCategory, idUser, "CategoryName", "CategoryDescription", new ArrayList<>());

        // When
        when(categoryRepositoryJpa.findByIdAndIdUser(idCategory, idUser)).thenReturn(Optional.of(category));
        var result = categoryRepositoryImplementation.findCategoryByIdAndIdUser(idCategory, idUser);

        // Then
        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getIdUser(), result.getIdUser());
        assertEquals(category.getCategoryName(), result.getCategoryName());

        verify(categoryRepositoryJpa).findByIdAndIdUser(idCategory, idUser);
    }

    @Test
    public void findObjectiveByIdAndIdUser() throws NotFoundObjectiveInDatabaseException, ErrorInExternalServiceException {
        // Given
        var idObjective = UUID.randomUUID();
        var idUser = UUID.randomUUID();
        ResponseEntity<ResponseObjectiveDTO> response = ResponseEntity.ok(new ResponseObjectiveDTO(idObjective, "ObjectiveName", "ObjectiveDescription", "status", null, idUser));
        var expectedObjective = response.getBody();

        // When
        when(objectiveServiceClient.getObjectiveById(idObjective, idUser)).thenReturn(response);
        var result = categoryRepositoryImplementation.findObjectiveByIdAndIdUser(idObjective, idUser);

        // Then
        assertNotNull(result);

        assert expectedObjective != null;
        assertEquals(expectedObjective.idObjective(), result.idObjective());
        assertEquals(expectedObjective.idUser(), result.idUser());
        assertEquals(expectedObjective.nameObjective(), result.nameObjective());
        assertEquals(expectedObjective.descriptionObjective(), result.descriptionObjective());

        verify(objectiveServiceClient).getObjectiveById(idObjective, idUser);
    }

    @Test
    public void removeObjectiveToCategory() throws NotFoundObjectiveInDatabaseException, ErrorInExternalServiceException, NotFoundCategoryInDatabaseException, DataBaseException {
       // Given
        var idObjective = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idUser = UUID.randomUUID();
        var expectedCategory = new Category(
                idCategory,
                idUser,
                "CategoryName",
                "CategoryDescription",
                new ArrayList<>()
        );
        var oldCategory = new Category(
                idCategory,
                idUser,
                "CategoryName",
                "CategoryDescription",
                List.of(idObjective)
        );

       // When
        when(categoryRepositoryJpa.findByIdAndIdUser(idCategory, idUser)).thenReturn(Optional.of(oldCategory));
        when(categoryRepositoryJpa.save(expectedCategory)).thenReturn(expectedCategory);
        var result = categoryRepositoryImplementation
                .removeObjectiveToCategory(idObjective, idCategory, idUser);

        // Then
        assertNotNull(result);
        assertEquals("Objective removed from category successfully", result.message());

        verify(categoryRepositoryJpa).findByIdAndIdUser(idCategory, idUser);
        verify(categoryRepositoryJpa).save(expectedCategory);
    }

    @Test
    public void removeCategory() throws DataBaseException {
        // Given
        var category = new Category(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "CategoryName",
                "CategoryDescription",
                List.of(UUID.randomUUID())
        );

        // When
        doNothing().when(categoryRepositoryJpa).delete(category);
        var result = categoryRepositoryImplementation.removeCategory(category);

        // Then
        assertNotNull(result);
        assertEquals("Category removed successfully", result.message());

        verify(categoryRepositoryJpa).delete(category);
    }

    @Test
    public void getCategories() throws NotFoundCategoryInDatabaseException, DataBaseException {
        // Given
        var idUser = UUID.randomUUID();
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(
                UUID.randomUUID(),
                idUser,
                "CategoryName",
                "CategoryDescription",
                List.of(UUID.randomUUID())
        ));
        categories.add(new Category(
                UUID.randomUUID(),
                idUser,
                "CategoryName",
                "CategoryDescription",
                List.of(UUID.randomUUID())
        ));

        // When
        when(categoryRepositoryJpa.findAllByIdUser(idUser)).thenReturn(categories);
        var result = categoryRepositoryImplementation.getCategories(idUser);

        // Then
        assertNotNull(result);
        assertEquals(categories.size(), result.size());

        verify(categoryRepositoryJpa).findAllByIdUser(idUser);
    }

}
