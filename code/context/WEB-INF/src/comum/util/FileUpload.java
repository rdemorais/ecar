/*
 * Criado em 20/01/2005
 *
 */
package comum.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ecar.util.Dominios;


/**
 * @author felipev
 * @since N/C
 * @version N/C
 */
public class FileUpload extends org.apache.commons.fileupload.FileUpload {
        
    
	 public static final String PREFIXO_NOME_ARQUIVOS_TEMPORARIOS = "ecartemp";
	 public static final String PREFIXO_LISTA_ARQUIVOS_TEMPORARIOS_SESSAO = "PREFIXO_LISTA_ARQUIVOS_TEMPORARIOS_SESSAO";
	 
    /**
     * Salva um FileItem no path especificado.<br>
     * 
     * @author felipev
     * @since 20/01/2005
     * @version N/C
     * @param arquivo
     * @param path
     * @return
     * @throws Exception
     */
    public static File salvarNoDisco(FileItem arquivo, String path) throws Exception{
       // extrai os diretorios do nome do arquivo
        File caminho = new File(path.substring(0, path.lastIndexOf("/")));
        File file = new File(path);
             
        
        // se o diretorio nao existir, cria-o
        if (!caminho.isDirectory())
            caminho.mkdirs();
        
        /*
         * Referente ao Bug 2714 - item 3
         */
        if (file.exists()){
        	String formato = "ddMMyyyyHHmmssSSS";
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            
        	String novoCaminho = path.substring(0, path.lastIndexOf("/")) + "/" + formatter.format(new Date()) + " -- " + file.getName();
        	File fileRenomeado = new File(novoCaminho);
        	file = fileRenomeado;
        }
        
        arquivo.write(file);
        return file;
    }
    
    
    
    /**
     * Método pra salvar arquivo do file item na sessao
     * @param request
     * @param fileItem
     * @param nomeCampo
     * @param caminhoReal
     * @return
     * @throws Exception
     */
    public static byte[] salvarArquivoTemporarioSessao(HttpServletRequest request, FileItem fileItem, String nomeCampo, String caminhoReal) throws Exception{
    	             
    	
    	Map<String, Object> arquivos = (Map<String, Object>)request.getSession().getAttribute(PREFIXO_LISTA_ARQUIVOS_TEMPORARIOS_SESSAO);
    	Map<String, Object> arquivo = new HashMap<String, Object>();
    	
    	 byte[] imagemByte = new byte[(int)fileItem.getSize()];
    	 InputStream input = fileItem.getInputStream();
    	 input.read(imagemByte);
    	
    	if (arquivos==null) {
    		arquivos = new HashMap<String, Object>();
    		arquivo.put(caminhoReal, imagemByte);
    		arquivos.put(nomeCampo, arquivo);
    		request.getSession().setAttribute(PREFIXO_LISTA_ARQUIVOS_TEMPORARIOS_SESSAO, arquivos);
    	} else {
    		arquivo.put(caminhoReal, imagemByte);
    		arquivos.put(nomeCampo, arquivo);
    	}
    	
    	
    	return imagemByte;
    }
    
    /**
     * Método para recuperar os bytes de um arquivo na sessao
     * @param request
     * @param nomeCampo
     * @return
     * @throws Exception
     */
    public static byte[] recuperarArquivoTemporarioSessao(HttpServletRequest request, String nomeCampo) throws Exception{
    	             
    	
    	Map<String, Object> arquivos = (Map<String, Object>)request.getSession().getAttribute(PREFIXO_LISTA_ARQUIVOS_TEMPORARIOS_SESSAO);
    	
    	 byte[] imagemByte = null;
    	 
    	
    	if (arquivos!=null) {
    		imagemByte = (byte[])((Map<String,Object>)arquivos.get(nomeCampo)).values().iterator().next();
    	}
    	
    	return imagemByte;
    }
    
