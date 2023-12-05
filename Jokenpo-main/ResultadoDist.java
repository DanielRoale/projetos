import java.rmi.*;

public interface ResultadoDist extends Remote {
    public String julgaItens(String item) throws RemoteException;
}
