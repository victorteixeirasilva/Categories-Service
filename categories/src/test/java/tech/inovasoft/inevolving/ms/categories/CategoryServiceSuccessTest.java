package tech.inovasoft.inevolving.ms.categories;

import jakarta.persistence.ElementCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestUpdateCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.*;
import tech.inovasoft.inevolving.ms.categories.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.ErrorInExternalServiceException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundCategoryInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundObjectiveInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepository;
import tech.inovasoft.inevolving.ms.categories.service.CategoryService;
import tech.inovasoft.inevolving.ms.categories.service.client.ObjectiveServiceClient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceSuccessTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ObjectiveServiceClient objectiveServiceClient;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void addCategory() throws DataBaseException {
        // Given
        var idUser = UUID.randomUUID();
        var requestDTO = new RequestCategoryDTO(
                "categoryName",
                "categoryDescription"
        );

        List<UUID> objectives = new ArrayList<>();

        var newCategory = new Category(
                idUser,
                requestDTO
        );

        var expectedCategory = new Category(
                UUID.randomUUID(),
                idUser,
                requestDTO.categoryName(),
                requestDTO.categoryDescription(),
                objectives
        );

        // When
        when(categoryRepository.saveCategory(newCategory)).thenReturn(expectedCategory);
        var result = categoryService.addCategory(idUser, requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(expectedCategory.getId(), result.getId());
        assertEquals(expectedCategory.getIdUser(), result.getIdUser());
        assertEquals(expectedCategory.getCategoryName(), result.getCategoryName());
        assertEquals(expectedCategory.getCategoryDescription(), result.getCategoryDescription());
        assertEquals(expectedCategory.getObjectives(), result.getObjectives());
        assertEquals(expectedCategory.getObjectives().size(), result.getObjectives().size());

        verify(categoryRepository).saveCategory(newCategory);
    }

    @Test
    public void addObjectiveToCategory() throws ErrorInExternalServiceException, DataBaseException, NotFoundCategoryInDatabaseException, NotFoundObjectiveInDatabaseException {
        // Given
        var idUser = UUID.randomUUID();
        var requestDTO = new RequestAddObjectiveToCategoryDTO(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        Category category = new Category(
                requestDTO.idCategory(),
                idUser,
                "categoryName",
                "categoryDescription",
                new ArrayList<>()
        );

        ResponseObjectiveDTO objective = new ResponseObjectiveDTO(
                requestDTO.idObjective(),
                "nameObjective",
                "descriptionObjective",
                "String statusObjective",
                Date.valueOf("2022-01-01"),
                idUser
        );

        ResponseCategoryAndNewObjectiveDTO expectedResponse = new ResponseCategoryAndNewObjectiveDTO(
                category,
                objective
        );

        // When
        when(categoryRepository.findCategoryByIdAndIdUser(requestDTO.idCategory(), idUser)).thenReturn(category);
        when(categoryRepository.findObjectiveByIdAndIdUser(requestDTO.idObjective(), idUser)).thenReturn(objective);
        when(categoryRepository.addObjectiveToCategory(idUser, requestDTO)).thenReturn(expectedResponse);
        var result = categoryService.addObjectiveToCategory(idUser, requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(expectedResponse.idCategory(), result.idCategory());
        assertEquals(expectedResponse.idUser(), result.idUser());
        assertEquals(expectedResponse.categoryName(), result.categoryName());
        assertEquals(expectedResponse.categoryDescription(), result.categoryDescription());
        assertEquals(expectedResponse.objective().idObjective(), result.objective().idObjective());
        assertEquals(expectedResponse.objective().nameObjective(), result.objective().nameObjective());
        assertEquals(expectedResponse.objective().descriptionObjective(), result.objective().descriptionObjective());
        assertEquals(expectedResponse.objective().idUser(), result.objective().idUser());

        verify(categoryRepository).findCategoryByIdAndIdUser(requestDTO.idCategory(), idUser);
        verify(categoryRepository).findObjectiveByIdAndIdUser(requestDTO.idObjective(), idUser);
        verify(categoryRepository).addObjectiveToCategory(idUser, requestDTO);
    }

    @Test
    public void removeObjectiveToCategory() throws NotFoundCategoryInDatabaseException, DataBaseException, ErrorInExternalServiceException, NotFoundObjectiveInDatabaseException {
        // Given
        var idUser = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var idObjective = UUID.randomUUID();

        var category = new Category();
        category.setId(idCategory);
        var objective = new ResponseObjectiveDTO(idObjective, "nameObjective", "descriptionObjective", "String statusObjective", Date.valueOf("2022-01-01"), idUser);

        // When
        when(categoryRepository.findCategoryByIdAndIdUser(idCategory, idUser)).thenReturn(category);
        when(categoryRepository.findObjectiveByIdAndIdUser(idObjective, idUser)).thenReturn(objective);
        when(categoryRepository.removeObjectiveToCategory(idObjective, idCategory, idUser)).thenReturn(new ResponseMessageDTO("Objective removed successfully"));
        var result = categoryService.removeObjectiveToCategory(idUser, idCategory, idObjective);

        // Then
        assertNotNull(result);
        assertEquals("Objective removed successfully", result.message());

        verify(categoryRepository).findCategoryByIdAndIdUser(idCategory, idUser);
        verify(categoryRepository).findObjectiveByIdAndIdUser(idObjective, idUser);
        verify(categoryRepository).removeObjectiveToCategory(idObjective, idCategory, idUser);
    }

    @Test
    public void removeCategory() throws NotFoundCategoryInDatabaseException, DataBaseException {
        // Given
        var idUser = UUID.randomUUID();
        var idCategory = UUID.randomUUID();

        var category = new Category();
        category.setId(idCategory);

        // When
        when(categoryRepository.findCategoryByIdAndIdUser(idCategory, idUser)).thenReturn(category);
        when(categoryRepository.removeCategory(category)).thenReturn(new ResponseMessageDTO("Category removed successfully"));
        var result = categoryService.removeCategory(idUser, idCategory);

        // Then
        assertNotNull(result);
        assertEquals("Category removed successfully", result.message());

        verify(categoryRepository).findCategoryByIdAndIdUser(idCategory, idUser);
        verify(categoryRepository).removeCategory(category);
    }

    @Test
    public void updateCategory() throws DataBaseException, NotFoundCategoryInDatabaseException {
        // Given
        var idUser = UUID.randomUUID();
        var idCategory = UUID.randomUUID();
        var requestDTO = new RequestUpdateCategoryDTO(
                "Category Name",
                "Category Description"
        );
        List<UUID> objectives = new ArrayList<>();
        var oldCategory = new Category(
                idCategory,
                idUser,
                "categoryName",
                "categoryDescription",
                objectives
        );

        var newCategory = new Category(
                idCategory,
                idUser,
                requestDTO.categoryName(),
                requestDTO.categoryDescription(),
                objectives
        );

        // When
        when(categoryRepository.findCategoryByIdAndIdUser(idCategory, idUser)).thenReturn(oldCategory);
        when(categoryRepository.saveCategory(newCategory)).thenReturn(newCategory);
        var result = categoryService.updateCategory(idUser, idCategory, requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(idCategory, result.getId());
        assertEquals(idUser, result.getIdUser());
        assertEquals(requestDTO.categoryName(), result.getCategoryName());
        assertEquals(requestDTO.categoryDescription(), result.getCategoryDescription());

        verify(categoryRepository).findCategoryByIdAndIdUser(idCategory, idUser);
        verify(categoryRepository).saveCategory(newCategory);
    }

    @Test
    public void getCategories() {
        // Given
        var idUser = UUID.randomUUID();
        List<ResponseCategoryDTO> categories = new ArrayList<>();
        categories.add(new ResponseCategoryDTO(
                UUID.randomUUID(),
                "categoryName1",
                "categoryDescription1"
        ));
        categories.add(new ResponseCategoryDTO(
                UUID.randomUUID(),
                "categoryName1",
                "categoryDescription1"
        ));
        var responseCategoriesDTO = new ResponseCategoriesDTO(idUser, categories);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(
                new Category(
                        categories.get(0).id(),
                        idUser,
                        categories.get(0).categoryName(),
                        categories.get(0).categoryDescription(),
                        new ArrayList<>()

                )
        );
        categoryList.add(
                new Category(
                        categories.get(1).id(),
                        idUser,
                        categories.get(1).categoryName(),
                        categories.get(1).categoryDescription(),
                        new ArrayList<>()

                )
        );

        // When
        when(categoryRepository.getCategories(idUser)).thenReturn(categoryList);
        var result = categoryService.getCategories(idUser);

        // Then
        assertNotNull(result);
        assertEquals(responseCategoriesDTO.idUser(), result.idUser());
        assertEquals(responseCategoriesDTO.categories().get(0).id(), result.categories().get(0).id());
        assertEquals(responseCategoriesDTO.categories().get(0).categoryName(), result.categories().get(0).categoryName());
        assertEquals(responseCategoriesDTO.categories().get(0).categoryDescription(), result.categories().get(0).categoryDescription());
        assertEquals(responseCategoriesDTO.categories().get(1).id(), result.categories().get(1).id());
        assertEquals(responseCategoriesDTO.categories().get(1).categoryName(), result.categories().get(1).categoryName());
        assertEquals(responseCategoriesDTO.categories().get(1).categoryDescription(), result.categories().get(1).categoryDescription());
        assertEquals(responseCategoriesDTO.categories().size(), result.categories().size());

        verify(categoryRepository).getCategories(idUser);

    }

    @Test
    public void getObjectivesByCategory() throws ErrorInExternalServiceException, DataBaseException, NotFoundCategoryInDatabaseException {
        // Given
        var idUser = UUID.randomUUID();
        var idCategory = UUID.randomUUID();

        List<UUID> objectivesUUIDs = new ArrayList<>();
        objectivesUUIDs.add(UUID.randomUUID());
        objectivesUUIDs.add(UUID.randomUUID());

        List<ResponseObjectiveDTO> objectives = new ArrayList<>();
        objectives.add(new ResponseObjectiveDTO(
                objectivesUUIDs.get(0),
                "nameObjective1",
                "descriptionObjective1",
                "String statusObjective1",
                Date.valueOf("2022-01-01"),
                idUser
        ));
        objectives.add(new ResponseObjectiveDTO(
                objectivesUUIDs.get(1),
                "nameObjective2",
                "descriptionObjective2",
                "String statusObjective2",
                Date.valueOf("2022-01-01"),
                idUser
        ));

        var category = new Category(
                idCategory,
                idUser,
                "categoryName",
                "categoryDescription",
                objectivesUUIDs
        );
        ResponseObjectivesByCategory responseObjectivesByCategory = new ResponseObjectivesByCategory(
                new ResponseCategoryDTO(category),
                objectives
        );

        // When
        when(categoryRepository.findCategoryByIdAndIdUser(idCategory, idUser)).thenReturn(category);
        when(categoryRepository.getObjectivesByCategory(idCategory, idUser)).thenReturn(objectivesUUIDs);
        when(objectiveServiceClient.getObjectiveById(objectivesUUIDs.get(0), idUser)).thenReturn(ResponseEntity.ok(objectives.get(0)));
        when(objectiveServiceClient.getObjectiveById(objectivesUUIDs.get(1), idUser)).thenReturn(ResponseEntity.ok(objectives.get(1)));
        var result = categoryService.getObjectivesByCategory(idUser, idCategory);

        // Then
        assertNotNull(result);
        assertEquals(
                responseObjectivesByCategory.category().id(),
                result.category().id()
        );
        assertEquals(
                responseObjectivesByCategory.category().categoryName(),
                result.category().categoryName()
        );
        assertEquals(
                responseObjectivesByCategory.category().categoryDescription(),
                result.category().categoryDescription()
        );
        assertEquals(responseObjectivesByCategory.objectives().size(), result.objectives().size());
        assertEquals(responseObjectivesByCategory.objectives().get(0).idObjective(), result.objectives().get(0).idObjective());
        assertEquals(responseObjectivesByCategory.objectives().get(0).idUser(), result.objectives().get(0).idUser());
        assertEquals(responseObjectivesByCategory.objectives().get(0).nameObjective(), result.objectives().get(0).nameObjective());
        assertEquals(responseObjectivesByCategory.objectives().get(1).idObjective(), result.objectives().get(1).idObjective());
        assertEquals(responseObjectivesByCategory.objectives().get(1).idUser(), result.objectives().get(1).idUser());
        assertEquals(responseObjectivesByCategory.objectives().get(1).nameObjective(), result.objectives().get(1).nameObjective());


        verify(categoryRepository).findCategoryByIdAndIdUser(idCategory, idUser);
        verify(categoryRepository).getObjectivesByCategory(idCategory, idUser);
    }
}
