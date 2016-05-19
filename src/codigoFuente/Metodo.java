package codigoFuente;

import java.util.ArrayList;
import java.util.List;

public class Metodo {

	private String nombre;
	private List<String> codigoFuente;
	
	public Metodo(String nombre){
		this.nombre = nombre;
		codigoFuente = new ArrayList<String>();
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public List<String> getCodigoFuente(){
		return this.codigoFuente;
	}
	
	public void addCodigo(String linea){
		codigoFuente.add(linea);
	}
}
