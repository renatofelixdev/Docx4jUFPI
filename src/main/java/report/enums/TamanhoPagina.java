package report.enums;

public enum TamanhoPagina {
	A4("A4");

    private String tipo;

    TamanhoPagina (String tipo){
        this.tipo = tipo;
    }

    public String toString(){
        return this.tipo;
    }

}
