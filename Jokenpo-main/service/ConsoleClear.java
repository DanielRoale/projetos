package service;

public class ConsoleClear {
    public static void Clear() {
        System.out.print("\033[H\033[2J");
    }
}
