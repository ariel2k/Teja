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
	
	
}
