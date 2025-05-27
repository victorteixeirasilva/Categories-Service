package tech.inovasoft.inevolving.ms.categories.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.service.CategoryService;

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
    public CompletableFuture<ResponseEntity> addCategory(
            @PathVariable("idUser") UUID idUser
    ){
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(categoryService.addCategory(idUser))
        );
    }

    @Operation(
            summary = " Adds a new Objective to the Category | Adiciona um novo Objetivo a Categoria.",
            description = "Returns a confirmation that the objective was added to the category, and the category and objective information| Retorna uma confirmação que o objetivo foi adicionado na categoria, e as informações da categoria e do objetivo."
    )
    @Async("asyncExecutor")
    @PostMapping("/objective/{idUser}")
    public CompletableFuture<ResponseEntity> addObjectiveToCategory(
            @PathVariable("idUser") UUID idUser,
            @RequestBody RequestAddObjectiveToCategoryDTO dto
    ){
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.addObjectiveToCategory(
                        idUser,
                        dto
                ))
        );
    }

    @Operation(
            summary = " Adds a new Objective to the Category | Adiciona um novo Objetivo a Categoria.",
            description = "Returns a confirmation that the objective was added to the category, and the category and objective information | Retorna uma confirmação que o objetivo foi adicionado na categoria, e as informações da categoria e do objetivo."
    )
    @Async("asyncExecutor")
    @DeleteMapping("/objective/{idUser}/{idCategory}/{idObjective}")
    public CompletableFuture<ResponseEntity> removeObjectiveToCategory(
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


}
