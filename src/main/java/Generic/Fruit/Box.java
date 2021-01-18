package Generic.Fruit;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit>{

    private List<Fruit> list;

    public Box(){
        list = new ArrayList<>();
    }


    public void addFruit(Fruit fruit){
        list.add(fruit);
    }


    public float getWeight(){
        float weight = 0;
        for (Fruit l : list){
            weight += l.getWeight();
        }
        return weight;
    }

    public boolean compare(Box<?> box){
      return Math.abs((this.getWeight() - box.getWeight())) < 0.0001;
    }

    public void pourOver(Box<T> another){
        for (Fruit f: this.list){
            another.list.add(f);
        }
        this.list.clear();
    }
}
