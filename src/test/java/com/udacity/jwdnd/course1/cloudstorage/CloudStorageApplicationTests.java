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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;

	private final String username = "pzastoup";
	private final String password = "whatabadpassword";

	private HomePage homePage;
	private NotesPage notesPage;
	private CredentialsPage credentialsPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.baseURL = "http://localhost:" + port;

		homePage = new HomePage(this.driver);
		notesPage = new NotesPage(this.driver);
		credentialsPage = new CredentialsPage(this.driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
		this.driver = null;
	}

	private void doLogin() {
		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	private void doLogout() {
		homePage.logout();
	}

	private void doSignUp() {
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);
	}

	@Test
	@Order(1)
	public void testHomePageNotAccessible() {
		driver.get(baseURL + "/home");

		assertFalse(driver.getTitle() == "Home");
	}

	@Test
	@Order(2)
	public void testHomePageAccessible() {

		doSignUp();

		doLogin();

		System.out.println("title" + driver.getTitle());

		assertEquals("Home", driver.getTitle());

		doLogout();

		assertFalse(driver.getTitle() == "Home");
	}

	@Test
	@Order(3)
	public void testCreateAndVerifyNote() throws InterruptedException {

		String title = "title";
		String description = "some description";

		doLogin();

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

	}

	@Test
	@Order(4)
	public void testEditAndVerifyNote() throws InterruptedException {

		String updatedTitle = "title updated";
		String updatedDescription = "some description updated";

		doLogin();

		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		// EDIT NOTE TESTS
		Thread.sleep(500);
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

	}

	@Test
	@Order(5)
	public void testDeleteAndVerifyNote() throws InterruptedException {

		doLogin();

		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		// DELETE NOTE TESTS
		Thread.sleep(500);
		notesPage.clickDeleteNoteButton();
		driver.switchTo().alert().accept();

		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div/div/span/a")).click();

		Thread.sleep(500);
		notesPage.clickNavNotesTab();

		assertEquals("", driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody")).getText());
	}

    @Test
    @Order(6)
    public void testCreateAndVerifyCredential() throws InterruptedException {

        String url = "http://localhost";
        String userName = "testuser";
        String passcode = "password";

        doLogin();

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

        assertEquals(url, addedCredential.getUrl());
        assertEquals(userName, addedCredential.getUserName());

    }

    @Test
    @Order(7)
    public void testEditAndVerifyCredential() throws InterruptedException {

        String updatedUrl = "http://localhost:8080";
        String updatedUserName = "testuser1";
        String updatedPasscode = "password123";

        doLogin();

        Thread.sleep(500);
        credentialsPage.clickNavCredentialsTab();

        // EDIT CREDENTIAL TESTS
        Thread.sleep(500);
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

    }

    @Test
    @Order(8)
    public void testDeleteAndVerifyCredential() throws InterruptedException {

        doLogin();

        Thread.sleep(500);
        credentialsPage.clickNavCredentialsTab();

        // DELETE CREDENTIAL TESTS
        Thread.sleep(500);
        credentialsPage.clickDeleteCredentialButton();
        driver.switchTo().alert().accept();

        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div/div/span/a")).click();

        Thread.sleep(500);
        credentialsPage.clickNavCredentialsTab();

        assertEquals("", driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody")).getText());
    }

}
