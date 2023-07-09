package sber.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Card;
import sber.bank.exceptions.BadArgumentException;
import sber.bank.exceptions.NotFoundException;
import sber.bank.service.implementation.CardService;
import sber.bank.validation.Validation;

/**
 * Контроллер, отвечающий за обработку запросов, связанных с банковскими картами.
 */
@RestController
@RequestMapping("api/card")
@Tag(name = "Банковские карты", description = "Методы для работы с банковскими картами")
public class CardController {
    /**
     * Сервис для работы с банковскими картами.
     */
    private final CardService cardService;

    /**
     * Конструктор с параметрами.
     *
     * @param cardService Сервис для работы с банковскими картами.
     */
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * Создание новой банковской карты.
     *
     * @param card Данные новой карты.
     * @return true, если создание карты успешно, в противном случае - false.
     * @throws BadArgumentException Если данные карты некорректны.
     */
    @Operation(summary = "Создать новую банковскую карту", description = "Создает новую банковскую карту на основе предоставленных данных.")
    @ApiResponse(responseCode = "200", description = "Карта успешно создана")
    @ApiResponse(responseCode = "400", description = "Некорректные данные карты")
    @PostMapping("/create")
    public boolean createCard(@RequestBody Card card) {
        // region Проверка входных данных
        Validation.validateCard(card);
        // endregion

        return cardService.create(card).equals(card);
    }

    /**
     * Удаление банковской карты по ее номеру.
     *
     * @param number Номер карты.
     * @throws BadArgumentException Если номер карты некорректен.
     * @throws NotFoundException    Если карта с указанным номером не найдена.
     */
    @Operation(summary = "Удалить банковскую карту", description = "Удаляет банковскую карту по ее номеру.")
    @ApiResponse(responseCode = "204", description = "Карта успешно удалена")
    @ApiResponse(responseCode = "400", description = "Некорректный номер карты")
    @ApiResponse(responseCode = "404", description = "Карта не найдена")
    @DeleteMapping("/delete/{number}")
    public void deleteCard(@Parameter(description = "Номер карты", example = "1234567890123456") @PathVariable Long number) {
        // region Проверка входных данных
        Validation.validateCardNumber(number);
        // endregion

        cardService.delete(number);
    }

    /**
     * Обновление данных банковской карты по ее номеру.
     *
     * @param number     Номер карты.
     * @param cardDetail Обновленные данные карты.
     * @throws BadArgumentException Если данные карты некорректны.
     * @throws NotFoundException    Если карта с указанным номером не найдена.
     */
    @Operation(summary = "Обновить данные банковской карты", description = "Обновляет данные банковской карты по ее номеру.")
    @ApiResponse(responseCode = "204", description = "Данные карты успешно обновлены")
    @ApiResponse(responseCode = "400", description = "Некорректные данные карты")
    @ApiResponse(responseCode = "404", description = "Карта не найдена")
    @PutMapping("/update/{number}")
    public void updateCard(@Parameter(description = "Номер карты", example = "1234567890123456") @PathVariable Long number,
                           @RequestBody Card cardDetail) {
        // region Проверка входных данных
        Validation.validateCardNumber(number);
        Validation.validateCard(cardDetail);
        // endregion

        cardService.update(number, cardDetail);
    }
}