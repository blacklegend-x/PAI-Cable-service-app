package com.example.gawex.ui;

import com.example.gawex.controller.ResCon;
import com.example.gawex.entity.Installation;
import com.example.gawex.service.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@PageTitle("Calendar Installation")
@Route("calendarInstallation")
@CssImport("./calendarInstallationPage.css")

public class CalendarInstallationUI extends VerticalLayout {

    @Autowired
    InstallationService installationService;

    @Autowired
    TypeInstallationService typeInstallationService;

    @Autowired
    FailureService failureService;

//    @Autowired
//    TypeFailureService typeFailureService;

    @Autowired
    ResCon resCon;



    @PostConstruct
    public void init(){

        if(LoginToken.token == 0){
            Notification.show("Zaloguj się!");
            LoginToken.token = 0;
            UI.getCurrent().getPage().setLocation("/login");
        }

        setClassName("calendarInstallationUI");

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

        //wyszukiwarka
        HorizontalLayout horizontalSearchInstallationLayout = new HorizontalLayout();
        horizontalSearchInstallationLayout.setClassName("horizontalSearchInstallationLayout");
        add(horizontalSearchInstallationLayout);

        TextField searchInGrid = new TextField();
        searchInGrid.setPlaceholder("Wyszukaj");
        searchInGrid.setClassName("searchInGrid");
        searchInGrid.getElement().setAttribute("theme", Lumo.DARK);
        searchInGrid.setPrefixComponent(VaadinIcon.SEARCH.create());
        horizontalSearchInstallationLayout.add(searchInGrid);


        HorizontalLayout horizontalGridInstallationLayout = new HorizontalLayout();
        horizontalGridInstallationLayout.setClassName("horizontalGridInstallationLayout");
        add(horizontalGridInstallationLayout);

        ValidationMessage contractNumberValidationMessage = new ValidationMessage();
        ValidationMessage nameValidationMessage = new ValidationMessage();
        ValidationMessage surnameValidationMessage = new ValidationMessage();
        ValidationMessage streetNameValidationMessage = new ValidationMessage();
        ValidationMessage buildingNumberValidationMessage = new ValidationMessage();
        ValidationMessage flatNumberValidationMessage = new ValidationMessage();
        ValidationMessage typeValidationMessage = new ValidationMessage();
        ValidationMessage numberPhoneValidationMessage = new ValidationMessage();
        ValidationMessage dateValidationMessage = new ValidationMessage();
        ValidationMessage timeValidationMessage = new ValidationMessage();
        ValidationMessage isBuildingValidationMessage = new ValidationMessage();
        ValidationMessage statusValidationMessage = new ValidationMessage();


        Grid<Installation> installationGrid = new Grid<>(Installation.class, false);
        Editor<Installation> editor = installationGrid.getEditor();

        Text chosenInstallationId = new Text("");

        installationGrid.setClassName("installationGrid");
        Grid.Column<Installation> contractNumberColumn = installationGrid.addColumn(Installation::getContractNumber).setHeader("umowa").setSortable(true).setResizable(true);
        Grid.Column<Installation> nameColumn = installationGrid.addColumn(Installation::getName).setHeader("Imię").setSortable(true);
        Grid.Column<Installation> surnameColumn = installationGrid.addColumn(Installation::getSurname).setHeader("Nazwisko").setSortable(true);
        Grid.Column<Installation> streetNameColumn = installationGrid.addColumn(Installation::getStreetName).setHeader("Ulica").setSortable(true);
        Grid.Column<Installation> buildingNumberColumn = installationGrid.addColumn(Installation::getBuildingNumber).setHeader("Numer budynku").setSortable(true);
        Grid.Column<Installation> flatNumberColumn = installationGrid.addColumn(Installation::getFlatNumber).setHeader("Numer lokalu").setSortable(true);
        Grid.Column<Installation> numberPhoneColumn = installationGrid.addColumn(Installation::getNumberPhone).setHeader("Telefon").setSortable(true);
        Grid.Column<Installation> isBuldingColumn = installationGrid.addColumn(Installation::getIsBuilding).setHeader("Czy budynek").setSortable(true);
        Grid.Column<Installation> dateColumn = installationGrid.addColumn(Installation::getDate).setHeader("Data montażu").setSortable(true);
        Grid.Column<Installation> timeColumn = installationGrid.addColumn(Installation::getTime).setHeader("Czas montażu").setSortable(true);
        Grid.Column<Installation> typeInstallationColumn = installationGrid.addColumn(Installation::getTypeInstallation).setHeader("Typ montażu").setSortable(true);
        Grid.Column<Installation> statusColumn = installationGrid.addColumn(Installation::getStatus).setHeader("Status").setSortable(true);

        Grid.Column<Installation> editColumn =  installationGrid.addComponentColumn(installation -> {
            Button editButton = new Button("Edytuj");
            editButton.addClickListener(e->{
                if (editor.isOpen())
                    editor.cancel();
                installationGrid.getEditor().editItem(installation);
                chosenInstallationId.setText(installation.getId().toString());

            });
            return editButton;
        });

        Binder<Installation> binder = new Binder<>(Installation.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField contractNumberField = new TextField();
        contractNumberField.setWidthFull();
        binder.forField(contractNumberField)
                .asRequired("Nie może być puste")
                .withStatusLabel(contractNumberValidationMessage)
                .bind(Installation::getContractNumber, Installation::setContractNumber);
        contractNumberColumn.setEditorComponent(contractNumberField);

        TextField nameField = new TextField();
        nameField.setWidthFull();
        binder.forField(nameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(nameValidationMessage)
                .bind(Installation::getName, Installation::setName);
        nameColumn.setEditorComponent(nameField);

        TextField surnameField = new TextField();
        surnameField.setWidthFull();
        binder.forField(surnameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(surnameValidationMessage)
                .bind(Installation::getSurname, Installation::setSurname);
        surnameColumn.setEditorComponent(surnameField);

        TextField streetNameField = new TextField();
        streetNameField.setWidthFull();
        binder.forField(streetNameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(streetNameValidationMessage)
                .bind(Installation::getStreetName, Installation::setStreetName);
        streetNameColumn.setEditorComponent(streetNameField);

        TextField buildingNumberField = new TextField();
        buildingNumberField.setWidthFull();
        binder.forField(buildingNumberField)
                .asRequired("Nie może być puste")
                .withStatusLabel(buildingNumberValidationMessage)
                .bind(Installation::getBuildingNumber, Installation::setBuildingNumber);
        buildingNumberColumn.setEditorComponent(buildingNumberField);

        TextField flatNumberField = new TextField();
        flatNumberField.setWidthFull();
        binder.forField(flatNumberField)
//                .asRequired("Nie może być puste")
                .withStatusLabel(flatNumberValidationMessage)
                .bind(Installation::getFlatNumber, Installation::setFlatNumber);
        flatNumberColumn.setEditorComponent(flatNumberField);

        TextField numberPhoneField = new TextField();
        numberPhoneField.setWidthFull();
        binder.forField(numberPhoneField)
                .asRequired("Nie może być puste")
                .withStatusLabel(numberPhoneValidationMessage)
                .bind(Installation::getNumberPhone, Installation::setNumberPhone);
        numberPhoneColumn.setEditorComponent(numberPhoneField);

        ComboBox<String> typeInstallationComboBox = new ComboBox<>();
        typeInstallationComboBox.setItems(typeInstallationService.getTypeInstallation());
        typeInstallationComboBox.setWidthFull();
        binder.forField(typeInstallationComboBox)
                .asRequired("Nie może być puste")
                .withStatusLabel(typeValidationMessage)
                .bind(Installation::getTypeInstallation, Installation::setTypeInstallation);
        typeInstallationColumn.setEditorComponent(typeInstallationComboBox);

        ComboBox<String> isBuildingInstallationComboBox = new ComboBox<>();
        isBuildingInstallationComboBox.setItems("tak","nie");
        isBuildingInstallationComboBox.setWidthFull();
        binder.forField(isBuildingInstallationComboBox)
                .asRequired("Nie może być puste")
                .withStatusLabel(typeValidationMessage)
                .bind(Installation::getIsBuilding, Installation::setIsBuilding);
        isBuldingColumn.setEditorComponent(isBuildingInstallationComboBox);


        ComboBox<String> statusInstallationComboBox = new ComboBox<>();
        statusInstallationComboBox.setItems("Przyjęto", "W realizacji", "Zrealizowano");
        statusInstallationComboBox.setWidthFull();
        binder.forField(statusInstallationComboBox)
                .asRequired("Nie może być puste")
                .withStatusLabel(typeValidationMessage)
                .bind(Installation::getStatus, Installation::setStatus);
        statusColumn.setEditorComponent(statusInstallationComboBox);


        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd. MM. yyyy");
        DatePicker datePickerField = new DatePicker();
        datePickerField.setI18n(singleFormatI18n);
        datePickerField.setClassName("datePicker");
        datePickerField.setI18n(new DatePicker.DatePickerI18n().setFirstDayOfWeek(1));
        datePickerField.setWidthFull();
        binder.forField(datePickerField).withConverter(new LocalDateToSqlDateConverter())
                .asRequired("Nie może być puste")
                .withStatusLabel(dateValidationMessage)
                .bind(Installation::getDate,Installation::setDate);
        dateColumn.setEditorComponent(datePickerField);


        TimePicker timePickerField = new TimePicker();
        timePickerField.setClassName("timePicker");
        timePickerField.setStep(Duration.ofMinutes(30));
        timePickerField.setMinTime(LocalTime.of(8, 0));
        timePickerField.setMaxTime(LocalTime.of(16, 0));
        timePickerField.setWidthFull();
        binder.forField(timePickerField).withConverter(new LocalTimeToSqlTimeConverter())
                .asRequired("Nie może być puste")
                .withStatusLabel(timeValidationMessage)
                .bind(Installation::getTime,Installation::setTime);
        timeColumn.setEditorComponent(timePickerField);



        Button saveButton = new Button("Zapisz", e -> editor.save());
        saveButton.addClickListener(btn->
                resCon.updateInstallation(chosenInstallationId.getText(),contractNumberField.getValue(), nameField.getValue(), surnameField.getValue(), streetNameField.getValue(), buildingNumberField.getValue(), flatNumberField.getValue(), numberPhoneField.getValue(), typeInstallationComboBox.getValue(),  timePickerField.getValue(), datePickerField.getValue(), isBuildingInstallationComboBox.getValue(),statusInstallationComboBox.getValue()));
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e->editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);

        editor.addCancelListener(e -> {
            contractNumberValidationMessage.setText("");
            nameValidationMessage.setText("");
            surnameValidationMessage.setText("");
            streetNameValidationMessage.setText("");
            buildingNumberValidationMessage.setText("");
            flatNumberValidationMessage.setText("");
            typeValidationMessage.setText("");
            numberPhoneValidationMessage.setText("");
            isBuildingValidationMessage.setText("");
            statusValidationMessage.setText("");
        });


        installationGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        List<Installation> installationList = installationService.getAll();
        GridListDataView<Installation> dataView = installationGrid.setItems(installationList);
        installationGrid.setItems(installationList);


        horizontalGridInstallationLayout.add(contractNumberValidationMessage, nameValidationMessage,surnameValidationMessage, streetNameValidationMessage, buildingNumberValidationMessage, flatNumberValidationMessage, numberPhoneValidationMessage);

        ComboBox<String> streetComboBox = new ComboBox<>();
        streetComboBox.setClassName("streetComboBox");
        streetComboBox.setPlaceholder("Ulica");

        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setClassName("typeComboBox");
        typeComboBox.setPlaceholder("Typ montażu");

        //STREET COMBO BOX
        streetComboBox.setItems(installationService.getStreetNames());
        streetComboBox.addValueChangeListener(e -> dataView.refreshAll());
        streetComboBox.addValueChangeListener(event -> {
            List<Installation> filteredInstallations = installationService.getAll();
            filteredInstallations = filteredInstallations.stream().filter(installation -> installation.getStreetName().equals(streetComboBox.getValue())).toList();
            if(!typeComboBox.isEmpty()){
                filteredInstallations = filteredInstallations.stream().filter(installation -> installation.getTypeInstallation().equals(typeComboBox.getValue())).toList();
            }
            installationGrid.setItems(filteredInstallations);
            if (!searchInGrid.isEmpty()) {
                dataView.addFilter(installation -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(installation.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(installation.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(installation.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(installation.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(installation.getBuildingNumber(),
                            searchTerm);
                    boolean matchesTypeFailure = matchesTerm(installation.getTypeInstallation(),
                            searchTerm);
                    return matchesContractNumber || matchesName || matchesSurname || matchesStreetName || matchesBuildingNumber || matchesTypeFailure;
                });
            }
        });

        //TYPE COMBO BOX
        typeComboBox.setItems(installationService.getTypes());
        typeComboBox.addValueChangeListener(e -> dataView.refreshAll());
        typeComboBox.addValueChangeListener(event -> {
            List<Installation> filteredInstallations = installationService.getAll();
            filteredInstallations = filteredInstallations.stream().filter(installation -> installation.getTypeInstallation().equals(typeComboBox.getValue())).toList();
            if (!streetComboBox.isEmpty()) {
                filteredInstallations = filteredInstallations.stream().filter(installation -> installation.getStreetName().equals(streetComboBox.getValue())).toList();
            }
            installationGrid.setItems(filteredInstallations);
            if (!searchInGrid.isEmpty()) {
                dataView.addFilter(installation -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(installation.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(installation.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(installation.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(installation.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(installation.getBuildingNumber(),
                            searchTerm);
                    boolean matchesTypeFailure = matchesTerm(installation.getTypeInstallation(),
                            searchTerm);
                    return matchesContractNumber || matchesName || matchesSurname || matchesStreetName || matchesBuildingNumber || matchesTypeFailure;
                });
            }
        });

        Button resetButton = new Button("Resetuj");
        resetButton.setClassName("resetButton");
        resetButton.addClickListener(btn-> {
            typeComboBox.clear();
            streetComboBox.clear();
            searchInGrid.clear();
            installationGrid.setItems(installationList);

        });

        horizontalSearchInstallationLayout.add(streetComboBox, typeComboBox, resetButton);

        //TEXTBOX
        searchInGrid.setValueChangeMode(ValueChangeMode.EAGER);
        searchInGrid.addValueChangeListener(e -> dataView.refreshAll());
        searchInGrid.addValueChangeListener(e -> {
            if (!searchInGrid.isEmpty()) {
                dataView.addFilter(installation -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(installation.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(installation.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(installation.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(installation.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(installation.getBuildingNumber(),
                            searchTerm);
                    boolean matchesTypeFailure = matchesTerm(installation.getTypeInstallation(),
                            searchTerm);
                    return matchesContractNumber || matchesName || matchesSurname || matchesStreetName || matchesBuildingNumber || matchesTypeFailure;
                });
            }
        });

        horizontalGridInstallationLayout.add(installationGrid);

    }

    private boolean matchesTerm(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }



}