    /**
     * Salvar fisicamente um arquivo da sessao
     * @param request
     * @param nomeCampo
     * @param url
     * @throws Exception
     */
    public static String salvarArquivoSessaoFisicamente(HttpServletRequest request, String nomeCampo, String url) throws Exception {
    	             
    	String nomeArquivoNovo = url;
    	Map<String, Object> arquivos = (Map<String, Object>)request.getSession().getAttribute(PREFIXO_LISTA_ARQUIVOS_TEMPORARIOS_SESSAO);
   	 	   	 	
        if (url.indexOf("RemoteFileSessao")>0) {
     
        	// extrai os diretorios do nome do arquivo
       	 	String caminhoArquivo = ((Map<String,Object>)arquivos.get(nomeCampo)).keySet().iterator().next();
       	 	byte[] imagemByte = (byte[])((Map<String,Object>)arquivos.get(nomeCampo)).values().iterator().next();
       	 
            File caminho = new File(caminhoArquivo.substring(0, caminhoArquivo.lastIndexOf("/")));
            File currentFolder = new File(".");
            File file = new File(caminhoArquivo);
        	
            // se o diretorio nao existir, cria-o
            if (!caminho.isDirectory())
                caminho.mkdirs();
            
            /*
             * Referente ao Bug 2714 - item 3
             */
            if (file.exists()){
            	String formato = "ddMMyyyyHHmmssSSS";
                SimpleDateFormat formatter = new SimpleDateFormat(formato);
                
            	String novoCaminho = caminhoArquivo.substring(0, caminhoArquivo.lastIndexOf("/")) + "/" + formatter.format(new Date()) + " -- " + file.getName();
            	nomeArquivoNovo = novoCaminho;
            	File fileRenomeado = new File(novoCaminho);
            	file = fileRenomeado;
            }
            
            FileOutputStream fileInputStream = new FileOutputStream(file);
            
            if (arquivos!=null) {
       	 		fileInputStream.write(imagemByte);
       	 		arquivos.remove(nomeCampo);
       		}        	
        }
        return nomeArquivoNovo;
   
    }
        
    
    /**
     * Salva um FileItem no path especificado.<br>
     * 
     * @param arquivo
     * @param path
     * @return
     * @author felipev
     * @since 20/01/2005
     * @version N/C
     * @throws Exception
     */
    public static File salvarNoDiscoComCaminhoCompleto(FileItem arquivo, String path) throws Exception{
        // extrai os diretorios do nome do arquivo
    	File diretorioCorrente = new File(".");
    	
    	File caminho = new File(diretorioCorrente.getCanonicalPath() + "/" + path.substring(0, path.lastIndexOf("/")) + "/");
        File file = new File(diretorioCorrente.getCanonicalPath() + "/" + path);
            
        
        // se o diretorio nao existir, cria-o
        if (!caminho.exists())
            caminho.mkdirs();
        
        /*
         * Referente ao Bug 2714 - item 3
         */
        if (file.exists()){
        	String formato = "ddMMyyyyHHmmssSSS";
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            
        	String novoCaminho = diretorioCorrente.getCanonicalPath() + "/" +  path.substring(0, path.lastIndexOf("/")) + "/" + formatter.format(new Date()) + " -- " + file.getName();
        	File fileRenomeado = new File(novoCaminho);
        	file = fileRenomeado;
        }
        
        arquivo.write(file);
        return file;
    }
    
    /**
     * Apaga arquivo especificado se esse existir.<br>
     * 
     * @param fileName
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean
     * @throws Exception
     */
    public static boolean apagarArquivo(String fileName) throws Exception{
        File arquivo = new File(fileName);
        if (arquivo.exists()){
        	return arquivo.delete();
        }
        else{
        	return true;
        }
    }
    
    /**
     * Cria uma lista de campos do tipo FileItem a partir do request multipart/mixed.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @return List
     * @throws Exception
     */
    public static List criaListaCampos(HttpServletRequest request) throws Exception{
    	ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        upload.setHeaderEncoding(Dominios.ENCODING_DEFAULT);
    	List items = upload.parseRequest(request);
        
        return items;
    }
        
    /**
     * Retorna o valor de um campo passado por request ( Mï¿½todo para ser usado em formulï¿½rios de upload).<br>
     * Um formulario de upload possui campos de form simples e campos de upload propriamente ditos.<br>
     * Utilizando isFormField() pode-se disntinguir entre os dois tipos.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param items 
     * @param campo
     * @return String
     * @throws Exception
     */
    public static String verificaValorCampo(List items, String campo) throws Exception{
        Iterator it = items.iterator();         
        while(it.hasNext()){
            FileItem fileItem = (FileItem) it.next();             
            if(fileItem.isFormField()){                             
                if(fileItem.getFieldName()!=null && fileItem.getFieldName().equals(campo)){
                    return Util.normalizaQuebraDeLinha(fileItem.getString());             
                }
            }                           
        }   
        return "";             
    }
    
    
    /**
     * Retorna o valor de um campo passado por request ( Mï¿½todo para ser usado em formulï¿½rios de upload).<br>
     * Um formulario de upload possui campos de form simples e campos de upload propriamente ditos.<br>
     * Utilizando isFormField() pode-se disntinguir entre os dois tipos.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param items 
     * @param campo
     * @return String
     * @throws Exception
     */
    public static Date verificaValorCampoDataBanco(List items, String campo) throws Exception{
    	String valor = verificaValorCampo(items, campo);
    	if ((valor != null) && (!"".equals(valor))) {
            try {
            	return(new Date(valor.substring(6)+"/"+valor.substring(3,5)+"/"+valor.substring(0,2)));
            } catch (Exception e) {
                return(null);
            }
		} else {
	        return(null);
		}
		   
	}
    
    
    /** 
     * Se o campo nï¿½o existir retorna null.<br>
     * Retorna o valor de um campo passado por request ( Mï¿½todo para ser usado em formulï¿½rios de upload).<br>
     * Um formulario de upload possui campos de form simples e campos de upload propriamente ditos.<br>
     * Utilizando isFormField() pode-se disntinguir entre os dois tipos.<br>
     * 
     * @param items 
     * @param campo
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     * @throws Exception
     */
    public static String verificaValorCampoNull(List items, String campo) throws Exception{
        Iterator it = items.iterator();         
        while(it.hasNext()){
            FileItem fileItem = (FileItem) it.next();             
            if(fileItem.isFormField()){                             
                if(fileItem.getFieldName()!=null && fileItem.getFieldName().equals(campo)){
                    if("".equals(fileItem.getString()))
                        return null;
                    else
                        fileItem.getString();
                }
            }                           
        }   
        return null;             
    }
    
