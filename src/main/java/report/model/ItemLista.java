package report.model;

import report.interfaces.IItemBuilder;

public class ItemLista extends ItemRelatorio{
	private int nivel;
	private boolean html;
	private boolean estarNoSumario;
	private boolean marcado;
	private boolean titulo;
	private String marcacao;
	private double espacoAntes;
	private double espacoDepois;
	private double espacoLinha;
	private int numbering;
	private String itemizacao;
	
	public ItemLista(IItemBuilder itemBuilder){
		this.setItemBuilder(itemBuilder);
		this.html = false;
		this.estarNoSumario = true;
		this.marcado = false;
		this.titulo = false;
		this.espacoAntes = 0.5;
		this.espacoDepois = 0.5;
		this.espacoLinha = 1.5;	
		this.numbering = 2;
		this.itemizacao = "";
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}
	
	public boolean isEstarNoSumario() {
		return estarNoSumario;
	}

	public void setEstarNoSumario(boolean estarNoSumario) {
		this.estarNoSumario = estarNoSumario;
	}

	public boolean isTitulo() {
		return titulo;
	}

	public void setTitulo(boolean titulo) {
		this.titulo = titulo;
	}

	@Override
	public void construir(Object... args) {
		this.getItemBuilder().addLista(this, args);		
	}

	public void setMarcado(boolean b) {
		this.marcado = b;
	}
	
	public boolean isMarcado(){
		return this.marcado;
	}

	public void setMarcacao(String string) {
		this.marcacao = string;
	}
	
	public String getMarcacao(){
		return this.marcacao;
	}

	public double getEspacoAntes() {
		return espacoAntes;
	}

	public void setEspacoAntes(double espacoAntes) {
		this.espacoAntes = espacoAntes;
	}

	public double getEspacoDepois() {
		return espacoDepois;
	}

	public void setEspacoDepois(double espacoDepois) {
		this.espacoDepois = espacoDepois;
	}

	public double getEspacoLinha() {
		return espacoLinha;
	}

	public void setEspacoLinha(double espacoLinha) {
		this.espacoLinha = espacoLinha;
	}

	public int getNumbering() {
		return numbering;
	}

	public void setNumbering(int numbering) {
		this.numbering = numbering;
	}

	public String getItemizacao() {
		return itemizacao;
	}

	public void setItemizacao(String itemizacao) {
		this.itemizacao = itemizacao;
	}
}
