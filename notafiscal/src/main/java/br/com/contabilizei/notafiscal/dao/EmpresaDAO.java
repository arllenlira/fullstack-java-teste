package br.com.contabilizei.notafiscal.dao;

import java.util.List;

import br.com.contabilizei.notafiscal.model.Empresa;

public interface EmpresaDAO {
	
	public void save(Empresa empresa);
	public void update(Empresa empresa);
    public Integer countEmpresas();
    public List<Empresa> listEmpresas();
    public List<Empresa> findEmpresas(int startPosition, int maxResults, String sortFields, String sortDirections);
    public Empresa get(Long id);
    public void delete(Long id);
    
}