    /**
     * Retorna um array com valores de um campo passado por request 
     * ( Mï¿½todo para ser usado em formulï¿½rios de upload).<br>
     * Para ser usado quando se deseja retornar na forma de array valores de campos que tenham o mesmo nome 
     * em um formulario.<br>
     * Um formulario de upload possui campos de form simples e campos de upload propriamente ditos.
     * Utilizando isFormField() pode-se disntinguir entre os dois tipos.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param items
     * @param campo
     * @return Object[]
     * @throws Exception
     */
    public static Object[] verificaValorCampoArray(List items, String campo) throws Exception{
        List retorno = new ArrayList();
        Iterator it = items.iterator();         
        while(it.hasNext()){
            FileItem fileItem = (FileItem) it.next();             
            if(fileItem.isFormField()){                             
                if(fileItem.getFieldName()!=null && fileItem.getFieldName().equals(campo)){
                    retorno.add(fileItem.getString());             
                }
            }                           
        }   
        return retorno.toArray();             
    }
    
    
    
    
    /**
     * Devolve o nome original do arquivo como ï¿½ conhecido pelo sistema de arquivos do lado do cliente.<br>
     * Na maioria das vezes devolve apenas o nome do arquivo.<br> Dependendo do browser, devolve o path + o nome
     * do arquivo.<br> Este mï¿½todo garante que somente serï¿½ devolvido o nome do arquivo. (sem path)
     * 
     * @author ..., rogerio
     * @since N/C
     * @version 0.1, 05/03/2007 
     * @param arquivo
     * @return String		- String do nome do arquivo.
     */
    public static String getNomeArquivo(FileItem arquivo) {
        String nomeArquivo = arquivo.getName();
        
        /* tratamento especial para getName no MSWIndows 
         * no windows: getName() retorna c:\pasta\pasta\arquivo.xyz
         * no linux: getname() retorna arquivo.xyz
         */
        if(nomeArquivo.lastIndexOf("\\") != -1) {
            nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("\\") + 1); 
        } else if (nomeArquivo.lastIndexOf("/") != -1)     // pode ser que aconteca no linux tb
            nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);

        return nomeArquivo;
    }
    
    /**
     * Devolve o nome do arquivo, retirando o caminho completo.
     * 
     * @author ..., Davi
     * @since N/C
     * @version 0.1, 19/10/2009 
     * @param nomeCompletoArquivo
     * @return String - String do nome do arquivo.
     */
    public static String getNomeArquivo(String nomeCompletoArquivo) {
        String nomeArquivo = nomeCompletoArquivo;
        
        /* tratamento especial para getName no MSWIndows 
         * no windows: getName() retorna c:\pasta\pasta\arquivo.xyz
         * no linux: getname() retorna arquivo.xyz
         */
        if(nomeCompletoArquivo.lastIndexOf("\\") != -1) {
            nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("\\") + 1); 
        } else if (nomeCompletoArquivo.lastIndexOf("/") != -1)     // pode ser que aconteca no linux tb
            nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
        
        return nomeArquivo;
    }
    
    /**
     * Dado um caminho fï¿½sico completo de um arquivo e seu contexto, retorna a string a partir do contexto.<br>
     * Ex: pathFisico = /home/garten/projetos/eclipse/ecar/upload/imagem.jpg<br>
     *     contextName = /ecar<br>
     * Retorna: /ecar/upload/imagem.jpg<br>
     * Utilizado para montar links em paginas jsp utilizando um caminho fixo de onde estï¿½ o arquivo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param pathFisico
     * @param contextName
     * @return String
     */
    public static String getPathLogico(String pathFisico, String contextName) {
    	if(pathFisico.indexOf(contextName)!= -1)
    		return pathFisico.substring(pathFisico.indexOf(contextName), pathFisico.length());
    	else 
    		return "";
    }
    
    /**
     * A partir do caminho fisico desde a raiz atï¿½ o contexto, devolve um caminho fisico NORMALIZADO.<br>
     * As barras usados pelo windows serï¿½o trocadas por /.<br>
     * 
     * @author N/C
     * @since N/C
     * @param realPath 
     * @param caminho
     * @param nomeArquivo
     * @return String				- caminho fisico completo.<br>
     * Ex: /home/garten/eclipse/ecar/upload/imagem.jpg<br>
     */
    public static String getPathFisico(String realPath, String caminho, String nomeArquivo){
    	
    	StringBuffer retorno = new StringBuffer();
    	realPath = realPath.replaceAll("\\\\", "/");               

        /* se existir um nome, concatena a '/' antes */
        if (!"".equals(nomeArquivo) && !caminho.endsWith("/"))
            nomeArquivo = "/" +  nomeArquivo;
        
       	retorno.append(realPath);
       	retorno.append(caminho);
       	retorno.append(nomeArquivo);
       
       	return retorno.toString();
    }
    
    
    /**
     * A partir do caminho fisico desde a raiz atï¿½ o contexto, devolve um caminho fisico NORMALIZADO.<br>
     * As barras usados pelo windows serï¿½o trocadas por /.<br>
     * 
     * @author N/C
     * @since N/C
     * @param realPath
     * @param caminho
     * @param nomeArquivo
     * @return String				- caminho fisico completo.<br>
     * Ex: /home/garten/eclipse/ecar/upload/imagem.jpg<br>
     * @throws IOException
     */
    public static String getPathFisicoCompleto(String realPath, String caminho, String nomeArquivo) throws IOException {
    	
    	StringBuffer retorno = new StringBuffer();
    	
    	File diretorioCorrente = new File(".");
    	
    	realPath = realPath.replaceAll("\\\\", "/");               

        /* se existir um nome, concatena a '/' antes */
        if (!"".equals(nomeArquivo))
            nomeArquivo = "/" +  nomeArquivo;
        
       	retorno.append(realPath);
       	retorno.append(caminho);
       	retorno.append(nomeArquivo);
       
       	//return diretorioCorrente.getCanonicalPath() + "/" + retorno.toString();
       	return "/" + retorno.toString();
    }
    
    /**
     * Faz a cópia de um arquivo para outro
     * @param arquivoOrigem
     * @param arquivoDestino
     * @throws IOException
     */
    public static void copiarArquivo(String arquivoOrigem, String arquivoDestino) throws IOException{
    	try {
            // Create channel on the source
            FileChannel origemChannel = new FileInputStream(arquivoOrigem).getChannel();
            
            // Create channel on the destination
            FileChannel destinoChannel = new FileOutputStream(arquivoDestino).getChannel();
        
            // Copy file contents from source to destination
            destinoChannel.transferFrom(origemChannel, 0, origemChannel.size());
        
            // Close the channels
            origemChannel.close();
            destinoChannel.close();
        } catch (IOException e) {
        	throw e;
        }
    }
    
    /**
     * Retorna o nome original de um arquivo gerado com Timestamp para não gerar duplicidade
     * 
     * @param nomeArquivo
     * @return
     */
    public static String getNomeArquivoOriginal(String nomeArquivo){
    	
    	String nomeOriginal = nomeArquivo;
    	
    	if (nomeArquivo.contains("--")){
    		nomeOriginal = nomeArquivo.substring(nomeArquivo.indexOf("--")+2, nomeArquivo.length());
        }
    	
    	return nomeOriginal;
    }
    
    public static void main(String[] args) throws Exception {
		FileUpload fileUpload = new FileUpload();
//		File file = new File("/home/02759475484/pessoal/MARCAS %%%.png");
		File file2 = new File("/home/02759475484/teste%.txt");
//		FileInputStream fileInputStream = new FileInputStream(file);
		FileOutputStream fileOutputStream = new FileOutputStream(file2);
		fileOutputStream.write(1);
	}
}

class FileFilterAnexo implements FilenameFilter {

	public boolean accept(File dir, String name) {
		if (name.startsWith("ecar")) { 
			return true;
		} else {
			return false;
		}
				
	}
	
}
