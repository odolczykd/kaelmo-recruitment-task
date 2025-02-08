package pl.kaelmo.odolczykd.recruitment.api.internal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.kaelmo.odolczykd.recruitment.api.internal.error.ErrorResponse;
import pl.kaelmo.odolczykd.recruitment.api.internal.mapper.UserMapper;
import pl.kaelmo.odolczykd.recruitment.api.internal.service.InternalApiService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class InternalApiController {
    private final InternalApiService internalApiService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        try {
            var users = internalApiService.getDataFromExternalApi()
                    .stream()
                    .map(userMapper::toRestUser)
                    .toList();
            return ResponseEntity.ok(users);
        } catch (ResponseStatusException rse) {
            return ResponseEntity
                    .status(rse.getStatusCode())
                    .body(new ErrorResponse(rse.getStatusCode().value(), rse.getReason()));
        }
    }
}
