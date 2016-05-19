package archivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Archivo {
	
	//Leer
	private File archivo;
    private FileReader fr;
    private BufferedReader br;
    
    //Region Constructor
    public Archivo(String path){
    	try {
	       archivo = new File (path);
	       fr = new FileReader (archivo);
	       br = new BufferedReader(fr);    
	    }
	    catch(Exception e){
	       e.printStackTrace();
	    }	
    }

	//Region Lectura
    public String leerArchivo(){
	    try{  
	    	String linea;
	        if((linea=br.readLine())!=null)
	            return linea;
	    }
	    catch(Exception e){
	       e.printStackTrace();
	    }
	    return null;  
    }
        //Region Cerrar
    public void cerrarArchivo(){
    	try{                    
	         if( null != fr ){   
	             fr.close();    
	          }                  
	       }catch (Exception e2){ 
	          e2.printStackTrace();
	       }
    }  
    
}
