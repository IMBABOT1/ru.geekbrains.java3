package Thread;

public class ThreadLesson {

    private char currentLetter = 'A';
    private Object object = new Object();


    public void method1() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    while (currentLetter != 'A') {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(currentLetter);
                    currentLetter = 'B';
                    object.notifyAll();
                }
            }

        });

        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void method2() {
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    while (currentLetter != 'B') {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(currentLetter);
                    currentLetter = 'C';
                    object.notifyAll();
                }
            }
        });
        try {
            t2.start();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void method3() {
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    while (currentLetter != 'C') {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(currentLetter);
                    currentLetter = 'A';
                    object.notifyAll();
                }
            }
        });
        try {
            t3.start();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
