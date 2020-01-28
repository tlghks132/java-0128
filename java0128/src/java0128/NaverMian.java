package java0128;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverMian {

	public static void main(String[] args) {
		// 필요한 HTML을 다운로드 : https:www.naver.com 에서 다운로드
		String html = null;

		try {
			// 다운로드 받을 URL을 생성
			// URL에 한글이 있으면 URLEncoder.encode 을 이용해서 인코딩을 해야한다.
			String addr = "https://www.naver.com";
			URL url = new URL(addr);

			// 연결
			HttpURLConnection cn = (HttpURLConnection) url.openConnection();
			cn.setConnectTimeout(30000);
			cn.setUseCaches(false);

			// 데이터 다운
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(cn.getInputStream()));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				sb.append(line + "\n");
			}

			// 사용한 스트림 정리
			br.close();
			cn.disconnect();
			// 데이터 확인
			html = sb.toString();
			System.out.println(html);

		} catch (Exception e) {
			System.out.println("다운로드 에러:" + e.getMessage());
			e.printStackTrace();
		}

		// HTML 파싱을 해서 원하는 자료구조 만들기
		try {

		} catch (Exception e) {
			System.out.println("파싱 예외:" + e.getMessage());
			e.printStackTrace();

		}

		if (html == null) {
			System.out.println("다운로드 받은 문자열이 없습니다.");
			return;
		}

		// HTML 파싱을 해서 원하는 자료구조 만들기
		try {
			//문자열을 메모리에 DOm(Document Object Model) 으로 펼치기
			Document document = Jsoup.parse(html);
			
			/*
			//span 태그 가져오기 - 태그는 중복될 수 있으므로 여러 개 리턴
			Elements span = document.getElementsByTag("span");
			//가져온 데이터를 순회하면서 출력
			for(int i =0; i < span.size(); i= i+1) {
				Element element = span.get(i);
				System.out.println(element.text());
			}
			*/
			
			/*
			Elements span =  document.getElementsByClass("an_txt");
			
			for(int i =0; i < span.size(); i= i+1) {
				Element element = span.get(i);
				System.out.println(element.text());
			}
			*/
			
			
			
			//선택자 이용
			Elements span = document.select("#PM_ID_serviceNavi > li:nth-child(9) > a");
			for(int i =0; i<span.size(); i= i+1) {
				Element element = span.get(i);
				System.out.println(element.text());
			}
			
			
			

		} catch (Exception e) {
			System.out.println("파싱 예외:" + e.getMessage());
			e.printStackTrace();

		}
	}

}
