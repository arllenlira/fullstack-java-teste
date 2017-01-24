package br.com.contabilizei.notafiscal.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import br.com.contabilizei.notafiscal.dao.EmpresaDAOImpl;
import br.com.contabilizei.notafiscal.dao.NotaFiscalDAOImpl;
import br.com.contabilizei.notafiscal.model.Empresa;
import br.com.contabilizei.notafiscal.model.NotaFiscal;

@Path("notaFiscal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotaFiscalResource extends Application{
	
	@Context
    UriInfo uriInfo;
 
    @Context
    Request request;
    
    private NotaFiscalDAOImpl notaFiscalDAO = new NotaFiscalDAOImpl();
    
    private Integer countNotaFiscals() {
        return notaFiscalDAO.countNotaFiscals();
    }
    
    @SuppressWarnings("unchecked")
    private List<NotaFiscal> findNotaFiscals(int startPosition, int maxResults, String sortFields, String sortDirections) {
    	return notaFiscalDAO.findNotaFiscals(startPosition, maxResults, sortFields, sortDirections);
    }
    
    private PaginatedListWrapper<NotaFiscal> findNotaFiscals(PaginatedListWrapper<NotaFiscal> wrapper) {
        wrapper.setTotalResults(notaFiscalDAO.countNotaFiscals());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(notaFiscalDAO.findNotaFiscals(start,
                                    wrapper.getPageSize(),
                                    wrapper.getSortFields(),
                                    wrapper.getSortDirections()));
        return wrapper;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedListWrapper<NotaFiscal> listNotaFiscals(@DefaultValue("1")
                                                    @QueryParam("page")
                                                    Integer page,
                                                    @DefaultValue("id")
                                                    @QueryParam("sortFields")
                                                    String sortFields,
                                                    @DefaultValue("asc")
                                                    @QueryParam("sortDirections")
                                                    String sortDirections) {
        PaginatedListWrapper<NotaFiscal> paginatedListWrapper = new PaginatedListWrapper<>();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(5);
        return findNotaFiscals(paginatedListWrapper);
    }
    
    @GET
    @Path("{id}")
    public NotaFiscal getNotaFiscal(@PathParam("id") Long id) {
        return notaFiscalDAO.get(id);
    }

    @POST
    public NotaFiscal saveNotaFiscal(NotaFiscal notaFiscal) {
    	EmpresaDAOImpl empresaDAO = new EmpresaDAOImpl();
        if (notaFiscal.getId() == null) {
            Empresa empresa = new Empresa();
            empresa = empresaDAO.get(notaFiscal.getEmpresaId());

            NotaFiscal notaFiscalToSave = new NotaFiscal();
            notaFiscalToSave.setNumero(notaFiscal.getNumero());
            notaFiscalToSave.setEmpresa(empresa);
            notaFiscalToSave.setDataEmissao(notaFiscal.getDataEmissao());
            notaFiscalToSave.setDescricao(notaFiscal.getDescricao());
            notaFiscalToSave.setValor(notaFiscal.getValor());
            notaFiscalToSave.setAnexo1(notaFiscal.getAnexo1());
            notaFiscalToSave.setAnexo2(notaFiscal.getAnexo2());
            notaFiscalToSave.setAnexo3(notaFiscal.getAnexo3());
            notaFiscalDAO.save(notaFiscalToSave);
        } else {
            Empresa empresa = new Empresa();
            empresa = empresaDAO.get(notaFiscal.getEmpresaId());
            
            NotaFiscal notaFiscalToUpdate = getNotaFiscal(notaFiscal.getId());
            notaFiscalToUpdate.setNumero(notaFiscal.getNumero());
            notaFiscalToUpdate.setEmpresa(empresa);
            notaFiscalToUpdate.setDataEmissao(notaFiscal.getDataEmissao());
            notaFiscalToUpdate.setDescricao(notaFiscal.getDescricao());
            notaFiscalToUpdate.setValor(notaFiscal.getValor());
            notaFiscalToUpdate.setAnexo1(notaFiscal.getAnexo1());
            notaFiscalToUpdate.setAnexo2(notaFiscal.getAnexo2());
            notaFiscalToUpdate.setAnexo3(notaFiscal.getAnexo3());
            notaFiscalDAO.update(notaFiscalToUpdate);
        }

        return notaFiscal;
    }
    
    

    @DELETE
    @Path("{id}")
    public void deleteNotaFiscal(@PathParam("id") Long id) {
       notaFiscalDAO.delete(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public NotaFiscal cadastro(MultivaluedMap<String, String> cityParams) {       
     	
    	return null;                         
    }
    
    @GET
    @Path("teste")
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateDistanceMilesKM() {
   
        String distanceValue = "OK";
        
        return distanceValue; 
    }

}
