package sber.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.bank.domain.Card;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.CardRepository;
import sber.bank.service.IService;

/**
 * Реализация сервиса для работы с банковскими картами.
 */
@Service
public class CardService implements IService<Card> {
    /**
     * Репозиторий для доступа к данным о банковских картах.
     */
    private final CardRepository cardRepository;

    /**
     * Конструктор с параметрами.
     *
     * @param cardRepository Репозиторий банковской карты.
     */
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    /**
     * Получение всех банковских карт.
     *
     * @return Итерируемая коллекция банковских карт.
     */
    @Override
    public Iterable<Card> getAll() {
        return cardRepository.findAll();
    }

    /**
     * Получение банковской карты по ее номеру.
     *
     * @param number Номер карты.
     * @return Банковская карта с указанным номером.
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @Override
    public Card getByPk(Long number) {
        return cardRepository.findById(number)
                .orElseThrow(() -> new NotFoundException("Не существует карты с номером " + number));
    }

    /**
     * Создание новой банковской карты.
     *
     * @param card Данные новой карты.
     * @return Созданная банковская карта.
     */
    @Override
    public Card create(Card card) {
        return cardRepository.save(card);
    }

    /**
     * Удаление банковской карты по ее номеру.
     *
     * @param number Номер карты.
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @Override
    public void delete(Long number) {
        cardRepository.delete(getByPk(number));
    }

    /**
     * Обновление данных банковской карты по ее номеру.
     *
     * @param number     Номер карты.
     * @param cardDetail Обновленные данные карты.
     * @throws NotFoundException Если карта с указанным номером не найдена.
     */
    @Override
    public void update(Long number, Card cardDetail) {
        Card card = getByPk(number);

        card.setAccount(cardDetail.getAccount());
        card.setExpirationDate(cardDetail.getExpirationDate());
        card.setCvv(cardDetail.getCvv());

        cardRepository.save(card);
    }
}