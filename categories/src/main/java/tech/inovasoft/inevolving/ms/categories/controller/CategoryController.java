package tech.inovasoft.inevolving.ms.categories.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundCategoryInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundObjectiveInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.model.Category;
import tech.inovasoft.inevolving.ms.categories.service.CategoryService;
import tech.inovasoft.inevolving.ms.categories.service.client.Auth_For_MService.TokenCache;
import tech.inovasoft.inevolving.ms.categories.service.client.Auth_For_MService.TokenService;
import tech.inovasoft.inevolving.ms.categories.service.client.Auth_For_MService.dto.TokenValidateResponse;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Categories | Categorias", description = "Categories Endpoint Manager | Gerenciador dos endpoints de Categorias de Objetivos")
@RestController
@RequestMapping("/ms/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TokenService tokenService;

    @Operation(
            summary = "Adds new category to the user | Adiciona uma nova categoria ao usuário.",
            description = "Returns the new category | Retorna a nova categoria."
    )
    @Async("asyncExecutor")
    @PostMapping("/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<Category>> addCategory(
            @PathVariable("idUser") UUID idUser,
            @RequestBody RequestCategoryDTO dto,
            @PathVariable String token
    ) throws DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                    HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.addCategory(idUser, dto)
        ));
    }

    @Operation(
            summary = " Adds a new Objective to the Category | Adiciona um novo Objetivo a Categoria.",
            description = "Returns a confirmation that the objective was added to the category, and the category and objective information| Retorna uma confirmação que o objetivo foi adicionado na categoria, e as informações da categoria e do objetivo."
    )
    @Async("asyncExecutor")
    @PostMapping("/objective/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<ResponseCategoryAndNewObjectiveDTO>> addObjectiveToCategory(
            @PathVariable("idUser") UUID idUser,
            @RequestBody RequestAddObjectiveToCategoryDTO dto,
            @PathVariable String token
    ) throws ErrorInExternalServiceException, DataBaseException, NotFoundCategoryInDatabaseException, NotFoundObjectiveInDatabaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.addObjectiveToCategory(idUser, dto)
        ));
    }

    @Operation(
            summary = "Remove a Objective from the Category | Remove um Objetivo da Categoria.",
            description = "Returns a confirmation that the objective was removed from the category | Retorna a confirmação que o objetivo foi removido da categoria."
    )
    @Async("asyncExecutor")
    @DeleteMapping("/objective/{idUser}/{idCategory}/{id}/{token}")
    public CompletableFuture<ResponseEntity<ResponseMessageDTO>> removeObjectiveToCategory(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idCategory") UUID idCategory,
            @PathVariable("id") UUID idObjective,
            @PathVariable String token
    ) throws NotFoundCategoryInDatabaseException, DataBaseException, NotFoundObjectiveInDatabaseException, ErrorInExternalServiceException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.removeObjectiveToCategory(idUser, idCategory, idObjective)
        ));
    }

    @Operation(
            summary = "Remove a category | Remove uma categoria.",
            description = "Returns a confirmation that the category was removed | Retorna a confirmação que a categoria foi removida."
    )
    @Async("asyncExecutor")
    @DeleteMapping("/{idUser}/{idCategory}/{token}")
    public CompletableFuture<ResponseEntity<ResponseMessageDTO>> removeCategory(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idCategory") UUID idCategory,
            @PathVariable String token
    ) throws NotFoundCategoryInDatabaseException, DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.removeCategory(idUser, idCategory)
        ));
    }

    @Operation(
            summary = " Update a category | Atualizar uma categoria",
            description = " Returns the updated category | Retorna a categoria atualizada."
    )
    @Async("asyncExecutor")
    @PutMapping("/{idUser}/{idCategory}/{token}")
    public CompletableFuture<ResponseEntity<Category>> updateCategory(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idCategory") UUID idCategory,
            @RequestBody RequestUpdateCategoryDTO dto,
            @PathVariable String token
    ) throws DataBaseException, NotFoundCategoryInDatabaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.updateCategory(idUser, idCategory, dto)
        ));
    }

    @Operation(
            summary = " Get categories | Ver categorias",
            description = " Returns a list with all categories of the user | Retorna uma lista com todas as categorias do usuário."
    )
    @Async("asyncExecutor")
    @GetMapping("/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<ResponseCategoriesDTO>> getCategories(
            @PathVariable("idUser") UUID idUser,
            @PathVariable String token
    ) throws NotFoundCategoryInDatabaseException, DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.getCategories(idUser)
        ));
    }

    @Operation(
            summary = "Get objectives by category | Objetivos por categoria",
            description = "Returns a list with all objectives of the category | Retorna uma lista com todos os objetivos da categoria."
    )
    @Async("asyncExecutor")
    @GetMapping("/{idUser}/{idCategory}/{token}")
    public CompletableFuture<ResponseEntity<ResponseObjectivesByCategory>> getObjectivesByCategory(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idCategory") UUID idCategory,
            @PathVariable String token
    ) throws ErrorInExternalServiceException, DataBaseException, NotFoundCategoryInDatabaseException, NotFoundObjectiveInDatabaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                categoryService.getObjectivesByCategory(idUser, idCategory)
        ));
    }






}
