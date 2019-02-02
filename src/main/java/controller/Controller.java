package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.Client;
import model.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;

/**
 * Created by Jakub Filipiak on 31.01.2019.
 */
public class Controller {

    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ClockworkPersistence");
    static EntityManager entityManager = factory.createEntityManager();
    private File logoFileTmp;

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
        Company company = Company.builder()
                .name(companyNameTextField.getText())
                .address(companyAddressTextField.getText())
                .email(companyEmailTextField.getText())
                .website(companyWebsiteTextField.getText())
                .mobile(companyMobileTextField.getText())
                .taxNumber(companyTaxNumberTextField.getText())
                .logoFile(logoFileTmp)
                .build();

        if(!company.getEmail().isEmpty()) {
            System.out.println(company.getEmail());
        }

        entityManager.getTransaction().begin();
        entityManager.merge(company);
        entityManager.getTransaction().commit();
    }

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
    public void createClient() {
        Client client = Client.builder()
                .name(clientNameTextField.getText())
                .address(clientAddressTextField.getText())
                .email(clientEmailTextField.getText())
                .mobile(clientMobileTextField.getText())
                .taxNumber(clientTaxNumberTextField.getText())
                .build();

        if(!client.getEmail().isEmpty()) {
            System.out.println(client.getEmail());
        }

        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();
    }
}
