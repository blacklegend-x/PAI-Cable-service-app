package com.example.gawex.ui;

import com.example.gawex.controller.ResCon;
import com.example.gawex.entity.Client;
import com.example.gawex.service.ClientService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@PageTitle("Client")
@Route("client")
@CssImport("./clientPage.css")

public class ClientUI extends VerticalLayout{

    @Autowired
    ClientService clientService;

    @Autowired
    ResCon resCon;

    @PostConstruct
    public void init(){
        if(LoginToken.token == 0){
            Notification.show("Zaloguj się!");
            LoginToken.token = 0;
            UI.getCurrent().getPage().setLocation("/login");
        }

        setClassName("clientUI");

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
        HorizontalLayout horizontalSearchClientLayout = new HorizontalLayout();
        horizontalSearchClientLayout.setClassName("horizontalSearchClientLayout");
        add(horizontalSearchClientLayout);

        TextField searchInGrid = new TextField();
        searchInGrid.setPlaceholder("Wyszukaj");
        searchInGrid.setClassName("searchInGrid");
        searchInGrid.getElement().setAttribute("theme", Lumo.DARK);
        searchInGrid.setPrefixComponent(VaadinIcon.SEARCH.create());
        horizontalSearchClientLayout.add(searchInGrid);



        //koniec wyszukiwarki


        //grid
        HorizontalLayout horizontalGridClientLayout = new HorizontalLayout();
        horizontalGridClientLayout.setClassName("horizontalGridClientLayout");
        add(horizontalGridClientLayout);

        ValidationMessage contractNumberValidationMessage = new ValidationMessage();
        ValidationMessage nameValidationMessage = new ValidationMessage();
        ValidationMessage surnameValidationMessage = new ValidationMessage();
        ValidationMessage streetNameValidationMessage = new ValidationMessage();
        ValidationMessage buildingNumberValidationMessage = new ValidationMessage();
        ValidationMessage flatNumberValidationMessage = new ValidationMessage();
        ValidationMessage emailValidationMessage = new ValidationMessage();
        ValidationMessage numberPhoneValidationMessage = new ValidationMessage();

        Grid<Client> clientGrid = new Grid<>(Client.class, false);
        Editor<Client> editor = clientGrid.getEditor();

        Text chosenClientId = new Text("");

        clientGrid.setClassName("clientGrid");
        Grid.Column<Client> contractNumberColumn = clientGrid.addColumn(Client::getContractNumber).setHeader("Numer umowy").setSortable(true);
        Grid.Column<Client> nameColumn = clientGrid.addColumn(Client::getName).setHeader("Imię").setSortable(true);
        Grid.Column<Client> surnameColumn = clientGrid.addColumn(Client::getSurname).setHeader("Nazwisko").setSortable(true);
        Grid.Column<Client> streetNameColumn = clientGrid.addColumn(Client::getStreetName).setHeader("Ulica").setSortable(true);
        Grid.Column<Client> buildingNumberColumn = clientGrid.addColumn(Client::getBuildingNumber).setHeader("Numer budynku").setSortable(true);
        Grid.Column<Client> flatNumberColumn = clientGrid.addColumn(Client::getFlatNumber).setHeader("Numer lokalu").setSortable(true);
        Grid.Column<Client> emailColumn = clientGrid.addColumn(Client::getEmail).setHeader("Email").setSortable(true);
        Grid.Column<Client> numberPhoneColumn = clientGrid.addColumn(Client::getNumberPhone).setHeader("Telefon").setSortable(true);
        Grid.Column<Client> editColumn =  clientGrid.addComponentColumn(client -> {
            Button editButton = new Button("Edytuj");
            editButton.addClickListener(e->{
                if (editor.isOpen())
                    editor.cancel();
                clientGrid.getEditor().editItem(client);
                chosenClientId.setText(client.getId().toString());

            });
            return editButton;
        });

        Binder<Client> binder = new Binder<>(Client.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField contractNumberField = new TextField();
        contractNumberField.setWidthFull();
        binder.forField(contractNumberField)
                        .asRequired("Nie może być puste")
                                .withStatusLabel(contractNumberValidationMessage)
                                        .bind(Client::getContractNumber, Client::setContractNumber);
        contractNumberColumn.setEditorComponent(contractNumberField);

        TextField nameField = new TextField();
        nameField.setWidthFull();
        binder.forField(nameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(nameValidationMessage)
                .bind(Client::getName, Client::setName);
        nameColumn.setEditorComponent(nameField);

        TextField surnameField = new TextField();
        surnameField.setWidthFull();
        binder.forField(surnameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(surnameValidationMessage)
                .bind(Client::getSurname, Client::setSurname);
        surnameColumn.setEditorComponent(surnameField);

        TextField streetNameField = new TextField();
        streetNameField.setWidthFull();
        binder.forField(streetNameField)
                .asRequired("Nie może być puste")
                .withStatusLabel(streetNameValidationMessage)
                .bind(Client::getStreetName, Client::setStreetName);
        streetNameColumn.setEditorComponent(streetNameField);

        TextField buildingNumberField = new TextField();
        buildingNumberField.setWidthFull();
        binder.forField(buildingNumberField)
                .asRequired("Nie może być puste")
                .withStatusLabel(buildingNumberValidationMessage)
                .bind(Client::getBuildingNumber, Client::setBuildingNumber);
        buildingNumberColumn.setEditorComponent(buildingNumberField);

        TextField flatNumberField = new TextField();
        flatNumberField.setWidthFull();
        binder.forField(flatNumberField)
                .asRequired("Nie może być puste")
                .withStatusLabel(flatNumberValidationMessage)
                .bind(Client::getFlatNumber, Client::setFlatNumber);
        flatNumberColumn.setEditorComponent(flatNumberField);

        TextField emailField = new TextField();
        emailField.setWidthFull();
        binder.forField(emailField)
                .asRequired("Nie może być puste")
                .withStatusLabel(emailValidationMessage)
                .bind(Client::getEmail, Client::setEmail);
        emailColumn.setEditorComponent(emailField);

        TextField numberPhoneField = new TextField();
        numberPhoneField.setWidthFull();
        binder.forField(numberPhoneField)
                .asRequired("Nie może być puste")
                .withStatusLabel(numberPhoneValidationMessage)
                .bind(Client::getNumberPhone, Client::setNumberPhone);
        numberPhoneColumn.setEditorComponent(numberPhoneField);

        Button saveButton = new Button("Zapisz", e -> editor.save());
        saveButton.addClickListener(btn->
                resCon.updateClient(chosenClientId.getText(), contractNumberField.getValue(),nameField.getValue(),surnameField.getValue(),streetNameField.getValue(),buildingNumberField.getValue(),flatNumberField.getValue(),emailField.getValue(),numberPhoneField.getValue()));
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
            emailValidationMessage.setText("");
            numberPhoneValidationMessage.setText("");
        });


        clientGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        List<Client> clientList = clientService.getAll();
        GridListDataView<Client> dataView = clientGrid.setItems(clientList);
        clientGrid.setItems(clientList);

        horizontalGridClientLayout.add(contractNumberValidationMessage, nameValidationMessage,surnameValidationMessage, streetNameValidationMessage, buildingNumberValidationMessage, flatNumberValidationMessage, emailValidationMessage, numberPhoneValidationMessage);

        ComboBox<String> streetComboBox = new ComboBox<>();
        streetComboBox.setClassName("streetComboBox");
        streetComboBox.setPlaceholder("Ulica");

        streetComboBox.setItems(clientService.getStreetNames());
        streetComboBox.addValueChangeListener(e -> dataView.refreshAll());
        streetComboBox.addValueChangeListener(event -> {
            if(searchInGrid.isEmpty()){
                List<Client> filteredClients = clientService.getByStreetName(streetComboBox.getValue());
                clientGrid.setItems(filteredClients);
            }else{
                List<Client> filteredClients = clientService.getByStreetName(streetComboBox.getValue());
                clientGrid.setItems(filteredClients);

                dataView.addFilter(client -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(client.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(client.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(client.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(client.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(client.getBuildingNumber(),
                            searchTerm);
                    return matchesContractNumber || matchesName || matchesSurname || matchesStreetName || matchesBuildingNumber;
                });
            }
        });

        Button resetButton = new Button("Resetuj");
        resetButton.setClassName("resetButton");
        resetButton.addClickListener(btn-> {
            streetComboBox.clear();
            searchInGrid.clear();
            clientGrid.setItems(clientList);

        });

        horizontalSearchClientLayout.add(streetComboBox);

        Button addClientButton = new Button("Dodaj nowego klienta");
        addClientButton.setClassName("addClientButton");
        addClientButton.addClickListener(btn-> {
           addClientButton.getUI().ifPresent(ui ->
                   ui.navigate("/clientAdd"));
        });

        horizontalSearchClientLayout.add(addClientButton, resetButton);

        searchInGrid.setValueChangeMode(ValueChangeMode.EAGER);
        searchInGrid.addValueChangeListener(e -> dataView.refreshAll());
        searchInGrid.addValueChangeListener(e -> {
            if(streetComboBox.isEmpty()){
                dataView.addFilter(client -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(client.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(client.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(client.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(client.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(client.getBuildingNumber(),
                            searchTerm);
                    return matchesContractNumber || matchesName || matchesSurname || matchesStreetName || matchesBuildingNumber;
                });
            }else{
                List<Client> filteredClients = clientService.getByStreetName(streetComboBox.getValue());
                clientGrid.setItems(filteredClients);

                dataView.addFilter(client -> {
                    String searchTerm = searchInGrid.getValue().trim();
                    if (searchTerm.isEmpty())
                        return true;
                    boolean matchesContractNumber = matchesTerm(client.getContractNumber(),
                            searchTerm);
                    boolean matchesName = matchesTerm(client.getName(), searchTerm);
                    boolean matchesSurname = matchesTerm(client.getSurname(),
                            searchTerm);
                    boolean matchesStreetName = matchesTerm(client.getStreetName(),
                            searchTerm);
                    boolean matchesBuildingNumber = matchesTerm(client.getBuildingNumber(),
                            searchTerm);
                    return matchesContractNumber || matchesName || matchesSurname || matchesStreetName || matchesBuildingNumber;
                });
            }
        });

        horizontalGridClientLayout.add(clientGrid);

    }

    private boolean matchesTerm(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }



}
