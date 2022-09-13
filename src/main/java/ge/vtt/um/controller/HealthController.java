package ge.vtt.um.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/heartbeat")
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.ok("I am running!");
    }
}
