package br.com.contabilizei.notafiscal.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.contabilizei.notafiscal.dao.factory.ConnectionFactory;
import br.com.contabilizei.notafiscal.model.NotaFiscal;

public class NotaFiscalDAOImpl implements NotaFiscalDAO{
	
	private static EntityManagerFactory emf = null;
	
	private EntityManager entityManager;
	
	public NotaFiscalDAOImpl(){
		this.entityManager = ConnectionFactory.getEntityManager();
	}

	public void save(NotaFiscal notaFiscal) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(notaFiscal);
		this.entityManager.getTransaction().commit();
	}


	public void update(NotaFiscal notaFiscal) {		
	    this.entityManager.getTransaction().begin();
	    this.entityManager.merge(notaFiscal);
	    this.entityManager.getTransaction().commit();
	}


	public Integer countNotaFiscals() {
        Query query = entityManager.createQuery("SELECT COUNT(e.id) FROM NotaFiscal e");
	    return ((Long) query.getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<NotaFiscal> listNotaFiscals() {
		return this.entityManager.createQuery("SELECT e FROM NotaFiscal e ORDER BY e.numero").getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<NotaFiscal> findNotaFiscals(int startPosition, int maxResults, String sortFields, String sortDirections) {
	     Query query = entityManager.createQuery("SELECT e FROM NotaFiscal e ORDER BY " + sortFields + " " + sortDirections);
	     query.setFirstResult(startPosition);
	     query.setMaxResults(maxResults);
	     return query.getResultList();
	}

	@Override
	public NotaFiscal get(Long id) {
		return entityManager.find(NotaFiscal.class, id);
	}

	@Override
	public void delete(Long id) {
		entityManager.getTransaction().begin();
        entityManager.remove(get(id));
        entityManager.getTransaction().commit();
	}
	

	


}
