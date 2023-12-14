package page;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyPage {
    private SelenideElement heading = $$("h3").get(1).shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(exactText("Оплата по карте"));
    private SelenideElement cardNumber = $(".input [placeholder = '0000 0000 0000 0000']");
    private SelenideElement monthField = $(".input [placeholder = '08']");
    private SelenideElement yearField = $(".input [placeholder = '22']");
    private SelenideElement ownerField = $$(".input__control").get(3);
    private SelenideElement CVCField = $(".input [placeholder = '999']");


    private SelenideElement successMessage = $$(".notification__content").find(text("Операция одобрена Банком."));
    private SelenideElement errorMessage = $$(" .notification__content").find(text("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement incorrectFormatError = $(byText("Неверный формат"));
    private SelenideElement validityError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));
    private SelenideElement emptyOwnerError = $(byText("Поле обязательно для заполнения"));
    private SelenideElement continueButton = $$("button span.button__text").find(exactText("Продолжить"));

    public BuyPage() {
        heading.shouldBe(visible);
    }

    public void putData(String number, String month, String year, String owner, String CVC) {
        cardNumber.setValue(number);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        CVCField.setValue(CVC);
        continueButton.click();
    }

    public void successNotificationWait() {
        successMessage.shouldBe(visible, Duration.ofSeconds(40));
    }

    public void errorNotificationWait() {
        errorMessage.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void wrongFormatNotificationWait() {
        incorrectFormatError.shouldBe(visible);
    }

    public void validityErrorNotificationWait() {
        validityError.shouldBe(visible);
    }

    public void expiredCardNotificationWait() {
        cardExpiredError.shouldBe(visible);
    }

    public void ownerEmptyNotificationWait() {
        emptyOwnerError.shouldBe(visible);
    }
}

