import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("111", "Test User");
    }

    @AfterEach
    void tearDown() {
        account = null;
    }

    @Test
    void testInitialBalance() {
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void testDeposit() {
        account.deposit(200.0);
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        account.deposit(500.0);
        account.withdraw(100.0);
        assertEquals(400.0, account.getBalance());
    }

    @Test
    void testWithdrawMoreThanBalance() {
        account.deposit(100.0);
        assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(500.0);
        });
    }

    @Test
    void testNegativeDeposit() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-20.0);
        });
    }

    @Test
    void testNegativeWithdraw() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-20.0);
        });
    }

    @Test
    void testTransactionHistory() {
        account.deposit(100.0);
        account.withdraw(20.0);

        List<Transaction> hist = account.getTransactionHistory();
        assertEquals(2, hist.size());

        assertEquals("DEPOSIT", hist.get(0).getType());
        assertEquals("WITHDRAW", hist.get(1).getType());
    }
}
