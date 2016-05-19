package codigoFuente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Metodo {

	private String nombre;
	private List<String> codigoFuente;
	private int lComentarioSimple, 
				lComentarioMultilinea;
	private Integer complejidadCiclomatica;	
	
	public Metodo(String nombre){
		this.nombre = nombre;
		codigoFuente = new ArrayList<String>();
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public int getLCodigo(){
		return this.codigoFuente.size() - 2;
	}
	
	public int getLComentarios(){
		return this.lComentarioMultilinea + this.lComentarioSimple;
	}
	
	public int getComplijdadCiclomatica(){
		return this.complejidadCiclomatica;
	}
	
	public List<String> getCodigoFuente(){
		return this.codigoFuente;
	}
	
	public void addCodigo(String linea){
		codigoFuente.add(linea);
	}
	
	public void calcularMetricas(){
		lComentarioSimple = 0; 
		lComentarioMultilinea = 0; 
		complejidadCiclomatica = 1;	
		
		//Obtengo lineas de codigo y de comentarios
		for (int i = 0; i < codigoFuente.size(); i++) {
			String linea = codigoFuente.get(i);
			if(!esBlanco(linea) && esComentarioSimple(linea))
					lComentarioSimple ++;
			else{
				int comentarioMultilinea = esComentarioMultiple(linea, i);
				if( comentarioMultilinea > -1){
					lComentarioMultilinea += comentarioMultilinea;
					i += comentarioMultilinea - 1;
				}
			}
		}
		
		calcularComplejidadCiclomatica();
	}
	
	private void calcularComplejidadCiclomatica() {
		//Listado de palabras que representan un salto en el curso de decision.
		String keywords[] = {"if", "else", "case", "default", "for", "while", "catch", "throw"};
        String condiciones[] = {"&&", "||"};
        int cantidad;
		for (int i = 0; i < codigoFuente.size(); i++) {
			String linea = codigoFuente.get(i);
			if(!esBlanco(linea) && !esComentarioSimple(linea)){
				int comentarioMultilinea = esComentarioMultiple(linea, i);
				if( comentarioMultilinea < 0){
					if (linea.matches(".*\\W*(if|else|case|default|while|for|catch|throw)\\W.*")) {
		            	for(String palabra : keywords) {
				        	cantidad = (linea.length() - linea.replace(palabra, "").length()) / palabra.length();
				        	if(cantidad > 0) {
				        		this.complejidadCiclomatica += cantidad;
				        	}
		            	}
		            }
		            if (linea.matches(".*(&&|\\|\\|).*")) {
		            	for(String simbolo : condiciones) {
		            		cantidad = (linea.length() - linea.replace(simbolo, "").length()) / simbolo.length();
				        	if(cantidad > 0) {
				        		this.complejidadCiclomatica += cantidad;
				        	}
		            	}
		            }
				}
			}
		}
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
			String lineaFinal = this.codigoFuente.get(i);
			while(!esFinDeComentarioMultiple(lineaFinal) && i < this.codigoFuente.size()){
				i++;		
				lineaFinal = this.codigoFuente.get(i);
			}
			return i - index + 1;
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
