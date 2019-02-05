package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import model.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Jakub Filipiak on 31.01.2019.
 */
public class Controller implements Initializable {

    private File logoFileTmp;
    private LocalDate localDate = LocalDate.now();

    public Company company = new Company();
    public Client client = new Client();
    public Author author = new Author();
    public Pricing pricing = new Pricing();
    ObservableList<Service> services = FXCollections.observableArrayList();




    //Company data input

    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextField companyAddressTextField;
    @FXML
    private TextField companyEmailTextField;
    @FXML
    private TextField companyWebsiteTextField;
    @FXML
    private TextField companyMobileTextField;
    @FXML
    private TextField companyTaxNumberTextField;
    @FXML
    private Label logoFileNameLabel;
    @FXML
    private Button logoFileChooserButton;
    @FXML
    private Button saveCompanyButton;
    @FXML
    private Button editCompanyButton;

    @FXML
    public void chooseLogoFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File logoFileRaw = fileChooser.showOpenDialog(null);

        if(logoFileRaw != null) {
            System.out.println(logoFileRaw.getAbsolutePath());
            logoFileTmp = logoFileRaw;
            logoFileNameLabel.setText(logoFileTmp.getName());
        }
    }

    @FXML
    public void createCompany() {
        company.setName(companyNameTextField.getText());
        company.setAddress(companyAddressTextField.getText());
        company.setEmail(companyEmailTextField.getText());
        company.setWebsite(companyWebsiteTextField.getText());
        company.setMobile(companyMobileTextField.getText());
        company.setTaxNumber(companyTaxNumberTextField.getText());
        company.setLogoFile(logoFileTmp);

        System.out.println(company.toString());

        companyNameTextField.setDisable(true);
        companyAddressTextField.setDisable(true);
        companyEmailTextField.setDisable(true);
        companyWebsiteTextField.setDisable(true);
        companyMobileTextField.setDisable(true);
        companyTaxNumberTextField.setDisable(true);
        logoFileNameLabel.setDisable(true);
        logoFileChooserButton.setDisable(true);
        saveCompanyButton.setDisable(true);
        editCompanyButton.setDisable(false);
    }

    @FXML
    public void enableCompanyEdit() {
        companyNameTextField.setDisable(false);
        companyAddressTextField.setDisable(false);
        companyEmailTextField.setDisable(false);
        companyWebsiteTextField.setDisable(false);
        companyMobileTextField.setDisable(false);
        companyTaxNumberTextField.setDisable(false);
        logoFileNameLabel.setDisable(false);
        logoFileChooserButton.setDisable(false);
        saveCompanyButton.setDisable(false);
        editCompanyButton.setDisable(true);
    }


    //Client data input

    @FXML
    private TextField clientNameTextField;
    @FXML
    private TextField clientAddressTextField;
    @FXML
    private TextField clientEmailTextField;
    @FXML
    private TextField clientMobileTextField;
    @FXML
    private TextField clientTaxNumberTextField;
    @FXML
    private Button saveClientButton;
    @FXML
    private Button editClientButton;

    @FXML
    public void createClient() {
        client.setName(clientNameTextField.getText());
        client.setAddress(clientAddressTextField.getText());
        client.setEmail(clientEmailTextField.getText());
        client.setMobile(clientMobileTextField.getText());
        client.setTaxNumber(clientTaxNumberTextField.getText());

        System.out.println(client.toString());

        clientNameTextField.setDisable(true);
        clientAddressTextField.setDisable(true);
        clientEmailTextField.setDisable(true);
        clientMobileTextField.setDisable(true);
        clientTaxNumberTextField.setDisable(true);
        saveClientButton.setDisable(true);
        editClientButton.setDisable(false);
    }

    @FXML
    public void enableClientEdit() {
        clientNameTextField.setDisable(false);
        clientAddressTextField.setDisable(false);
        clientEmailTextField.setDisable(false);
        clientMobileTextField.setDisable(false);
        clientTaxNumberTextField.setDisable(false);
        saveClientButton.setDisable(false);
        editClientButton.setDisable(true);
    }


    //Author data input

    @FXML
    private TextField authorNameTextField;
    @FXML
    private TextField authorEmailTextField;
    @FXML
    private Button saveAuthorButton;
    @FXML
    private Button editAuthorButton;

    @FXML
    public void createAuthor() {
        author.setName(authorNameTextField.getText());
        author.setEmail(authorEmailTextField.getText());

        System.out.println(author.toString());

        authorNameTextField.setDisable(true);
        authorEmailTextField.setDisable(true);
        saveAuthorButton.setDisable(true);
        editAuthorButton.setDisable(false);
    }

    @FXML
    public void enableAuthorEdit() {
        authorNameTextField.setDisable(false);
        authorEmailTextField.setDisable(false);
        saveAuthorButton.setDisable(false);
        editAuthorButton.setDisable(true);
    }


    //Pricing data input

    @FXML
    private TextField pricingNameTextField;
    @FXML
    private TextField pricingNumberTextField;
    @FXML
    private Label pricingPreparationDateLabel;
    @FXML
    private DatePicker pricingValidityDatePicker;
    @FXML
    private Button savePricingButton;
    @FXML
    private Button editPricingButton;
    @FXML
    private Label pricingNetCostLabel;
    @FXML
    private Label pricingTaxCostLabel;
    @FXML
    private Label pricingTotalCostLabel;
    @FXML
    private Label pricingTotalAmountOfHoursLabel;
    @FXML
    private TextField pricingSummaryTextField;
    @FXML
    private TextArea pricingCommentTextField;

    @FXML
    public void createPricing() {
        pricing.setName(pricingNameTextField.getText());
        pricing.setNumber(pricingNumberTextField.getText());
        pricing.setPreparationDate(localDate);
        pricing.setValidityDate(pricingValidityDatePicker.getValue());

        System.out.println(pricing.toString());

        pricingNameTextField.setDisable(true);
        pricingNumberTextField.setDisable(true);
        pricingPreparationDateLabel.setDisable(true);
        pricingValidityDatePicker.setDisable(true);
        savePricingButton.setDisable(true);
        editPricingButton.setDisable(false);
    }

    @FXML
    public void enablePricingEdit() {
        pricingNameTextField.setDisable(false);
        pricingNumberTextField.setDisable(false);
        pricingPreparationDateLabel.setDisable(false);
        pricingValidityDatePicker.setDisable(false);
        savePricingButton.setDisable(false);
        editPricingButton.setDisable(true);
    }



    //Table of services

    @FXML
    private TableView<Service> tableView;
    @FXML
    private TableColumn<Service, String> serviceNameColumn;
    @FXML
    private TableColumn<Service, Integer> serviceAmountOfHoursColumn;
    @FXML
    private TableColumn<Service, Integer> serviceUnitCostColumn;
    @FXML
    private TableColumn<Service, Integer> serviceCostColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pricingPreparationDateLabel.setText(localDate.toString());

        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        serviceAmountOfHoursColumn.setCellValueFactory(new PropertyValueFactory<>(
                "amountOfHours"));
        serviceUnitCostColumn.setCellValueFactory(new PropertyValueFactory<>(
                "unitCost"));
        serviceCostColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        serviceNameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        serviceAmountOfHoursColumn.setStyle("-fx-alignment: CENTER;");
        serviceUnitCostColumn.setStyle("-fx-alignment: CENTER;");
        serviceCostColumn.setStyle("-fx-alignment: CENTER;");

        tableView.setEditable(true);

        serviceNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serviceAmountOfHoursColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        serviceUnitCostColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    public void changeServiceNameCellEvent(TableColumn.CellEditEvent edittedCell) {
        Service serviceSelected = tableView.getSelectionModel().getSelectedItem();
        serviceSelected.setName(edittedCell.getNewValue().toString());

        services = tableView.getItems();
        services.forEach(System.out::println);
    }

    public void changeServiceAmountOfHoursCellEvent(TableColumn.CellEditEvent edittedCell) {
        Service serviceSelected = tableView.getSelectionModel().getSelectedItem();
        serviceSelected.setAmountOfHours(Integer.parseInt(edittedCell.getNewValue().toString()));

        services = tableView.getItems();

        updateServiceCostAndPricingCostsAndTotalAmountOfHours();

        services.forEach(System.out::println);
    }

    public void changeServiceUnitCostCellEvent(TableColumn.CellEditEvent edittedCell) {
        Service serviceSelected = tableView.getSelectionModel().getSelectedItem();
        serviceSelected.setUnitCost(Integer.parseInt(edittedCell.getNewValue().toString()));

        services = tableView.getItems();

        updateServiceCostAndPricingCostsAndTotalAmountOfHours();

        services.forEach(System.out::println);
    }

    // creating new service

    @FXML
    private TextField newServiceNameTextField;
    @FXML
    private TextField newServiceAmountOfHoursTextField;
    @FXML
    private TextField newServiceUnitCostTextField;

    @FXML
    public void addNewService() {
        Service newService = Service.builder()
                .name(newServiceNameTextField.getText())
                .amountOfHours(Integer.parseInt(newServiceAmountOfHoursTextField.getText()))
                .unitCost(Integer.parseInt(newServiceUnitCostTextField.getText()))
                .cost(Integer.parseInt(newServiceAmountOfHoursTextField.getText()) * Integer.parseInt(newServiceUnitCostTextField.getText()))
                .build();

        tableView.getItems().add(newService);

        services = tableView.getItems();

        updateServiceCostAndPricingCostsAndTotalAmountOfHours();

        services.forEach(System.out::println);

        newServiceNameTextField.clear();
        newServiceAmountOfHoursTextField.clear();
        newServiceUnitCostTextField.clear();
    }

    @FXML
    public void deleteExistingService() {
        Service selectedService;
        services = tableView.getItems();
        selectedService = tableView.getSelectionModel().getSelectedItem();
        if(selectedService != null)
        services.remove(selectedService);

        updateServiceCostAndPricingCostsAndTotalAmountOfHours();

        services.forEach(System.out::println);
    }

    public void updateServiceCostAndPricingCostsAndTotalAmountOfHours() {
        int tmpTotalNetCost = 0;
        int tmpTotalAmountOfHours = 0;
        for (Service tmpService: services) {
            tmpService.setCost(tmpService.getAmountOfHours() * tmpService.getUnitCost());
            tmpTotalNetCost += tmpService.getCost();
            tmpTotalAmountOfHours += tmpService.getAmountOfHours();
        }
        pricing.setNetCost(tmpTotalNetCost);
        pricing.setTaxCost(tmpTotalNetCost * 0.23);
        pricing.setTotalCost(tmpTotalNetCost + tmpTotalNetCost * 0.23);
        pricing.setTotalAmountOfHours(tmpTotalAmountOfHours);

        tableView.setItems(services);
        tableView.refresh();

        pricingNetCostLabel.setText(String.format("%.2f PLN", pricing.getNetCost()));
        pricingTaxCostLabel.setText(String.format("%.2f PLN",
                pricing.getTaxCost()));
        pricingTotalCostLabel.setText(String.format("%.2f PLN",
                pricing.getTotalCost()));
        pricingTotalAmountOfHoursLabel.setText("Suma:   " + pricing.getTotalAmountOfHours() + " godz.");
    }

    @FXML
    public void createPricingFile() {
        pricing.setSummary(pricingSummaryTextField.getText());
        pricing.setComment(pricingCommentTextField.getText());





        //What is in 'database'
        System.out.println("-------------------------------");
        System.out.println(company.toString());
        System.out.println("---");
        System.out.println(client.toString());
        System.out.println("---");
        System.out.println(author.toString());
        System.out.println("---");
        services.forEach(System.out::println);
        System.out.println("---");
        System.out.println(pricing.toString());
        System.out.println("-------------------------------");
        //End of "What is in 'database'"
    }
}
