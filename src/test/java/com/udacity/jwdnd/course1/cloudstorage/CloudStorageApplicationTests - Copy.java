package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

	@Test
	public void testUserSignupLoginAndCredentialFunctionality() throws InterruptedException {

		String username = "pzastoup";
		String password = "whatabadpassword";

		String url = "http://localhost";
		String userName = "testuser";
		String passcode = "password";

		String updatedUrl = "http://localhost:8080";
		String updatedUserName = "testuser1";
		String updatedPasscode = "password123";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		CredentialsPage credentialsPage = new CredentialsPage(driver);

		Thread.sleep(500);
		credentialsPage.clickNavCredentialsTab();

		// ADD NEW CREDENTIAL TESTS
		Thread.sleep(500);
		credentialsPage.clickAddNewCredentialButton();

		Thread.sleep(500);
		credentialsPage.addEditCredential(url, userName, passcode);

		Thread.sleep(500);
		driver.findElement(By.xpath("/html/body/div/div/span/a")).click();

		// CONFIRM NEW CREDENTIAL ADDED
		Thread.sleep(500);
		credentialsPage.clickNavCredentialsTab();

		Thread.sleep(500);
		CredentialForm addedCredential = credentialsPage.getFirstCredential();

		System.out.println("addedCredential : " + addedCredential.toString());

		assertEquals(url, addedCredential.getUrl());
		assertEquals(userName, addedCredential.getUserName());

		// EDIT CREDENTIAL TESTS
		credentialsPage.clickEditCredentialButton();

		Thread.sleep(500);
		credentialsPage.addEditCredential(updatedUrl, updatedUserName, updatedPasscode);

		Thread.sleep(500);
		driver.findElement(By.xpath("/html/body/div/div/span/a")).click();

		// CONFIRM CREDENTIAL EDITED
		Thread.sleep(500);
		credentialsPage.clickNavCredentialsTab();

		Thread.sleep(500);
		CredentialForm updatedCredential = credentialsPage.getFirstCredential();

		assertEquals(updatedUrl, updatedCredential.getUrl());
		assertEquals(updatedUserName, updatedCredential.getUserName());

		// DELETE CREDENTIAL TESTS
		credentialsPage.clickDeleteCredentialButton();
		driver.switchTo().alert().accept();

		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div/div/span/a")).click();

		Thread.sleep(500);
		credentialsPage.clickNavCredentialsTab();

		assertEquals("", driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody")).getText());
	}

}
