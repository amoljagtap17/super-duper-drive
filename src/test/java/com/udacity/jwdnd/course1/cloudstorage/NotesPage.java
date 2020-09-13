package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesPage {

    @FindBy(id="note-title")
    private WebElement noteTitleField;

    @FindBy(id="note-description")
    private WebElement noteDescriptionField;

    @FindBy(id="nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(css="#nav-notes > button")
    private WebElement addNewNoteButton;

    @FindBy(xpath="//*[@id=\"noteModal\"]/div/div/div[3]/button[2]")
    private WebElement saveChangesButton;

    @FindBy(className = "noteTitleText")
    private WebElement firstNoteTitleText;

    @FindBy(className = "noteDescriptionText")
    private WebElement firstNoteDescriptionText;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/button")
    private WebElement editNoteButton;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/a")
    private WebElement deleteNoteButton;

    public NotesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addEditNote(String title, String description) {
        this.noteTitleField.clear();
        this.noteTitleField.sendKeys(title);

        this.noteDescriptionField.clear();
        this.noteDescriptionField.sendKeys(description);

        this.saveChangesButton.click();
    }

    public void clickNavNotesTab() {
        this.navNotesTab.click();
    }

    public void clickAddNewNoteButton() {
        this.addNewNoteButton.click();
    }

    public void clickEditNoteButton() {
        this.editNoteButton.click();
    }

    public void clickDeleteNoteButton() {
        this.deleteNoteButton.click();
    }

    public NoteForm getFirstNote() {
        NoteForm noteForm = new NoteForm(
        1,
            firstNoteTitleText.getText(),
            firstNoteDescriptionText.getText(),
            123
        );

        return noteForm;
    }

}
