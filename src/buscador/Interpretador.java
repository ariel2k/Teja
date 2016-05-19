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
	
	private void pasarAList(){
		String linea = arch.leerArchivo();
		while(linea!=null){
			lineasDeCodigo.add(linea);
			linea = arch.leerArchivo();
		}
	}
	
	private void interpretarArchivo(){
		int cantDeLlaves = 0;
		for (int i = 0; i < lineasDeCodigo.size(); i++) {
			String linea = lineasDeCodigo.get(i);
			/*if(!esBlanco(linea) && !esComentarioSimple(linea)){
				int comentarioMultilinea = esComentarioMultiple(linea, i);
				if( comentarioMultilinea > -1){
					i += comentarioMultilinea;
				}else{*/
					int rta = buscarClaseOMetodo(linea,cantDeLlaves);
					agregarLinea(linea, cantDeLlaves, rta);
					cantDeLlaves += rta;
			//	}
		//	}
		}
	}
	
	private void agregarLinea(String linea, int cantDeLlaves, int rta) {
		// TODO Auto-generated method stub
		if(rta + cantDeLlaves > 1 || (cantDeLlaves == 2 && rta == -1)){
			agregarLineasAlMetodo(linea);
		}else
		if(rta + cantDeLlaves == 1){
			agregarLineasALaClase(linea);
		}
	}

	private void agregarLineasALaClase(String linea) {
		// TODO Auto-generated method stub
		int size = this.clases.size()-1;
		Clase c = this.clases.get(size);
		c.addLineaCodigo(linea, -1);
	}

	private void agregarLineasAlMetodo(String linea) {
		// TODO Auto-generated method stub
		int sizeClases = this.clases.size()-1;
		int sizeMetodosUltimaClase = this.clases.get(sizeClases).getNombresMetodos().size() -1;
		this.clases.get(sizeClases).addLineaCodigo(linea, sizeMetodosUltimaClase);
	}

	private int buscarClaseOMetodo(String linea, int cantDeLlaves) {
		if(linea.indexOf("{") > -1){
			cantDeLlaves++;
			if(cantDeLlaves == 1){
				agregarClase(linea);
			}else
			if(cantDeLlaves == 2){
				agregarMetodo(linea);
			}
			return 1;
		}
		if(linea.indexOf("}") > -1){
			cantDeLlaves--;
			return -1;
		}
		return 0;
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
		// TODO Auto-generated method stub
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

	private boolean esBlanco(String linea){
		/* () = subgrupo
		 * | = or
		 * \\s = algun caracter en blanco
		 * + = puede haber mas de uno
		 * regex: subgrupo de nada o conjunto de caracteres de separacion
		 * */
		return linea.matches("(|\\s+)");
	}
	
	private boolean esComentarioSimple(String linea){
		/* ? = concatenacion
		 * // = caracter que tiene que estar luego del conjunto de espacios en blanco
		 * .*$ = hasta el final de la linea
		 * */
		return linea.matches("^(|\\s+)?//.*$");
	}

	private int esComentarioMultiple(String linea, int index){
		if (esInicioDeComentarioMultiple(linea)) {
			int i = index;
			String lineaFinal = this.lineasDeCodigo.get(i);
			while(!esFinDeComentarioMultiple(lineaFinal) && i < this.lineasDeCodigo.size()){
				i++;		
				lineaFinal = this.lineasDeCodigo.get(i);
			}
			return i - index;
		}
		return -1;
	} 

	private boolean esInicioDeComentarioMultiple(String linea){
		return linea.indexOf("/*") > -1;
	}

	private boolean esFinDeComentarioMultiple(String linea){
		return linea.indexOf("*/") > -1;
	}
	
}
