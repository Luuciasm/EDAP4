import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {
    private MapaAutores autores;
    private MapaPubli publicaciones;
    private Graph grafo;

	@Before
	public void setUp() throws Exception {
		autores = new MapaAutores();
        publicaciones = new MapaPubli();
        grafo = new Graph();
        
        autores.cargarAutores("Datuak/authors-name-all.txt");
        publicaciones.cargarPublicaciones("Datuak/publications-titles-all.txt");
        publicaciones.cargarPublisAutor("Datuak/publications-authors-all-final.txt");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCrearGrafo() {
		grafo.crearGrafo(autores, publicaciones);
		assertNotNull(grafo);
	}

//	@Test
//	public void testEstanConectados1() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testEstanConectados2() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testRandomWalkPageRank() {
	    grafo.crearGrafo(autores, publicaciones);
	    
	    HashMap<String, Double> resul = grafo.randomWalkPageRank();
	    
	    // Verificar que no es null
	    assertNotNull(resul);
	    
	    // Verificar que tiene autores
	    assertTrue(resul.size() > 0);
	    
	    // Verificar que tiene todos los autores del grafo
	    assertEquals(grafo.th.size(), resul.size());
	    
	    // Verificar que todos los valores son válidos (entre 0 y 1)
	    for(String autor : resul.keySet()) {
	        Double valor = resul.get(autor);
	        assertTrue(valor >= 0.0);
	        assertTrue(valor <= 1.0);
	    }
	    
	    // Verificar que la suma de todos los valores es aproximadamente 1.0
	    double suma = 0.0;
	    for(Double valor : resul.values()) {
	        suma += valor;
	    }
	    assertTrue(Math.abs(suma - 1.0) < 0.01);
	    
	    // Verificar que hay al menos un autor con relevancia > 0
	    boolean hayValorPositivo = false;
	    for(Double valor : resul.values()) {
	        if(valor > 0.0) {
	            hayValorPositivo = true;
	            break;
	        }
	    }
	    assertTrue(hayValorPositivo);
	}

	@Test
	public void testPageRank() {
	    grafo.crearGrafo(autores, publicaciones);
	    
	    HashMap<String, Double> resul = grafo.pageRank();
	    
	    // Verificar que no es null
	    assertNotNull(resul);
	    
	    // Verificar que tiene autores
	    assertTrue(resul.size() > 0);
	    
	    // Verificar que tiene todos los autores del grafo
	    assertEquals(grafo.th.size(), resul.size());
	    
	    // Verificar que todos los valores son válidos (entre 0 y 1)
	    for(String autor : resul.keySet()) {
	        Double valor = resul.get(autor);
	        assertTrue(valor >= 0.0);
	        assertTrue(valor <= 1.0);
	    }
	    
	    // Verificar que la suma de todos los valores es aproximadamente 1.0
	    double suma = 0.0;
	    for(Double valor : resul.values()) {
	        suma += valor;
	    }
	    assertTrue(Math.abs(suma - 1.0) < 0.01);
	    
	    // Verificar que hay al menos un autor con relevancia > 0
	    boolean hayValorPositivo = false;
	    for(Double valor : resul.values()) {
	        if(valor > 0.0) {
	            hayValorPositivo = true;
	            break;
	        }
	    }
	    assertTrue(hayValorPositivo);
	}

}
