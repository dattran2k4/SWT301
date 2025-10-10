import dattq.example.AccountService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTest {
    static AccountService accountService;

    @BeforeAll
    static void initAll() {
        accountService = new AccountService();
    }

    @AfterAll
    static void cleanupAll() {
        accountService = null;
    }

    @DisplayName("Test registerAccount với dữ liệu từ CSV")
    @ParameterizedTest(name = "Test {index}: username={0}, password={1}, email={2}, expected={3}")
    @CsvFileSource(resources = "/dataAccount.csv", numLinesToSkip = 1)
    void testRegisterAccount(String username, String password, String email, boolean expected) throws IOException {
        boolean result = accountService.registerAccount(username, password, email);

        // Assert
        assertEquals(expected, result);

        // Ghi kết quả vào file UnitTest.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("UnitTest.txt", true))) {
            writer.write(String.format("username=%s, password=%s, email=%s => expected=%s, actual=%s%n",
                    username, password, email, expected, result));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
