package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "nav-files")
    private List<WebElement> navFilesPanel;

    @FindBy(id = "nav-notes")
    private List<WebElement> navNotesPanel;

    @FindBy(id = "nav-credentials")
    private List<WebElement> navCredentialsPanel;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickNavFilesTab() {
        navFilesTab.click();
    }

    public void clickNavNotesTab() {
        navNotesTab.click();
    }

    public void clickNavCredentialsTab() {
        navCredentialsTab.click();
    }

    public int getNavFilesElements() {
        return navFilesPanel.size();
    }

    public int getNavNotesElements() {
        return navNotesPanel.size();
    }

    public int getNavCredentialsElements() {
        return navCredentialsPanel.size();
    }
}
