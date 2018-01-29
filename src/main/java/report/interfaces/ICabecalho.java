package report.interfaces;

import java.util.List;

import report.model.ItemRelatorio;

public interface ICabecalho {
	public void construirCabecalho();
	public List<ItemRelatorio> getItens();
}
