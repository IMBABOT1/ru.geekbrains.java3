package Thread;

public class Main {

    public static void main(String[] args) {
        ThreadLesson t = new ThreadLesson();
        for (int i = 0; i <5 ; i++) {
            t.method1();
            t.method2();
            t.method3();
        }

    }
}
