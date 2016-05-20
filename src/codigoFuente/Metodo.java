package codigoFuente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;

public class Metodo {
	Set<String> setOperadores = new HashSet<String>();
	/*Set que contendra los operandos del codigo fuente*/
	Set<String> setOperandos = new HashSet<String>();
	private String nombre;
	private List<String> codigoFuente;
	private int lComentarioSimple, 
				lComentarioMultilinea;
	private Integer complejidadCiclomatica;	
	private Integer longitudHalstead,
					cantidadOperadoresUnicos,
					cantidadOperadores,
					cantidadOperandosUnicos,
					cantidadOperandos;	
	private double volumenHalstead;
	public Metodo(String nombre){
		this.nombre = nombre;
		codigoFuente = new ArrayList<String>();
	}
	
	public String getNombre(){
		return this.nombre;
	}
	public int getLongitud(){
		return this.longitudHalstead;
	}
	public double getVolumen() {
		return this.volumenHalstead;
	}
	public int getLCodigo(){
		if (this.codigoFuente.get(1).indexOf("{") > -1) {
			return this.codigoFuente.size() - 3;
		}
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
		cantidadOperadoresUnicos = 0;
		cantidadOperadores = 0;
		cantidadOperandosUnicos = 0;
		cantidadOperandos = 0;	
		
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
		calcularHalstead();
	}
	
	private void calcularComplejidadCiclomatica() {
		//Listado de palabras que representan un salto en el curso de decision.
		String decisiones[] = {"if", "else", "case", "default", "for", "while", "catch", "throw"};
		String condiciones[] = {"&&", "||"};
        int cantidad;
		for (int i = 0; i < codigoFuente.size(); i++) {
			String linea = codigoFuente.get(i);
			if(!esBlanco(linea) && !esComentarioSimple(linea)){
				int comentarioMultilinea = esComentarioMultiple(linea, i);
				if( comentarioMultilinea < 0){
					if (linea.matches(".*\\W*(if|else|case|default|while|for|catch|throw)\\W.*")) {
						for(String palabra : decisiones) {
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
	private void calcularHalstead() {
		
		Set<String> setOperadores = new HashSet<String>();
		/*
		for (String linea : codigoFuente) {
            buscarOperadores(linea);
            buscarOperandos(linea);
        }
		*/
		for (int i = 1; i < codigoFuente.size(); i++) {
			String linea = codigoFuente.get(i);
			if(!esBlanco(linea) && !esComentarioSimple(linea)){
				int comentarioMultilinea = esComentarioMultiple(linea, i);
				if( comentarioMultilinea < 0){
					//System.out.println(linea);
					buscarOperadores(linea);
		            buscarOperandos(linea);
				}
			}
		}
        
        this.cantidadOperadoresUnicos = this.setOperadores.size();
        this.cantidadOperandosUnicos = this.setOperandos.size();
        
        this.longitudHalstead = this.cantidadOperadores + this.cantidadOperandos;
        this.volumenHalstead = (this.longitudHalstead * (Math.log(this.cantidadOperadoresUnicos + this.cantidadOperandosUnicos)) / Math.log(2));
        // Hago esa cuenta para calcular el log en base 2. log en base 2 = log(x) / log(2)

	}
    
    void buscarOperadores(String linea) {
    	/*Listado de palabras que consideramos operadores*/
		String operadores [] = { "{" , "}", "++", "(" , ")", "[", "]", "if", "else", "case", "default", "for", "while", "catch", "throw",
								"+", "-", "*", "/", "==", "!=", "=", "<=", ">=", "<", ">",
								"&&", "||", "and", "or", "equal to",};

		/*Set que contendra los operadores del codigo fuente*/
		for(int i = 0; i < operadores.length - 1; i++)
    		if(linea.contains(operadores[i])) {
    			this.cantidadOperadores += 1;
    			this.setOperadores.add(operadores[i]);
    		}
    }
    
    void buscarOperandos(String linea) {
    	String operandos[] = linea.split("^.*(if|else|case|default|for|while|catch|throw|\\+|-|\\*|\\/"
    									 + "|={1}?|!=|={2}?|<=|>=|<{1}?|>{1}?|&&|\\|{2}?|and|or|equal to|\\{|\\}|\\+\\+|\\(|\\)|\\[|\\]).*");

		
		/*Set que contendra los operandos del codigo fuente*/

    	for(int i = 0; i < operandos.length ; i++)
    	{
    		this.cantidadOperandos += 1;
    		this.setOperandos.add(operandos[i]);
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
	
	public int contarPalabraEnMetodo(String palabraABuscar){
		int cant = 0;
		for (int i = 1; i < this.codigoFuente.size()-1; i++) {
			int opt = codigoFuente.get(i).indexOf(palabraABuscar);
			if(opt > -1)
				cant ++;
		}
		return cant;
	}
	
}
