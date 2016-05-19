package codigosEjemplo;

public class ClaseParaJugar 
{

	public void todoComentarioSimple()
	{
		//hola
		//yo
		//soy
		//un
		//comentario
	}
	
	public void todoComentarioMultiple(){
		/* HOla
		 * Soy
		 *Comentario
		 *multiple
		 * */
	}
	
	public void todoComentarioMultipleConLlave(){
		/* HOla
		 * Soy
		 *Comentario { 
		 *vamo a ver si entra a la llave
		 *}
		 *multiple
		 * */
	}
	
	public void mixComentarios(){
		/* HOla
		 *multiple
		 * */
		//hola
		//yo
		//soy
	}
	
	public void comentarioSYCodigo_mitad(){
		//hola
		//yo
		//soy
		System.out.println("hola");
		System.out.println("hola");
		System.out.println("hola");
	}
	
	public void comentarioSYCodigo_2_3(){
		//hola
		//yo
		System.out.println("hola");
		System.out.println("hola");
		System.out.println("hola");
	}
	
	public void comentarioSYCodigo_2_5(){
		//hola
		//yo
		System.out.println("hola");
		System.out.println("hola");
		System.out.println("hola");
		System.out.println("hola");
		System.out.println("hola");
	}
	
	public void ComplejidadCiclomatica_1(){
		System.out.println("hola");
	}
	
	public void ComplejidadCiclomatica_2(){
		if (2 > 1) 
			System.out.println("hola");
		
	}
	
	public void ComplejidadCiclomatica_2_2(){
		if (1 > 1) 
			System.out.println("hola");
		else
			System.out.println("chau");
		
	}
	
	public void ComplejidadCiclomaticaAND_3(){
		if (1 > 1 && 2 == 3) 
			System.out.println("hola");
		else
			System.out.println("chau");
	}
	
	public void ComplejidadCiclomaticaOR_3(){
		if (1 > 1 || 2 == 3) 
			System.out.println("hola");
		else
			System.out.println("chau");
	}
	
	public void CCFor_2(){
		for (int i = 0; i < 3; i++) {
			System.out.println("hola");
		}
	}
	
	public void CCFor_3(){
		for (int i = 0; i < 3; i++) {
			if (i>1) {
				System.out.println("es");
			}
			System.out.println("hola");
		}
	}
	
	public void FanIn_1(){
		FanOut_1();
	}
	
	public void FanOut_1(){
		
	}
	
	public void MetodoConLlaveAbajo_1()
	{
		//Que hacemo
	}
	
	
	
}
