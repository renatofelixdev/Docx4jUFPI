package report.model;

import java.util.ArrayList;
import java.util.List;

import report.api.docx4j.TipoFonte;
import report.interfaces.ICabecalho;

/**
 * @author Vanderson Moura
 *
 */
public class IRelatorio {
	private List<ItemRelatorio> itensRelatorio;
	private ICabecalho cabecalho;
	private TipoFonte fonte;

	public ICabecalho getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(ICabecalho cabecalho) {
		this.cabecalho = cabecalho;
	}

	public IRelatorio() {
		this.itensRelatorio = new ArrayList<ItemRelatorio>();
	}
	
	public List<ItemRelatorio> getItensRelatorio() {
		return itensRelatorio;
	}

	public void setItensRelatorio(List<ItemRelatorio> itensRelatorio) {
		this.itensRelatorio = itensRelatorio;
	}

	public TipoFonte getFonte() {
		return fonte;
	}

	public void setFonte(TipoFonte fonte) {
		this.fonte = fonte;
	}
}
