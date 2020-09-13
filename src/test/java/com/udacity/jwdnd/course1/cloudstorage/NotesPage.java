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

    public NotesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addNewNote(String title, String description) {
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);
        this.saveChangesButton.click();
    }

    public void clickNavNotesTab() {
        this.navNotesTab.click();
    }

    public void clickAddNewNoteButton() {
        this.addNewNoteButton.click();
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
