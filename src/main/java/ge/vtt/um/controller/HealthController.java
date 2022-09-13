package ge.vtt.um.controller;

import ge.vtt.um.model.transfer.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/heartbeat")
    public ResponseEntity<GeneralResponse> heartbeat() {
        return ResponseEntity.ok(GeneralResponse.builder().message("I am working!").status(HttpStatus.OK.value()).build());
    }
}
