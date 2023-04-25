import java.util.ArrayList;
import java.util.List;

public class Toys {
    private int id;
    private String name;
    private int count;
    private int weight;
    private int bound;
    public static int idCount = 1;
    private static List<Toys> toys = new ArrayList<>();

    public Toys(String name, Integer count) {
        this.id = Toys.idCount++;
        this.name = name;
        this.count = count;
    }

      
    public int getId() {return id;}
    public String getName() {return name;}
    public int getCount() {return count;}
    public int getWeight() {return weight;}
    public int getBound() {return bound;}
    public int setName(String name) {
        if (name.equals("")) {
            return 1;
        }
        this.name = name;
        return 0;
    }

    public int setCount(int count) {
        if (count < 0) {
            return 2;
        } else {
            this.count = count;
            return 0;
        }
    }

    public int setWeight(int weight) {
        if (weight < 1 || weight > 100) {
            return 4;
        } else {
            this.weight = weight;
            return 0;
        }
    }

    public int setBound(int bound) {
        if (bound < 0) {
            return 8;
        } else {
            this.bound = bound;
            return 0;
        }
    }

    public Toys(String name, int count, int weight)  throws exception {
        int error = 0;
        toys.add(this);
        this.id = toys.size() - 1;
        error += setName(name);
        error += setCount(count);
        error += setWeight(weight);
        if (error != 0) {throw new exception(error, "Ошибка создания экземпляра класса");}
    }

    @Override
    public String toString()
     {
        return String.format(new StringBuilder().append("\tID: %d; ")
            .append(" name: %s; ")
            .append(" count: %d; ")
            .append(" weight: %d; ")
            .append(" bound: %d")
            .toString(), id, name, count, weight, bound);
        
    }

}