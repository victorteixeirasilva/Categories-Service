package tech.inovasoft.inevolving.ms.categories.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;
import tech.inovasoft.inevolving.ms.categories.service.client.dto.RequestCreateObjectiveDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@FeignClient(name = "objectives-service", url = "http://localhost:8088/ms/objectives")
public interface ObjectiveServiceClient {

    @GetMapping("/{idObjective}/{idUser}")
    ResponseEntity<ResponseObjectiveDTO> getObjectiveById(@PathVariable UUID idObjective, @PathVariable UUID idUser);

}
