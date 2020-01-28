package java0128;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeRun {

	public static void main(String[] args) {
		//크롬드라이브의 위치를 프로퍼티에
		System.setProperty("webdriver.chrom.driver", "C:\\Users\\tlghk\\Documents\\javaWeb\\chromedriver_win32\\Chromedriver.exe");
		//크롬실행
		WebDriver driver = new ChromeDriver();
		//크롬에서 사이트에 접속
		driver.get("https://www.naver.com");
		//접속한 사이트의 html 가져오기
		System.out.println(driver.getPageSource());
	}

}
