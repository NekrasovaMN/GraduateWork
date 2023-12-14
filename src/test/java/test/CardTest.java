package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.BuyPage;
import page.StartPage;
import data.DataHelper;
import data.SQLHelper;

import static com.codeborne.selenide.Selenide.*;
import static data.SQLHelper.cleanDatabase;


public class CardTest {
    StartPage startPage;
    BuyPage buyPage;


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        startPage = new StartPage();
        buyPage = startPage.goToBuyPage();
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
    @DisplayName("Test BuyApprovedCard")
    public void shouldTestForApprovedCard() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Test BuyDeclinedCard")
    public void shouldTestNegativeForDeclinedCard() {
        buyPage.putData(DataHelper.declinedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.errorNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Card Number is incorrect less than digits")
    public void shouldTestIncorrectCardNumberLess() {
        buyPage.putData(DataHelper.getInvalidCardNumberLess(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Month value is a single digit numeric value")
    public void shouldTestMonthSingleDigitNumericValue() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getInvalidMonthOneNumber(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Month value is 00")
    public void shouldTestMonthNumber00() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getZeroMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.validityErrorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Month more 12")
    public void shouldTestMonthNumberMore12() {
        buyPage.putData(DataHelper.approvedCardNumber(), "13", DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.validityErrorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value is 00")
    public void shouldTestYearNumber00() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getZeroYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.expiredCardNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value is more than 5 years from the date of filling")
    public void shouldTestYearMoreFiveYears() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getInvalidMoreYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.validityErrorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value is a single digit numeric value")
    public void shouldTestYearSingleDigitNumericValue() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getInvalidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Year value should be past")
    public void shouldTestPastYear() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getInvalidPastYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.expiredCardNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Owner value contains Cyrillic")
    public void shouldTestOwnerWithCyrillic() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getInvalidOwnerCyrillic(), DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("Owner value is symbols")
    public void shouldTestOwnerValueASymbols() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getInvalidOwnerSymbols(), DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("Owner value contains 1 letter")
    public void shouldTestOwnerWithOneLetter() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), "N", DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("CVC value is two digit number")
    public void shouldTestCvcAsTwoDigitNumber() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getInvalidCvc2());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("CVC value is 00")
    public void shouldTestCVCNumber00() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getZeroCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("CardNumber value is empty")
    public void shouldTestCardNumberIsEmpty() {
        buyPage.putData(DataHelper.getInvalidCardNumberEmpty(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("Month value is empty")
    public void shouldTestMonthIsEmpty() {
        buyPage.putData(DataHelper.approvedCardNumber(), "", DataHelper.getValidYear(), DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("Year value is empty")
    public void shouldTestYearIsEmpty() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), "", DataHelper.getValidOwner(), DataHelper.getValidCvc());
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Owner value is empty")
    public void shouldTestOwnerIsEmpty() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), "", DataHelper.getValidCvc());
        buyPage.ownerEmptyNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }


    @Test
    @DisplayName("CVC value is empty")
    public void shouldTestCVCIsEmpty() {
        buyPage.putData(DataHelper.approvedCardNumber(), DataHelper.getValidMonth(), DataHelper.getValidYear(), DataHelper.getValidOwner(), "");
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }

    @Test
    @DisplayName("Test empty form")
    public void shouldTestEmptyForm() {
        buyPage.putData("", "", "", "", "");
        buyPage.wrongFormatNotificationWait();
        buyPage.wrongFormatNotificationWait();
        buyPage.wrongFormatNotificationWait();
        buyPage.ownerEmptyNotificationWait();
        buyPage.wrongFormatNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
}