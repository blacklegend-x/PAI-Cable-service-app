package com.example.gawex.ui;

import com.example.gawex.controller.ResCon;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


@PageTitle("Add Client")
@Route("clientAdd")
@CssImport("./clientAddPage.css")

public class ClientAddUI extends VerticalLayout {

    @Autowired
    ResCon resCon;

    @PostConstruct
    public void init(){

        setSizeFull();

        if(LoginToken.token == 0){
            Notification.show("Zaloguj się!");
            LoginToken.token = 0;
            UI.getCurrent().getPage().setLocation("/login");
        }


        setClassName("clientAddUI");

        getElement().setAttribute("theme", Lumo.DARK);


        //menu
        {
            HorizontalLayout horizontalMenuLayout = new HorizontalLayout();
            horizontalMenuLayout.setClassName("horizontalMenuLayout");
            add(horizontalMenuLayout);

            HorizontalLayout horizontalMenuLogoLayout = new HorizontalLayout();
            horizontalMenuLogoLayout.setClassName("horizontalMenuLogoLayout");
            horizontalMenuLayout.add(horizontalMenuLogoLayout);

            HorizontalLayout horizontalMenuButtonLayout = new HorizontalLayout();
            horizontalMenuButtonLayout.setClassName("horizontalMenuButtonLayout");
            horizontalMenuLayout.add(horizontalMenuButtonLayout);

            Button clientButton = new Button("Klienci");
            clientButton.setClassName("button");
            clientButton.addClickListener(btn -> {
                clientButton.getUI().ifPresent(ui ->
                        ui.navigate("/client"));
            });

            Button installationButton = new Button("Dodaj montaż");
            installationButton.setClassName("button");
            installationButton.addClickListener(btn -> {
                installationButton.getUI().ifPresent(ui ->
                        ui.navigate("/installation"));
            });

            Button failureButton = new Button("Dodaj awarię");
            failureButton.setClassName("button");
            failureButton.addClickListener(btn -> {
                failureButton.getUI().ifPresent(ui ->
                        ui.navigate("/failure"));
            });

            Button calendarInstallationButton = new Button("Montaże");
            calendarInstallationButton.setClassName("button");
            calendarInstallationButton.addClickListener(btn -> {
                calendarInstallationButton.getUI().ifPresent(ui ->
                        ui.navigate("/calendarInstallation"));
            });

            Button calendarFailureButton = new Button("Awarie");
            calendarFailureButton.setClassName("button");
            calendarFailureButton.addClickListener(btn -> {
                calendarFailureButton.getUI().ifPresent(ui ->
                        ui.navigate("/calendarFailure"));
            });

            Button logoutButton = new Button("Wyloguj");
            logoutButton.setClassName("button");
            logoutButton.addClickListener(btn -> {
                logoutButton.getUI().ifPresent(ui ->
                        ui.navigate("/login"));
                LoginToken.token = 0;
            });

            horizontalMenuButtonLayout.add(clientButton, installationButton, failureButton, calendarInstallationButton, calendarFailureButton, logoutButton);


        }

        HorizontalLayout horizontalClientAddPageLayout = new HorizontalLayout();
        horizontalClientAddPageLayout.setClassName("horizontalClientAddPageLayout");
        add(horizontalClientAddPageLayout);

        horizontalClientAddPageLayout.getElement().setAttribute("theme", Lumo.DARK);

        //formularz
        VerticalLayout verticalAddDataLayout = new VerticalLayout();
        verticalAddDataLayout.setClassName("verticalAddDataLayout");
        horizontalClientAddPageLayout.add(verticalAddDataLayout);

        HorizontalLayout horizontalAddDataLayout = new HorizontalLayout();
        horizontalAddDataLayout.setClassName("horizontalAddDataLayout");
        verticalAddDataLayout.add(horizontalAddDataLayout);


        TextField contractNumberField = new TextField("Numer umowy");
        contractNumberField.setClassName("contractNumberField");
//        contractNumberField.setRequiredIndicatorVisible(true);
        contractNumberField.setHelperText("Obowiązkowe");
        contractNumberField.getElement().setAttribute("theme", Lumo.DARK);

        TextField nameField = new TextField("Imię");
        nameField.setClassName("nameField");
//        nameField.setRequiredIndicatorVisible(true);
        nameField.setHelperText("Obowiązkowe");
        nameField.getElement().setAttribute("theme", Lumo.DARK);

        TextField surnameField = new TextField("Nazwisko");
        surnameField.setClassName("surnameField");
//        surnameField.setRequiredIndicatorVisible(true);
        surnameField.setHelperText("Obowiązkowe");
        surnameField.getElement().setAttribute("theme", Lumo.DARK);

        horizontalAddDataLayout.add(contractNumberField,nameField,surnameField);

        HorizontalLayout horizontalAddData1Layout = new HorizontalLayout();
        horizontalAddData1Layout.setClassName("horizontalAddData1Layout");
        verticalAddDataLayout.add(horizontalAddData1Layout);

        TextField streetNameField = new TextField("Ulica");
        streetNameField.setClassName("streetNameField");
        streetNameField.setHelperText("Obowiązkowe");
//        streetNameField.setRequiredIndicatorVisible(true);
        streetNameField.getElement().setAttribute("theme", Lumo.DARK);

        TextField buildingNumberField = new TextField("Numer budynku");
        buildingNumberField.setClassName("buildingNumberField");
//        buildingNumberField.setRequiredIndicatorVisible(true);
        buildingNumberField.setHelperText("Obowiązkowe");
        buildingNumberField.getElement().setAttribute("theme", Lumo.DARK);

        TextField flatNumberField = new TextField("Numer lokalu");
        flatNumberField.setClassName("flatNumberField");
        flatNumberField.getElement().setAttribute("theme", Lumo.DARK);

        horizontalAddData1Layout.add(streetNameField,buildingNumberField,flatNumberField);

        HorizontalLayout horizontalAddData2Layout = new HorizontalLayout();
        horizontalAddData2Layout.setClassName("horizontalAddData2Layout");
        verticalAddDataLayout.add(horizontalAddData2Layout);

        TextField numberPhoneField = new TextField("Numer telefonu");
        numberPhoneField.setClassName("numberPhoneField");
//        numberPhoneField.setRequired(true);
        numberPhoneField.setHelperText("Obowiązkowe");
        numberPhoneField.getElement().setAttribute("theme", Lumo.DARK);

        TextField emailField = new TextField("Email");
        emailField.setClassName("emailField");
        emailField.getElement().setAttribute("theme", Lumo.DARK);

        horizontalAddData2Layout.add(numberPhoneField,emailField);

        HorizontalLayout horizontalAddData3Layout = new HorizontalLayout();
        horizontalAddData3Layout.setClassName("horizontalAddData3Layout");
        verticalAddDataLayout.add(horizontalAddData3Layout);

        Button cancelButton = new Button("Anuluj");
        cancelButton.setClassName("cancelButton");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        horizontalAddData3Layout.add(cancelButton);

        cancelButton.addClickListener(btn -> {
            cancelButton.getUI().ifPresent(ui ->
                    ui.navigate("/client"));
        });

        Button saveButton = new Button("Zapisz");
        saveButton.setClassName("saveButton");
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        horizontalAddData3Layout.add(saveButton);

        saveButton.addClickListener(btn -> {
            if (contractNumberField.getValue().isEmpty() || nameField.getValue().isEmpty() || surnameField.getValue().isEmpty() || streetNameField.getValue().isEmpty() || buildingNumberField.getValue().isEmpty() || numberPhoneField.getValue().isEmpty()) {
                Notification.show("Uzupełnij wszystkie pola obowiązkowe");
            } else if (!nameField.getValue().equals("") && !surnameField.getValue().equals("") && !contractNumberField.getValue().equals("") && !streetNameField.getValue().equals("") && !buildingNumberField.getValue().equals("") && !numberPhoneField.getValue().equals("")) {
//                    timePicker.getStyle().set("color", "default");

                resCon.saveClient(contractNumberField.getValue(), nameField.getValue(), surnameField.getValue(), streetNameField.getValue() ,buildingNumberField.getValue(), flatNumberField.getValue(), numberPhoneField.getValue(), emailField.getValue());
                Notification.show("Dane zostały zapisane");
                nameField.clear();
                surnameField.clear();
                contractNumberField.clear();
                streetNameField.clear();
                buildingNumberField.clear();
                flatNumberField.clear();
                emailField.clear();
                numberPhoneField.clear();
            }
        });
    }
}