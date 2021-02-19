package com.exercise.hexaware.util;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {

	public void waitToVisibilityOfElementById(WebDriver driver,String idElement,int timeTowait) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeTowait);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idElement)));
			
		}catch(TimeoutException timeoutex){
			timeoutex.printStackTrace();
			
			StringBuilder stb = new StringBuilder();
			stb.append("Después de ");
			stb.append(timeTowait);
			stb.append(" segundos no se logro encontrar al elemento con el id ");
			stb.append(idElement);
			throw new Exception(stb.toString());
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Error al esperar el elemento "+e.getMessage());
		}
	}
}
