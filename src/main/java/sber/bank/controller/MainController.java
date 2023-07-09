package sber.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Основная страница", description = "Методы для основной страницы")
public class MainController {

    @Operation(summary = "Приветствие", description = "Возвращает приветствие")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса"),
    })
    @GetMapping
    public String greeting() {
        return "Hello, World!";
    }

}
