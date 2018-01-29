package report.model;

import report.interfaces.IItemBuilder;

public class ItemQuebraPagina extends ItemRelatorio{

	public ItemQuebraPagina(IItemBuilder itemBuilder) {
		this.setItemBuilder(itemBuilder);
	}

	@Override
	public void construir(Object... args) {
		this.getItemBuilder().addQuebraPagina(this, args);		
	}

}
