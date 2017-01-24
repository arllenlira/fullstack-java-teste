package br.com.contabilizei.notafiscal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.contabilizei.notafiscal.dao.factory.ConnectionFactory;
import br.com.contabilizei.notafiscal.model.Empresa;

public class EmpresaDAOImpl implements EmpresaDAO{
	
 
	private static EntityManagerFactory emf = null;
	private EntityManager entityManager = null;
	
	public EmpresaDAOImpl(){
		this.entityManager = ConnectionFactory.getEntityManager();
	}

	public void save(Empresa empresa) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(empresa);
		this.entityManager.getTransaction().commit();
	}


	public void update(Empresa empresa) {		
	    this.entityManager.getTransaction().begin();
	    this.entityManager.merge(empresa);
	    this.entityManager.getTransaction().commit();
	}


	public Integer countEmpresas() {
        Query query = entityManager.createQuery("SELECT COUNT(e.id) FROM Empresa e");
	    return ((Long) query.getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> listEmpresas() {
		return this.entityManager.createQuery("SELECT e FROM Empresa e ORDER BY e.razaoSocial").getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<Empresa> findEmpresas(int startPosition, int maxResults, String sortFields, String sortDirections) {
	     Query query = entityManager.createQuery("SELECT e FROM Empresa e ORDER BY " + sortFields + " " + sortDirections);
	     query.setFirstResult(startPosition);
	     query.setMaxResults(maxResults);
	     return query.getResultList();
	}

	@Override
	public Empresa get(Long id) {
		return entityManager.find(Empresa.class, id);
	}

	@Override
	public void delete(Long id) {
		entityManager.getTransaction().begin();
        entityManager.remove(get(id));
        entityManager.getTransaction().commit();
	}

}
