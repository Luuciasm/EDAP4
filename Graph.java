import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Graph {
	
      HashMap<String, ArrayList<String>> enlaces;
	

      public void crearGrafo(ListaWebs lista){
		// Post: crea el grafo desde la lista de webs
		//       Los nodos son nombres de páginas web		
		
            // COMPLETAR CÓDIGO
	}
	
	public void print(){
	   for (String web : enlaces.keySet()){
		System.out.print("Element: " + web + " >>> ");
		for (String saliente : enlaces.get(web))  System.out.print(saliente + " ### ");
		
		System.out.println();
	   }
	}
	
	public boolean estanConectados1(String a1, String a2){
		Queue<String> porExaminar = new LinkedList<String>();
		
		boolean enc = false;



                 // COMPLETAR CÓDIGO
		
		return enc;

	}
	
	public ArrayList<String> estanConectados2(String a1, String a2) {
	       
	       // COMPLETAR CÓDIGO
	       
	       return null;
	
	}
	
       public HashMap<String, double> calcularRandomWalkRank(int nTests) {
		double d = 0.85; // damping factor
		Random r = new Random();
		
	       // COMPLETAR CÓDIGO

       }
       
       
       public HashMap<String, double> calcularPageRank() {
		boolean trace = false; // tracing the pagerank algorithm
		boolean damping = true; // tracing the pagerank algorithm
			
	       // COMPLETAR CÓDIGO

       }
       
       public void imprimirLosDeMejorPageRank(double[] pr, int n) { // inefficient but valid!
       // Post: imprime los n elementos de mayor valor.
       //       Es ineficiente porque calcula los máximos consecutivamente, y borra el máximo anterior, es decir, borra los valores de entrada
       //       Puede ser útil para visualizar los resultados
		for (int x = 1; x <= n; x++) {
			double max = -1;
			int iMax = -1;
			for (int j = 0; j < pr.length; j++)
				if (pr[j] > max) {
					max = pr[j];
					iMax = j;
				}
			System.out.println("The " + x + "th best element is "
					+ id2String[iMax] + " with value " + max);
			pr[iMax] = -1; // delete the maximum
		}
	}
	
}
