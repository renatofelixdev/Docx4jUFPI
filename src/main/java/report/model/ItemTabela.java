package report.model;

import java.util.ArrayList;
import java.util.List;

import report.interfaces.IItemBuilder;

public class ItemTabela extends ItemRelatorio{
	private boolean html;
	private List<ItemLinha> linhas;
	private int tamanhoCabecalho;
	
	public ItemTabela(IItemBuilder itemBuilder){
		this.setItemBuilder(itemBuilder);
		tamanhoCabecalho = 2;
		this.linhas = new ArrayList<ItemLinha>();
	}
	
	public ItemTabela(){
		tamanhoCabecalho = 2;
		this.linhas = new ArrayList<ItemLinha>();
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}
	
	public List<ItemLinha> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<ItemLinha> linhas) {
		this.linhas = linhas;
	}
	
	public void addAllLinhas(List<ItemLinha> linhas){
		this.linhas.addAll(linhas);
	}
	
	public void addLinha(ItemLinha linha){
		this.linhas.add(linha);
	}

	public int getTamanhoCabecalho() {
		return tamanhoCabecalho;
	}

	public void setTamanhoCabecalho(int tamanhoCabecalho) {
		this.tamanhoCabecalho = tamanhoCabecalho;
	}

	@Override
	public void construir(Object... args) {
		this.getItemBuilder().addTabela(this, args);		
	}
	
	public ItemColuna get(int linha, int coluna){
		return getLinhas().get(linha).getColunas().get(coluna);
	}
}
