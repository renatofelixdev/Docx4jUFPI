package report.model;

import java.io.InputStream;

import report.enums.Alinhamento;
import report.interfaces.IItemBuilder;

public class ItemImagem extends ItemRelatorio{
	
	private InputStream imagem;
	private Double altura;
	private Double largura;
	private Alinhamento alinhamento;
	
	public ItemImagem(IItemBuilder itemBuilder){
		this.setItemBuilder(itemBuilder);
	}
	
	public InputStream getImagem() {
		return imagem;
	}
	public void setImagem(InputStream imagem) {
		this.imagem = imagem;
	}
	public Double getAltura() {
		return altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}
	public Double getLargura() {
		return largura;
	}
	public void setLargura(Double largura) {
		this.largura = largura;
	}
	
	
	public Alinhamento getAlinhamento() {
		return alinhamento;
	}

	public void setAlinhamento(Alinhamento alinhamento) {
		this.alinhamento = alinhamento;
	}

	@Override
	public void construir(Object... args) {
		this.getItemBuilder().addImagem(this, args);		
	}
	
	

}
