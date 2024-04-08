package org.example;

import java.time.Duration;
import java.util.Scanner;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Tests {

    @Test
    public void doubleClick() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://klik-test.ru/dabl-klik-test");
        WebElement clicker = driver.findElement(By.id("clicker"));

        // Проверяем два одиночных клика
        clicker.click();
        clicker.click();
        WebElement textarea = driver.findElement(By.id("textarea"));
        System.out.println("Скорость нажатия двух одиночных кликов: " + textarea.getAttribute("value"));

        // Сбрасываем результат
        WebElement reset = driver.findElement(By.id("reset"));
        reset.click();

        // Ожидаем сброса результата
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d){
                return driver.findElement(By.id("textarea")).getAttribute("value").isEmpty();
            }
        });

        // Проверяем двойной клик
        Actions builder = new Actions(driver);
        builder.doubleClick(clicker).perform();
        textarea = driver.findElement(By.id("textarea"));
        System.out.println("Скорость нажатия двойного клика: " + textarea.getAttribute("value"));


        driver.quit();
    }

    @Test
    public void dragNDrop () throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        WebElement source = driver.findElement(By.id("column-a"));
        WebElement target = driver.findElement(By.id("column-b"));

        Actions builder = new Actions(driver);
        builder.dragAndDrop(source, target).perform();

        // Пользователь должен проверить, что первым стоит бокс B, вторым стоит бокс A
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void clickNHold() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://testkru.com/Interactions/DragAndDrop");

        WebElement box1 = driver.findElement(By.id("box1"));
        Assert.assertEquals(box1.getText(), "Box 1", "Expected text 'Box 1'");

        Actions builder = new Actions(driver);
        builder.clickAndHold(box1).perform();

        // Ожидаем изменения текста бокса на 'Holding Box 1' при удержании
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d){
                return  box1.getText().equals("Holding Box 1");
            }
        });

        driver.quit();
    }

    @Test
    public void contextClick() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");

        WebElement menu = driver.findElement(By.className("context-menu-one"));


        Actions builder = new Actions(driver);
        builder.contextClick(menu).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

        // Пользователь должен проверить, что появился alert с текстом "clicked: cut"
        Thread.sleep(5000);

        driver.quit();
    }

    @Test
    public void dragNDropWithOffset() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/dragabble");

        WebElement source = driver.findElement(By.id("dragBox"));

        Actions builder = new Actions(driver);
        builder.dragAndDropBy(source,  400,150).perform();

        // Пользователь должен проверить, что бокс "Drag me" находится внутри контейнера
        Thread.sleep(3000);

        driver.quit();
    }

    @Test
    public void keyDownKeyUp() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://codepen.io/alexcss/pen/LZBNBJ");

        driver.switchTo().frame(0);

        WebElement textarea = (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.presenceOfElementLocated(By.id("area")));

        Actions builder = new Actions(driver);
        builder.keyDown(textarea, Keys.SHIFT).sendKeys("upper").keyUp(Keys.SHIFT).sendKeys("lower").perform();

        Assert.assertEquals(textarea.getAttribute("value"), "UPPERlower", "Expected text 'UPPERlower'");

        driver.quit();
    }

    @Test
    public void moveToElementAndClick() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://uitestingplayground.com/mouseover");

        // Ховер на ссылку, чтобы её DOM изменился
        WebElement link = driver.findElement(By.className("text-primary"));

        Actions builder = new Actions(driver);
        builder.moveToElement(link).perform();

        // Получаем новый путь до ссылки при ховере
        WebElement hoverLink = driver.findElement(By.className("text-warning"));
        // Кликаем на ссылку последовательно два раза
        hoverLink.click();
        hoverLink.click();

        // Проверяем, что количество кликов равно двум
        WebElement count = driver.findElement(By.id("clickCount"));
        Assert.assertEquals(count.getText(), "2", "Expected count '2'");

        //driver.quit();
    }

    @Test
    public void moveByOffset() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.geeksforgeeks.org/");

        (new WebDriverWait(driver, Duration.ofSeconds(5)))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Tutorials')]")));

        // Открываем меню "Tutorials"
        WebElement tutorials = driver.findElement(By.xpath("//span[contains(text(), 'Tutorials')]"));

        Actions builder = new Actions(driver);
        builder.moveToElement(tutorials).perform();

        // Открываем подменю "Java"
        builder.moveByOffset(0, 100).perform();
        // Перемещаем курсор мыши на пункты подменю "Java"
        builder.moveByOffset(400, 0).perform();
        // Открываем подменю "Java Quiz"
        builder.moveByOffset(0, 300).perform();
        // Наводим курсор мыши на пункт "Java Quiz"
        builder.moveByOffset(300,0).perform();
        // Кликаем на "Java Quiz", чтобы перейти на страницу квиза
        builder.click().perform();

        // Проверяем, что title страницы совпадает с ожидаемым
        Assert.assertEquals(driver.getTitle(), "50 Java Language MCQs with Answers - GeeksforGeeks", "Error");

        driver.quit();
    }

}