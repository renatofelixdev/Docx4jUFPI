package report.model;

import report.enums.Alinhamento;
import report.enums.TipoMesclagemVertical;

public class ItemColuna extends ItemRelatorio{
	
	private Integer mesclarHorizontal;
	private String corDeFundo;
	private Alinhamento alinhamentoVertical;
	private Alinhamento alinhamentoHorizontal;
	private TipoMesclagemVertical mesclarVertical;
	private boolean borderBottom;
	private boolean borderLeft;
	private boolean borderTop;
	private boolean borderRight;

	public ItemColuna(){
		this.corDeFundo = "ffffff";
		this.mesclarHorizontal = 0;
		this.borderBottom = true;
		this.borderLeft = true;
		this.borderTop = true;
		this.borderRight = true;
		this.alinhamentoHorizontal = null;
		this.alinhamentoVertical = null;
	}
	
	public Integer getMesclarHorizontal() {
		return mesclarHorizontal;
	}
	
	public void setMesclarHorizontal(Integer mesclarHorizontal) {
		this.mesclarHorizontal = mesclarHorizontal;
	}
	
	public String getCorDeFundo() {
		return corDeFundo;
	}
	
	public void setCorDeFundo(String corDeFundo) {
		this.corDeFundo = corDeFundo;
	}
	public Alinhamento getAlinhamentoVertical() {
		return alinhamentoVertical;
	}

	public void setAlinhamentoVertical(Alinhamento alinhamentoVertical) {
		this.alinhamentoVertical = alinhamentoVertical;
	}

	public Alinhamento getAlinhamentoHorizontal() {
		return alinhamentoHorizontal;
	}

	public void setAlinhamentoHorizontal(Alinhamento alinhamentoHorizontal) {
		this.alinhamentoHorizontal = alinhamentoHorizontal;
	}
	
	public TipoMesclagemVertical getMesclarVertical() {
		return mesclarVertical;
	}
	
	public void setMesclarVertical(TipoMesclagemVertical mesclar) {
		this.mesclarVertical = mesclar;
	}

	public boolean isBorderBottom() {
		return borderBottom;
	}

	public void setBorderBottom(boolean borderBottom) {
		this.borderBottom = borderBottom;
	}

	public boolean isBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(boolean borderLeft) {
		this.borderLeft = borderLeft;
	}

	public boolean isBorderTop() {
		return borderTop;
	}

	public void setBorderTop(boolean borderTop) {
		this.borderTop = borderTop;
	}

	public boolean isBorderRight() {
		return borderRight;
	}

	public void setBorderRight(boolean borderRight) {
		this.borderRight = borderRight;
	}
	
	public void semBordas(){
		this.borderBottom = false;
		this.borderLeft = false;
		this.borderTop = false;
		this.borderRight = false;
	}

	@Override
	public void construir(Object... args) {
	}
	
}
