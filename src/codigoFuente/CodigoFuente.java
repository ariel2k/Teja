package codigoFuente;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CodigoFuente {

	private List<String> lineasDeCodigo; 
	private int lComentarioSimple, lComentarioMultilinea, lBlanco, lCodigo;
	
	public CodigoFuente(){
		this.lineasDeCodigo = new ArrayList<String>();
	}
	
	public void addLinea(String linea){
		this.lineasDeCodigo.add(linea);
	}
	
	public void analizarCodigo(){
		/*
		int i, j;
		for (i=0; i < this.lineasDeCodigo.size(); i++) {
			
			if(this.lineasDeCodigo.get(i).matches("/*.*$")){
				System.out.println("INI: ["+ this.lineasDeCodigo.get(i) + "]");
				j=i;
				while(this.lineasDeCodigo.get(j).matches("$* /")){ //<<-- JUNTAR SI SE DESCOMENTA * /
					System.out.println("MED: ["+ this.lineasDeCodigo.get(j) + "]");
					j++;
				}
				i=j;
				System.out.println("FIN: ["+ this.lineasDeCodigo.get(i) + "]");
			}			
		}
		*/
		int i=0;
		for (String linea : lineasDeCodigo) {
			if(esBlanco(linea))
				System.out.println("------BLANCO:----- [" + linea +"]");
			else if(esComentarioSimple(linea))
				System.out.println("COMENTARIO SIMPLE: [" + linea +"]");
			else{
				int comentarioMultilinea = esComentarioMultiple(linea, i);
				if( comentarioMultilinea > -1){
					System.out.println("COMENTARIO MULTIP: \n[");
					for (int j=0; j<=comentarioMultilinea; j++) {
						System.out.println(this.lineasDeCodigo.get(j+i));
					}
					System.out.println("]");
				}
			} 
			i++;
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
			while(!esFinDeComentarioMultiple(this.lineasDeCodigo.get(i))
				&& i < this.lineasDeCodigo.size()){
				i++;
			}
			return i - index;
		}
		return -1;
	} 

	private boolean esInicioDeComentarioMultiple(String linea){
		return linea.matches("^(|\\s+)?/*.*$?");
	}

	private boolean esFinDeComentarioMultiple(String linea){
		return linea.matches("\\*/");
	}
		
}
