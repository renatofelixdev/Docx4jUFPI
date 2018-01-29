package report.model;

import report.interfaces.IItemBuilder;

public class ItemSumario extends ItemRelatorio {

	public ItemSumario(IItemBuilder iItemBuilder){
		this.setItemBuilder(iItemBuilder);
	}
	
	@Override
	public void construir(Object... args) {
		this.getItemBuilder().addSumario(this, args);
	}

}
