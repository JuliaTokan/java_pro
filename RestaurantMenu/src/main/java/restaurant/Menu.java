package restaurant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Menu {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("RestaurantMenu");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add dish");
                    System.out.println("2: find dishes by price");
                    System.out.println("3: find discont dishes");
                    System.out.println("4: find dishes less than 1 kg");
                    System.out.println("5: show menu");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addDish(sc);
                            break;
                        case "2":
                            findByPrice(sc);
                            break;
                        case "3":
                            findByDiscont();
                            break;
                        case "4":
                            findLessKG();
                            break;
                        case "5":
                            showAll();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void addDish(Scanner sc) {
        System.out.print("Enter dish name: ");
        String name = sc.nextLine();

        System.out.print("Enter dish price: ");
        String sprice = sc.nextLine();
        int price = Integer.parseInt(sprice);

        System.out.print("Enter dish weight: ");
        String weignt = sc.nextLine();
        int weight = Integer.parseInt(weignt);

        System.out.print("Enter dish discount (true/false): ");
        String disc = sc.nextLine();
        boolean discount = Boolean.parseBoolean(disc);

        em.getTransaction().begin();
        try {
            Dish dish = new Dish(name, price, weight, discount);
            em.persist(dish);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void findByPrice(Scanner sc) {
        System.out.print("Enter min price: ");
        String minPrice = sc.nextLine();
        int min_price = Integer.parseInt(minPrice);

        System.out.print("Enter max price: ");
        String maxPrice = sc.nextLine();
        int max_price = Integer.parseInt(maxPrice);

        Query query = em.createQuery("SELECT dish FROM Dish dish WHERE ((dish.price > :min_price)AND(dish.price < :max_price))", Dish.class);
        query.setParameter("max_price", max_price);
        query.setParameter("min_price", min_price);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish dish : list)
            System.out.println(dish);
    }

    private static void showAll() {
        Query query = em.createQuery("SELECT dish FROM Dish dish", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish dish : list)
            System.out.println(dish);
    }

    private static void findByDiscont() {
        Query query = em.createQuery("SELECT dish FROM Dish dish WHERE dish.discount = true", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish dish : list)
            System.out.println(dish);
    }

    private static void findLessKG() {
        Query query = em.createQuery("SELECT dish FROM Dish dish", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        int sum = 0;
        System.out.println("Dishes less than 1 KG:");

        for (Dish dish : list) {
            if (sum + dish.getWeight() < 1000) {
                sum += dish.getWeight();
                System.out.println(dish);
            }
        }
    }
}
