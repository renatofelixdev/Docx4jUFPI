package report.util;

import org.apache.poi.xssf.usermodel.XSSFColor;

public class Utils {
	
	/** max rgb color. */
	private static final short RGB8BITS = 256;

	public static String strJoin(final short[] aArr, final String sSep) {
		StringBuilder sbStr = new StringBuilder();
		for (int i = 0, il = aArr.length; i < il; i++) {
			if (i > 0) {
				sbStr.append(sSep);
			}
			sbStr.append(aArr[i]);
		}
		return sbStr.toString();
	}
	
	public static short[] getTripletFromXSSFColor(
			final XSSFColor xssfColor) {

		short[] rgbfix = { RGB8BITS, RGB8BITS, RGB8BITS };
		if (xssfColor != null) {
			byte[] rgb = xssfColor.getRGBWithTint();
			if (rgb == null) {
				rgb = xssfColor.getRGB();
			}
			// Bytes are signed, so values of 128+ are negative!
			// 0: red, 1: green, 2: blue
			rgbfix[0] = (short) ((rgb[0] < 0) ? (rgb[0] + RGB8BITS)
					: rgb[0]);
			rgbfix[1] = (short) ((rgb[1] < 0) ? (rgb[1] + RGB8BITS)
					: rgb[1]);
			rgbfix[2] = (short) ((rgb[2] < 0) ? (rgb[2] + RGB8BITS)
					: rgb[2]);
		}
		return rgbfix;
	}
}
