package sber.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Card;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.CardRepository;

/**
 * Контроллер, отвечающий за обработку запросов, связанных с банковскими картами.
 */
@RestController
@RequestMapping("/card")
public class CardController {
    /**
     * Репозиторий банковской карты.
     */
    @Autowired
    private CardRepository cardRepository;

    /**
     * Создание новой банковской карты.
     *
     * @param card Данные новой карты.
     * @return Сообщение об успешном создании.
     */
    @PostMapping("/create")
    public String createCard(@RequestBody Card card) {
        cardRepository.save(card);

        return "Successfully created: " + card;
    }

    /**
     * Удаление банковской карты по ее номеру.
     *
     * @param number Номер карты.
     * @return Сообщение об успешном удалении.
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @PostMapping("/delete/{number}")
    public String deleteCard(@PathVariable Long number) {
        Card card = cardRepository.findById(number)
                .orElseThrow(() -> new NotFoundException("Card does not exist with number: " + number));

        cardRepository.delete(card);
        return "Successfully deleted: " + card;
    }

    /**
     * Обновление данных банковской карты по ее номеру.
     *
     * @param number     Номер карты.
     * @param cardDetail Обновленные данные карты.
     * @return Сообщение об успешном обновлении.
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @PutMapping("/update/{number}")
    public String updateCard(@PathVariable Long number, @RequestBody Card cardDetail) {
        Card card = cardRepository.findById(number)
                .orElseThrow(() -> new NotFoundException("Card does not exist with number: " + number));

        card.setAccount(cardDetail.getAccount());
        card.setExpirationDate(cardDetail.getExpirationDate());
        card.setCvv(cardDetail.getCvv());

        cardRepository.save(card);

        return "Successfully updated!";
    }
}