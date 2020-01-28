package java0128;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HaniMain {

	public static void main(String[] args) {
		// 한겨레 신문사 웹 페이지에서 코로나로 검색된 기사 내용을 전부 파일에 저장하기.

		// 1.코로나로 검색된 기사 개수 파악하기
		// 기사 개수 저장할 변수
		int cnt = 0;
		try {
			String keyword = URLEncoder.encode("코로나", "utf-8");

			String addr = "http://search.hani.co.kr/Search?command=query&keyword=" + keyword
					+ "%EC%BD%94%EB%A1%9C%EB%82%98&media=news&submedia=&sort=d&period=all"
					+ "&datefrom=2000.01.01&dateto=2020.01.28&pageseq=2";

			URL url = new URL(addr);
			// 연결옵션과 연결
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(20000);
			con.setUseCaches(false);

			// 문자열을 전부 읽어서 sb에 저장
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				sb.append(line + "\n");
			}
			String html = sb.toString();
			con.disconnect();
			// 데이터 확인 - 확인되면 주석 처리
			// System.out.println(html);

			// html을 DOm으로 펼치기
			Document document = Jsoup.parse(html);

			// 태그 이름으로 찾기
			// Elements spans = documnet.getElementsByTag("span");

			// 클래스 이름으로 찾기
			// Elements spans = documnet.getElementsByTag("Total");

			// 선택자료를 이용해서 찾기
			Elements spans = document.select("#contents > div.search-result-section.first-child > div > span");

			String temp = spans.get(0).text();
			// 240 건 이라고 temp 에 저장 240만 추출해서 정수로 변환해서 cnt에 저장
			String[] qr = temp.split(" ");
			cnt = Integer.parseInt(qr[0]);
			// System.out.println(cnt);

		} catch (Exception e) {
			System.out.println("기사 개수 파악 예외: " + e.getMessage());
			e.printStackTrace();
		}

		// 검색된 데이터의 링크를 전부 찾아서 list에 사빕
		ArrayList<String> list = new ArrayList<String>();
		try {
			// 페이지 수 구하기
			int pagesu = cnt / 10;
			if (pagesu % 10 != 0) {
				pagesu = pagesu + 1;
			}

			// 페이지 단위로 순회
			for (int i = 0; i < pagesu; i = i + 1) {
				String keyword = URLEncoder.encode("코로나", "utf-8");
				// 한겨레 신문사 뉴스 검색 URL만들기
				String addr = "http://search.hani.co.kr/Search?command=query&keyword=" + keyword
						+ "%EC%BD%94%EB%A1%9C%EB%82%98&media=news&submedia=&sort=d&period=all"
						+ "&datefrom=2000.01.01&dateto=2020.01.28&pageseq=2" + i;

				URL url = new URL(addr);

				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(10000);
				con.setUseCaches(false);

				// 특정 페이지의 데이터를 읽지 못하더라도 다음 페이지를 읽기 위해서 반복문안에 예외처리문장 삽입
				try {
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					while (true) {
						String line = br.readLine();
						if (line == null) {
							break;
						}
						sb.append(line + "\n");
					}
					String html = sb.toString();
					br.close();
					con.disconnect();

					Document dod = Jsoup.parse(html);
					Elements links = dod.select("dl > dt > a");
					for (int j = 0; j < links.size(); j = j + 1) {
						// a 태그 안 문자열을 가져오기
						// System.out.println(links.get(j).text());

						// a 태그의 주소link 가져오기
						// System.out.println(links.get(j).attr("href"));
						list.add(links.get(j).attr("href"));
					}
					// System.out.println(list.size());

				} catch (Exception e) {
					System.out.println("링크 수집 실패: " + e.getMessage());
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			System.out.println("링크 수집 예외: " + e.getMessage());
			e.printStackTrace();

		}

		// list에 저장된 링크의 데이터를 전부 읽어서 파일에 저장
		try {
			for (String addr : list) {
				try {
					URL url = new URL(addr);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setUseCaches(false);
					con.setConnectTimeout(10000);

					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					while (true) {
						String line = br.readLine();
						if (line == null) {
							break;
						}
						sb.append(line + "\n");
					}
					String html = sb.toString();
					br.close();
					con.disconnect();
					// System.out.println(html);
					// 파일 기록

					Document document = Jsoup.parse(html);
					Elements articles = document.getElementsByClass("text");
					// 찾아온 데이를 파일에 기록
					for (int i = 0; i < articles.size(); i = i + 1) {
						PrintWriter pw = new PrintWriter(new FileOutputStream("./코로나.txt", true));
						pw.println(articles.get(i).text());
						pw.flush();
						pw.close();
					}

					// 실제 여러 개의 페이지에서 스크래핑 할 떄 딜레이 주는 것이 좋다.
					// Thread.sleep(1000);
					
				} catch (Exception e) {
					System.out.println("기사 읽어오기 예외: " + e.getMessage());
				}
			}

		} catch (Exception e) {
			System.out.println("링크 읽기 예외:" + e.getMessage());
		}
	}

}
