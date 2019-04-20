package com.alliedtesting;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleSearch
{
    @Test
    public void test() throws InterruptedException {

        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);

        // use this to visit Google
        driver.get("http://www.google.com");
        // Alternatively the same thing can be done like this
//         driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        element.sendKeys("Woodpecker");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

//        // Check the title of the page
//        System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("woodpecker");
            }
        });

//        List<WebElement> links = driver.findElements(By.xpath("//div[h2[not(contains(text(),'People also ask'))]]//div[@class='r']//a[contains(.,'Woodpecker')]"));
//        int numberOfLinks = links.size();
//
//        for(int i = 0; i < numberOfLinks; i++){
//            List<WebElement> links2 = driver.findElements(By.xpath("//div[h2[not(contains(text(),'People also ask'))]]//div[@class='r']//a[contains(.,'Woodpecker')]"));
//            links2.get(i).click();
//            System.out.println("Page title " + driver.getTitle());
//            System.out.println("Page url " + driver.getCurrentUrl());
//            System.out.println("Number of cheetahs: " + StringUtils.countMatches(driver.getPageSource().toLowerCase(),"woodpecker"));
//            driver.navigate().back();
//            Thread.sleep(3000);
//        }
        int sheetIndex = 0;
        int pageNumber = 1;

        for(int n = 0; n < 10; n++) {
            System.out.println("++++pageNumber = " + pageNumber);
            List<WebElement> links = driver.findElements(By.xpath("//div[h2[not(contains(text(),'People also ask'))]]//div[@class='r']//a[contains(translate(., 'woodpecker', 'Woodpecker'),'Woodpecker')]"));
            links.addAll(driver.findElements(By.xpath("//div[h2[not(contains(text(),'People also ask'))]]//div[@class='r']//a[contains(., 'WOODPECKER')]")));
            List<String> nameLinks = new ArrayList<>();
            for (WebElement el : links) {
                nameLinks.add(el.getText());
                System.out.println(el.getAttribute("href"));
            }

            for (int i = 0; i < nameLinks.size(); i++) {
                WebElement el = driver.findElement(By.linkText(nameLinks.get(i)));
                el.click();
                Thread.sleep(1000);
                String pageTitle = driver.getTitle();
                System.out.println("Page title " + pageTitle);
                String pageUrl = driver.getCurrentUrl();
                System.out.println("Page url " + pageUrl);
                String wordNumber = ((Integer) StringUtils.countMatches(driver.getPageSource().toLowerCase(), "woodpecker")).toString();
                System.out.println("Number of Woodpeckers: " + wordNumber);
                driver.navigate().back();
                Thread.sleep(3000);

                try {
                    ExcelFileCreator.workBookCreator(pageTitle, pageUrl, wordNumber, sheetIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("****sheetIndex = " + sheetIndex);
                sheetIndex++;
            }
            pageNumber++;

                if (pageNumber == 11) {
                    break;
                }
            driver.findElement(By.xpath("//a[@class='fl'][contains(text(),'" + pageNumber + "')]")).click();
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getTitle().toLowerCase().startsWith("woodpecker");
                }
            });
        }

        //Close the browser
        driver.quit();
    }
}
