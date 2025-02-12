package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

class RegistrationTest {
    private String generatorDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    public void setUp() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    void shouldTestValidForm() { //Заполнение формы валидными данными

        String planningData = generatorDate(3, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Оренбург");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $("[class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningData));
    }

    @Test
    void shouldTestCityEnglish() { //Заполнение формы город указан на английском

        String planningData = generatorDate(10, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Orenburg");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Доставка в выбранный город недоступна"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestNotValidCity() { //Заполнение формы указан не административный город

        String planningData = generatorDate(10, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Аксай");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Доставка в выбранный город недоступна"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestCityEmpty() { //Заполнение формы город не указан

        String planningData = generatorDate(10, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Поле обязательно для заполнения"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestCityWithSpecSymbols() { //Заполнение формы город указан через нижнее подчеркивание

        String planningData = generatorDate(10, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Санкт_Петербург");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Доставка в выбранный город недоступна"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestMinus2Days() { //Заполнение формы дата менее 3-х дней

        String planningData = generatorDate(2, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Заказ на выбранную дату невозможен"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestMinus4Days() { //Заполнение формы дата более 3-х дней

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $("[class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningData));
    }

    @Test
    void shouldTestToday() { //Заполнение формы сегодняшняя дата

        String planningData = generatorDate(0, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Заказ на выбранную дату невозможен"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestMinus3Days() { //Заполнение формы  дата в прошлом

        String planningData = generatorDate(-3, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Заказ на выбранную дату невозможен"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestValidName() { //Заполнение формы ФИО указано полностью

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Петрович Петров");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $("[class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningData));
    }

    @Test
    void shouldTestNameEnglish() { //Заполнение формы ФИО на английском

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Petrov Petr");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Имя и Фамилия указаные неверно"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestOnlyName() { //Заполнение формы указано только имя

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $("[class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningData));
    }

    @Test
    void shouldTestNameWithYO() { //Заполнение формы указано имя с букой ё

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Пётр Семёнов");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Имя и Фамилия указаные неверно"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestDoubleFirstName() { //Заполнение формы ФИО указано через дефис

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Семенов-Иванов");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $("[class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningData));
    }

    @Test
    void shouldTestNameWithSpecSymbols() { //Заполнение формы ФИО со спец.симоволом

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Семенов*");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Имя и Фамилия указаные неверно"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestNameWithNum() { //Заполнение формы ФИО из цифр

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("789 789");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Имя и Фамилия указаные неверно"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestNoName() { //Заполнение формы ФИО не указано

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("+79051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Поле обязательно для заполнения"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestPhoneWithoutPlus() { //Заполнение формы телефон через 8

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Семенов");
        $("[name='phone']").setValue("89051252525");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Телефон указан неверно"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestPhoneWith10Number() { //Заполнение формы номер телефона 10 цифр

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Семенов");
        $("[name='phone']").setValue("+7905125252");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Телефон указан неверно"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestPhoneWith12Number() { //Заполнение формы номер телефона 12 цифр

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Семенов");
        $("[name='phone']").setValue("+790512525254");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Телефон указан неверно"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestPhoneWithSpecSymbols() { //Заполнение формы номер телефона со спец.символами

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Семенов");
        $("[name='phone']").setValue("+7(905)125-25-25");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Телефон указан неверно"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestPhoneEmpty() { //Заполнение формы номер телефона не указан

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Семенов");
        $("[name='phone']").setValue("");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Поле обязательно для заполнения"))
                .should(Condition.visible, Duration.ofSeconds(5));
        $("[class='notification__content']").shouldNot(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestUncheckedBox() { //Заполнение формы чекбокс не отмечен

        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[name='name']").setValue("Петр Семенов");
        $("[name='phone']").setValue("+79051501515");
        $("[class='button__text']").click();
        $(".input_invalid[data-test-id='agreement']").should(exist);
        $("[data-test-id='notification']").shouldNot(Condition.visible, Duration.ofSeconds(15));
    }
}

