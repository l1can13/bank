package sber.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.CardRepo;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardRepo cardRepo;

    @PostMapping("/create")
    public String createCard(@RequestBody Card card) {
        cardRepo.save(card);

        return "Successfully created: " + card;
    }

    @PostMapping("/delete/{number}")
    public String deleteCard(@PathVariable Long number) {
        Card card = cardRepo.findById(number)
                .orElseThrow(() -> new NotFoundException("Card not exist with number: " + number));

        cardRepo.delete(card);
        return "Successfully deleted: " + card;
    }

    @PutMapping("/update/{number}")
    public String updateCard(@PathVariable Long number, @RequestBody Card cardDetail) {
        Card card = cardRepo.findById(number)
                .orElseThrow(() -> new NotFoundException("Card not exist with number: " + number));

        card.setAccount(cardDetail.getAccount());
        card.setExpirationDate(cardDetail.getExpirationDate());
        card.setCvv(cardDetail.getCvv());

        cardRepo.save(card);

        return "Successfully updated!";
    }
}
