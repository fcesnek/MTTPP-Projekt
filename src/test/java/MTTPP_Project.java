import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.mifmif.common.regex.Generex;

public class MTTPP_Project {
    public WebDriver driver;
    public String testURL = "https://www.futura-it.hr/";
    public String email;
    public String password;
    public String name, surname;

    // Pomoćne metode koje pomoću paketa Generex iz regularnog izraza generiraju nasumični string zadane duljine
    private String generateNumbers(int length) {
        Generex generex = new Generex("\\d{" + length + "}");
        String numberString = generex.random();
        return numberString;
    }
    private String generateLetters(int length) {
        Generex generex = new Generex("[a-zA-Z]{" + length + "}");
        String randomString = generex.random();
        return randomString;
    }
    private String generateNumbersAndLetters(int length) {
        Generex generex = new Generex("[a-zA-Z0-9]{" + length + "}");
        String randomString = generex.random();
        return randomString;
    }

    @BeforeMethod
    public void setupTest() {
        System.setProperty("webdriver.chrome.driver", "C:/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(testURL);

        // Generiranje emaila čiji je lokalni dio duljine 5 do 10 znakova
        email = generateLetters(5 + (int)(Math.random() * ((10 - 5) + 1))) + "@gmail.com";
        // Generiranje nasumičnog stringa koji će se koristiti kao lozinka duljine 8 do 20 znakova
        password = generateNumbersAndLetters(8 + (int)(Math.random() * ((20 - 8) + 1)));
    }
    @Test
    public void registrationTest() throws InterruptedException {
        // Klik na gumb za registraciju
        WebElement registerButton = driver.findElement(By.xpath("/html/body//div[@class='header-top']//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-12']/div/ul//a[@href='https://www.futura-it.hr/account-create.asp']/span[.='Registrirajte se']"));
        registerButton.click();
        Thread.sleep(2000);

        // Potvrdi kolačiće
        WebElement confirmCookiesButton = driver.findElement(By.xpath("/html/body/div[1]/a[3]"));
        confirmCookiesButton.click();

        Thread.sleep(2000);

        // Upis emaila
        WebElement emailTextBox = driver.findElement(By.id("email"));
        emailTextBox.sendKeys(email);

        // Upis lozinke
        WebElement passwordTextBox = driver.findElement(By.id("passlog"));
        passwordTextBox.sendKeys(password);

        // Upis potvrde lozinke
        WebElement passwordConfirmTextBox = driver.findElement(By.id("passlog2"));
        passwordConfirmTextBox.sendKeys(password);

        // Upis prezimena
        surname = generateLetters(5 + (int)(Math.random() * ((12 - 5) + 1)));
        WebElement surnameTextBox = driver.findElement(By.id("prezime"));
        surnameTextBox.sendKeys(surname);

        // Upis mjesta
        WebElement locationTextBox = driver.findElement(By.id("mjesto"));
        locationTextBox.sendKeys(generateLetters(5 + (int)(Math.random() * ((15 - 5) + 1))));

        // Upis postanskog broja
        WebElement zipcodeTextBox = driver.findElement(By.id("posta"));
        zipcodeTextBox.sendKeys(generateNumbers(5));

        // Upis imena
        name = generateLetters(3 + (int)(Math.random() * ((10 - 3) + 1)));
        WebElement nameTextBox = driver.findElement(By.id("ime"));
        nameTextBox.sendKeys(name);

        // Upis adrese
        WebElement addressTextBox = driver.findElement(By.id("adresa"));
        addressTextBox.sendKeys(generateNumbersAndLetters(10 + (int)(Math.random() * ((20 - 10) + 1))));

        // Upis broja telefona
        WebElement phoneTextBox = driver.findElement(By.id("telefon"));
        phoneTextBox.sendKeys(generateNumbers(10));

        // Izbor dana rođenja
        Select dayDropDown = new Select(driver.findElement(By.id("Bdan")));
        dayDropDown.selectByIndex(1 + (int)(Math.random() * ((30 - 1) + 1)));

        // Izbor mjeseca rođenja
        Select monthDropDown = new Select(driver.findElement(By.id("Bmjesec")));
        monthDropDown.selectByIndex(1 + (int)(Math.random() * ((12 - 1) + 1)));

        // Izbor godine rođenja
        Select yearDropDown = new Select(driver.findElement(By.id("Bgodina")));
        yearDropDown.selectByIndex(16 + (int)(Math.random() * ((70 - 16) + 1)));

        // Označavanje checkboxa za prihvaćanje uvjeta korištenja
        WebElement acceptCheckbox = driver.findElement(By.xpath("/html//form[@id='commentForm']//label[.='Prihvaćam uvjete korištenja']"));
        acceptCheckbox.click();

        Thread.sleep(2000);

        // Klik na potvrdu registracije
        WebElement confirmButton = driver.findElement(By.xpath("/html//input[@id='gumblog']"));
        confirmButton.click();

        Thread.sleep(3000);

        // Provjera unesenih podataka
        WebElement nameLabel = driver.findElement(By.xpath("/html/body/header/div[1]/div/div/div[2]/div/ul/li/a"));
        Assert.assertEquals(nameLabel.getText(), surname + " " + name);

        Thread.sleep(2000);

        // Odjava
        Actions tooltip = new Actions(driver);
        WebElement logoutButton = driver.findElement(By.xpath("/html/body/header/div[1]/div/div/div[2]/div/ul/li/a"));
        tooltip.moveToElement(logoutButton).moveToElement(driver.findElement(By.xpath("/html/body//div[@class='header-top']/div[@class='container']//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-12']/div/ul//div/ul//a[@href='/index.asp?MM_Logoutnow=1']/span[.='Odjava']"))).click().build().perform();

        Thread.sleep(2000);
    }

    @Test
    public void loginTest() throws InterruptedException {
        String validMail = "mttpp_test@gmail.com";
        String validPass = "mttpp_test";

        // Klik na gumb za prijavu
        WebElement loginButton = driver.findElement(By.xpath("/html/body//div[@class='header-top']/div[@class='container']//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-12']/div/ul//a[@href='https://www.futura-it.hr/prijava.asp']"));
        loginButton.click();
        Thread.sleep(2000);

        // Potvrda kolačića
        WebElement confirmCookiesButton = driver.findElement(By.xpath("/html/body/div[1]/a[3]"));
        confirmCookiesButton.click();

        Thread.sleep(2000);

        // Upis emaila
        WebElement emailTextBox = driver.findElement(By.id("username"));
        emailTextBox.sendKeys(validMail);

        // Upis lozinke
        WebElement passwordTextBox = driver.findElement(By.id("pass"));
        passwordTextBox.sendKeys(validPass);

        // Klik na gumb za potvrdu unosa
        WebElement confirmButton = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[3]/input"));
        confirmButton.click();

        Thread.sleep(2000);

        // Provjera prikazanog imena i unesenog imena
        WebElement nameLabel = driver.findElement(By.xpath("/html/body/header/div[1]/div/div/div[2]/div/ul/li/a"));
        Assert.assertEquals(nameLabel.getText(), "MTTPP Test");

        Thread.sleep(2000);
    }

    @Test
    public void addProductToCartTest() throws InterruptedException {
        // Potvrda kolačića
        WebElement confirmCookiesButton = driver.findElement(By.xpath("/html/body/div[1]/a[3]"));
        confirmCookiesButton.click();
        Thread.sleep(2000);

        // Izbor kategorije proizvoda
        WebElement categoryButton = driver.findElement(By.xpath("/html//div[@class='container2 index-kategorije mt-30']/div[1]/a[@title='Stolna računala']/img[@alt='Stolna računala']"));
        categoryButton.click();
        Thread.sleep(3000);

        // Dodavanje proizvoda u košaricu
        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id=\"th\"]/div/div[1]/div[2]/div[4]/div[1]/a"));
        addToCartButton.click();
        Thread.sleep(1000);

