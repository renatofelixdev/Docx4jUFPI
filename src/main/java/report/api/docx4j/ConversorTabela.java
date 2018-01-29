package report.api.docx4j;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import report.enums.Alinhamento;
import report.enums.TipoMesclagemVertical;
import report.interfaces.IItemBuilder;
import report.model.ItemColuna;
import report.model.ItemLinha;
import report.model.ItemParagrafo;
import report.model.ItemTabela;
import report.util.Utils;

public class ConversorTabela {

	private InputStream file;
	private IItemBuilder itemBuilder;

	public ConversorTabela(InputStream file, IItemBuilder itemBuilder){
		this.file = file;
		this.itemBuilder = itemBuilder;
	}

	@SuppressWarnings("deprecation")
	public ItemTabela toItemTabela() throws IOException{
		ItemTabela tabela = new ItemTabela(itemBuilder);

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheetAt(0);

		List<String> mesclagens = new ArrayList<String>();
		List<Integer> linhasComMesclagem = new ArrayList<Integer>();
		for(CellRangeAddress cra: sheet.getMergedRegions()){
			if(cra.getFirstRow() == cra.getLastRow()){
				if(cra.getFirstColumn() != cra.getLastColumn()){
					linhasComMesclagem.add(new Integer(cra.getFirstRow()));
					mesclagens.add(0+","+cra.getFirstRow()+","+cra.getFirstColumn()+","+cra.getLastColumn());
				}
			}else{
				if(cra.getFirstColumn() != cra.getLastColumn()){
					for(int i = cra.getFirstRow(); i <= cra.getLastRow(); i++){
						linhasComMesclagem.add(new Integer(i));
						mesclagens.add(0+","+i+","+cra.getFirstColumn()+","+cra.getLastColumn());
					}

					for(int i = cra.getFirstColumn(); i <= cra.getLastColumn(); i++){
						linhasComMesclagem.add(new Integer(cra.getFirstRow()));
						mesclagens.add(1+","+i+","+cra.getFirstRow()+","+cra.getLastRow());
					}
				}else{
					linhasComMesclagem.add(new Integer(cra.getFirstRow()));
					mesclagens.add(1+","+cra.getFirstColumn()+","+cra.getFirstRow()+","+cra.getLastRow());
				}
			}
		}
		
		int maxrow = 70;
		if(sheet.getLastRowNum()+1 < maxrow) maxrow = sheet.getLastRowNum()+1;
		for(int i= 0; i < maxrow/*sheet.getLastRowNum()*/; i++){
			ItemLinha il = new ItemLinha();

			boolean temMesclagem = false;
			boolean mesclando = false;
			int mesclaH = 0;

			List<Integer> colunasMescladas = new ArrayList<Integer>();

			if(linhasComMesclagem.contains(i)){
				temMesclagem = true;
				for(String tupla : mesclagens){
					String [] t = tupla.split(",");
					int tipo = Integer.valueOf(t[0]);
					//horizontal
					if(tipo == 0 && i == Integer.valueOf(t[1])){
						colunasMescladas.add(new Integer(Integer.valueOf(t[2])));
						colunasMescladas.add(new Integer(Integer.valueOf(t[3])));
					}
				}

				Collections.sort(colunasMescladas);
			}
			int maxcol = 30;
			if(sheet.getRow(i).getLastCellNum() < maxcol) maxcol = sheet.getRow(i).getLastCellNum();
			for(int j = 0; j < maxcol; j++){				
				ItemColuna ic = new ItemColuna();
				ItemParagrafo ip = new ItemParagrafo(itemBuilder);
				ip.setTamanhoFonte(10);
				ip.setEspacoLinha(0.5);
				if(sheet.getRow(i).getCell(j) == null)
					break;
				else{
					switch (sheet.getRow(i).getCell(j).getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						ip.setConteudo(sheet.getRow(i).getCell(j).getRichStringCellValue().getString());
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						
						if (DateUtil.isCellDateFormatted(sheet.getRow(i).getCell(j))) {
							ip.setConteudo(String.valueOf(sheet.getRow(i).getCell(j).getDateCellValue()));
						} else {
							String valor = new DecimalFormat(sheet.getRow(i).getCell(j).getCellStyle().getDataFormatString()).format(sheet.getRow(i).getCell(j).getNumericCellValue());
							ip.setConteudo(valor.replaceAll("\"", "").replaceAll("General", ""));
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						ip.setConteudo(String.valueOf(sheet.getRow(i).getCell(j).getBooleanCellValue()));
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						ip.setConteudo(String.valueOf(sheet.getRow(i).getCell(j).getNumericCellValue()));
						break;
					}

					Cell cell = sheet.getRow(i).getCell(j);

					switch(cell.getCellStyle().getAlignmentEnum()) {
					case RIGHT:
						ip.setAlinhamento(Alinhamento.DIREITA);
						break;
					case LEFT:
						ip.setAlinhamento(Alinhamento.ESQUERDA);
						break;
					case CENTER:
						ip.setAlinhamento(Alinhamento.CENTRO);
						break;
					case JUSTIFY:
						ip.setAlinhamento(Alinhamento.JUSTIFICADO);
						break;
					default:
						break;
					}

					switch(cell.getCellStyle().getBorderBottomEnum()) {
					case NONE:
						ic.setBorderBottom(false);
						break;
					default:
						ic.setBorderBottom(true);
						break;						
					}

					switch(cell.getCellStyle().getBorderLeftEnum()) {
					case NONE:
						ic.setBorderLeft(false);
						break;
					default:
						ic.setBorderLeft(true);
						break;						
					}

					switch(cell.getCellStyle().getBorderRightEnum()) {
					case NONE:
						ic.setBorderRight(false);
						break;
					default:
						ic.setBorderRight(true);
						break;						
					}

					switch(cell.getCellStyle().getBorderTopEnum()) {
					case NONE:
						ic.setBorderTop(false);
						break;
					default:
						ic.setBorderTop(true);
						break;						
					}
					
					if(cell.getCellStyle() != null && ((XSSFCellStyle) cell.getCellStyle()).getFont().getBold())
						ip.setNegrito(true);
					
					
					if(cell.getCellStyle() != null && cell.getCellStyle().getFillForegroundColorColor() != null) {
						ic.setCorDeFundo(this.converteCor(Utils.strJoin(Utils.getTripletFromXSSFColor((XSSFColor) cell.getCellStyle().getFillForegroundColorColor()), ",")));
					}
				}

				//mesclagem vertical
				for(String tupla : mesclagens){
					String [] t = tupla.split(",");
					int tipo = Integer.valueOf(t[0]);
					//vertical
					if(tipo == 1 && j == Integer.valueOf(t[1])){
						if(i == Integer.valueOf(t[2]))
							ic.setMesclarVertical(TipoMesclagemVertical.INICIO);
						else if(i == Integer.valueOf(t[3]))
							ic.setMesclarVertical(TipoMesclagemVertical.FIM);
						else if(i > Integer.valueOf(t[2]) && i < Integer.valueOf(t[3]))
							ic.setMesclarVertical(TipoMesclagemVertical.CONTINUA);
					}
				}


				//mesclagem horizontal
				boolean ultimaColunaMesclagem = false;
				if(temMesclagem){
					if(mesclaH*2 < colunasMescladas.size()){
						int z = colunasMescladas.get(mesclaH*2);
						if(j == z){
							ic.setMesclarHorizontal(1+colunasMescladas.get(1+(mesclaH*2))-colunasMescladas.get(mesclaH*2));
							ic.getItensRelatorio().add(ip);
							il.addColuna(ic);
							mesclando = true;
							mesclaH = 0;
						}else if(j == colunasMescladas.get(1+(mesclaH*2))){
							mesclando = false;
							ultimaColunaMesclagem = true;
							mesclaH++;
						}
					}
				}

				if(!mesclando && !ultimaColunaMesclagem){
					ic.getItensRelatorio().add(ip);
					il.addColuna(ic);
				}
			}
			
			tabela.addLinha(il);
		}

		return tabela;
	}
	
	private String converteCor(String rgb) {
		String cor [] = rgb.split(",");
		return String.format("#%02x%02x%02x", Integer.valueOf(cor[0]), Integer.valueOf(cor[1]), Integer.valueOf(cor[2]));
	}
}


