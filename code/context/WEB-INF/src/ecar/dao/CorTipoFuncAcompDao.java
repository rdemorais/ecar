package ecar.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import comum.database.Dao;
import comum.util.FileUpload;

import ecar.exception.ECARException;
import ecar.pojo.Cor;
import ecar.pojo.CorTipoFuncAcompCtfa;
import ecar.pojo.CorTipoFuncAcompCtfaPK;
import ecar.pojo.TipoFuncAcompTpfa;

/**
 * @author rogerio
 * @since 28/02/2007
 */
public class CorTipoFuncAcompDao extends Dao {
	
	/**
	 * Construtor. Chama o Session factory do Hibernate.
	 * @param request
	 */
	public CorTipoFuncAcompDao(HttpServletRequest request) {
		super();
		this.request = request;
	} // fim construtor
	
	/**
	 * Salva o objeto CorTipoFuncAcompCtfa.
	 * @author rogerio
	 * @since 0.1, 28/02/2007
	 * @version 0.1, 28/02/2007
	 * @param ctfa
	 * @throws ECARException
	 */
	public void salvar(CorTipoFuncAcompCtfa ctfa) throws ECARException {
		super.salvar(ctfa);
	} // fim salvar
	
	/**
	 * Cria a lista das imagens da cor do tipo função de acompanhamento (ctfa) no banco de dados.<br>
	 * @author rogerio
	 * @since 0.1, 28/02/2007
	 * @version 0.1, 28/02/2007
	 * @param posicao
	 * @param listFuncao
	 * @param listCampo
         * @param cor
         * @param currentList
         * @return List
	 * @throws ECARException
	 */
	public List criarListaCorTipoFuncAcomp(String posicao, List listFuncao, List listCampo, Cor cor, List currentList, Map<String, String> arquivos) throws ECARException, Exception {	
		Iterator itf = listFuncao.iterator();
		Iterator itc = null;
	
		while( itf.hasNext() ) {
			TipoFuncAcompTpfa tfa = (TipoFuncAcompTpfa) itf.next();
			
			CorTipoFuncAcompCtfaPK ctfapk = new CorTipoFuncAcompCtfaPK();
			ctfapk.setCodCor(cor.getCodCor());
			ctfapk.setCodTpfa(tfa.getCodTpfa());
			ctfapk.setPosicaoCtfa(posicao);
			CorTipoFuncAcompCtfa ctfa = null;
			
			/* -- 
			 * Foi necessário tratar com try/ catch pois ao pesquisar e não encontrar,
			 * o método retorna uma exception.
			 * -- */
			try {
				ctfa = (CorTipoFuncAcompCtfa) this.buscar(CorTipoFuncAcompCtfa.class, ctfapk);
			} catch( ECARException e ) {
				ctfa = new CorTipoFuncAcompCtfa();
				ctfa.setComp_id(ctfapk);
				ctfa.setCor(cor);
				ctfa.setTipoFuncAcompTpfa(tfa);
				ctfa.setPosicaoCtfa(posicao);
			}
			
			itc = listCampo.iterator();
			while( itc.hasNext() ) {
				FileItem file = (FileItem) itc.next();
				String fieldName = posicao + "_" + tfa.getCodTpfa();
				
				// registra no banco
				if( fieldName.equals(file.getFieldName())){ //&& ( file.getName() != null && !"".equals(file.getName().trim())) ) {
					String status = FileUpload.verificaValorCampo(listCampo, "hid" + fieldName).trim();
					if (status.equals("_excluir")){
						ctfa.setCaminhoImagemCtfa(null);
					}
					if (file.getName() != null && !"".equals(file.getName().trim())){
						
						Object nomeArquivoGravado = arquivos.get(file.getFieldName());
						if (nomeArquivoGravado != null && !nomeArquivoGravado.equals("")){
							ctfa.setCaminhoImagemCtfa(FileUpload.getNomeArquivo(nomeArquivoGravado.toString())); //FileUpload.getPathFisico("", imagePath, FileUpload.getNomeArquivo(nomeArquivoGravado.toString())));
						}
						else{
							ctfa.setCaminhoImagemCtfa(FileUpload.getNomeArquivo(file.getName()));
						}
												
					}
					currentList.add(ctfa);
				}
			}
		}
		
		return currentList;
	} // fim salvarImagemCorTipoFuncAcomp()
} // fim class
