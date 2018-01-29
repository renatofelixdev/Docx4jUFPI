package report.enums;

public enum SecaoDocx {
	CONTINUO("continuous"),
	PROXIMA_PAGINA("nextPage");
	
	private String tipo;
	
	private SecaoDocx(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
