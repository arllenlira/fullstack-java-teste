package br.com.contabilizei.notafiscal.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import br.com.contabilizei.notafiscal.dao.factory.ConnectionFactory;
import br.com.contabilizei.notafiscal.model.RegimeTributario;

public class RegimeTributarioDAOImpl implements RegimeTributarioDAO{
	
	private static EntityManagerFactory emf = null;
	
	private EntityManager entityManager;
	
	public RegimeTributarioDAOImpl(){
		this.entityManager = ConnectionFactory.getEntityManager();
	}

	public RegimeTributario get(Long id) {
		return entityManager.find(RegimeTributario.class, id);
	}


}
