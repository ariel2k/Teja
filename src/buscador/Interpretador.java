package buscador;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import codigoFuente.*;
import archivo.Archivo;

public class Interpretador {

	private List<Clase> clases;
	private Archivo arch;
	private List<String> lineasDeCodigo;
	
	public Interpretador(String path){
		arch = new Archivo(path);
		clases = new ArrayList<Clase>();
		lineasDeCodigo = new ArrayList<String>();
		pasarAList();
		interpretarArchivo();
	}
	
	public List<Clase> getClases(){
		return clases;
	}
	
	public List<String> getNombreClases(){
		List<String> nombreClases = new ArrayList<String>();
		for (Clase clase: clases) {
			nombreClases.add(clase.getNombre());
		}
		return nombreClases;
	}
	
	//Pasa todas las lineas del archivo al List de lineasDeCodigo
	private void pasarAList(){
		String linea = arch.leerArchivo();
		while(linea!=null){
			lineasDeCodigo.add(linea);
			linea = arch.leerArchivo();
		}
	}
	
	//Lee linea por linea y la interpreta
	private void interpretarArchivo(){
		int cantDeLlaves = 0;
		for (int i = 0; i < lineasDeCodigo.size(); i++) {
			String linea = lineasDeCodigo.get(i);
			//busca una llave abierta que indique que sea inicio de clase o de comentario
			int rta = buscarClaseOMetodo(linea,cantDeLlaves, i);
			//agrega la linea leida a la clase, o al metodo
			agregarLinea(linea, cantDeLlaves, rta);
			cantDeLlaves += rta;
		}
	}
	
	//comprueba si la linea se agrega al metodo o a la clase
	private void agregarLinea(String linea, int cantDeLlaves, int rta) {
		//hay 2 llaves abiertas en total es porque la linea es del metodo o lee la llave cerrada
		if(rta + cantDeLlaves > 1 || (cantDeLlaves == 2 && rta == -1)){
			agregarLineasAlMetodo(linea);
		}else
		//Agrega linea a la clase	
		if(rta + cantDeLlaves == 1){
			agregarLineasALaClase(linea);
		}
	}

	//busca la ultima clase que hay en la List para agregar la linea a esa clase
	private void agregarLineasALaClase(String linea) {
		//le resto menos uno porque el indice empieza en 0 pero el clases.size() me va a dar mas grande
		int size = this.clases.size()-1;
		//Asigno una vez 
		Clase c = this.clases.get(size);
		//le mando la linea y el indice. Si es -1 es poque es linea de la clase y no del metodo
		c.addLineaCodigo(linea, -1);
	}

	private void agregarLineasAlMetodo(String linea) {
		//le resto menos uno porque el indice empieza en 0 pero el clases.size() me va a dar mas grande
		int sizeClases = this.clases.size()-1;
		//le resto menos uno porque el indice empieza en 0 pero el clases.size() me va a dar mas grande
		int sizeMetodosUltimaClase = this.clases.get(sizeClases).getNombresMetodos().size() -1;
		//le digo que agregue la linea a la ultima clase y al ultimo metodo de esa clase
		this.clases.get(sizeClases).addLineaCodigo(linea, sizeMetodosUltimaClase);
	}

	private int buscarClaseOMetodo(String linea, int cantDeLlaves, int i) {
		//Pregunto si la linea es solamente una llave abierta por si la llave abierta la pusieron abajo del renglon. Ejemplo:
		//Public Class pepe
		//{
		if(esLlaveAbierta(linea)){
			//busco la llave en la linea
			if(linea.indexOf("{") > -1){
				//aumento la cantidad de llaves
				cantDeLlaves++;
				//Si la cantidad de llaves es 1, es inicio de clase
				if(cantDeLlaves == 1){
					//como la linea era solo una llave, le mando la linea anterior que tiene el nombre de la clase.
					String linea2 = this.lineasDeCodigo.get(--i);
					//le mando la linea que tiene el nombre
					agregarClase(linea2);
					//agrego la linea a la clase
					this.agregarLineasALaClase(linea2);
				}else
				//si la cantidad de llaves el 2, se inicio un metodo
				if(cantDeLlaves == 2){
					//como la linea era solo una llave, le mando la linea anterior que tiene el nombre del metodo
					String linea2 = this.lineasDeCodigo.get(--i);
					//le mando la linea que tiene el nombre
					agregarMetodo(linea2);
					//agrego la linea al metodo
					agregarLineasAlMetodo(linea2);
				}
				//retorno 1 que significa que se agrego una clase o metodo
				return 1;
			}
		}
		//Si la linea no tiene solamente la llave. por ejemplo: Public class pepe{
		if(!esLlaveAbierta(linea)){
			//busco la llave abierta
			if(linea.indexOf("{") > -1){
				//aumento la cantidad de llaves
				cantDeLlaves++;
				//Si la cantidad de llaves es 1, es inicio de clase
				if(cantDeLlaves == 1){
					//le mando la linea actual porque esa tiene el nombre de la clase
					linea = this.lineasDeCodigo.get(i);
					agregarClase(linea);
				}else
				//si la cantidad de llaves el 2, se inicio un metodo
				if(cantDeLlaves == 2){
					linea = this.lineasDeCodigo.get(i);
					agregarMetodo(linea);
				}
				//retorno 1 que significa que se agrego una clase o metodo
				return 1;
			}
		}
		
		//Si tiene una llave cerrada, significa que termino un bloque, pero no se sabe si es fin de bloque if, for, o de clase o metodo.
		//Eso lo sacas con la cantidad de llaves.
		if(linea.indexOf("}") > -1){
			cantDeLlaves--;
			//retorno -1 que significa que se cerro una llave, puede o no ser fin de clase o metodo. 
			return -1;
		}
		//Retorno 0, que significa que existio llave
		return 0;
	}

	private boolean esLlaveAbierta(String linea) {
		//a la linea, le borro la llave abierta y despues me fijo si es una linea en blanco 
		return linea.replace("{", "").matches("(|\\s+)");
	}

	private void agregarMetodo(String linea) {
		String[] palabras = linea.split(" ");
		//System.out.println("LINEA METODO: ");
		int indiceParentesis;
		String nombreMetodo = "";
		for (int i = 0; i < palabras.length; i++) {
			//System.out.println(i + ": " + palabras[i]);
			indiceParentesis = palabras[i].indexOf("(");
			if(indiceParentesis > -1){
				nombreMetodo = palabras[i].substring(0, indiceParentesis);
			}
		}
		//System.out.println(nombreMetodo);
		int sizeClases = this.clases.size() -1;
		this.clases.get(sizeClases).addMetodo(nombreMetodo);
	}
	
	private void agregarClase(String linea) {
		//System.out.println("----LINEA CLASE---");
		String[] palabras = linea.split(" ");
		String nombreClase;
		if(palabras[2].indexOf("{") > -1){
			nombreClase = palabras[2].substring(0, palabras[2].length()-1);
		}else{
			nombreClase = palabras[2];
		}
		//System.out.println(nombreClase);
		Clase c = new Clase(nombreClase);
		this.clases.add(c);
	}

}
