package org.example;

import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.By;
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

}