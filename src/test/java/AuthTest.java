package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");

        $("[data-test-id='login'] .input__control").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(50))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");

        $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(50))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        $("[data-test-id='login'] .input__control").setValue(wrongLogin);
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(50))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("The message Field must be filled in should appear")
    void shouldGetMessageFieldMustBeFilledInShouldAppearLogin() {
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id='login'] .input__control").setValue("");
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'] .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(50))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(50))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("The message Field must be filled in should appear")
    void shouldGetMessageFieldMustBeFilledInShouldAppearPassword() {
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue("");
        $("[data-test-id='action-login']").click();
        $("[data-test-id='password'] .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(50))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("The message field must be filled in should appear")
    void shouldGetMessageFieldMustBeFilledInShouldAppearInForm() {

        $("[data-test-id='login'] .input__control").setValue("");
        $("[data-test-id='password'] .input__control").setValue("");
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'] .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(50))
                .shouldBe(Condition.visible);
        $("[data-test-id='password'] .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(50))
                .shouldBe(Condition.visible);
    }

}
