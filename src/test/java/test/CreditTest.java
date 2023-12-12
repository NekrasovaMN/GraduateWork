package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.StartPage;
import data.DataHelper;
import data.SQLHelper;
import static com.codeborne.selenide.Selenide.*;
import static data.SQLHelper.cleanDatabase;

public class CreditTest {

    StartPage startPage;
    CreditPage creditPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        startPage = new StartPage();
        creditPage = startPage.goToCreditPage();
    }

    @AfterEach
    void TearDownAll() {
        cleanDatabase();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Test BuyApprovedCard by credit")
    public void shouldTestForApprovedCardCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("Test BuyDeclinedCard by credit")
    public void shouldTestNegativeForDeclinedCardCreditForm() {
        creditPage.putData(DataHelper.declinedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.errorNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("Card Number is incorrect less than digits")
    public void shouldTestIncorrectCardNumberLessCreditForm() {
        creditPage.putData(DataHelper.getInvalidCardNumberLess(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.wrongCardNumberNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Month value is a single digit numeric value")
    public void shouldTestMonthSingleDigitNumericValueCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getInvalidMonthOneNumber(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.wrongMonthNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Month value is 00")
    public void shouldTestMonthNumber00CreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getZeroMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.validityErrorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Month more 12")
    public void shouldTestMonthNumberMore12CreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getInvalidMonthTwoNumbers(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.validityErrorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value is 00")
    public void shouldTestYearNumber00CreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getZeroYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.expiredCardNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value is more than 5 years from the date of filling")
    public void shouldTestYearMoreFiveYearsCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getInvalidMoreYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.validityErrorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value is a single digit numeric value")
    public void shouldTestYearSingleDigitNumericValueCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getInvalidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.wrongYearNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value should be past")
    public void shouldTestPastYearCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getInvalidPastYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.expiredCardNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Owner value contains Cyrillic")
    public void shouldTestOwnerWithCyrillicCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getInvalidOwnerCyrillic(), DataHelper.getValidCvc());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Owner value is symbols")
    public void shouldTestOwnerValueASymbolsCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getInvalidOwnerSymbols(), DataHelper.getValidCvc());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Owner value contains 1 letter")
    public void shouldTestOwnerWithOneLetterCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), "N", DataHelper.getValidCvc());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("CVC value is two digit numbre")
    public void shouldTestCvcAsTwoDigitNumberCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getInvalidCvc2());
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("CVC value is 00")
    public void shouldTestCVCNumber00CreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getZeroCvc());
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("CardNumber value is empty")
    public void shouldTestCardNumberIsEmptyCreditForm() {
        creditPage.putData(DataHelper.getInvalidCardNumberEmpty(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.wrongCardNumberNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Month value is empty")
    public void shouldTestMonthIsEmptyCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), "", DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.wrongMonthNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value is empty")
    public void shouldTestYearIsEmptyCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), "", DataHelper.getValidOwner(), DataHelper.getValidCvc());
        creditPage.wrongYearNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Owner value is empty")
    public void shouldTestOwnerIsEmptyCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), "", DataHelper.getValidCvc());
        creditPage.ownerEmptyNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("CVC value is empty")
    public void shouldTestCVCIsEmptyCreditForm() {
        creditPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), "");
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Test empty form")
    public void shouldTestEmptyCreditForm() {
        creditPage.putData("", "", "", "", "");
        creditPage.wrongCardNumberNotificationWait();
        creditPage.wrongMonthNotificationWait();
        creditPage.wrongYearNotificationWait();
        creditPage.ownerEmptyNotificationWait();
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
}

