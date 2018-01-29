package report.interfaces;

import java.io.InputStream;

import report.model.IRelatorio;

/**
 * @author Vanderson Moura
 *
 */
public interface IRelatorioBuilder {
	public InputStream construirRelatorio(IRelatorio relatorio);
}

