package Lesson6;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class MainClass {
    public static final int CARS_COUNT = 4;


    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }


        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

    }
}

class Car implements Runnable {
    private static int CARS_COUNT;
    public CyclicBarrier cyclicBarrier;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private boolean isActive;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        cyclicBarrier = new CyclicBarrier(1);
    }

    @Override
    public void run() {
        CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cyclicBarrier.await();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }
}

    abstract class Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}
class Road extends Stage {
    public AtomicInteger atomicInteger;
    public AtomicInteger another;
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        this.atomicInteger = new AtomicInteger(20);
        this.another = new AtomicInteger(1);
    }

    @Override
    public void go(Car c) {
        another.addAndGet(2);
        if (another.get() == 3 && length == 60){
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        }
        try{
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            atomicInteger.addAndGet(20);
          // System.out.println(atomicInteger);
            if (length == 40 && atomicInteger.get() == 40){
                String s = c.getName() + " WIN";
                System.out.println(s);
            }
            if (length == 40 && atomicInteger.get() == 100){
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Tunnel extends Stage {
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    final Semaphore semaphore = new Semaphore(2);
    @Override
    public void go(Car c) {
        try {
            semaphore.acquire();
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Race {
    private ArrayList<Stage> stages;

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new ArrayList<Stage>(Arrays.asList(stages));
    }

}