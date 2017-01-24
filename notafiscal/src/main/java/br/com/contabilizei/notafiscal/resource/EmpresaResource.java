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
import br.com.contabilizei.notafiscal.dao.RegimeTributarioDAOImpl;
import br.com.contabilizei.notafiscal.model.Empresa;
import br.com.contabilizei.notafiscal.model.RegimeTributario;

@Path("empresas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmpresaResource extends Application{
	
	@Context
    UriInfo uriInfo;
 
    @Context
    Request request;
    
 
    private EmpresaDAOImpl empresaDAO = new EmpresaDAOImpl();
    
    private Integer countEmpresas() {
        return empresaDAO.countEmpresas();
    }
    
    @SuppressWarnings("unchecked")
    private List<Empresa> findEmpresas(int startPosition, int maxResults, String sortFields, String sortDirections) {
    	return empresaDAO.findEmpresas(startPosition, maxResults, sortFields, sortDirections);
    }
    
    private PaginatedListWrapper<Empresa> findEmpresas(PaginatedListWrapper<Empresa> wrapper) {
        wrapper.setTotalResults(empresaDAO.countEmpresas());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(empresaDAO.findEmpresas(start,
                                    wrapper.getPageSize(),
                                    wrapper.getSortFields(),
                                    wrapper.getSortDirections()));
        return wrapper;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedListWrapper<Empresa> listEmpresas(@DefaultValue("1")
                                                    @QueryParam("page")
                                                    Integer page,
                                                    @DefaultValue("id")
                                                    @QueryParam("sortFields")
                                                    String sortFields,
                                                    @DefaultValue("asc")
                                                    @QueryParam("sortDirections")
                                                    String sortDirections) {
        PaginatedListWrapper<Empresa> paginatedListWrapper = new PaginatedListWrapper<>();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(5);
        return findEmpresas(paginatedListWrapper);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listEmpresas")
    public List<Empresa> listEmpresas() {
        return empresaDAO.listEmpresas();
    }
    
    @GET
    @Path("{id}")
    public Empresa getEmpresa(@PathParam("id") Long id) {
        return empresaDAO.get(id);
    }

    @POST
    public Empresa saveEmpresa(Empresa empresa) {
    	RegimeTributarioDAOImpl regimeTributarioDAO = new RegimeTributarioDAOImpl();
    	
        if (empresa.getId() == null) {
            RegimeTributario regimeTributario = new RegimeTributario();
            regimeTributario = regimeTributarioDAO.get(empresa.getRegimeTributarioId());
            
            Empresa empresaToSave = new Empresa();
            empresaToSave.setRazaoSocial(empresa.getRazaoSocial());
            empresaToSave.setCnpj(empresa.getCnpj());
            empresaToSave.setRegimeTributario(regimeTributario);
            empresaToSave.setAnexo(empresa.getAnexo());
            empresaToSave.setEmail(empresa.getEmail());
            empresaDAO.save(empresa);
        } else {
            RegimeTributario regimeTributario = new RegimeTributario();
            regimeTributario = regimeTributarioDAO.get(empresa.getRegimeTributarioId());
            
            Empresa empresaToUpdate = getEmpresa(empresa.getId());
            empresaToUpdate.setRazaoSocial(empresa.getRazaoSocial());
            empresaToUpdate.setCnpj(empresa.getCnpj());
            empresaToUpdate.setRegimeTributario(regimeTributario);
            empresaToUpdate.setAnexo(empresa.getAnexo());
            empresaToUpdate.setEmail(empresa.getEmail());
            empresaDAO.update(empresa);
        }

        return empresa;
    }

    @DELETE
    @Path("{id}")
    public void deleteEmpresa(@PathParam("id") Long id) {
       empresaDAO.delete(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa cadastro(MultivaluedMap<String, String> cityParams) {       
     	
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
