package codigoFuente;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodigoFuente{

	private ArrayList<String> lineasDeCodigo; 
	private double volumenHalstead;
	private int lComentarioSimple = 0, 
			lComentarioMultilinea = 0, 
			lBlanco = 0, 
			lCodigo = 0;
	private Integer longitudHalstead,
					cantidadOperadoresUnicos = 0,
					cantidadOperadores = 0,
					cantidadOperandosUnicos = 0,
					cantidadOperandos = 0;	
	
	public CodigoFuente(){
		this.lineasDeCodigo = new ArrayList<String>();
	}
	
	public void addLinea(String linea){
		this.lineasDeCodigo.add(linea);
	}
	public String[] lineasdeCodigo() {
		String[] arr = new String[this.lineasDeCodigo.size()];
		arr = this.lineasDeCodigo.toArray(arr);
		return arr;
	}
	public void analizarCodigo(){
		for (int i = 0; i < lineasDeCodigo.size(); i++) {
			String linea = lineasDeCodigo.get(i);
			if(esBlanco(linea)){
				//System.out.println("------BLANCO:----- [" + linea +"]");
				lBlanco++;
			}else 
				if(esComentarioSimple(linea)){
				//System.out.println("COMENTARIO SIMPLE: [" + linea +"]");
				lComentarioSimple ++;
			}else{
				int comentarioMultilinea = esComentarioMultiple(linea, i);
				if( comentarioMultilinea > -1){
					lComentarioMultilinea ++;
					//System.out.println("COMENTARIO MULTIP: \n[");
					for (int j=0; j<=comentarioMultilinea; j++) {
						//System.out.println(this.lineasDeCodigo.get(j+i));
					}
					i += comentarioMultilinea;
					//System.out.println("]");
				}else{
					lCodigo++;
				}
			}
		}
		
		System.out.println("Lineas totales: " + lineasDeCodigo.size());
		System.out.println("Cant Blancos: " + lBlanco);
		System.out.println("Cant C. Simp: " + lComentarioSimple);
		System.out.println("Cant C. Mult: " + lComentarioMultilinea);
		System.out.println("Cant Condigo: " + lCodigo);
		System.out.printf("Porcentaje de codigo: %.2f", (lCodigo*100./(lineasDeCodigo.size())));
		System.out.println();
		System.out.printf("Porcentaje de comentarios: %.2f",  100-(lCodigo*100./(lineasDeCodigo.size())));
		System.out.println();
		System.out.println("Complejidad ciclomatica: " + complejidadCiclomatica(lineasDeCodigo));
		System.out.println("Halstead: " + calcularHalstead(lineasDeCodigo));
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
	
	public int complejidadCiclomatica(List<String> lineasArchivo) {

        //Log log = new Log();
        //La complejidad ciclomática mínima.
        int valorCC = 1;
        //Listado de palabras que representan un salto en el curso de decision.
        String keywords[] = {"if", "else", "case", "default", "for", "while", "catch", "throw"};
        String condiciones[] = {"&&", "||"};
        int cantidad;
        
        for (String linea : lineasArchivo) {
           // log.debug("Leyendo linea " + linea);

            if (linea.matches(".*\\W*(if|else|case|default|while|for|catch|throw)\\W.*")) {
            	for(String palabra : keywords) {
		        	cantidad = (linea.length() - linea.replace(palabra, "").length()) / palabra.length();
		        	if(cantidad > 0) {
		        		valorCC += cantidad;
		        		//log.debug(cantidad +" "+ palabra + " encontrado/s.");
		        	}
            	}
            }
            
            if (linea.matches(".*(&&|\\|\\|).*")) {
            	for(String simbolo : condiciones) {
            		cantidad = (linea.length() - linea.replace(simbolo, "").length()) / simbolo.length();
		        	if(cantidad > 0) {
		        		valorCC += cantidad;
		        		//log.debug(cantidad +" "+ simbolo + " encontrado/s.");
		        	}
            	}
            }
        }
        return valorCC;
    }
	
	public String calcularHalstead(List<String> lineasArchivo) {
		
		/*Set que contendra los operadores del codigo fuente*/
		Set<String> setOperadores = new HashSet<String>();
		
		/*Set que contendra los operandos del codigo fuente*/
		Set<String> setOperandos = new HashSet<String>();

//    	Log log = new Log();
    	// Inicializo las metricas en 0
    	this.longitudHalstead = 0;
    	this.volumenHalstead = 0.0;
    	    	
        for (String linea : lineasArchivo) {
            //log.debug("Leyendo línea " + linea);
            
            buscarOperadores(linea);
            buscarOperandos(linea);
        }
        
        this.cantidadOperadoresUnicos = setOperadores.size();
        this.cantidadOperandosUnicos = setOperandos.size();
        
        this.longitudHalstead = this.cantidadOperadores + this.cantidadOperandos;
        this.volumenHalstead = (this.longitudHalstead * (Math.log(this.cantidadOperadoresUnicos.doubleValue() + 
        							  Math.log(this.cantidadOperandosUnicos.doubleValue())) / Math.log(2)));
        					// Hago esa cuenta para calcular el log en base 2. log en base 2 = log(x) / log(2)
        return String.format("Longitud: %d - Volumen: %.2f - (Operadores %d - Operandos %d)", 
	              longitudHalstead, volumenHalstead, cantidadOperadores, cantidadOperandos);
	}
    
    void buscarOperadores(String linea) {
    	/*Listado de palabras que consideramos operadores*/
		String operadores [] = {"if", "else", "case", "default", "for", "while", "catch", "throw",
								"+", "-", "*", "/", "==", "!=", "=", "<=", ">=", "<", ">",
								"&&", "||", "and", "or", "equal to"};

		/*Set que contendra los operadores del codigo fuente*/
		Set<String> setOperadores = new HashSet<String>();
    	for(int i = 0; i < operadores.length - 1; i++)
    		if(linea.contains(operadores[i])) {
    			this.cantidadOperadores += 1;
    			setOperadores.add(operadores[i]);
    		}
    }
    
    void buscarOperandos(String linea) {
    	String operandos[] = linea.split("^.*(if|else|case|default|for|while|catch|throw|\\+|-|\\*|\\/"
    									 + "|={1}?|!=|={2}?|<=|>=|<{1}?|>{1}?|&&|\\|{2}?|and|or|equal to).*");

		
		/*Set que contendra los operandos del codigo fuente*/
		Set<String> setOperandos = new HashSet<String>();
    	for(int i = 0; i < operandos.length ; i++)
    	{
    		this.cantidadOperandos += 1;
    		setOperandos.add(operandos[i]);
    	}
    }
	
    List<String> listarMetodos2() {
		Pattern REGEX = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])");
		ArrayList<String> pepe = new ArrayList<String>();
		
		for (int i = 0; i < this.lineasDeCodigo.size(); i++) {
			Matcher m = REGEX.matcher(this.lineasDeCodigo.get(i));
			if(m.find()) {
    			System.out.println(i);
    			pepe.add(this.lineasDeCodigo.get(i));
    		}
    		if(this.lineasDeCodigo.get(i).matches("}")) {
    			System.out.println(i);
    		}
		}
		return pepe;
    }
    
    List<String> listarMetodos(String cod[]) {
		Pattern REGEX = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])");
		ArrayList<String> pepe = new ArrayList<String>();
		
    	for(int i = 0; i < cod.length; i++) {
    		Matcher m = REGEX.matcher(cod[i]);
    		if(m.find()) {
    			System.out.println(i);
    			pepe.add(cod[i]);
    		}
    		if(cod[i].matches("}")) {
    			System.out.println(i);
    		}
    	}
    	return pepe;
    }
    
    List<String> listarClases(){
    	ArrayList<String> clases = new ArrayList<String>();
    	return clases;
    }
}
