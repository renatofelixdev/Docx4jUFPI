package report.api.docx4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum TipoFonte {
	TAHOMA("Tahoma"),
	ARIAL("Arial"),
	TIMES_NEW_ROMAN("Times New Roman");

	private String tipo;

	TipoFonte (String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String toString(){
		return this.tipo;
	}	

	public static TipoFonte getEnumByDesc(String desc) {
		for (TipoFonte  elemento : TipoFonte.values()) {
			if(elemento.getTipo().equals(desc)) {
				return elemento;
			}
		}
		return null;
	}

	public static List<String> todasStrings() {
		List<String> lista = new ArrayList<String>();
		for (TipoFonte  elemento : TipoFonte.values()) {
			lista.add(elemento.getTipo());
		}
		return lista;
	}

	/**
	 * Retorna a lista de todas os tipos ordenados alfabeticamente
	 * @return
	 */
	public static List<TipoFonte> tipoFonteEmOrdem(){
		List<String> lista = TipoFonte.todasStrings();
		Collections.sort(lista, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareToIgnoreCase(s2);
			}
		});
		List<TipoFonte> tipoFonte = new ArrayList<TipoFonte>();
		for (String string : lista) {
			tipoFonte.add(TipoFonte.getEnumByDesc(string));
		}
		return tipoFonte;
	}
}
