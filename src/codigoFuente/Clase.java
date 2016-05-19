package codigoFuente;

import java.util.ArrayList;
import java.util.List;

public class Clase {
	private String nombre;
	private List<Metodo> metodos;
	private List<String> codigoFuente;
	
	public Clase(String nombre){
		this.nombre = nombre;
		metodos = new ArrayList<Metodo>();
		codigoFuente = new ArrayList<String>();
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public List<String> getNombresMetodos(){
		List<String> nombreMetodos = new ArrayList<String>();
		for (Metodo metodo : metodos) {
			nombreMetodos.add(metodo.getNombre());
		}
		return nombreMetodos;
	}
	
	public void addMetodo(String nombreMetodo){
		metodos.add(new Metodo(nombreMetodo));
	}
	
	public List<Metodo> getMetodos(){
		return metodos;
	}
	
	public void addLineaCodigo(String linea, int indiceMetodo){
		if(indiceMetodo==-1){
			codigoFuente.add(linea);
		}else{
			metodos.get(indiceMetodo).addCodigo(linea);
		}
	}
	
	
}
