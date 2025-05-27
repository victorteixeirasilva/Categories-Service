package tech.inovasoft.inevolving.ms.categories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.repository.interfaces.CategoryRepository;
import tech.inovasoft.inevolving.ms.categories.service.CategoryService;

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

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void addCategory() {
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
    public void addObjectiveToCategory() {
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


}
