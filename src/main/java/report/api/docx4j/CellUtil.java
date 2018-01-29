package report.api.docx4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class CellUtil {

	@SuppressWarnings("deprecation")
	public static String getCellValueAsString(Cell cell){
		String value = "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				value = String.valueOf(cell.getDateCellValue());
			} else {
				value = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			value = String.valueOf(cell.getNumericCellValue());
			break;
		}
		return value;
	}
}
