package fete.bird.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fb")
public class FallbackController {
    @PostMapping(value = "/course")
    public ResponseEntity<HttpStatus> ticketFallback(){
        return ResponseEntity.ok(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping(value = "/assessment")
    public ResponseEntity<HttpStatus> reservationFallback(){
        return ResponseEntity.ok(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
