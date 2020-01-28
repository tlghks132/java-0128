package java0128;

import java.util.function.Consumer;
import java.util.function.Function;

//매개변수가 없고 리턴타입이 없는 메소드를 소유한 인터페이스
interface NoArgNoReturn{
	public void method1();
}


//매개변수가 있고 리턴 타입이 없는 경우 : 원본에 작업을 해서 원본을 변환시키는 인터페이스
interface ArgNoReturn{
	public void method2(int x);
}

//매개변수는 없고 리턴 타입만 있는 경우 : 거의 없는 경우
interface NoArgReturn{
	public double method3();

}

//매개변수가 있고 리턴타입이 있는경우 - 가장 많은 경우
interface ArgReturn{
	public int method4(String str);
}



public class LambdaMain {
	public static void main(String[] args) {
		//매개변수가 없고 리턴도 없는 인터페이스 활용
		NoArgNoReturn ob1 = ()->{System.out.println("매개변수가 없고 리턴도 없는 람다");};
		ob1.method1();
		
		//매개변수가 있는 경우 - 매개변수의 자료형은 생략이 가능, 매개변수가 1개인 경우는 ( )로 감싸지 않아도 된다.
		ArgNoReturn ob2 = (int x) -> {System.out.println(x + 10);};
		ob2.method2(100);
		
		NoArgReturn ob3 = () -> {return 10.3;};
		double d = ob3.method3();
		System.out.println(d);
		
		ArgReturn ob4 = (str)->{return Integer.parseInt(str);};
		int i = ob4.method4("123219");
		System.out.println(i);
		
		//Consumer 인터페이스는 매개변수 1개이고 리턴타입이 없는 메소드를 소유
		Consumer <String> consumer = (t)->{System.out.println(t);};
		consumer.accept("Java Lambda");
		
		//Function 인터페이스는 매개변수가 1개이고 리턴타입이 있는 메소드를 소유
		//제너릭에서 앞의 자료형은 매개변수의 자료형이고 뒤의 자료형은 리턴타입의 자료형
		//데이터를 받아서 변환 후 리턴해주는 메소드
		Function<String, String> function = (str) ->{if(str.length() <=3) return str;
		else return str.substring(0, 2) + "**";};
		String r = function.apply("hi");
		System.out.println(r);
		r= function.apply("Fuck");
		System.out.println(r);

			
	}
}



