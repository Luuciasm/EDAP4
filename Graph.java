import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {
	
      HashMap<String, Integer> th;
      String[] keys;
      ArrayList<Integer>[] adjList;
	
	int numVertices;              // número de vértices en el grafo
    String[] vertices;            // valores de los vértices
    List<Integer>[] listaAdy;     // lista de adyacencia (más eficiente que matriz)
    private Map<String, Integer> indiceVertice; // mapeo nombre -> índice

      
      public Graph() {
    	  numVertices = 0;
          vertices = new String[0];
          listaAdy = new ArrayList[0];
          indiceVertice = new HashMap<>();
      }
	
      
      public void crearGrafo(MapaAutores autores, MapaPubli publicaciones) {
	  	// Post: crea el grafo desde la lista de autores
		//       Los nodos son nombres de autores
    	  
   	    // Paso 1: llenar th�
   	    th = new HashMap<String, Integer>();
   	    int cont = 0;
   	    for (Autor a : autores.getMapaAutores().values()) {
   	    	if (!th.containsKey(a.getNombre())) {
   	    		th.put(a.getNombre(), cont);
   	    		cont++;
        	}
	    }

   	    // Paso 2: llenar keys�
   	    keys = new String[th.size()];
   	    for (String nombreAutor : th.keySet()) {
   	    	keys[th.get(nombreAutor)] = nombreAutor;
    	}

   	    // Paso 3: llenar adjList�
   	    adjList = new ArrayList[th.size()];
   	    for (int i = 0; i < adjList.length; i++) {
   	    	adjList[i] = new ArrayList<Integer>();
    	}

	    // Recorremos cada publicación y conectamos a todos sus autores entre sí
	    HashMap<String, ArrayList<String>> mapaPublisAutor = publicaciones.getMapaPublisAutor();
	    for (String idPubli : mapaPublisAutor.keySet()) {
	        ArrayList<String> autoresPubli = mapaPublisAutor.get(idPubli);
	        for (int i = 0; i < autoresPubli.size(); i++) {
	            Autor autor1 = autores.buscarAutor(autoresPubli.get(i));  
	            if (autor1 != null && th.containsKey(autor1.getNombre())) {
	                int pos1 = th.get(autor1.getNombre());
	                for (int j = i + 1; j < autoresPubli.size(); j++) {
	                    Autor autor2 = autores.buscarAutor(autoresPubli.get(j));
	                    if (autor2 != null && th.containsKey(autor2.getNombre())) {
	                        int pos2 = th.get(autor2.getNombre());
	                        if (!adjList[pos1].contains(pos2)) {
	                            adjList[pos1].add(pos2);
	                        }
	                        if (!adjList[pos2].contains(pos1)) {
	                            adjList[pos2].add(pos1);
	                        }
	                    }
	                }
	            }
	        }
	    }
	}



	
	public void print(){
	   for (int i = 0; i < adjList.length; i++){
		System.out.print("Element: " + i + " " + keys[i] + " --> ");
		for (int k: adjList[i])  System.out.print(keys[k] + " ### ");
		
		System.out.println();
	   }
	}
	
	public boolean estanConectados1(String a1, String a2){
	    if(!th.containsKey(a1) || !th.containsKey(a2)) {
	        return false;
	    }
	    if(a1.equals(a2)) {
	        return true;
	    }  
	    Queue<Integer> porExaminar = new LinkedList<Integer>();
	    int pos1 = th.get(a1);
	    int pos2 = th.get(a2);
	    boolean[] examinados = new boolean[th.size()];
	    porExaminar.add(pos1);
	    examinados[pos1] = true;
	    while(!porExaminar.isEmpty()) {
	        int actual = porExaminar.remove();
	        if(actual == pos2) {
	            return true;
	        }
	        for(int vecino : adjList[actual]) {
	            if(!examinados[vecino]) {
	                porExaminar.add(vecino);
	                examinados[vecino] = true;
	            }
	        }
	    }
	    return false;
	}

	public ArrayList<String> estanConectados2(String a1, String a2){
		ArrayList<String> rdo = new ArrayList<String>();
		if(!th.containsKey(a1) || !th.containsKey(a2)) {
			return rdo;
		}
		if(a1.equals(a2)) {
			rdo.add(a1);
			return rdo;
		}
		HashMap<String, String> mapa = new HashMap<String, String>();
		mapa.put(a1, a2);
		Queue<Integer> porExaminar = new LinkedList<Integer>();
		int pos1 = th.get(a1);
		int pos2 = th.get(a2);
		boolean enc = false;
		boolean[] examinados = new boolean[th.size()];
		porExaminar.add(pos1);
		while(!enc && !porExaminar.isEmpty()) {
			int actual = porExaminar.remove();
			if(actual == pos2) {
				enc = true;
			}else {
				examinados[actual] = true;
				for (int j: adjList[actual]) {
					if(!examinados[j]) {
						porExaminar.add(j);
						mapa.put(keys[j], keys[actual]);
					}
				}
			}
		}
		if(enc) {
			rdo.add(a2);
			String nom1 = "";
			String nom2 = a2;
			while(!nom1.equals(a1)) {
				nom1 = mapa.get(nom2);
				rdo.add(nom1);
				nom2 = nom1;
			}
		}
		return rdo;

	}
	
	
	public HashMap<String, Double> randomWalkPageRank(){
	    HashMap<String, Double> resul = new HashMap<>();
	    Random aleatorio = new Random(System.currentTimeMillis());
	    int total = 0;
	    
	    // Inicializar contador de visitas a 0 para todos los nodos
	    for(String autor: th.keySet()) {
	        resul.put(autor, 0.0);
	    }
	    
	    // Realizar un random walk desde cada nodo del grafo
	    for(String autorInicial: th.keySet()) {
	        HashSet<String> examinados = new HashSet<>();
	        String autorActual = autorInicial;
	        boolean continuarRecorrido = true;
	        
	        // Contar visita al nodo inicial
	        int visitas = resul.get(autorActual).intValue();
	        visitas++;
	        total++;
	        resul.put(autorActual, (double)visitas);
	        examinados.add(autorActual);

	        while(continuarRecorrido) {
	            // Generar número aleatorio para decidir si continuar (85% probabilidad)
	            int prob = aleatorio.nextInt(100);
	            
	            if(prob >= 85) {
	                // El usuario decide terminar (15% de probabilidad)
	                continuarRecorrido = false;
	            } else {
	                int pos = th.get(autorActual);
	                
	                // Verificar si el nodo tiene enlaces salientes
	                if(adjList[pos].size() == 0) {
	                    continuarRecorrido = false;
	                } else {
	                    // Seleccionar ALEATORIAMENTE uno de los enlaces salientes
	                    int indiceAleatorio = aleatorio.nextInt(adjList[pos].size());
	                    String siguienteAutor = keys[adjList[pos].get(indiceAleatorio)];
	                    
	                    // Verificar si ya fue visitado en este recorrido
	                    if(examinados.contains(siguienteAutor)) {
	                        continuarRecorrido = false;
	                    } else {
	                        autorActual = siguienteAutor;
	                        examinados.add(autorActual);
	                        
	                        visitas = resul.get(autorActual).intValue();
	                        visitas++;
	                        total++;
	                        resul.put(autorActual, (double)visitas);
	                    }
	                }
	            }
	        }
	    }

	    // Normalizar los resultados dividiendo por el total de accesos
	    for(String autor: resul.keySet()) {
	        double x = resul.get(autor) / total;
	        resul.put(autor, x);
	    }
	    
	    return resul;
	}
	
	

	public HashMap<String, Double> pageRank(){
	    
	    HashMap<String, Double> rankings = new HashMap<>();
	    int totalNodos = th.keySet().size();
	    double factorAmortiguacion = 0.85;
	    double valorInicial = 1.0 / totalNodos;


	    // Inicializar todos los nodos con la misma probabilidad
	    for(String autor: th.keySet()) {
	        rankings.put(autor, valorInicial);
	    }

	    // Crear HashMap inverso (índice -> nombre)
	    HashMap<Integer, String> indiceANombre = new HashMap<>();
	    for(String autor: th.keySet()) {
	        indiceANombre.put(th.get(autor), autor);
	    }

	    // Crear grafo inverso (quién apunta a cada nodo)
	    HashMap<String, ArrayList<String>> grafoInverso = new HashMap<>();
	    
	    // Inicializar el grafo inverso
	    for(String autor: th.keySet()) {
	        grafoInverso.put(autor, new ArrayList<>());
	    }
	    
	    // Llenar el grafo inverso: para cada nodo, guardar quién le apunta
	    for(String nodoOrigen: th.keySet()) {
	        int indiceOrigen = th.get(nodoOrigen);
	        
	        // Para cada nodo al que apunta nodoOrigen
	        for(Integer indiceDestino: adjList[indiceOrigen]) {
	            // AHORA ES O(1) en vez de O(n)
	            String nodoDestino = indiceANombre.get(indiceDestino);
	            
	            if(nodoDestino != null) {
	                grafoInverso.get(nodoDestino).add(nodoOrigen);
	            }
	        }
	    }

	    double diferenciaAnterior = 1.0;
	    double diferenciaActual = 0.0;
	    boolean convergencia = false;
	    int iteracion = 0;

	    while(!convergencia) {
	        iteracion++;
	        if(iteracion % 10 == 0) {
	        }
	        
	        diferenciaActual = 0.0;
	        HashMap<String, Double> nuevo = new HashMap<>();

	        for(String autor: rankings.keySet()) {
	            double acumulador = 0.0;

	            // Solo recorrer los nodos que apuntan hacia 'autor'
	            for(String nodoOrigen: grafoInverso.get(autor)) {
	                int indiceOrigen = th.get(nodoOrigen);
	                int enlacesSalientes = adjList[indiceOrigen].size();
	                
	                if(enlacesSalientes > 0) {
	                    acumulador += rankings.get(nodoOrigen) / enlacesSalientes;
	                }
	            }

	            // Aplicar la fórmula de PageRank
	            double nuevoValor = ((1.0 - factorAmortiguacion) / totalNodos) + (factorAmortiguacion * acumulador);
	            diferenciaActual += Math.abs(rankings.get(autor) - nuevoValor);
	            nuevo.put(autor, nuevoValor);
	        }
	        
	        rankings = nuevo;

	        if(Math.abs(diferenciaAnterior - diferenciaActual) < 0.0001) {
	            convergencia = true;
	        } else {
	            diferenciaAnterior = diferenciaActual;
	        }
	        
	        if(iteracion > 1000) {
	            break;
	        }
	    }
	    return rankings;
	}

}








