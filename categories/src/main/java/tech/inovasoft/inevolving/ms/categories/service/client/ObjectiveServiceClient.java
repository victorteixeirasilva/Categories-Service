package tech.inovasoft.inevolving.ms.categories.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;

import java.util.UUID;

@FeignClient(name = "objectives-service", url = "http://localhost:8080/ms/objectives")
public interface ObjectiveServiceClient {

    @GetMapping("/{idObjective}") // TODO: Testar o get
    ResponseObjectiveDTO getObjectiveById(@PathVariable UUID idObjective);

}
