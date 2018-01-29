package report.model;

import report.enums.SecaoDocx;
import report.enums.TamanhoPagina;
import report.interfaces.IItemBuilder;

public class ItemSecao extends ItemRelatorio{
	private TamanhoPagina tamanhoDaPagina;
	private boolean paisagem;
	private boolean cabecalho;
	private boolean ultimaSecao;
	private SecaoDocx tipoSecao;
	
	public ItemSecao(IItemBuilder itemBuilder){
		this.paisagem = false;
		this.cabecalho = true;
		this.setItemBuilder(itemBuilder);
		this.tipoSecao = SecaoDocx.CONTINUO;
	}
	
	public ItemSecao(TamanhoPagina tp, boolean paisagem, boolean cabecalho, IItemBuilder itemBuilder){
		this.paisagem = paisagem;
		this.tamanhoDaPagina = tp;
		this.cabecalho = cabecalho;
		this.setItemBuilder(itemBuilder);
		this.tipoSecao = SecaoDocx.CONTINUO;
	}

	public TamanhoPagina getTamanhoDaPagina() {
		return tamanhoDaPagina;
	}

	public void setTamanhoDaPagina(TamanhoPagina tamanhoDaPagina) {
		this.tamanhoDaPagina = tamanhoDaPagina;
	}

	public boolean isPaisagem() {
		return paisagem;
	}

	public void setPaisagem(boolean paisagem) {
		this.paisagem = paisagem;
	}

	public boolean isCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(boolean cabecalho) {
		this.cabecalho = cabecalho;
	}
	
	public boolean isUltimaSecao() {
		return ultimaSecao;
	}

	public void setUltimaSecao(boolean ultimaSecao) {
		this.ultimaSecao = ultimaSecao;
	}

	public SecaoDocx getTipoSecao() {
		return tipoSecao;
	}

	public void setTipoSecao(SecaoDocx tipoSecao) {
		this.tipoSecao = tipoSecao;
	}

	@Override
	public void construir(Object... args) {
		this.getItemBuilder().addSecao(this, args);
		
	}

	
	
	
}
