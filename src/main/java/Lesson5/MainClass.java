package Lesson5;

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
    private CyclicBarrier cyclicBarrier;
    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

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
        for (int i = 0; i < 1; i++) {
            race.getStages().get(i).go1(this);
        }
        for (int i = 1; i < 2; i++) {
            race.getStages().get(i).go2(this);
        }
        for (int i = 2; i < 3; i++) {
            race.getStages().get(i).go3(this);
        }

    }
}

    abstract class Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }

    public abstract void go1(Car c);
    public abstract void go2(Car c);
    public abstract void go3(Car c);
}
class Road extends Stage {
    public AtomicInteger winner;
    public AtomicInteger go;
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        this.winner = new AtomicInteger(1);
        this.go = new AtomicInteger(1);
    }

    @Override
    public void go2(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void go3(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            winner.addAndGet(1);
            if (winner.get() == 2){
                String s = c.getName() + " WIN";
                System.out.println(s);
            }
            if (winner.get() == 5){
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void go1(Car c) {
        go.addAndGet(1);
        if (go.get() == 2){
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        }
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
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
    public void go1(Car c) {

    }

    @Override
    public void go2(Car c) {
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


    @Override
    public void go3(Car c) {

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