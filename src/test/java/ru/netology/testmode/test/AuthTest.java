package ru.netology.testmode.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.generateLogin;
import static ru.netology.testmode.data.DataGenerator.generatePassword;

class AuthTest {

    @BeforeEach
    void setup() {

        open("http://localhost:9999");
    }

    @Test
    void shouldLoginRegisteredActiveUser() {
        Configuration.holdBrowserOpen = true;
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("active");
        $(".input__box>[name='login']").setValue(user.getLogin());
        $(".input__box>[name='password']").setValue(user.getPassword());
        $(".button").click();

    }

    @Test
    void shouldLoginBlockedUser() {
        Configuration.holdBrowserOpen = true;
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("blocked");
        $(".input__box>[name='login']").setValue(user.getLogin());
        $(".input__box>[name='password']").setValue(user.getPassword());
        $(".button").click();

    }

    @Test
    void shouldInvalidLogin() {
        Configuration.holdBrowserOpen = true;
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("active");
        var anotherLogin = generateLogin();
        $(".input__box>[name='login']").setValue(anotherLogin);
        $(".input__box>[name='password']").setValue(user.getPassword());
        $(".button").click();

    }

    @Test
    void shouldInvalidPassword() {
        Configuration.holdBrowserOpen = true;
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("active");
        var anotherPassword = generatePassword();
        $(".input__box>[name='login']").setValue(user.getLogin());
        $(".input__box>[name='password']").setValue(anotherPassword);
        $(".button").click();

    }
}