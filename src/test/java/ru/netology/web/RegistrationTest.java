package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

class RegistrationTest {
    private String generatorDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void Test1() { //Заполнение формы валидными данными
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(3, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Оренбург");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Встреча успешно забронирована")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test2() { //Заполнение формы город указан на английском
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(10, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Orenburg");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Доставка в выбранный город недоступна")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test3() { //Заполнение формы указан не административный город
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(10, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Аксай");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Доставка в выбранный город недоступна")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test4() { //Заполнение формы город не указан
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(10, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Поле обязательно для заполнения")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test5() { //Заполнение формы город указан через нижнее подчеркивание
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(10, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Санкт_Петербург");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Доставка в выбранный город недоступна")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test6() { //Заполнение формы дата менее 3-х дней
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(2, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Заказ на выбранную дату невозможен")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test7() { //Заполнение формы дата более 3-х дней
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Встреча успешно забронирована")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test8() { //Заполнение формы сегодняшняя дата
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(0, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Заказ на выбранную дату невозможен")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test9() { //Заполнение формы  дата в прошлом
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(-3, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Заказ на выбранную дату невозможен")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test10() { //Заполнение формы ФИО указано полностью
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Петрович Петров");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Встреча успешно забронирована")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test11() { //Заполнение формы ФИО на английском
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Petrov Petr");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Имя и Фамилия указаные неверно.")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test12() { //Заполнение формы указано только имя
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Встреча успешно забронирована")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test13() { //Заполнение формы указано имя с букой ё
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Пётр Семёнов");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Имя и Фамилия указаные неверно")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test14() { //Заполнение формы ФИО указано через дефис
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Семенов-Иванов");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Встреча успешно забронирована")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test15() { //Заполнение формы ФИО со спец.симоволом
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Семенов*");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Имя и Фамилия указаные неверно")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test16() { //Заполнение формы ФИО из цифр
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("789 789");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Имя и Фамилия указаные неверно")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test17() { //Заполнение формы ФИО не указано
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Поле обязательно для заполнения")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test18() { //Заполнение формы телефон через 8
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Семенов");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("89051252525");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Телефон указан неверно")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test19() { //Заполнение формы номер телефона 10 цифр
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Семенов");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+7905125252");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Телефон указан неверно")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test20() { //Заполнение формы номер телефона 12 цифр
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Семенов");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+790512525254");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Телефон указан неверно")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test21() { //Заполнение формы номер телефона со спец.символами
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Семенов");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+7(905)125-25-25");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Телефон указан неверно")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test22() { //Заполнение формы номер телефона не указан
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Семенов");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("");
        $("[data-test-id=agreement]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Поле обязательно для заполнения")).should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void Test23() { //Заполнение формы чекбокс не отмечен
        Selenide.open("http://localhost:9999");
        String planningData = generatorDate(4, "dd.MM.yyyy");
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=\"date\"] span.input__box [placeholder=\"Дата встречи\"]").doubleClick().press(Keys.BACK_SPACE).setValue(planningData);
        $("[data-test-id=name] [type=text]").setValue("Петр Семенов");
        $("[data-test-id=\"phone\"] span.input__box [type=\"tel\"]").setValue("+79051501515");
        $("[class=button__text]").click();
        $(".input_invalid[data-test-id=\"agreement\"]").should(exist);
        $("[data-test-id=\"notification\"]").shouldNot(Condition.visible, Duration.ofSeconds(15));

    }

}

