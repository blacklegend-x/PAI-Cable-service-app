package com.example.gawex.ui;

import com.example.gawex.controller.ResCon;
import com.example.gawex.entity.Failure;
import com.example.gawex.service.FailureService;
import com.example.gawex.service.LocalDateToSqlDateConverter;
import com.example.gawex.service.TypeFailureService;
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

@PageTitle("Calendar Failure")
@Route("calendarFailure")
@CssImport("./calendarFailurePage.css")
public class CalendarFailureUI extends VerticalLayout {

    @Autowired
    FailureService failureService;

    @Autowired
    TypeFailureService typeFailureService;

    @Autowired
    ResCon resCon;


    @PostConstruct
    public void init() {

        if (LoginToken.token == 0) {
            Notification.show("Zaloguj się!");
            LoginToken.token = 0;
            UI.getCurrent().getPage().setLocation("/login");
        }

        setClassName("calendarFailureUI");

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
        HorizontalLayout horizontalSearchFailureLayout = new HorizontalLayout();
        horizontalSearchFailureLayout.setClassName("horizontalSearchFailureLayout");
        add(horizontalSearchFailureLayout);

        TextField searchInGrid = new TextField();
        searchInGrid.setPlaceholder("Wyszukaj");
        searchInGrid.setClassName("searchInGrid");
        searchInGrid.getElement().setAttribute("theme", Lumo.DARK);
        searchInGrid.setPrefixComponent(VaadinIcon.SEARCH.create());
        horizontalSearchFailureLayout.add(searchInGrid);


        HorizontalLayout horizontalGridFailureLayout = new HorizontalLayout();
        horizontalGridFailureLayout.setClassName("horizontalGridFailureLayout");
        add(horizontalGridFailureLayout);

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


        Grid<Failure> failureGrid = new Grid<>(Failure.class, false);
        Editor<Failure> editor = failureGrid.getEditor();

        Text chosenFailureId = new Text("");

        failureGrid.setClassName("failureGrid");
        Grid.Column<Failure> contractNumberColumn = failureGrid.addColumn(Failure::getContractNumber).setHeader("umowa").setSortable(true).setResizable(true);
        Grid.Column<Failure> nameColumn = failureGrid.addColumn(Failure::getName).setHeader("Imię").setSortable(true);
        Grid.Column<Failure> surnameColumn = failureGrid.addColumn(Failure::getSurname).setHeader("Nazwisko").setSortable(true);
        Grid.Column<Failure> streetNameColumn = failureGrid.addColumn(Failure::getStreetName).setHeader("Ulica").setSortable(true);
        Grid.Column<Failure> buildingNumberColumn = failureGrid.addColumn(Failure::getBuildingNumber).setHeader("Numer budynku").setSortable(true);
        Grid.Column<Failure> flatNumberColumn = failureGrid.addColumn(Failure::getFlatNumber).setHeader("Numer lokalu").setSortable(true);
        Grid.Column<Failure> numberPhoneColumn = failureGrid.addColumn(Failure::getNumberPhone).setHeader("Telefon").setSortable(true);
        Grid.Column<Failure> isBuldingColumn = failureGrid.addColumn(Failure::getIsBuilding).setHeader("Czy budynek").setSortable(true);
        Grid.Column<Failure> dateColumn = failureGrid.addColumn(Failure::getDate).setHeader("Data montażu").setSortable(true);
        Grid.Column<Failure> timeColumn = failureGrid.addColumn(Failure::getTime).setHeader("Czas montażu").setSortable(true);
        Grid.Column<Failure> typeFailureColumn = failureGrid.addColumn(Failure::getTypeFailure).setHeader("Typ montażu").setSortable(true);
        Grid.Column<Failure> statusColumn = failureGrid.addColumn(Failure::getStatus).setHeader("Status").setSortable(true);


        Grid.Column<Failure> editColumn = failureGrid.addComponentColumn(failure -> {
            Button editButton = new Button("Edytuj");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                failureGrid.getEditor().editItem(failure);
                chosenFailureId.setText(failure.getId().toString());

            });
            return editButton;
        });

        Binder<Failure> binder = new Binder<>(Failure.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField contractNumberField = new TextField();
        contractNumberField.setWidthFull();
        binder.forField(contractNumberField)
                .asRequired("Nie może być puste")
                .withStatusLabel(contractNumberValidationMessage)
                .bind(Failure::getContractNumber, Failure::setContractNumber);
        contractNumberColumn.setEditorComponent(contractNumberField);

        TextField nameField = new TextField();
        nameField.setWidthFull();
        binder.forField(nameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(nameValidationMessage)
                .bind(Failure::getName, Failure::setName);
        nameColumn.setEditorComponent(nameField);

        TextField surnameField = new TextField();
        surnameField.setWidthFull();
        binder.forField(surnameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(surnameValidationMessage)
                .bind(Failure::getSurname, Failure::setSurname);
        surnameColumn.setEditorComponent(surnameField);

        TextField streetNameField = new TextField();
        streetNameField.setWidthFull();
        binder.forField(streetNameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(streetNameValidationMessage)
                .bind(Failure::getStreetName, Failure::setStreetName);
        streetNameColumn.setEditorComponent(streetNameField);

        TextField buildingNumberField = new TextField();
        buildingNumberField.setWidthFull();
        binder.forField(buildingNumberField)
                .asRequired("Nie może być puste")
                .withStatusLabel(buildingNumberValidationMessage)
                .bind(Failure::getBuildingNumber, Failure::setBuildingNumber);
        buildingNumberColumn.setEditorComponent(buildingNumberField);

        TextField flatNumberField = new TextField();
        flatNumberField.setWidthFull();
        binder.forField(flatNumberField)
//                .asRequired("Nie może być puste")
                .withStatusLabel(flatNumberValidationMessage)
                .bind(Failure::getFlatNumber, Failure::setFlatNumber);
        flatNumberColumn.setEditorComponent(flatNumberField);

        TextField numberPhoneField = new TextField();
        numberPhoneField.setWidthFull();
        binder.forField(numberPhoneField)
                .asRequired("Nie może być puste")
                .withStatusLabel(numberPhoneValidationMessage)
                .bind(Failure::getNumberPhone, Failure::setNumberPhone);
        numberPhoneColumn.setEditorComponent(numberPhoneField);

        ComboBox<String> typeFailureComboBox = new ComboBox<>();
        typeFailureComboBox.setItems(typeFailureService.getTypeFailure());
        typeFailureComboBox.setWidthFull();
        binder.forField(typeFailureComboBox)
                .asRequired("Nie może być puste")
                .withStatusLabel(typeValidationMessage)
                .bind(Failure::getTypeFailure, Failure::setTypeFailure);
        typeFailureColumn.setEditorComponent(typeFailureComboBox);

        ComboBox<String> isBuildingFailureComboBox = new ComboBox<>();
        isBuildingFailureComboBox.setItems("tak","nie");
        isBuildingFailureComboBox.setWidthFull();
        binder.forField(isBuildingFailureComboBox)
                .asRequired("Nie może być puste")
                .withStatusLabel(typeValidationMessage)
                .bind(Failure::getIsBuilding, Failure::setIsBuilding);
        isBuldingColumn.setEditorComponent(isBuildingFailureComboBox);

        ComboBox<String> statusFailureComboBox = new ComboBox<>();
        statusFailureComboBox.setItems("Przyjęto", "W realizacji", "Zrealizowano");
        statusFailureComboBox.setWidthFull();
        binder.forField(statusFailureComboBox)
                .asRequired("Nie może być puste")
                .withStatusLabel(typeValidationMessage)
                .bind(Failure::getStatus, Failure::setStatus);
        statusColumn.setEditorComponent(statusFailureComboBox);


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
                .bind(Failure::getDate,Failure::setDate);
        dateColumn.setEditorComponent(datePickerField);

        TimePicker timePickerField = new TimePicker();
        timePickerField.setClassName("timePicker");
        timePickerField.setStep(Duration.ofMinutes(30));
        timePickerField.setMinTime(LocalTime.of(8, 0));
        timePickerField.setMaxTime(LocalTime.of(16, 0));
        timePickerField.setWidthFull();


        Button saveButton = new Button("Zapisz", e -> editor.save());
        saveButton.addClickListener(btn ->
                resCon.updateFailure(chosenFailureId.getText(), contractNumberField.getValue(), nameField.getValue(), surnameField.getValue(), streetNameField.getValue(), buildingNumberField.getValue(), flatNumberField.getValue(), numberPhoneField.getValue(), typeFailureComboBox.getValue(), timePickerField.getValue(), datePickerField.getValue(), isBuildingFailureComboBox.getValue(), statusFailureComboBox.getValue()));
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton);
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
            timeValidationMessage.setText("");
            dateValidationMessage.setText("");
            isBuildingValidationMessage.setText("");
            statusValidationMessage.setText("");
        });


        failureGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        List<Failure> failureList = failureService.getAll();
        GridListDataView<Failure> dataView = failureGrid.setItems(failureList);
        failureGrid.setItems(failureList);

        horizontalGridFailureLayout.add(contractNumberValidationMessage, nameValidationMessage, surnameValidationMessage, streetNameValidationMessage, buildingNumberValidationMessage, flatNumberValidationMessage, numberPhoneValidationMessage);

        ComboBox<String> streetComboBox = new ComboBox<>();
        streetComboBox.setClassName("streetComboBox");
        streetComboBox.setPlaceholder("Ulica");

        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setClassName("typeComboBox");
        typeComboBox.setPlaceholder("Typ awarii");

        //STREET COMBO BOX
        streetComboBox.setItems(failureService.getStreetNames());
        streetComboBox.addValueChangeListener(e -> dataView.refreshAll());
        streetComboBox.addValueChangeListener(event -> {
            List<Failure> filteredFailures = failureService.getAll();
            filteredFailures = filteredFailures.stream().filter(failure -> failure.getStreetName().equals(streetComboBox.getValue())).toList();
            if(!typeComboBox.isEmpty()){
                filteredFailures = filteredFailures.stream().filter(failure -> failure.getTypeFailure().equals(typeComboBox.getValue())).toList();
            }
            failureGrid.setItems(filteredFailures);
            if (!searchInGrid.isEmpty()) {
                dataView.addFilter(failure -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(failure.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(failure.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(failure.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(failure.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(failure.getBuildingNumber(),
                            searchTerm);
                    boolean matchesTypeFailure = matchesTerm(failure.getTypeFailure(),
                            searchTerm);
                    return matchesContractNumber || matchesName || matchesSurname || matchesStreetName || matchesBuildingNumber || matchesTypeFailure;
                });
            }
        });

        //TYPE COMBO BOX
        typeComboBox.setItems(failureService.getTypes());
        typeComboBox.addValueChangeListener(e -> dataView.refreshAll());
        typeComboBox.addValueChangeListener(event -> {
            List<Failure> filteredFailures = failureService.getAll();
            filteredFailures = filteredFailures.stream().filter(failure -> failure.getTypeFailure().equals(typeComboBox.getValue())).toList();
            if (!streetComboBox.isEmpty()) {
                filteredFailures = filteredFailures.stream().filter(failure -> failure.getStreetName().equals(streetComboBox.getValue())).toList();
            }
            failureGrid.setItems(filteredFailures);
            if (!searchInGrid.isEmpty()) {
                dataView.addFilter(failure -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(failure.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(failure.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(failure.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(failure.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(failure.getBuildingNumber(),
                            searchTerm);
                    boolean matchesTypeFailure = matchesTerm(failure.getTypeFailure(),
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
            failureGrid.setItems(failureList);

        });

        horizontalSearchFailureLayout.add(streetComboBox, typeComboBox, resetButton);

        //TEXTBOX
        searchInGrid.setValueChangeMode(ValueChangeMode.EAGER);
        searchInGrid.addValueChangeListener(e -> dataView.refreshAll());
        searchInGrid.addValueChangeListener(e -> {
            if (!searchInGrid.isEmpty()) {
                dataView.addFilter(failure -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(failure.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(failure.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(failure.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(failure.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(failure.getBuildingNumber(),
                            searchTerm);
                    boolean matchesTypeFailure = matchesTerm(failure.getTypeFailure(),
                            searchTerm);
                    return matchesContractNumber || matchesName || matchesSurname || matchesStreetName || matchesBuildingNumber || matchesTypeFailure;
                });
            }
        });

        horizontalGridFailureLayout.add(failureGrid);

    }

    private boolean matchesTerm(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }


}
