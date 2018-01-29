package report.model;

import java.util.ArrayList;
import java.util.List;

import report.interfaces.IItemBuilder;

/**
 * @author Vanderson Moura
 *
 */
public abstract class ItemRelatorio {
	
	private List<ItemRelatorio> itensRelatorio;
	private String conteudo;
	private IItemBuilder itemBuilder;
	
	public ItemRelatorio() {
		this.itensRelatorio = new ArrayList<ItemRelatorio>();
	}
	
	public List<ItemRelatorio> getItensRelatorio() {
		return itensRelatorio;
	}
	public void setItensRelatorio(List<ItemRelatorio> itensRelatorio) {
		this.itensRelatorio = itensRelatorio;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
		
	public IItemBuilder getItemBuilder() {
		return itemBuilder;
	}

	public void setItemBuilder(IItemBuilder itemBuilder) {
		this.itemBuilder = itemBuilder;
	}

	public abstract void construir(Object... args);
	
}
