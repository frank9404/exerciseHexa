package com.exercise.hexaware.util;

import org.openqa.selenium.WebElement;

public class Input {

	 public void sendTextToElement(WebElement webElement,String text) throws Exception {
	 
		 try {
			 
			 webElement.clear();
			 webElement.sendKeys(text);
			 
		 }catch(Exception e) {
			 e.printStackTrace();
			 throw new Exception("No se logro ingresar el texto al elemento " + e.getMessage());
		 }
		
	 }
}
