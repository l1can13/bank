package sber.bank.service;

/**
 * Интерфейс для сервиса.
 *
 * @param <T> Тип сервиса.
 */
public interface IService<T> {
    /**
     * Получает все объекты типа T.
     *
     * @return Итерируемый объект с элементами типа T.
     */
    Iterable<T> getAll();

    /**
     * Получает объект типа T по его первичному ключу.
     *
     * @param pk Первичный ключ объекта.
     * @return Объект типа T с указанным первичным ключом.
     */
    T getByPk(Long pk);

    /**
     * Создает новый объект типа T.
     *
     * @param obj Данные нового объекта.
     * @return Созданный объект типа T.
     */
    T create(T obj);

    /**
     * Удаляет объект типа T по его первичному ключу.
     *
     * @param pk Первичный ключ объекта.
     */
    void delete(Long pk);

    /**
     * Обновляет данные объекта типа T.
     *
     * @param pk        Первичный ключ объекта.
     * @param objDetail Обновленные данные объекта.
     */
    void update(Long pk, T objDetail);
}