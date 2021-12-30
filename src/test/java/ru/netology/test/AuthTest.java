package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {

    @BeforeEach
    void setup() {

        open("http://localhost:9999");
    }

    @Test
    void shouldLoginRegisteredActiveUser() {
        Configuration.holdBrowserOpen = true;
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("active");
        DataGenerator.sendRequest(user);
        $(".input__box>[name='login']").setValue(user.getLogin());
        $(".input__box>[name='password']").setValue(user.getPassword());
        $(".button").click();
        $("[id='root']").shouldBe(exactText("Личный кабинет"));
    }

    @Test
    void shouldLoginBlockedUser() {
        Configuration.holdBrowserOpen = true;
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("blocked");
        DataGenerator.sendRequest(user);
        $(".input__box>[name='login']").setValue(user.getLogin());
        $(".input__box>[name='password']").setValue(user.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldInvalidLogin() {
        Configuration.holdBrowserOpen = true;
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("active");
        DataGenerator.sendRequest(user);
        var anotherLogin = DataGenerator.generateLogin();
        $(".input__box>[name='login']").setValue(anotherLogin);
        $(".input__box>[name='password']").setValue(user.getPassword());
        $(".button").click();
        $("[class='notification__content']").shouldBe(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidPassword() {
        Configuration.holdBrowserOpen = true;
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("active");
        DataGenerator.sendRequest(user);
        var anotherPassword = DataGenerator.generatePassword();
        $(".input__box>[name='login']").setValue(user.getLogin());
        $(".input__box>[name='password']").setValue(anotherPassword);
        $(".button").click();
        $("[class='notification__content']").shouldBe(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}