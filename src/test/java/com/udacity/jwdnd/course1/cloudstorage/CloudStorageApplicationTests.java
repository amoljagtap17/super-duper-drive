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

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		NotesPage notesPage = new NotesPage(driver);

		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		Thread.sleep(500);
		notesPage.clickAddNewNoteButton();

		Thread.sleep(500);
		notesPage.addNewNote(title, description);

		Thread.sleep(500);

		WebElement continueLink = driver.findElement(By.xpath("/html/body/div/div/span/a"));
		continueLink.click();

		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		Thread.sleep(500);
		NoteForm addedNote = notesPage.getFirstNote();

		assertEquals(title, addedNote.getNoteTitle());
		assertEquals(description, addedNote.getNoteDescription());
	}

}
