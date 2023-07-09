package sber.bank.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sber.bank.domain.Account;
import sber.bank.domain.User;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> expectedAccounts = getAllAccounts();
        when(accountRepository.findAll()).thenReturn(expectedAccounts);

        Iterable<Account> actualAccounts = accountService.getAll();

        assertIterableEquals(expectedAccounts, actualAccounts);
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    public void testGetAccountByNumber_ValidNumber_ReturnsAccount() {
        Long accountNumber = 1001001001001001L;
        Account expectedAccount = new Account(getAllAccounts().get(0));
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(expectedAccount));

        Account actualAccount = accountService.getByPk(accountNumber);

        assertEquals(expectedAccount, actualAccount);
        verify(accountRepository, times(1)).findById(accountNumber);
    }

    @Test
    public void testGetAccountByNumber_InvalidNumber_ThrowsNotFoundException() {
        Long accountNumber = 1234567890L;
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.getByPk(accountNumber));
        verify(accountRepository, times(1)).findById(accountNumber);
    }

    @Test
    void create_ShouldCreateAccount() {
        Account account = new Account();
        account.setNumber(1001001001001001L);
        account.setUser(new User(1L, "John Smith", null, "123 Main St, City"));
        account.setCurrency("USD");
        account.setBalance(5000.0);

        when(accountRepository.save(account)).thenReturn(account);

        Account createdAccount = accountService.create(account);

        assertEquals(account, createdAccount);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void delete_ShouldDeleteAccount_WhenAccountExists() {
        Account account = new Account();
        account.setNumber(1001001001001001L);
        account.setUser(new User(1L, "John Smith", null, "123 Main St, City"));
        account.setCurrency("USD");
        account.setBalance(5000.0);

        when(accountRepository.findById(account.getNumber())).thenReturn(Optional.of(account));

        assertDoesNotThrow(() -> accountService.delete(account.getNumber()));
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void delete_ShouldThrowNotFoundException_WhenAccountDoesNotExist() {
        Long accountNumber = 1001001001001001L;

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.delete(accountNumber));
        verify(accountRepository, never()).delete(any());
    }

    @Test
    void update_ShouldUpdateAccount_WhenAccountExists() {
        Account existingAccount = new Account();
        existingAccount.setNumber(1001001001001001L);
        existingAccount.setUser(new User(1L, "John Smith", null, "123 Main St, City"));
        existingAccount.setCurrency("USD");
        existingAccount.setBalance(5000.0);

        Account updatedAccount = new Account();
        updatedAccount.setNumber(1001001001001001L);
        updatedAccount.setUser(new User(1L, "John Doe", null, "456 Elm St, Town"));
        updatedAccount.setCurrency("EUR");
        updatedAccount.setBalance(2000.0);

        when(accountRepository.findById(existingAccount.getNumber())).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(existingAccount)).thenReturn(existingAccount);

        assertDoesNotThrow(() -> accountService.update(existingAccount.getNumber(), updatedAccount));
        assertEquals(updatedAccount.getUser().getName(), existingAccount.getUser().getName());
        assertEquals(updatedAccount.getCurrency(), existingAccount.getCurrency());
        assertEquals(updatedAccount.getBalance(), existingAccount.getBalance());
        verify(accountRepository, times(1)).findById(existingAccount.getNumber());
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    void update_ShouldThrowNotFoundException_WhenAccountDoesNotExist() {
        Long accountNumber = 1001001001001001L;
        Account updatedAccount = new Account();
        updatedAccount.setNumber(1001001001001001L);
        updatedAccount.setUser(new User(1L, "John Doe", null, "456 Elm St, Town"));
        updatedAccount.setCurrency("EUR");
        updatedAccount.setBalance(2000.0);

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.update(accountNumber, updatedAccount));
        verify(accountRepository, never()).save(any());
    }

    private List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        User user = new User(1L, "John Smith", null, "123 Main St, City");

        Account account = new Account();
        account.setNumber(1001001001001001L);
        account.setUser(user);
        account.setCurrency("USD");
        account.setBalance(5000.0);

        accounts.add(account);

        return accounts;
    }
}