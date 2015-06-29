/*
 * Criado em 19/01/2005
 *
 */
package ecar.dao;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import comum.database.Dao;
import comum.util.Data;
import comum.util.FileUpload;

import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.HistoricoIettupH;
import ecar.pojo.HistoricoMaster;
import ecar.pojo.HistoricoMotivo;
import ecar.pojo.ItemEstrUplCategIettuc;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UploadTipoArquivoUta;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author felipev
 *
 */
public class ItemEstruturaUploadDao extends Dao{
       
    
    /**
     *
     * @param request
     */
    public ItemEstruturaUploadDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Retorna os Anexos Ativos de uma Categoria de um Item
     * @param categoria
     * @return Collection de Objetos ItemEstrUplCategIettuc
     * @throws ECARException
     */
    public Collection getAtivos(ItemEstrUplCategIettuc categoria) throws ECARException{
        try{
            return this.getSession().createFilter(categoria.getItemEstrutUploadIettups(), "where this.indAtivoIettups = 'S'").list();    
        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);
        }        
    }
    
    /**
     * Constr�i um objeto ItemEstrutUploadIettup a partir de atributos passados no request.
     * Realiza upload do arquivo
     * @param campos
     * @param upload 
     * @param pathRaiz 
     * @param pathRelativo
     * @throws ECARException
     */
    public void setItemEstruturaUpload(List campos, ItemEstrutUploadIettup upload, String pathRaiz, String pathRelativo) throws ECARException{
        try{
            ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(
                    ItemEstruturaIett.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "codIett")));
            upload.setItemEstruturaIett(itemEstrutura);
            
            if(!"".equals(FileUpload.verificaValorCampo(campos, "cod")))
                upload.setCodIettup(Long.valueOf(FileUpload.verificaValorCampo(campos, "cod")));
            
            upload.setDescricaoIettup(FileUpload.verificaValorCampo(campos, "descricaoIettup"));
            
            upload.setIndAtivoIettup("S");
                      
            if(!"".equals(FileUpload.verificaValorCampo(campos, "codIettuc")))
                upload.setItemEstrUplCategIettuc( (ItemEstrUplCategIettuc) this.buscar(
                        ItemEstrUplCategIettuc.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "codIettuc"))));
            	upload.getItemEstrUplCategIettuc().setIndAtivoIettuc("S");
            
            if(!"".equals(FileUpload.verificaValorCampo(campos, "uploadTipoArquivoUta")))
                upload.setUploadTipoArquivoUta( (UploadTipoArquivoUta) this.buscar(
                        UploadTipoArquivoUta.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "uploadTipoArquivoUta"))));

            
            if(!"".equals(FileUpload.verificaValorCampo(campos, "usuarioUsu")))
                upload.setUsuarioUsu( (UsuarioUsu) this.buscar(
                        UsuarioUsu.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "usuarioUsu"))));

            
            /* realiza o upload do arquivo */
            Iterator it = campos.iterator();  
            String formato = "ddMMyyyyHHmmssSSS";            
            while(it.hasNext()){
                FileItem fileItem = (FileItem) it.next();             
                if(!fileItem.isFormField() && !"".equals(fileItem.getName())){    
                    
                    if(upload.getArquivoIettup() != null)
                        FileUpload.apagarArquivo(upload.getArquivoIettup());
                    SimpleDateFormat formatter = new SimpleDateFormat(formato);
                    String dataGravacao = formatter.format(new Date());
                    upload.setArquivoIettup(FileUpload.getPathFisico("", pathRelativo, dataGravacao+" - "+FileUpload.getNomeArquivo(fileItem)));
                    upload.setNomeOriginalIettup(FileUpload.getNomeArquivo(fileItem));
                    upload.setTamanhoIettup(Long.valueOf(fileItem.getSize()));
                    FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathRelativo, dataGravacao+" - "+FileUpload.getNomeArquivo(fileItem)));
                }
            }
            
        } catch (Exception e){
            this.logger.error(e);
            throw new ECARException("erro.exception");
        }
        
                
    }
    
    
    /**
     * Constr�i um objeto ItemEstrutUploadIettup a partir de atributos passados no request.
     * Realiza upload do arquivo
     * @param campos
     * @param upload
     * @param pathRaiz 
     * @param pathRelativo
     * @param acompRelatorioArel
     * @param itemEstrUplCategIettuc
     * @throws ECARException
     */
    public void setItemEstruturaUpload(List campos, ItemEstrutUploadIettup upload, String pathRaiz, String pathRelativo, AcompRelatorioArel acompRelatorioArel, ItemEstrUplCategIettuc itemEstrUplCategIettuc) throws ECARException{
    	upload.setAcompRelatorioArel(acompRelatorioArel);
    	upload.setItemEstrUplCategIettuc(itemEstrUplCategIettuc);
    	this.setItemEstruturaUpload(campos, upload, pathRaiz, pathRelativo);
    }
    
    
    
    /**
     * Exclui os Registros de Upload com os c�digos existente no Array. Exclui tamb�m os arquivos referenciados por esses
     * registros 
     * @param codigosParaExcluir
     * @param pathRaiz
     * @param usuario
     * @param origemAcompanhamento - funcionalidade origem que solicita o servi�o. 
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, String pathRaiz, UsuarioUsu usuario, boolean origemAcompanhamento) throws ECARException {
    	    	
    	
    	ItemEstruturaUploadCategoriaDao categoriaDao = new ItemEstruturaUploadCategoriaDao(request);
	    	
    	
        for (int i = 0; i < codigosParaExcluir.length; i++) {    
        	
            ItemEstrutUploadIettup upload = (ItemEstrutUploadIettup) this.buscar(ItemEstrutUploadIettup.class, Long.valueOf(codigosParaExcluir[i]));
	        ItemEstrUplCategIettuc categoria = upload.getItemEstrUplCategIettuc();
	            
	        ItemEstrutUploadIettup uploadPEsquisa = new ItemEstrutUploadIettup();
	        uploadPEsquisa.setItemEstrUplCategIettuc(categoria);
	        uploadPEsquisa.setIndAtivoIettup("S");
	            
	        List itensAtivosCategoria = pesquisar(uploadPEsquisa, new String[]{"codIettup", "asc"});
	            
	        //seta a variavel booleana para excluir a categoria caso ela possua menos de 2 arquivos associados
	        boolean excluirCategoria = itensAtivosCategoria.size() > 1 ? false : true; 
            
        	ConfiguracaoDao dao = new ConfiguracaoDao(request);
        	ConfiguracaoCfg config = dao.getConfiguracao();
        	
        	HistoricoMaster historicoMaster = new HistoricoMaster();
            
        	if("S".equals(config.getIndGerarHistoricoCfg())) {
            	
    			historicoMaster.setDataHoraHistorico(new Date());
    			historicoMaster.setUsuManutencao(upload.getUsuarioUsuManutencao() == null?usuario:upload.getUsuarioUsuManutencao());
    			historicoMaster.setCodReferenciaGeral(upload.getItemEstruturaIett().getCodIett());
    			historicoMaster.setHistoricoMotivo((HistoricoMotivo)super.buscar(HistoricoMotivo.class, Long.valueOf(26)));
    			session.save(historicoMaster);
    			
    			HistoricoIettupH iettuph = new HistoricoIettupH();
    			ItemEstrutUploadIettup iettup = (ItemEstrutUploadIettup)super.buscar(ItemEstrutUploadIettup.class, upload.getCodIettup());
    			
    			iettuph.setArquivoIettup(iettup.getArquivoIettup());
    			iettuph.setCodIettup(iettup.getCodIettup());
    			iettuph.setCodIettupH(iettup.getCodIettup());
    			iettuph.setDataInclusaoIettup(iettup.getDataInclusaoIettup());
    			iettuph.setDescricaoIettup(iettup.getDescricaoIettup());
    			iettuph.setHistoricoMaster(historicoMaster);
    			iettuph.setIndAtivoIettup(iettup.getIndAtivoIettup());
    			iettuph.setItemEstrUplCategIettuc(iettup.getItemEstrUplCategIettuc());
    			iettuph.setItemEstruturaIett(iettup.getItemEstruturaIett());
    			//iettuph.setNomeIettuc();
    			iettuph.setNomeOriginalIettup(iettup.getNomeOriginalIettup());
    			iettuph.setTamanhoIettup(iettup.getTamanhoIettup());
    			iettuph.setUploadTipoArquivoUta(iettup.getUploadTipoArquivoUta());
    			iettuph.setUsuarioUsu(iettup.getUsuarioUsu());
    			iettuph.setUsuManutencao((iettup.getUsuarioUsuManutencao() == null?usuario:iettup.getUsuarioUsuManutencao()));

    			session.save(iettuph);
        	}

            this.excluir(upload, pathRaiz, usuario);
            
             //Excluir a categoria caso n�o haja mais nenhum arquivo na mesma no momento da exclus�o deste arquivo
			
            //Mudan�a de requisito. Caso Mantis: 0010956.
            // Independente da origem da exclus�o. Deve verificar exclus�o autom�tica da pasta gerada pelo acompanhamento.
			if (excluirCategoria/* && origemAcompanhamento*/){
				if(categoria.getAcompReferenciaItemAri()!=null){
					categoriaDao.excluir(categoria, pathRaiz);
				}
			}
        }
    }
    
    /**
     * Exclui um  upload e apaga o arquivo vinculado a ela
     * @param upload
     * @param pathRaiz
     * @param usuario
     * @throws ECARException
     */ 
    public void excluir(ItemEstrutUploadIettup upload, String pathRaiz, UsuarioUsu usuario) throws ECARException{
        try{            
        	upload.setUsuarioUsuManutencao(usuario);
        	upload.setIndAtivoIettup("N");
        	upload.setIndExclusaoPosHistorico(Boolean.TRUE);
        	
            if(upload.getArquivoIettup()!=null){
            	//Verifica se o arquivo � usado como anexo de outro item, essa situa��o pode ocorrer por causa 
            	// da cria��o de itens a partir de outros j� existentes 
            	if (arquivoUsadoPorOutroItem(upload)) {
            		super.alterar(upload);
            	} else if (FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, upload.getArquivoIettup(), ""))){
                	super.alterar(upload);
                } else {
                    throw new ECARException("erro.excluirArquivo");        
                }
            } else
            	super.alterar(upload);
        }
        catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("erro.exception");
        }
    }
    /**
     * Verifica se o arquivo atual � usado por outro arquivo antes de apag�-lo do disco.
     * Essa verifica��o � necess�ria pois quando se cria um item a partir do outro e os 
     * arquivos do mesmo s�o compartilhados. Ent�o se apagar aqui ir� dar problema no 
     * outro item. 
     * 
     * @param upload
     * @return
     */
    private boolean arquivoUsadoPorOutroItem(ItemEstrutUploadIettup upload) throws ECARException{
		ItemEstruturaUploadDao uploadDao = new ItemEstruturaUploadDao(request);
		ItemEstrutUploadIettup uploadNovo = new ItemEstrutUploadIettup();
		uploadNovo.setArquivoIettup(upload.getArquivoIettup());
		List uploads = uploadDao.pesquisar(uploadNovo,null);
		int i=0;
		
		if (uploads != null && uploads.size()>1){
			for (Iterator iterator = uploads.iterator(); iterator.hasNext();) {
				ItemEstrutUploadIettup arquivo = (ItemEstrutUploadIettup) iterator.next();
				if (arquivo.getIndAtivoIettup().equals(Dominios.SIM)){
					return true;
				}
				
			}
		}
		return false;
	}

	/**
     * Salva um registro de  Upload
     * @param upload
         * @param usuario
         * @throws ECARException
     */
    public void salvar(ItemEstrutUploadIettup upload, UsuarioUsu usuario) throws ECARException{
		upload.setUsuarioUsu(usuario);
       	upload.setDataInclusaoIettup(Data.getDataAtual());
       	super.salvar(upload);
    }
    
    /**
     *
     * @param upload
     * @param usuario
     * @param filhos
     * @throws ECARException
     */
    public void salvar(ItemEstrutUploadIettup upload, UsuarioUsu usuario, List filhos)throws ECARException{
    	upload.setUsuarioUsu(usuario);
       	upload.setDataInclusaoIettup(Data.getDataAtual());
    	super.salvar(upload, filhos);
    }
    
    /**
     *
     * @param upload
     * @param usuario
     * @throws ECARException
     */
    public void alterar(ItemEstrutUploadIettup upload, UsuarioUsu usuario) throws ECARException {
		upload.setUsuarioUsuManutencao(usuario);
		
    	ConfiguracaoDao dao = new ConfiguracaoDao(request);
    	ConfiguracaoCfg config = dao.getConfiguracao();
    	
    	HistoricoMaster historicoMaster = new HistoricoMaster();
		
    	if("S".equals(config.getIndGerarHistoricoCfg())) {
        	
			historicoMaster.setDataHoraHistorico(new Date());
			historicoMaster.setUsuManutencao(upload.getUsuarioUsuManutencao());
			historicoMaster.setCodReferenciaGeral(upload.getItemEstruturaIett().getCodIett());
			historicoMaster.setHistoricoMotivo((HistoricoMotivo)super.buscar(HistoricoMotivo.class, Long.valueOf(25)));
			session.save(historicoMaster);
			
			HistoricoIettupH iettuph = new HistoricoIettupH();
			ItemEstrutUploadIettup iettup = (ItemEstrutUploadIettup)super.buscar(ItemEstrutUploadIettup.class, upload.getCodIettup());
			
			iettuph.setArquivoIettup(iettup.getArquivoIettup());
			iettuph.setCodIettup(iettup.getCodIettup());
			iettuph.setCodIettupH(iettup.getCodIettup());
			iettuph.setDataInclusaoIettup(iettup.getDataInclusaoIettup());
			iettuph.setDescricaoIettup(iettup.getDescricaoIettup());
			iettuph.setHistoricoMaster(historicoMaster);
			iettuph.setIndAtivoIettup(iettup.getIndAtivoIettup());
			iettuph.setItemEstrUplCategIettuc(iettup.getItemEstrUplCategIettuc());
			iettuph.setItemEstruturaIett(iettup.getItemEstruturaIett());
			//iettuph.setNomeIettuc();
			iettuph.setNomeOriginalIettup(iettup.getNomeOriginalIettup());
			iettuph.setTamanhoIettup(iettup.getTamanhoIettup());
			iettuph.setUploadTipoArquivoUta(iettup.getUploadTipoArquivoUta());
			iettuph.setUsuarioUsu(iettup.getUsuarioUsu());
			iettuph.setUsuManutencao(iettup.getUsuarioUsuManutencao());

			session.save(iettuph);
    	}	
		
		super.alterar(upload);
	}
    
    /**
     * Retorna os Anexos Ativos de um Item
     * @param categoria
     * @return Collection de Objetos ItemEstrUplCategIettuc
     * @throws ECARException
     */
    public List<ItemEstrutUploadIettup> getAnexosIett(Long codigoIett) throws ECARException{
        try{
			String hql = "FROM ItemEstrutUploadIettup ind "
					+ "WHERE ind.indAtivoIettup = 'S' "
					+ "AND ind.itemEstruturaIett.codIett = :codIett";

			Query q = getSession().createQuery(hql);

			q.setLong("codIett", codigoIett);

			List<ItemEstrutUploadIettup> lista = q.list();
			
			return lista;
			
			
        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);
        }        
    } 

    public List<ItemEstrutUploadIettup> getAnexosIettNovo(Long codigoIett, List<AcompReferenciaAref> listaAref) throws ECARException{
    	
    	StringBuilder hql = null;
    	Query query = null;
    	List<ItemEstrutUploadIettup> lista = null;
    	
        try{

        	hql = new StringBuilder();
			hql.append("FROM ItemEstrutUploadIettup ind ");
			hql.append("WHERE ind.indAtivoIettup = 'S' ");
			hql.append("AND ind.itemEstruturaIett.codIett = :codIett ");
			hql.append("AND ind.acompRelatorioArel.acompReferenciaItemAri.acompReferenciaAref in (:listaAref) ");
			hql.append("ORDER BY ind.codIettup DESC");

			query = getSession().createQuery(hql.toString());

			query.setLong("codIett", codigoIett);
			query.setParameterList("listaAref", listaAref);

			lista = query.list();
			
			return lista;
			
			
        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);
        }        
    } 
    
//    public ItemEstrutUploadIettup getAnexoIettup(Long codigoIettup) throws ECARException{
//        try{
//			String hql = "FROM ItemEstrutUploadIettup ind "
//					+ "WHERE ind.indAtivoIettup = 'S' "
//					+ "AND ind.itemEstruturaIett.codIettup = :codigoIettup";
//
//			Query q = getSession().createQuery(hql);
//
//			q.setLong("codigoIettup", codigoIettup);
//
//			ItemEstrutUploadIettup item = (ItemEstrutUploadIettup) q.uniqueResult();
//			
//			return item;
//			
//			
//        } catch(HibernateException e){
//        	this.logger.error(e);
//            throw new ECARException(e);
//        }        
//    }        
}
