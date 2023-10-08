package Login;

public class Main {

	public static void main(String[] args) {
		
		Credencial usuarioSenha = new Credencial();

		PagLogin login = new PagLogin(usuarioSenha.getLoginInfo());
	}

}
