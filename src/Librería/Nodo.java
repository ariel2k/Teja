package Librer�a;

public class Nodo {
	private String cad;
	
	public Nodo(String input) {
		this.cad = input;
	}
	
	public boolean eSCSimple() {
		this.cad.replaceAll(" ", "");
		return true;
	}
}
