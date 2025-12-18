import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Prueba {

	public static void main(String[] args) {
		MapaPubli mp = new MapaPubli();
		MapaAutores ma = new MapaAutores();
		mp.cargarPublicaciones("Datuak/publications-titles-all.txt");
        mp.cargarPublisAutor("Datuak/publications-authors-all-final.txt");
		ma.cargarAutores("Datuak/authors-name-all.txt");
		Graph grafo = new Graph();
		grafo.crearGrafo(ma, mp);
		
		long milisInicio = System.currentTimeMillis();
		HashMap<String, Double> rw = grafo.randomWalkPageRank();
		long milisFin = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución de randomWalkPageRank: "+(milisFin-milisInicio)+" milisegundos");
		
		long milisInicio1 = System.currentTimeMillis();
		HashMap<String, Double> pr = grafo.pageRank();
		long milisFin1 = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución de PageRank: "+(milisFin1-milisInicio1)+" milisegundos");

		// Mostrar top 10 de Random Walk
		System.out.println("Top 10 Random Walk:");
		rw.entrySet().stream()
		    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
		    .limit(10)
		    .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));

		// Mostrar top 10 de PageRank
		System.out.println("\nTop 10 PageRank:");
		pr.entrySet().stream()
		    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
		    .limit(10)
		    .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));


	}


}
