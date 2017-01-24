package br.com.contabilizei.notafiscal.dao;

import java.util.List;

import br.com.contabilizei.notafiscal.model.NotaFiscal;

public interface NotaFiscalDAO {
	
	public void save(NotaFiscal notaFiscal);
	public void update(NotaFiscal notaFiscal);
    public Integer countNotaFiscals();
    public List<NotaFiscal> listNotaFiscals();
    public List<NotaFiscal> findNotaFiscals(int startPosition, int maxResults, String sortFields, String sortDirections);
    public NotaFiscal get(Long id);
    public void delete(Long id);
    
}
