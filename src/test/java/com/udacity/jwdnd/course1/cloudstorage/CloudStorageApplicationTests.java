package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
		this.driver = null;
	}

	/*@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}*/

	@Test
	public void testUserSignupLoginAndNoteFunctionality() throws InterruptedException {

		String username = "pzastoup";
		String password = "whatabadpassword";

		String title = "title";
		String description = "some description";

		String updatedTitle = "title updated";
		String updatedDescription = "some description updated";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		NotesPage notesPage = new NotesPage(driver);

		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		// ADD NEW NOTE TESTS
		Thread.sleep(500);
		notesPage.clickAddNewNoteButton();

		Thread.sleep(500);
		notesPage.addEditNote(title, description);

		Thread.sleep(500);
		driver.findElement(By.xpath("/html/body/div/div/span/a")).click();

		// CONFIRM NEW NOTE ADDED
		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		Thread.sleep(500);
		NoteForm addedNote = notesPage.getFirstNote();

		assertEquals(title, addedNote.getNoteTitle());
		assertEquals(description, addedNote.getNoteDescription());

		// EDIT NOTE TESTS
		notesPage.clickEditNoteButton();

		Thread.sleep(500);
		notesPage.addEditNote(updatedTitle, updatedDescription);

		Thread.sleep(500);
		driver.findElement(By.xpath("/html/body/div/div/span/a")).click();

		// CONFIRM NOTE EDITED
		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		Thread.sleep(500);
		NoteForm updatedNote = notesPage.getFirstNote();

		assertEquals(updatedTitle, updatedNote.getNoteTitle());
		assertEquals(updatedDescription, updatedNote.getNoteDescription());

		// DELETE NOTE TESTS
		notesPage.clickDeleteNoteButton();
		driver.switchTo().alert().accept();

		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div/div/span/a")).click();

		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		assertEquals("", driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody")).getText());
	}

}
