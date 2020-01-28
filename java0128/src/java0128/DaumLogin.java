package java0128;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DaumLogin {

	public static void main(String[] args) {
		// 크롬 드라이버의 위치를 프로피터에 추가
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\tlghk\\Documents\\javaWeb\\chromedriver_win32\\Chromedriver.exe");
		// 크롬실행
		WebDriver driver = new ChromeDriver();
		// 크롬에서 다음 로그인 사이트에 접속
		driver.get("https://logins.daum.net/accounts/signinform.do?url=https%3A%2F%2Fwww.daum.net%2F");
		// 아이디와 비밀번호 입력받기
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디를 입력하세요: ");
		String id = sc.nextLine();
		System.out.print("패스워드를 입력하세요: ");
		String pw = sc.nextLine();

		driver.findElement(By.xpath("//*[@id=\"id\"]")).sendKeys(id);
		driver.findElement(By.xpath("//*[@id=\"inputPwd\"]")).sendKeys(pw);

		// 로그인 버튼 클릭
		driver.findElement(By.xpath("//*[@id=\"loginBtn\"]")).click();
		sc.close();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 카페 접속
		// ggangpae1 - wnddkd1969
		driver.get("http://cafe.daum.net/JavaTest96");

		// 프레임 전환 : down 이라는 프레임으로 전환
		driver.switchTo().frame(driver.findElement(By.id("down")));
		System.out.println("입력할 내용: ");
		String memo = sc.nextLine();
		// 입력받은 내용을 입력란에 추가
		driver.findElement(By.xpath("//*[@id=\"memoForm__memo\"]/div/table/tbody/tr[1]/td[1]/div/textarea"))
				.sendKeys("매크로 연습");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		//등록버튼 클릭
		driver.findElement(By.xpath("//*[@id=\"replyWrite-1510\"]/div/table/tbody/tr[1]/td[1]/div/textarea")).click();
		sc.close();

	}

}
