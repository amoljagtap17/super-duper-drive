package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPage {

    @FindBy(id="credential-url")
    private WebElement credentialUrlField;

    @FindBy(id="credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id="credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id="nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(css="#nav-credentials > button")
    private WebElement addNewCredentialButton;

    @FindBy(xpath="//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]")
    private WebElement saveChangesButton;

    @FindBy(className = "credentialUrlText")
    private WebElement firstCredentialUrlText;

    @FindBy(className = "credentialUsernameText")
    private WebElement firstCredentialUsernameText;

    @FindBy(className = "credentialPasswordText")
    private WebElement firstCredentialPasswordText;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
    private WebElement editCredentialButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")
    private WebElement deleteCredentialButton;

    public CredentialsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addEditCredential(String url, String userName, String password) {
        this.credentialUrlField.clear();
        this.credentialUrlField.sendKeys(url);

        this.credentialUsernameField.clear();
        this.credentialUsernameField.sendKeys(userName);

        this.credentialPasswordField.clear();
        this.credentialPasswordField.sendKeys(password);

        this.saveChangesButton.click();
    }

    public void clickNavCredentialsTab() {
        this.navCredentialsTab.click();
    }

    public void clickAddNewCredentialButton() {
        this.addNewCredentialButton.click();
    }

    public void clickEditCredentialButton() {
        this.editCredentialButton.click();
    }

    public void clickDeleteCredentialButton() {
        this.deleteCredentialButton.click();
    }

    public CredentialForm getFirstCredential() {
        CredentialForm credentialForm = new CredentialForm(
            null,
            firstCredentialUrlText.getText(),
            firstCredentialUsernameText.getText(),
            null,
            firstCredentialPasswordText.getText(),
            123
        );

        return credentialForm;
    }

}
