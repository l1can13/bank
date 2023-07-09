package sber.bank.controller;

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
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @PostMapping("/delete/{number}")
    public void deleteCard(@PathVariable Long number) {
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
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @PutMapping("/update/{number}")
    public void updateCard(@PathVariable Long number, @RequestBody Card cardDetail) {
        // region Проверка входных данных
        Validation.validateCardNumber(number);
        Validation.validateCard(cardDetail);
        // endregion

        cardService.update(number, cardDetail);
    }
}