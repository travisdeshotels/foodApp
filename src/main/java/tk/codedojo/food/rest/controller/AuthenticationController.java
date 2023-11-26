package tk.codedojo.food.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.beans.SignUpRequest;
import tk.codedojo.food.service.AuthenticationService;

@RestController
@RequestMapping("/api/food/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<Customer> signup(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(service.signup(request));
    }
}
