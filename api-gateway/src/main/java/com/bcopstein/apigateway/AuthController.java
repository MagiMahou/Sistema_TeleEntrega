package com.bcopstein.apigateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.bcopstein.apigateway.dto.LoginRequest;
import com.bcopstein.apigateway.dto.LoginResponse;

@RestController
@CrossOrigin("*")
public class AuthController {

    private final org.springframework.web.reactive.function.client.WebClient webClient;

    public AuthController(org.springframework.web.reactive.function.client.WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://pizzaria-service").build();
    }

    // Simula a geração de token que antes estava no pizzaria-service
    private String gerarToken(String identificador, String perfil) {
        return "token-" + perfil.toLowerCase() + "-" + identificador + "-" + System.currentTimeMillis();
    }

    @PostMapping("/admin/login")
    public Mono<ResponseEntity<Object>> adminLogin(@RequestBody Mono<LoginRequest> requestMono) {
        return requestMono.flatMap(request -> 
            webClient.post()
                .uri("/clientes/validar-login")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(java.util.Map.class)
                .map(response -> {
                    String cpf = (String) response.get("cpf");
                    // Como cpf do admin fixo é "00000000000", se for ele assumimos ADMIN, senao COZINHA (caso seja possivel ter cozinha via clientes)
                    String perfil = "00000000000".equals(cpf) ? "ADMINISTRADOR" : "COZINHA";
                    return ResponseEntity.ok((Object) new LoginResponse(request.email(), gerarToken(cpf, perfil)));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((Object) "Credenciais internas inválidas.")))
        );
    }

    @PostMapping("/clientes/login")
    public Mono<ResponseEntity<Object>> clienteLogin(@RequestBody Mono<LoginRequest> requestMono) {
        return requestMono.flatMap(request -> 
            webClient.post()
                .uri("/clientes/validar-login")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(java.util.Map.class)
                .map(response -> {
                    String cpf = (String) response.get("cpf");
                    return ResponseEntity.ok((Object) new LoginResponse(request.email(), gerarToken(cpf, "CLIENTE")));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((Object) "Credenciais de cliente inválidas.")))
        );
    }
}
