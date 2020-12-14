package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main1 {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {

        int add = 1;
        int remove = 2;
        int list = 3;
        int exit = 4;

        System.out.println(ConsoleColors.BLUE_BOLD + "Please select an option");
        System.out.println(ConsoleColors.CYAN + add + ". add");
        System.out.println(ConsoleColors.CYAN + remove + ". remove");
        System.out.println(ConsoleColors.CYAN + list + ". list");
        System.out.println(ConsoleColors.CYAN + exit + ". exit");

        Scanner optionScanner = new Scanner(System.in);
        label:
        while (true) {
            String options = optionScanner.nextLine();
            switch (options) {
                case "add":
                    add();
                    break label;
                case "1":
                    add();
                    break label;
                case "remove":
                    remove();
                    break label;
                case "2":
                    remove();
                    break label;
                case "list":
                    list();
                    break label;
                case "3":
                    list();
                    break label;
                case "exit":
                    exit();
                    break label;
                case "4":
                    exit();
                    break label;
                default:
                    System.out.println(ConsoleColors.YELLOW + "Remember to choose something from selected options by using number or description from the list");
                    break;
            }
        }
    }

    public static void add() {
        System.out.println(ConsoleColors.YELLOW + "Please add task description");
        Scanner scannerAddStr = new Scanner(System.in);
        int counter = 0;
        while (counter < 4) {
            String s = scannerAddStr.nextLine();
            if (counter == 0) {
                try (FileWriter fileWriter = new FileWriter("tasks.csv", true)) {
                    fileWriter.append(s).append(",");
                } catch (IOException e) {
                    System.out.println("IOException");
                }
                System.out.println(ConsoleColors.YELLOW + "Please add task due date");
                counter++;
            } else if (counter == 1) {

                try (FileWriter fileWriter = new FileWriter("tasks.csv", true)) {
                    fileWriter.append(" ").append(s).append(",");
                } catch (IOException e) {
                    System.out.println("IOException");
                }
                System.out.println(ConsoleColors.YELLOW + "Is your taks important or not? True / False");
                counter++;
            } else if (counter == 2) {
                try (FileWriter fileWriter = new FileWriter("tasks.csv", true)) {
                    fileWriter.append(" ").append(s).append("\n");
                } catch (IOException e) {
                    System.out.println("IOException");
                }
                System.out.println(ConsoleColors.YELLOW + "Do you want add next task? y = yes <-> n = no i want return to menu");
                counter++;
            } else if (counter == 3) {
                if (s.equals("y")) {
                    counter = 0;
                    System.out.println(ConsoleColors.YELLOW + "Please add task description");
                } else if (s.equals("n")) {
                    counter++;
                } else {
                    System.out.println("choose y or n");
                }
            }
        }
        menu();
    }


    public static void remove() {

        Path dir = Paths.get("tasks.csv");
        if (!Files.exists(dir)) {
            System.out.println("File not exist.");
            System.exit(0);
        }
        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                    //Check:  System.out.println(tab[i][j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] tasks = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        String n = scanner.nextLine();
        int nToNumber = Integer.parseInt(n);
        while (nToNumber < 0) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            scanner.nextLine();
        }
        //usuwanie
        int index = nToNumber;
        try {
            if (index < tab.length) {
                StringBuilder whatWasRemove = new StringBuilder();
                for (int j = 0; j < tab[index].length; j++) {
                    whatWasRemove.append(tab[index][j]).append(" ");

                }
                System.out.println("You deleted: " + ConsoleColors.GREEN_BRIGHT + whatWasRemove);
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");
        }
        //sprawdzenie tablicy po usunieciu
        System.out.println(ConsoleColors.RED_BOLD + "Data in file after your action: ");
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.println(ConsoleColors.RESET + tasks[i][j]);
            }
        }
        //zapis do pliku
        dir = Paths.get("tasks.csv");
        String[] lines = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            lines[i] = String.join(",", tasks[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        menu();
    }

    public static void list() {
        int counter = 0;
        File file = new File("tasks.csv");
        StringBuilder reading = new StringBuilder();
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                reading.append(counter).append(" : ").append(scan.nextLine() + "\n");
                counter++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku.");
        }
        System.out.println(ConsoleColors.PURPLE + reading.toString());
        menu();
    }

    public static void exit() {
        System.out.println(ConsoleColors.RED + "Bye Bye");
    }
}
