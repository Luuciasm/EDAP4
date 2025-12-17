import java.util.HashMap;

public class Test {

	public static void main(String[] args) {
		MapaPubli mp = new MapaPubli();
		MapaAutores ma = new MapaAutores();
		mp.cargarPublicaciones("Datuak/publications-titles-all.txt");
        mp.cargarPublisAutor("Datuak/publications-authors-all-final.txt");
		ma.cargarAutores("Datuak/authors-name-all.txt");
		Graph grafo = new Graph();
		grafo.crearGrafo(ma, mp);
	
//		System.out.println("CREAR GRAFO:");
//        long milisInicio = System.currentTimeMillis();
//        grafo.crearGrafo(ma, mp);
//        long milisFin = System.currentTimeMillis();
//		System.out.println("Tiempo de ejecución: "+(milisFin-milisInicio)+" milisegundos");
//		
//		System.out.println("RANDOM WALK PAGE RANK:");
//		long milisInicio2 = System.currentTimeMillis();
//        grafo.randomWalkPageRank();
//        long milisFin2 = System.currentTimeMillis();
//		System.out.println("Tiempo de ejecución: "+(milisFin2-milisInicio2)+" milisegundos");
//		
//		System.out.println("RANDOM WALK PAGE RANK:");
//		long milisInicio3 = System.currentTimeMillis();
//        grafo.randomWalkPageRank();
//        long milisFin3 = System.currentTimeMillis();
//		System.out.println("Tiempo de ejecución: "+(milisFin3-milisInicio3)+" milisegundos");
		
		System.out.println("=== Prueba 2: Datos Reales ===\n");
        //System.out.println("Nodos en el grafo: " + grafo.obtenerTodosNodos().size());
        System.out.println();

        // Ejecutar Random Walk PageRank
        System.out.println("--- Calculando Random Walk PageRank ---");
        long inicioRW = System.currentTimeMillis();
        HashMap<String, Double> resultadosRW = grafo.randomWalkPageRank();
        long finRW = System.currentTimeMillis();
        System.out.println("Tiempo Random Walk: " + (finRW - inicioRW) + " ms");
        imprimirTopResultados(resultadosRW, 10);
        System.out.println();

        // Ejecutar Algoritmo PageRank (Iterativo)
        System.out.println("--- Calculando PageRank (Fórmula iterativa) ---");
        long inicioPR = System.currentTimeMillis();
        HashMap<String, Double> resultadosPR = grafo.pageRank();
        long finPR = System.currentTimeMillis();
        System.out.println("Tiempo PageRank: " + (finPR - inicioPR) + " ms");
        imprimirTopResultados(resultadosPR, 10);
       
        
        
        
        
	}
	private static void imprimirTopResultados(HashMap<String, Double> resultados, int topN) {
        System.out.println("\nTop " + topN + " autores por relevancia:");
        resultados.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())) // Orden descendente
                .limit(topN)
                .forEach(e -> System.out.printf("  %s: %.6f\n", e.getKey(), e.getValue()));
    }

}
