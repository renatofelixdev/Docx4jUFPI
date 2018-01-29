package report.interfaces;

import report.model.ItemImagem;
import report.model.ItemLista;
import report.model.ItemParagrafo;
import report.model.ItemQuebraPagina;
import report.model.ItemSecao;
import report.model.ItemSumario;
import report.model.ItemTabela;

public interface IItemBuilder {
	
	public void addParagrafo(ItemParagrafo itemParagrafo, Object... args);
	
	public void addImagem(ItemImagem itemImagem, Object... args);
	
	public void addSecao(ItemSecao itemSecao, Object... args);
	
	public void addLista(ItemLista itemLista, Object... args);
	
	public void addQuebraPagina(ItemQuebraPagina itemQuebraPagina, Object... args);
	
	public void addTabela(ItemTabela itemTabela, Object... args);
	
	public void addLegenda(ItemParagrafo itemParagrafo, Object... args);

	public void addSumario(ItemSumario itemSumario, Object[] args);
}
