package report.enums;

public enum TipoMesclagemVertical {
	INICIO("restart"),
	CONTINUA("continue"),
	FIM("stop");
	
	private String tipo;

	TipoMesclagemVertical (String tipo){
        this.tipo = tipo;
    }

    public String toString(){
        return this.tipo;
    }
}
