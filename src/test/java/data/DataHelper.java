package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvc;
    }

    public static String approvedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String declinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumberEmpty() {
        return " ";
    }

    public static String getInvalidCardNumberLess() {
        Faker faker = new Faker();
        return Long.toString(faker.number().randomNumber(11, true));
    }

    public static String getInvalidCardNumberMore() {
        Faker faker = new Faker();
        return Long.toString(faker.number().randomNumber(17, true));
    }

    public static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonthOneNumber() {
        String[] month = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int rnd = new Random().nextInt(month.length);
        return month[rnd];
    }

    public static String getInvalidMonthTwoNumbers() {
        String[] month = {"21", "22", "23", "24", "25", "26", "27", "28", "29"};
        int rnd = new Random().nextInt(month.length);
        return month[rnd];
    }

    public static String getZeroMonth() {
        return "00";
    }

    public static String getValidYear() {
        return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidYear() {
        String[] year = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int rnd = new Random().nextInt(year.length);
        return year[rnd];
    }

    public static String getInvalidPastYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidMoreYear() {
        return LocalDate.now().plusYears(7).format(DateTimeFormatter.ofPattern("yy"));
    }


    public static String getZeroYear() {
        return "00";
    }

    public static String getValidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String getInvalidOwnerCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String getInvalidOwnerSymbols() {
        String[] owner = {"212*62", "32/12", "36%", "4+", "-5", "=6", "^7", "(8)"};
        int rnd = new Random().nextInt(owner.length);
        return owner[rnd];
    }

    public static String getValidCvc() {
        Faker faker = new Faker();
        return faker.numerify("###");
    }

    public static String getInvalidCvc2() {
        Faker faker = new Faker();
        return faker.numerify("##");
    }

    public static String getInvalidCvc4() {
        Faker faker = new Faker();
        return faker.numerify("####");
    }

    public static String getZeroCvc() {
        return "000";
    }
}

