package com.exercise.hexaware;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.exercise.hexaware.util.Input;
import com.exercise.hexaware.util.Wait;

public class TestExerciseHexa {

	private WebDriver driver;
	private Input input;
	private Wait wait;

	private String url = "https://www.amazon.com";
	private String product = "ipad air 2 case";
	private String lowPrice = "20";
	private String highPrice = "100";
	

	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver");
		driver = new ChromeDriver();
		input = new Input();
		wait = new Wait();
		
		driver.manage().window().maximize();
		driver.get(url);
	}

	
	@Test
	public void testAmazon() {

		try {

			ArrayList<Item> itemList = performFlow();			
			verifyPrices(itemList);
			
			ArrayList<Item> listShortedPrices = shortListByPrice(itemList);
			ArrayList<Item> listShortedPrices2 = shortListByPrice(itemList);
	
			assertArrayEquals(listShortedPrices.toArray(), listShortedPrices2.toArray());
			Item bestPrice = listShortedPrices2.get(0);
			
			ArrayList<Item> listShortedScores = shortListByScore(itemList);
			Item bestScore = listShortedScores.get(0);
						
			
			System.out.println("******************************");
			if((bestPrice.getPrice() <= bestScore.getPrice()) && (bestPrice.getScore() >= bestScore.getScore())) {
				System.out.println("Deberías comprar el item "+bestPrice );
			}else {
				ArrayList<Item> listFinalScorePrice = new ArrayList<Item>();
				for (Item item : listShortedScores) {
					
					if(item.getScore() == bestScore.getScore()){
						listFinalScorePrice.add(item);
					}
					
				}
				
				if(listFinalScorePrice.size() != 0)
					bestScore = shortListByPrice(listFinalScorePrice).get(0);
				
					
				System.out.println("Deberías comprar el item "+bestScore );
			}
			System.out.println("******************************");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public ArrayList<Item> performFlow() throws Exception {
		
		WebElement webElemSearchBox = driver.findElement(By.id("twotabsearchtextbox"));
		input.sendTextToElement(webElemSearchBox, product);
		webElemSearchBox.submit();

		wait.waitToVisibilityOfElementById(driver, "low-price", 5);

		WebElement webElemLowPrice = driver.findElement(By.id("low-price"));
		input.sendTextToElement(webElemLowPrice, lowPrice);

		WebElement webElemHighPrice = driver.findElement(By.id("high-price"));
		input.sendTextToElement(webElemHighPrice, highPrice);
		webElemHighPrice.submit();

		List<WebElement> productList = driver.findElements(By.xpath("//div[@data-index]"));


		ArrayList<Item> itemList = new ArrayList<Item>();
		int iter = 0;
		int score = 0;
		do {
			Item item = new Item();
			WebElement element = productList.get(iter);

			String name = element.findElement(By.cssSelector("span.a-color-base.a-text-normal")).getText();
			System.out.println("Name:" + name);
			
			String priceWhole = element.findElement(By.className("a-price-whole")).getText();
			String priceFraction = element.findElement(By.className("a-price-fraction")).getText();
			System.out.println("priceWhole:" + priceWhole + " priceFraction:" + priceFraction);
			
			Actions action = new Actions(driver);
			
			try {
				WebElement elementx = element.findElement(By.cssSelector("a.a-popover-trigger.a-declarative"));
				action.moveToElement(elementx).perform();
				Thread.sleep(3000);
			}catch(NoSuchElementException ex) {
				System.out.println("El producto "+name+" no tiene score");
				iter++;
				continue;
				
			}
			System.out.println("PASA DEL POPOVER");
			
			String scoreRatting = driver
					.findElements(By.xpath("//span[@data-hook='acr-average-stars-rating-text']")).get((score))
					.getText();
			System.out.println("scoreRatting: " + scoreRatting);
			
			action.moveToElement(element.findElement(By.cssSelector("span.a-color-base.a-text-normal"))).perform();
			
			item.setName(name);
			item.setPrice(Float.parseFloat(priceWhole.trim() + "." + priceFraction.trim()));
			item.setScore(Float.parseFloat(scoreRatting.replace("out of 5", "").trim()));
			itemList.add(item);
			iter++;
			score++;
			
		}while(itemList.size() <  5 || iter > productList.size());
		
		return itemList;
	}
	
	public void verifyPrices(ArrayList<Item> itemList) {
		System.out.println("arrayPrices.length " + itemList.size());
		for(Item item : itemList) {
			assertTrue((item.getPrice()>=20 && item.getPrice() <=100));
		}
	}
	
	
	public ArrayList<Item> shortListByPrice(ArrayList<Item> externList)
	{
		ArrayList<Item> list = new ArrayList<Item>();
		list = externList;
		Item temp;
	    int t = list.size();
	    for (int i = 1; i < t; i++) 
	    {
	         for (int k = t- 1; k >= i; k--) 
	         {
	                if(list.get(k).getPrice() < list.get(k-1).getPrice())
	                {
	                    temp = list.get(k);
	                    list.set(k, list.get(k-1));
	                    list.set(k-1, temp);
	                }
	         }
	    }
	    return list;
	}
	
	public  ArrayList<Item> shortListByScore(ArrayList<Item> externList)
	{
		ArrayList<Item> list = new ArrayList<Item>();
		list = externList;
		Item temp;
	    int t = list.size();
	    for (int i = 1; i < t; i++) 
	    {
	         for (int k = t- 1; k >= i; k--) 
	         {
	                if(list.get(k).getScore() > list.get(k-1).getScore())
	                {
	                    temp = list.get(k);
	                    list.set(k, list.get(k-1));
	                    list.set(k-1, temp);
	                }
	         }
	    }
	    return list;
	}



}
