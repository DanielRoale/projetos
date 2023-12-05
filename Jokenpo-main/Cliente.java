import java.rmi.*;
import java.util.Scanner;

import service.ConsoleClear;
import service.ConsoleColor;

public class Cliente {
    public static void main(String args[]) {

        ConsoleClear.Clear(); // Limpa o console do cliente automaticamente a cada jogada

        // Iniciando em uma thread apartada para gozar de multithread
        Thread t1 = new Thread(() -> {
            try {

                if (args.length == 0) {
                    System.out.println(
                            ConsoleColor.RED + "Atenção: deve ser passado como argumento o endereço IP do servidor."
                                    + ConsoleColor.RESETCOLOR);
                    return;
                }

                Scanner sc = new Scanner(System.in);
                String enderecoServ = args[0];
                ResultadoDist servidor = (ResultadoDist) Naming.lookup("rmi://" + enderecoServ + "/ServidorJogo");

                System.out.print(ConsoleColor.GREEN + "Escolha entre pedra, papel ou tesoura: ");
                String item = sc.nextLine().toLowerCase().trim();

                String resultado = servidor.julgaItens(item);
                System.out.println(resultado);

                sc.close(); // Fecha recursos da classe Scanner. Poderiamos usar try-with-resources também

            } catch (Exception e) {
                e.getMessage();
            }
        });

        t1.start();

        ConsoleColor.RESETCOLOR.toString();
    }
}
