package sber.bank.controller;

import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Card;
import sber.bank.exceptions.NotFoundException;
import sber.bank.service.implementation.CardService;

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
     */
    @PostMapping("/create")
    public boolean create(@RequestBody Card card) {
        return cardService.create(card).equals(card);
    }

    /**
     * Удаление банковской карты по ее номеру.
     *
     * @param number Номер карты.
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @PostMapping("/delete/{number}")
    public void delete(@PathVariable Long number) {
        cardService.delete(number);
    }

    /**
     * Обновление данных банковской карты по ее номеру.
     *
     * @param number     Номер карты.
     * @param cardDetail Обновленные данные карты.
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @PutMapping("/update/{number}")
    public void update(@PathVariable Long number, @RequestBody Card cardDetail) {
        cardService.update(number, cardDetail);
    }
}