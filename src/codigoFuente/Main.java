package codigoFuente;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CodigoFuente cod = new CodigoFuente();
		//Espacios en blanco
		cod.addLinea("");
		cod.addLinea(" ");
		cod.addLinea("  ");
		cod.addLinea("       ");
		cod.addLinea("  	     ");
		//Comentarios simples
		cod.addLinea("//");
		cod.addLinea("// hola");
		cod.addLinea("//hola");
		cod.addLinea(" //hola");
		cod.addLinea("	 //hola");
		
		//Comentarios multilinea
		cod.addLinea("/*     */");
		
		cod.addLinea("/*");
		cod.addLinea("*/");
		
		cod.addLinea("/*  ");
		cod.addLinea("    */");
		
		cod.addLinea("/*  ddd");
		cod.addLinea("  //hola   ");
		cod.addLinea("ddd */");
		
		cod.addLinea("aritonto");
		
		cod.addLinea("if(rocio=0 && ariel!=3)");
		cod.addLinea("while rocio==3 or hernan==5");
		
		cod.analizarCodigo();
	}

}
