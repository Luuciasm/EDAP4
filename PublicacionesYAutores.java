import java.util.ArrayList;

public class PublicacionesYAutores {
	
	private MapaPubli mp;
	private MapaAutores ma;
	
	public PublicacionesYAutores() {
		mp = new MapaPubli();
		ma = new MapaAutores();
		mp.cargarCitadas("Datuak/publications-citedPubs-all.txt");
		mp.cargarPublicaciones("Datuak/publications-titles-all.txt");
		mp.cargarPublisAutor("Datuak/publications-authors-all-final.txt");
		ma.cargarAutores("Datuak/authors-name-all.txt");
	}
	
	public ArrayList<Autor> obtenerListaAutores (Publicacion p){
		ArrayList<String> lista = mp.obtenerListaAutores(p);
		ArrayList<Autor> lista2 = new ArrayList<>();
		for (String idAutor: lista) {
			Autor a = ma.buscarAutor(idAutor);
			lista2.add(a);
		}
		return lista2;
	}

}
