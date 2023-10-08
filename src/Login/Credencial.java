package Login;

import java.util.HashMap;

public class Credencial {

	HashMap<String,String> infologin = new HashMap<String,String>();
	
	Credencial(){
		
		infologin.put("Rodrigo", "admin");
		infologin.put("Daniel", "admin");
	}
	
	protected HashMap getLoginInfo(){
		return infologin;
	}
}
