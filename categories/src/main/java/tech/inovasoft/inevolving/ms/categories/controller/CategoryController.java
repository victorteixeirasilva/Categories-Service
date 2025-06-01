package tech.inovasoft.inevolving.ms.categories.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestUpdateCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoriesDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryAndNewObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectivesByCategory;
import tech.inovasoft.inevolving.ms.categories.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.ErrorInExternalServiceException;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.service.CategoryService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Categories | Categorias", description = "Categories Endpoint Manager | Gerenciador dos endpoints de Categorias de Objetivos")
@RestController
@RequestMapping("/ms/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Adds new category to the user | Adiciona uma nova categoria ao usuário.",
            description = "Returns the new category | Retorna a nova categoria."
    )
    @Async("asyncExecutor")
    @PostMapping("/{idUser}")
    public CompletableFuture<ResponseEntity<Category>> addCategory(
            @PathVariable("idUser") UUID idUser,
            @RequestBody RequestCategoryDTO dto
    ) throws DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.addCategory(idUser, dto)
        ));
    }

    @Operation(
            summary = " Adds a new Objective to the Category | Adiciona um novo Objetivo a Categoria.",
            description = "Returns a confirmation that the objective was added to the category, and the category and objective information| Retorna uma confirmação que o objetivo foi adicionado na categoria, e as informações da categoria e do objetivo."
    )
    @Async("asyncExecutor")
    @PostMapping("/objective/{idUser}")
    public CompletableFuture<ResponseEntity<ResponseCategoryAndNewObjectiveDTO>> addObjectiveToCategory(
            @PathVariable("idUser") UUID idUser,
            @RequestBody RequestAddObjectiveToCategoryDTO dto
    ) throws ErrorInExternalServiceException, DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.addObjectiveToCategory(
                        idUser,
                        dto
                ))
        );
    }

    @Operation(
            summary = "Remove a Objective from the Category | Remove um Objetivo da Categoria.",
            description = "Returns a confirmation that the objective was removed from the category | Retorna a confirmação que o objetivo foi removido da categoria."
    )
    @Async("asyncExecutor")
    @DeleteMapping("/objective/{idUser}/{idCategory}/{idObjective}")
    public CompletableFuture<ResponseEntity<ResponseMessageDTO>> removeObjectiveToCategory(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idCategory") UUID idCategory,
            @PathVariable("idObjective") UUID idObjective
    ){
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.removeObjectiveToCategory(
                        idUser,
                        idCategory,
                        idObjective
                ))
        );
    }

    @Operation(
            summary = "Remove a category | Remove uma categoria.",
            description = "Returns a confirmation that the category was removed | Retorna a confirmação que a categoria foi removida."
    )
    @Async("asyncExecutor")
    @DeleteMapping("/{idUser}/{idCategory}")
    public CompletableFuture<ResponseEntity<ResponseMessageDTO>> removeCategory(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idCategory") UUID idCategory
    ){
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.removeCategory(
                        idUser,
                        idCategory
                ))
        );
    }

    @Operation(
            summary = " Update a category | Atualizar uma categoria",
            description = " Returns the updated category | Retorna a categoria atualizada."
    )
    @Async("asyncExecutor")
    @PatchMapping("/{idUser}/{idCategory}")
    public CompletableFuture<ResponseEntity<Category>> updateCategory(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idCategory") UUID idCategory,
            @RequestBody RequestUpdateCategoryDTO dto
    ) throws DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.updateCategory(
                        idUser,
                        idCategory,
                        dto
                ))
        );
    }

    @Operation(
            summary = " Get categories | Ver categorias",
            description = " Returns a list with all categories of the user | Retorna uma lista com todas as categorias do usuário."
    )
    @Async("asyncExecutor")
    @GetMapping("/{idUser}")
    public CompletableFuture<ResponseEntity<ResponseCategoriesDTO>> getCategories(
            @PathVariable("idUser") UUID idUser
    ){
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.getCategories(
                        idUser
                ))
        );
    }

    @Operation(
            summary = "",
            description = ""
    )
    @Async("asyncExecutor")
    @GetMapping("/{idUser}/{idCategory}")
    public CompletableFuture<ResponseEntity<ResponseObjectivesByCategory>> getCategories(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idCategory") UUID idCategory
    ) throws ErrorInExternalServiceException, DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.getObjectivesByCategory(
                        idUser,
                        idCategory
                ))
        );
    }






}