        // Otvaranje košarice
        WebElement showCartButton = driver.findElement(By.xpath("/html//div[@id='cartAdd']//div[@class='modal-footer']/a[2]"));
        showCartButton.click();

        // Provjera da produkt postoji u košarici, tj. da mu cijena nije 0 kn
        WebElement priceLabel = driver.findElement(By.xpath("/html/body/header/div[2]/div/div/div[3]/div/div/ul/li[2]/a"));
        Assert.assertNotEquals(priceLabel.getText(), "0 kn");
        Thread.sleep(2000);
    }

    @Test
    public void removeProductFromCartTest() throws InterruptedException {
        addProductToCartTest();
        Thread.sleep(2000);

        // Nakon dodavanja produkta u košaricu, klik na gumb za uklanjanje
        WebElement removeButton = driver.findElement(By.linkText("Ukloni iz košarice"));
        removeButton.click();
        Thread.sleep(5000);

        // Potvrda da se produkt ukloni
        WebElement confirmButton = driver.findElement(By.linkText("PRIHVATI"));
        confirmButton.click();
        Thread.sleep(2000);

        // Provjera je li cijena produkata u košarici jednaka 0kn, tj. da ne postoji niti jedan produkt u košarici
        WebElement priceLabel = driver.findElement(By.xpath("/html/body/header/div[2]/div/div/div[3]/div/div/ul/li[2]/a"));
        Assert.assertEquals(priceLabel.getText(), "0 kn");
    }

    @Test
    public void addProductToWishlist() throws InterruptedException {

        // Lista želja dostupna je samo prijavljenim korisnicima, stoga se prijavimo
        loginTest();

        // Kliknemo na neku kategoriju
        WebElement categoryButton = driver.findElement(By.xpath("/html//div[@class='container2 index-kategorije mt-30']/div[1]/a[@title='Stolna računala']/img[@alt='Stolna računala']"));
        categoryButton.click();
        Thread.sleep(2000);

        // Dodamo produkt na listu želja i spremimo mu ime
        String productName = driver.findElement(By.xpath("//*[@id=\"th\"]/div/div[1]/h2/a")).getText();
        WebElement addToWishlistButton = driver.findElement(By.xpath("/html//div[@id='th']/div[@class='row']/div[1]/div[@class='product-content']/div[@class='lista-kosarica']/div[@class='wishlist']/a[@title='']/i[@class='fa fa-list']\n"));
        addToWishlistButton.click();
        Thread.sleep(2000);

        // Koristeći JS skriptu, navigiramo do vrha stranice
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,-250)", "");

        // Navigiramo do korisničkih stranica
        Actions tooltip = new Actions(driver);
        WebElement hoverName = driver.findElement(By.xpath("/html/body/header/div[1]/div/div/div[2]/div/ul/li/a"));
        tooltip.moveToElement(hoverName).moveToElement(driver.findElement(By.xpath("/html/body/header/div[1]/div/div/div[2]/div/ul/li/div/ul/li[1]/a"))).click().perform();
        Thread.sleep(3000);

        // Kliknemo na listu želja
        WebElement wishlistTab = driver.findElement(By.linkText("LISTA ŽELJA"));
        wishlistTab.click();
        Thread.sleep(2000);

        // Usporedimo ime produkta u listi želja s prethodno spremljenim imenom
        WebElement wishlistElement = driver.findElement(By.xpath("//*[@id=\"PTabWishList\"]/table/tbody/tr[3]/td[2]/a"));
        Assert.assertEquals(wishlistElement.getText(), productName);
        Thread.sleep(2000);
    }

    @AfterMethod
    public void teardownTest() {
        driver.quit();
    }
}
