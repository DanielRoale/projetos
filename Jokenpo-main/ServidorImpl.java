import java.rmi.*;
import java.rmi.server.*;
import java.util.Random;

public class ServidorImpl extends UnicastRemoteObject implements ResultadoDist {
    public ServidorImpl() throws RemoteException {
        super();
    }

    public String julgaItens(String item) throws RemoteException {

        String[] itensArray = { "pedra", "papel", "tesoura" }; // Array com itens possíveis do jogo
        Random random = new Random();
        String itensRandomicos = itensArray[random.nextInt(itensArray.length)];

        if (item.equals(itensRandomicos)) {
            System.out.println(
                    String.format("Empate! Cliente escolheu: %s\nServidor escolheu: %s", item, itensRandomicos));
            return String.format("Empate! Cliente escolheu: %s\nServidor escolheu: %s", item, itensRandomicos);
        } else if ((item.equals("pedra") && itensRandomicos.equals("tesoura"))
                || (item.equals("tesoura") && itensRandomicos.equals("papel"))
                || (item.equals("papel") && itensRandomicos.equals("pedra"))) {
            System.out.println(String.format("Cliente venceu! Cliente escolheu: %s\nServidor escolheu: %s", item,
                    itensRandomicos));
            return String.format("Cliente venceu! Cliente escolheu: %s\nServidor escolheu: %s", item,
                    itensRandomicos);
        } else {
            System.out.println(String.format("Servidor venceu! Cliente escolheu: %s\nServidor escolheu: %s",
                    item, itensRandomicos));
            return String.format("Servidor venceu! Cliente escolheu: %s\nServidor escolheu: %s",
                    item, itensRandomicos);
        }
    }
}
