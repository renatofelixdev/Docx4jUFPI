package report.model;

import report.api.docx4j.TipoFonte;
import report.enums.Alinhamento;
import report.enums.TipoLegenda;
import report.interfaces.IItemBuilder;

public class ItemParagrafo extends ItemRelatorio{
	private int recuo;
	private boolean recuoPrimeiraLinha;
	private Alinhamento alinhamento;
	private boolean negrito;
	private boolean italico;
	private boolean sublinhado;
	private TipoFonte fonte;
	private Integer tamanhoFonte;
	private boolean html;
	private double espacoAntes;
	private double espacoDepois;
	private double espacoLinha;
	private boolean legenda;
	private boolean legendaInicio;
	private ItemRelatorio referencia;
	private Integer recuoPersonalizado;
	private boolean estarNoSumario;
	private int nivel;
	private TipoLegenda tipoLegenda;
	private int nivelLegendaQuadro;
	
	public ItemParagrafo(IItemBuilder itemBuilder){
		this.recuo = 0;
		this.nivelLegendaQuadro = 0;
		this.alinhamento = Alinhamento.JUSTIFICADO;
		this.negrito = false;
		this.italico = false;
		this.sublinhado = false;
		this.fonte = TipoFonte.TIMES_NEW_ROMAN;
		this.tamanhoFonte = 24;
		this.html = false;
		this.espacoAntes = 0.5;
		this.espacoDepois = 0.5;
		this.espacoLinha = 1.5;	
		this.legenda = false;
		this.setItemBuilder(itemBuilder);
		this.recuoPrimeiraLinha = true;
		this.legendaInicio = false;
		this.recuoPersonalizado = null;
		this.estarNoSumario = false;
		this.tipoLegenda = TipoLegenda.FIGURA;
	}

	public Integer getRecuoPersonalizado() {
		return recuoPersonalizado;
	}

	public void setRecuoPersonalizado(Integer recuoPersonalizado) {
		this.recuoPersonalizado = recuoPersonalizado;
	}

	public int getRecuo() {
		return recuo;
	}
	public void setRecuo(int recuo) {
		this.recuo = recuo;
	}
	public Alinhamento getAlinhamento() {
		return alinhamento;
	}
	public void setAlinhamento(Alinhamento alinhamento) {
		this.alinhamento = alinhamento;
	}
	public boolean isNegrito() {
		return negrito;
	}
	public void setNegrito(boolean negrito) {
		this.negrito = negrito;
	}
	public boolean isItalico() {
		return italico;
	}
	public void setItalico(boolean italico) {
		this.italico = italico;
	}
	public boolean isSublinhado() {
		return sublinhado;
	}
	public void setSublinhado(boolean sublinhado) {
		this.sublinhado = sublinhado;
	}
	public TipoFonte getFonte() {
		return fonte;
	}
	public void setFonte(TipoFonte fonte) {
		this.fonte = fonte;
	}
	public Integer getTamanhoFonte() {
		return tamanhoFonte;
	}
	public void setTamanhoFonte(Integer tamanhoFonte) {
		this.tamanhoFonte = tamanhoFonte*2;
	}
	public boolean isHtml() {
		return html;
	}
	public void setHtml(boolean html) {
		this.html = html;
	}
	public double getEspacoAntes() {
		return espacoAntes;
	}
	public void setEspacoAntes(double d) {
		this.espacoAntes = d;
	}
	public double getEspacoDepois() {
		return espacoDepois;
	}
	public void setEspacoDepois(double d) {
		this.espacoDepois = d;
	}
	public double getEspacoLinha() {
		return espacoLinha;
	}
	public void setEspacoLinha(double espacoLinha) {
		this.espacoLinha = espacoLinha;
	}

	public boolean isLegenda() {
		return legenda;
	}

	public void setLegenda(boolean legenda) {
		this.legenda = legenda;
	}

	public boolean isRecuoPrimeiraLinha() {
		return recuoPrimeiraLinha;
	}

	public void setRecuoPrimeiraLinha(boolean recuoPrimeiraLinha) {
		this.recuoPrimeiraLinha = recuoPrimeiraLinha;
	}

	public boolean isEstarNoSumario() {
		return estarNoSumario;
	}

	public void setEstarNoSumario(boolean estarNoSumario) {
		this.estarNoSumario = estarNoSumario;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public boolean isLegendaInicio() {
		return legendaInicio;
	}

	public void setLegendaInicio(boolean legendaInicio) {
		this.legendaInicio = legendaInicio;
	}

	@Override
	public void construir(Object... args) {
		if(this.isLegenda())
			this.getItemBuilder().addLegenda(this, args);		
		else
			this.getItemBuilder().addParagrafo(this, args);		
	}

	public void setReferencia(ItemRelatorio item) {
		this.referencia = item;		
	}
	
	public ItemRelatorio getReferencia()
	{
		return this.referencia;
	}

	public TipoLegenda getTipoLegenda() {
		return tipoLegenda;
	}

	public void setTipoLegenda(TipoLegenda tipoLegenda) {
		this.tipoLegenda = tipoLegenda;
	}

	public int getNivelLegendaQuadro() {
		return nivelLegendaQuadro;
	}

	public void setNivelLegendaQuadro(int nivelLegendaQuadro) {
		this.nivelLegendaQuadro = nivelLegendaQuadro;
	}
}
