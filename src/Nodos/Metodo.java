package Nodos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Metodo {
	public Metodo() {
		
	}
	
	public boolean esMetodo(String codigo[]) {
		Pattern REGEX = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])");
		ArrayList<String> pepe = new ArrayList<String>();
		
    	for(int i = 0; i < codigo.length; i++) {
    		Matcher m = REGEX.matcher(codigo[i]);
    		if(m.find()) {
    			
    			pepe.add(codigo[i]);
    		}
    	}
    	return true;
	}
}
