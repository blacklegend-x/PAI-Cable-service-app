package com.example.gawex.ui;

import com.example.gawex.controller.ResCon;
import com.example.gawex.entity.Failure;
import com.example.gawex.service.ClientService;
import com.example.gawex.service.FailureService;
import com.example.gawex.service.InstallationService;
import com.example.gawex.service.TypeFailureService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@PageTitle("Failure")
@Route("failure")
@CssImport("./failurePage.css")

public class FailureUI extends VerticalLayout {

    @Autowired
    ClientService clientService;

    @Autowired
    TypeFailureService typeFailureService;

    @Autowired
    ResCon resCon;

    @Autowired
    FailureService failureService;

    @Autowired
    InstallationService installationService;


    @PostConstruct
    public void init(){

        setSizeFull();

        if(LoginToken.token == 0){
            Notification.show("Zaloguj się!");
            LoginToken.token = 0;
            UI.getCurrent().getPage().setLocation("/login");
        }


        setClassName("failureUI");


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

            failureButton.setClassName("buttonactive");

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

        HorizontalLayout horizontalFailureAddPageLayout = new HorizontalLayout();
        horizontalFailureAddPageLayout.setClassName("horizontalFailureAddPageLayout");
        add(horizontalFailureAddPageLayout);

        horizontalFailureAddPageLayout.getElement().setAttribute("theme", Lumo.DARK);

        //formularz
        VerticalLayout verticalDataLayout = new VerticalLayout();
        verticalDataLayout.setClassName("verticalDataLayout");
        horizontalFailureAddPageLayout.add(verticalDataLayout);

        HorizontalLayout horizontalDataLayout = new HorizontalLayout();
        horizontalDataLayout.setClassName("horizontalDataLayout");
        verticalDataLayout.add(horizontalDataLayout);

        TextField contractNumberField = new TextField("Numer umowy");
        contractNumberField.setClassName("contractNumberField");
        contractNumberField.setHelperText("Obowiązkowe");
        contractNumberField.getElement().setAttribute("theme", Lumo.DARK);

        TextField nameField = new TextField("Imię");
        nameField.setClassName("nameField");
        nameField.setHelperText("Obowiązkowe");
        nameField.getElement().setAttribute("theme", Lumo.DARK);

        TextField surnameField = new TextField("Nazwisko");
        surnameField.setClassName("surnameField");
        surnameField.setHelperText("Obowiązkowe");
        surnameField.getElement().setAttribute("theme", Lumo.DARK);

        horizontalDataLayout.add(contractNumberField,nameField,surnameField);

        HorizontalLayout horizontalData1Layout = new HorizontalLayout();
        horizontalData1Layout.setClassName("horizontalData1Layout");
        verticalDataLayout.add(horizontalData1Layout);

        TextField streetNameField = new TextField("Ulica");
        streetNameField.setClassName("streetNameField");
        streetNameField.setHelperText("Obowiązkowe");
        streetNameField.getElement().setAttribute("theme", Lumo.DARK);

        TextField buildingNumberField = new TextField("Numer budynku");
        buildingNumberField.setClassName("buildingNumberField");
        buildingNumberField.setHelperText("Obowiązkowe");
        buildingNumberField.getElement().setAttribute("theme", Lumo.DARK);

        TextField flatNumberField = new TextField("Numer lokalu");
        flatNumberField.setClassName("flatNumberField");
        flatNumberField.getElement().setAttribute("theme", Lumo.DARK);

        horizontalData1Layout.add(streetNameField,buildingNumberField,flatNumberField);

        HorizontalLayout horizontalData2Layout = new HorizontalLayout();
        horizontalData2Layout.setClassName("horizontalData2Layout");
        verticalDataLayout.add(horizontalData2Layout);

        TextField numberPhoneField = new TextField("Numer telefonu");
        numberPhoneField.setClassName("numberPhoneField");
        numberPhoneField.setHelperText("Obowiązkowe");
        numberPhoneField.getElement().setAttribute("theme", Lumo.DARK);

        Checkbox isBuildingCheckbox = new Checkbox("Awaria na budynku");
        isBuildingCheckbox.setLabel("Awaria na budynku");
        isBuildingCheckbox.setClassName("isBuildingCheckbox");

        isBuildingCheckbox.addValueChangeListener(box ->{
            if (isBuildingCheckbox.getValue()==true){
                resCon.isBuilding="tak";
            }else if (isBuildingCheckbox.getValue()==false){
                resCon.isBuilding="nie";
            }
        });

        horizontalData2Layout.add(numberPhoneField, isBuildingCheckbox);

        HorizontalLayout horizontalData3Layout = new HorizontalLayout();
        horizontalData3Layout.setClassName("horizontalData3Layout");
        verticalDataLayout.add(horizontalData3Layout);

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd. MM. yyyy");

        DatePicker datePicker = new DatePicker("Wybierz datę naprawy");
        datePicker.setI18n(singleFormatI18n);
        datePicker.setClassName("datePicker");
        datePicker.setHelperText("Obowiązkowe");
        datePicker.setI18n(new DatePicker.DatePickerI18n().setFirstDayOfWeek(1));

        TimePicker timePicker = new TimePicker();
        timePicker.setLabel("Wybierz godzinę naprawy");
        timePicker.setClassName("timePicker");
        timePicker.setHelperText("Obowiązkowe");
        timePicker.setStep(Duration.ofMinutes(30));
        timePicker.setMinTime(LocalTime.of(8, 0));
        timePicker.setMaxTime(LocalTime.of(16, 0));


        horizontalData3Layout.add(datePicker, timePicker);

        HorizontalLayout horizontalData4Layout = new HorizontalLayout();
        horizontalData4Layout.setClassName("horizontalData4Layout");
        verticalDataLayout.add(horizontalData4Layout);

        ComboBox<String> typeFailureComboBox = new ComboBox<>("Typ awarii");
        typeFailureComboBox.setClassName("typeInstallationComboBox");
        typeFailureComboBox.setHelperText("Obowiązkowe");
        typeFailureComboBox.setItems(typeFailureService.getTypeFailure());


        Button addFailureButton = new Button("Dodaj awarie");
        addFailureButton.setClassName("addFailureButton");
        addFailureButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        horizontalData4Layout.add(typeFailureComboBox, addFailureButton);

        addFailureButton.addClickListener(btn -> {
            if(contractNumberField.getValue().isEmpty() || nameField.getValue().isEmpty() || surnameField.getValue().isEmpty() || streetNameField.getValue().isEmpty() || buildingNumberField.getValue().isEmpty() || numberPhoneField.getValue().isEmpty() || typeFailureComboBox.isEmpty() || datePicker.isEmpty() || timePicker.isEmpty()){
                Notification.show("Uzupełnij wszystkie pola obowiązkowe");
            }else if(!nameField.getValue().equals("") && !surnameField.getValue().equals("") && !contractNumberField.getValue().equals("") && !streetNameField.getValue().equals("") && !buildingNumberField.getValue().equals("") && !numberPhoneField.getValue().equals("") && !datePicker.getValue().toString().equals("") && !timePicker.getValue().toString().equals("") && !typeFailureComboBox.getValue().equals("")){


                if (failureService.getByDateTime(Date.valueOf(datePicker.getValue()), Time.valueOf(timePicker.getValue())) != null || installationService.getByDateTime(Date.valueOf(datePicker.getValue()), Time.valueOf(timePicker.getValue())) != null){
                    Notification.show("Wybierz inny termin");
                    timePicker.clear();
                }else {
                    resCon.date= Date.valueOf(datePicker.getValue());
                    resCon.time= Time.valueOf(timePicker.getValue());
                    resCon.typeFailure=typeFailureComboBox.getValue();
                    resCon.saveFailure(resCon.typeFailure, resCon.date, resCon.time, resCon.isBuilding, contractNumberField.getValue(), nameField.getValue(), surnameField.getValue(), buildingNumberField.getValue(), flatNumberField.getValue(), streetNameField.getValue(), numberPhoneField.getValue());
                    Notification.show("Dane zostały zapisane");
                    nameField.clear();
                    surnameField.clear();
                    contractNumberField.clear();
                    streetNameField.clear();
                    buildingNumberField.clear();
                    flatNumberField.clear();
                    isBuildingCheckbox.clear();
                    numberPhoneField.clear();

                    datePicker.clear();
                    timePicker.clear();
                    typeFailureComboBox.setValue("");
                }

            }

        });

        VerticalLayout verticalGridLayout = new VerticalLayout();
        verticalGridLayout.setClassName("verticalGridLayout");
        horizontalFailureAddPageLayout.add(verticalGridLayout);

        Grid<Failure> failureGrid = new Grid<>(Failure.class, false);
        failureGrid.setClassName("failureGrid");
        failureGrid.addColumn(Failure::getContractNumber).setHeader("Umowa").setSortable(true);
        failureGrid.addColumn(Failure::getName).setHeader("Imię").setSortable(true);
        failureGrid.addColumn(Failure::getSurname).setHeader("Nazwisko").setSortable(true);
        failureGrid.addColumn(Failure::getDate).setHeader("Data").setSortable(true);
        failureGrid.addColumn(Failure::getTime).setHeader("Czas").setSortable(true);
        failureGrid.addColumn(Failure::getTypeFailure).setHeader("Typ").setSortable(true);

        failureGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        List<Failure> failureList = failureService.getAll();
        failureGrid.setItems(failureList);
        verticalGridLayout.add(failureGrid);


    }
}
