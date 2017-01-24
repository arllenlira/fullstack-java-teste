package br.com.contabilizei.notafiscal.dao.factory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ConnectionFactory {
	
	//nome da unidade de persistencia definia no persistence.xml
	private static final String UNIT_NAME = "persistence_unit_nota_fiscal";
	  
	private static EntityManagerFactory emf = null;
	  
	private static EntityManager em = null;
	
	public static EntityManager getEntityManager() {	
        try{  
            if ( emf == null )  {
                emf = Persistence.createEntityManagerFactory(UNIT_NAME);  
                System.out.println("Factory = "+emf);  
            }
            em = emf.createEntityManager();  
        }catch(Exception e){  
            System.out.println("Não conseguiu acesar o Banco ");  
        }  
        return em; 
    }

}
