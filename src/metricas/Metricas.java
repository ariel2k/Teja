package metricas;

import codigoFuente.*;

public class Metricas {

	private Clase clase;
	private Metodo m;
	
	public Metricas(Clase clase){
		this.clase = clase;
	}

	public void calcumarMetricas(int iMetodo) {
		m = clase.getMetodos().get(iMetodo);
		m.calcularMetricas();
	}

	public int getLComentarios() {
		return m.getLComentarios();
	}
	
	public int getLCodigo() {
		return m.getLCodigo();
	}

	public int getComplejidadCiclomatica() {
		return m.getComplijdadCiclomatica();
	}
	
	public int getFanIn(){
		int fanIn = 0;
		for (String nombreMetodo : clase.getNombresMetodos()) {
			fanIn += m.contarPalabraEnMetodo(nombreMetodo);
		}	
		return fanIn;
	}
	
	public int getFanOut(){
		int fanOut=0;
		for (Metodo metodo : clase.getMetodos()) {
			fanOut += metodo.contarPalabraEnMetodo(m.getNombre());
		}
		return fanOut;
	}
	
}
