import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class app {
    public static List<Toys> toys_list;
    public static List<Toys> prize_list;
    public static void main(String[] args) throws Exception {
        toys_list = new ArrayList<>();
        prize_list = new ArrayList<>();
        Scanner iScanner = new Scanner(System.in);
        try {
            System.out.println();
            System.out.println(" Добро пожаловать в  магазин детских товаров!\n" +
                   "Примите участие в розыгрыше  и получите приз!\n");
            head_menu(iScanner);
        } catch (exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getInfo());
        } finally {
            iScanner.close();
        }
    }
    public static void createToy(Scanner iScanner) {
        System.out.printf(" Введите имя игрушки: ");
        iScanner.nextLine();
        String name = iScanner.nextLine();
        int count = inputData(" Введите количество игрушек: ", iScanner);
        int weight = inputData(" Введите шанс выпадения игрушки при розыгрыше (от 1 до 100): ", iScanner);
        try {
            toys_list.add(new Toys(name, count, weight));
            System.out.println("\n* Теперь список игрушек стал таким: *");
            System.out.println(toys_list.get(0));
        } catch (exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getInfo());
        }
    }

    public static void changeWeight(Scanner iScanner) throws exception{
        int id = inputData("Введите ID игрушки: ", iScanner);
        int weight = inputData(" Введите шанс выпадения игрушки при розыгрыше (от 1 до 100): ", iScanner);
        if (toys_list.get(id).setWeight(weight) == 4) {
            throw new exception(4, "* Ошибка изменения экземпляра класса *");
        }
        System.out.println("\n* Теперь список игрушек стал таким: *");
        System.out.println(toys_list.get(id));
    }

    public static int inputData(String text, Scanner iScanner) {
        System.out.printf(text);
        int num = 0;
        if (iScanner.hasNextInt()) {
            num = iScanner.nextInt();
        } else {
            num = -1;
        }
        return num;
    }

    public static void delivery_toys(Scanner iScanner) throws exception  {
        int n = -1;
        while (n == -1){
            n = inputData(" Сколько игрушек привезти: ", iScanner);
            System.out.println("\n* Теперь список игрушек стал таким: *");
        }
        for (int i = 0; i < n; i++) {
            String name = String.format("Toys %d", ThreadLocalRandom.current().nextInt(0, 1000));
            int count = ThreadLocalRandom.current().nextInt(1, 10);
            int weight = ThreadLocalRandom.current().nextInt(1, 100);
            toys_list.add(new Toys(name, count, weight));
            System.out.println(toys_list.get(toys_list.size() - 1));
        }
    }
    public static int check_toys(){
        if (toys_list.size() == 0) {
            System.out.println("\n* В магазине нет игрушек *");
            return -1;
        }
        return 0;
    }
    public static void print_toys() {
        System.out.println("\n* Остались игрушки: *");
        for (int i = 0; i < toys_list.size(); i++) {
            System.out.println(toys_list.get(i));
        }
        check_toys();
    }

    public static void game() {
        int wt = 0;
        if (check_toys() == 0) {
            for (int i = 0; i < toys_list.size(); i++) {
                wt += toys_list.get(i).getWeight();
                toys_list.get(i).setBound(wt);
            }
            
            int rnd = ThreadLocalRandom.current().nextInt(0, wt) + 1;
            for (int i = 0; i < toys_list.size(); i++) {
                if(rnd > toys_list.get(i).getBound() - toys_list.get(i).getWeight() && rnd < toys_list.get(i).getBound()) {
                    if(toys_list.get(i).getCount() == 0) {
                        game();
                    } else {
                        prize_list.add(toys_list.get(i));
                        toys_list.get(i).setCount(toys_list.get(i).getCount() - 1);
                        System.out.println();
                        System.out.printf("\n* Выпала игрушка: %s *",toys_list.get(i));
                        System.out.println();
                    }
                }
            }
        }
    }

    public static void get_prize() {
        if (prize_list.size() == 0) {
            System.out.println("\n* Нет выйгрышей *");
        } else {
            Date date = new Date();
            try (FileWriter fw = new FileWriter("prizes_list.txt", StandardCharsets.UTF_8, true)) {
                fw.append(String.format("%s %s\n", prize_list.get(0), date));
                fw.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println();
            System.out.printf("\n* Выдан приз %s *", prize_list.get(0));
            System.out.println();
            prize_list.remove(0);
        }
    }

    public static void head_menu(Scanner iScanner) throws exception {
        int sel_menu = -1;
        System.out.println();
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("Введите число, соответствующее пункту меню:");
        System.out.println("-------------------------------------------");
        System.out.println("1. Добавить игрушку");
        System.out.println("2. Изменить шанс выпадения игрушки");
        System.out.println("3. Разыграть игрушку");
        System.out.println("4. Забрать приз");
        System.out.println("5. Привезти партию игрушек");
        System.out.println("6. Вывести список доступных игрушек");
        System.out.println("7. Выход");
        sel_menu = inputData("", iScanner);
        switch (sel_menu) {
            case 1:
                createToy(iScanner);
                head_menu(iScanner);
                break;
            case 2:
                changeWeight(iScanner);
                head_menu(iScanner);
                break;
            case 3:
                game();
                head_menu(iScanner);
                break;
            case 4:
                get_prize();
                head_menu(iScanner);
                break;
            case 5:
                delivery_toys(iScanner);
                head_menu(iScanner);
                break;
            case 6:
                print_toys();
                head_menu(iScanner);
                break;
            case 7:
                System.out.println();
                System.out.println("До встречи!");
                System.out.println("Ждем вас снова в нашем магазине!");
                System.out.println();
                System.out.println();
                System.exit(0);
                break;
            default:
                head_menu(iScanner);
                break;
        }
    }
    
}