package com.example.gawex.ui;

import com.example.gawex.service.EmployeeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@PageTitle("Login")
@Route("login")
@RequestMapping(path = "/main")
@CssImport("./loginPage.css")
public class LoginUI extends VerticalLayout{
    @Autowired
    EmployeeService employeeService;

    @PostConstruct
    public void init() {
        setClassName("loginUI");

        //cała strona - głowny VerticalLayout
        VerticalLayout loginPage = new VerticalLayout();
        loginPage.setClassName("loginPage");
        add(loginPage);
        loginPage.getElement().setAttribute("theme", Lumo.DARK);

        VerticalLayout loginFormVertical = new VerticalLayout();
        loginFormVertical.setClassName("loginFormVertical");
        loginPage.add(loginFormVertical);

        //formularz - dodanie formularza logowania do głównego VerticalLayout
        LoginForm loginForm = new LoginForm();

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setUsername("Użytkownik");
        i18n.getForm().setPassword("Hasło");
        i18n.getForm().setSubmit("Zaloguj");
        i18n.getForm().setTitle("Aplikacja serwisowa");
        loginForm.setI18n(i18n);

        loginForm.setClassName("loginForm");
        loginForm.getElement().setAttribute("no-autofocus", "");
        loginFormVertical.add(loginForm);
        loginForm.getElement().setAttribute("theme", Lumo.DARK);

        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.addLoginListener(loginEvent -> {
            if (employeeService.getByLogin(loginEvent.getUsername()) != null && employeeService.getPasswordByLogin(loginEvent.getUsername()).equals(loginEvent.getPassword())){
                //zalogowano poprawnie
                LoginToken.token = 1;
                UI.getCurrent().navigate("client");
            }else{
                //nie zalogowano poprawnie
                Notification.show("Zła nazwa użytkownika lub hasło");
                LoginToken.token = 0;
                loginEvent.getSource().setEnabled(true); //ustawienie statusu klawisza na aktywny po podaniu blednych danych
            }
        });
    }

}

