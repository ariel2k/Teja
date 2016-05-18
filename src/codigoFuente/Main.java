package codigoFuente;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CodigoFuente cod = new CodigoFuente();
		//Espacios en blanco
		cod.addLinea("public int MetodoPiola(){");
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
		cod.addLinea("}");
		cod.addLinea("");
		cod.addLinea("private int resolver()");
		cod.addLinea("{");
		cod.addLinea("var x = 3 * 2;");
		cod.addLinea("}");
		cod.analizarCodigo();
		ArrayList<String> dat = (ArrayList<String>) cod.listarMetodos(cod.lineasdeCodigo());
		for(String i : dat) {
			System.out.println(i);
		}
	}

}
