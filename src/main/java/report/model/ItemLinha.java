package report.model;

import java.util.ArrayList;
import java.util.List;

public class ItemLinha {

	private List<ItemColuna> colunas;
	
	public ItemLinha(){
		this.colunas = new ArrayList<ItemColuna>();
	}
	
	public List<ItemColuna> getColunas() {
		return colunas;
	}
	public void setColunas(List<ItemColuna> colunas) {
		this.colunas = colunas;
	}
	
	public void addColuna(ItemColuna ic){
		this.colunas.add(ic);
	}
	
	public void addAllColunas(List<ItemColuna> ics){
		this.colunas.addAll(ics);
	}	
	
}
