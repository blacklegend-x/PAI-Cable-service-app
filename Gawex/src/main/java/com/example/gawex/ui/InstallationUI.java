package com.example.gawex.ui;

import com.example.gawex.controller.ResCon;
import com.example.gawex.entity.Installation;
import com.example.gawex.service.ClientService;
import com.example.gawex.service.FailureService;
import com.example.gawex.service.InstallationService;
import com.example.gawex.service.TypeInstallationService;
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

@PageTitle("Installation")
@Route("installation")
@CssImport("./installationPage.css")

public class InstallationUI extends VerticalLayout {

    @Autowired
    ClientService clientService;

    @Autowired
    ResCon resCon;

    @Autowired
    InstallationService installationService;

    @Autowired
    TypeInstallationService typeInstallationService;

    @Autowired
    FailureService failureService;


    @PostConstruct
    public void init(){

        if(LoginToken.token == 0){
            Notification.show("Zaloguj się!");
            LoginToken.token = 0;
            UI.getCurrent().getPage().setLocation("/login");
        }

        setClassName("installationUI");

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

        HorizontalLayout horizontalInstallationAddPageLayout = new HorizontalLayout();
        horizontalInstallationAddPageLayout.setClassName("horizontalInstallationAddPageLayout");
        add(horizontalInstallationAddPageLayout);

        horizontalInstallationAddPageLayout.getElement().setAttribute("theme", Lumo.DARK);

        //formularz
        VerticalLayout verticalDataLayout = new VerticalLayout();
        verticalDataLayout.setClassName("verticalDataLayout");
        horizontalInstallationAddPageLayout.add(verticalDataLayout);

        HorizontalLayout horizontalDataLayout = new HorizontalLayout();
        horizontalDataLayout.setClassName("horizontalDataLayout");
        verticalDataLayout.add(horizontalDataLayout);

        TextField contractNumberField = new TextField("Numer umowy");
        contractNumberField.setClassName("contractNumberField");
        contractNumberField.getElement().setAttribute("theme", Lumo.DARK);

        TextField nameField = new TextField("Imię");
        nameField.setClassName("nameField");
        nameField.getElement().setAttribute("theme", Lumo.DARK);

        TextField surnameField = new TextField("Nazwisko");
        surnameField.setClassName("surnameField");
        surnameField.getElement().setAttribute("theme", Lumo.DARK);

        horizontalDataLayout.add(contractNumberField,nameField,surnameField);

        HorizontalLayout horizontalData1Layout = new HorizontalLayout();
        horizontalData1Layout.setClassName("horizontalData1Layout");
        verticalDataLayout.add(horizontalData1Layout);

        TextField streetNameField = new TextField("Ulica");
        streetNameField.setClassName("streetNameField");
        streetNameField.getElement().setAttribute("theme", Lumo.DARK);

        TextField buildingNumberField = new TextField("Numer budynku");
        buildingNumberField.setClassName("buildingNumberField");
        buildingNumberField.getElement().setAttribute("theme", Lumo.DARK);

        TextField flatNumberField = new TextField("Numer lokalu");
        flatNumberField.setClassName("flatNumberField");
        flatNumberField.getElement().setAttribute("theme", Lumo.DARK);

        horizontalData1Layout.add(streetNameField,buildingNumberField,flatNumberField);

        HorizontalLayout horizontalData2Layout = new HorizontalLayout();
        horizontalData2Layout.setClassName("horizontalData2Layout");
        verticalDataLayout.add(horizontalData2Layout);

        TextField numberPhoneField = new TextField("Numer telefonu");
        numberPhoneField.setClassName("streetNameField");
        numberPhoneField.getElement().setAttribute("theme", Lumo.DARK);

        Checkbox isBuildingCheckbox = new Checkbox("Awaria na budynku");
        isBuildingCheckbox.setLabel("Montaż na budynku");
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

        DatePicker datePicker = new DatePicker("Wybierz datę montażu");
        datePicker.setI18n(singleFormatI18n);
        datePicker.setClassName("datePicker");
        datePicker.setI18n(new DatePicker.DatePickerI18n().setFirstDayOfWeek(1));

        TimePicker timePicker = new TimePicker();
        timePicker.setLabel("Wybierz godzinę montażu");
        timePicker.setClassName("timePicker");
        timePicker.setStep(Duration.ofMinutes(30));
        timePicker.setMinTime(LocalTime.of(8, 0));
        timePicker.setMaxTime(LocalTime.of(16, 0));
        horizontalData3Layout.add(datePicker, timePicker);

        HorizontalLayout horizontalData4Layout = new HorizontalLayout();
        horizontalData4Layout.setClassName("horizontalData4Layout");
        verticalDataLayout.add(horizontalData4Layout);

        ComboBox<String> typeInstallationComboBox = new ComboBox<>("Typ montażu");
        typeInstallationComboBox.setClassName("typeInstallationComboBox");
        typeInstallationComboBox.setItems(typeInstallationService.getTypeInstallation());

        Button addInstallationButton = new Button("Dodaj montaż");
        addInstallationButton.setClassName("addInstallationButton");
        addInstallationButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        horizontalData4Layout.add(typeInstallationComboBox, addInstallationButton);

        addInstallationButton.addClickListener(btn -> {
            if(contractNumberField.getValue().isEmpty() || nameField.getValue().isEmpty() || surnameField.getValue().isEmpty() || streetNameField.getValue().isEmpty() || buildingNumberField.getValue().isEmpty() || numberPhoneField.getValue().isEmpty() || typeInstallationComboBox.isEmpty() || datePicker.isEmpty() || timePicker.isEmpty()){
                Notification.show("Uzupełnij wszystkie pola obowiązkowe");
            }else if(!nameField.getValue().equals("") && !surnameField.getValue().equals("") && !contractNumberField.getValue().equals("") && !streetNameField.getValue().equals("") && !buildingNumberField.getValue().equals("") && !numberPhoneField.getValue().equals("") && !datePicker.getValue().toString().equals("") && !timePicker.getValue().toString().equals("") && !typeInstallationComboBox.getValue().equals("")){

                if (installationService.getByDateTime(Date.valueOf(datePicker.getValue()), Time.valueOf(timePicker.getValue())) != null || failureService.getByDateTime(Date.valueOf(datePicker.getValue()), Time.valueOf(timePicker.getValue())) != null){
                    Notification.show("Wybierz inny termin");
                    timePicker.clear();
                }else{
                    resCon.date= Date.valueOf(datePicker.getValue());
                    resCon.time= Time.valueOf(timePicker.getValue());
                    resCon.typeInstallation=typeInstallationComboBox.getValue();
                    resCon.saveInstallation(resCon.typeInstallation, resCon.date, resCon.time, resCon.isBuilding, contractNumberField.getValue(), nameField.getValue(), surnameField.getValue(), buildingNumberField.getValue(), flatNumberField.getValue(), streetNameField.getValue(), numberPhoneField.getValue());
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
                    typeInstallationComboBox.setValue("");
                }


            }

        });

        VerticalLayout verticalGridLayout = new VerticalLayout();
        verticalGridLayout.setClassName("verticalGridLayout");
        horizontalInstallationAddPageLayout.add(verticalGridLayout);

        Grid<Installation> installationGrid = new Grid<>(Installation.class, false);
        installationGrid.setClassName("installationGrid");
        installationGrid.addColumn(Installation::getContractNumber).setHeader("Umowa").setSortable(true);
        installationGrid.addColumn(Installation::getName).setHeader("Imię").setSortable(true);
        installationGrid.addColumn(Installation::getSurname).setHeader("Nazwisko").setSortable(true);
        installationGrid.addColumn(Installation::getDate).setHeader("Data").setSortable(true);
        installationGrid.addColumn(Installation::getTime).setHeader("Czas").setSortable(true);
        installationGrid.addColumn(Installation::getTypeInstallation).setHeader("Typ").setSortable(true);

        installationGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        List<Installation> installationList = installationService.getAll();
        installationGrid.setItems(installationList);
        verticalGridLayout.add(installationGrid);


    }
}
