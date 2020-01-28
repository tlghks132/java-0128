package java0128;

public class RunnableImpl implements Runnable {

	@Override
	public void run() {
		//클래스를 만들어서 사용한다고 출력
		try {
			for(int i=0; i<10; i=i+1) {
				Thread.sleep(1000);
				System.out.println("클래스를 만들어서 사용");
			}
			}catch(Exception e) {
				System.out.println("예외 발생: " + e.getMessage());
			}
			
		}

	}


