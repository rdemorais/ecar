package ecar.servlet.relatorio;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hslf.model.AutoShape;
import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.ShapeTypes;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import comum.util.Util;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.EmpresaDao;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EmpresaEmp;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;
 

/**
 *
 * @author 70744416353
 */
public class MontaPPT {
		
     //public static void main(String[] args) throws IOException {
    /**
     *
     * @param xml
     * @param caminhoImagens
     * @param request
     * @param response
     * @throws IOException
     */
    public void parserXMLPPT(StringBuffer xml, String caminhoImagens, HttpServletRequest request, HttpServletResponse response) throws IOException {

      // Carrega o XML
      //File docFile = new File("/home/03017428478/Desktop/RelatorioAcompanhamento.xml");
      InputSource docFile = new InputSource(new ByteArrayInputStream(xml.toString().getBytes(Dominios.ENCODING_DEFAULT)));
             
      // Realizando o parser do XML do relatório 
      Document doc = null;
       try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(docFile);
      } catch (Exception e) {
           System.out.print("Problem parsing the file.");
      }
     
      // Obtendo o elemento raiz do XML
      Element root = doc.getDocumentElement();
      
      // Iniciando a criação do PPT
             
      //Criando o arquivo PPT
      SlideShow ppt;
      ppt = new SlideShow();    
         
      // Determinando o caminho e o nome do arquivo de saída
      String raizUpload = request.getParameter("raizUpload");
      String caminho = raizUpload + "/relatorioPpt";
      String nomeArquivo = "relatorioAcompanhamento.ppt";
	
      if(!caminho.endsWith("/"))
			caminho = caminho + "/";
		
      String caminhoCompleto = caminho + nomeArquivo;
     
      // Cria o diretorio se nao existir
      File diretorio = new File(caminho);
	
      if(!diretorio.isDirectory()) 
		diretorio.mkdirs();
	
      FileOutputStream out = new FileOutputStream(caminhoCompleto);
      
                   
      // ************** Montagem do Item ****************** //
         
      String nomeItem = null;
      String coordenador = "";
      
      // Caso não existam itens a serem exibidos,
      // O slide é exibido apenas com a informação de que nenhum item foi encontrado.
      if (root.getElementsByTagName("semItens").getLength() > 0) {
    	  Slide s = ppt.createSlide();
    	  adicionaTitulo(request, root, s, ppt, caminhoImagens);
    	  TextBox semItens = adicionaTextBox(s, "Nenhum item foi encontrado para os parâmetros deste relatório.", 
    			  null, 5, 102, 600, 20, 0);
    	  formataFonte(semItens, 14, "Arial", true, false, false, Color.blue, TextBox.AlignLeft);
    	  adicionaRodape(root, s);
    	  ppt.write(out);
          exibePPT(request, response, out, caminhoCompleto);    	  
      }
     
      NodeList itens = root.getElementsByTagName("item");
            
      ArrayList slides = new ArrayList();
      
      Slide s1;
      int quantidadeSlides = 0;
      int posicaoYQuebraSlide = 548;
      int incrementoParaOutroParecer = 0;
      int desceEtapas = 0;
          
      for (int i = 0; i < itens.getLength(); i++) {
          Node item = itens.item(i);
          NodeList filhosItemCorrente = item.getChildNodes();
          Node nd = null;
          NamedNodeMap map = item.getAttributes();
          nd = map.getNamedItem("nomeItem");
          
          
          if (!nd.getNodeValue().equals("")) {
        	  nomeItem = nd.getNodeValue();
          }
                  
          // Obtendo a tag ocorrencias do item corrente
          // Obtendo os pareceres do item corrente
          Node noOcorrenciaCorrente = null;
          Node noParecerCorrente = null;
          ArrayList pareceres = new ArrayList();
          String nomeNo;
          
          for (int o = 0; o < filhosItemCorrente.getLength(); o++) {
        	  nomeNo = filhosItemCorrente.item(o).getNodeName();
        	  if (nomeNo.equals("ocorrencias")) {
        		  noOcorrenciaCorrente = filhosItemCorrente.item(o);        		  
        	  } else if (nomeNo.equals("parecer")) {
        		  noParecerCorrente = filhosItemCorrente.item(o);
        		  pareceres.add(noParecerCorrente);
        		  desceEtapas++;
        	  }
          }
          
          // Se a tag item corrente tem o atributo nomeItem != "", cria-se um novo slide
          if (!nd.getNodeValue().equals("")) {        	  
        	   // Criando o slide
	          s1 = null;
	          incrementoParaOutroParecer = 0;
	          slides.add(quantidadeSlides, ppt.createSlide());
	          s1 = (Slide)slides.get(quantidadeSlides);
	          quantidadeSlides++;  
          // Caso contrário, continuamos a escrever no mesmo slide
          } else {  
        	  s1 = (ppt.getSlides()[ppt.getSlides().length - 1]);        	  
          } 
          
          // ************** Montagem do Título ****************** //         
          
          adicionaTitulo(request, root, s1, ppt, caminhoImagens);
              
          // ************** Montagem do Órgao ****************** //
         
          // Obtendo o Órgão no XML
          nd = map.getNamedItem("orgaoItem");
    	
          String orgao = nd.getNodeValue();//pegarAtributo(doc, "item", "orgao");       
        
          // Obtendo o Responsável Técnico no XML                   
          nd = map.getNamedItem("labelRespTecnicoIett");
          if (!nd.getNodeValue().trim().equals("")) {
	          coordenador = nd.getNodeValue();
	          nd = map.getNamedItem("valorRespTecnicoIett");
	          coordenador += "" + nd.getNodeValue();
          }
         
          //Texto do label DR1 - Número de Ocorrencias (label)          
          nd = map.getNamedItem("labelDR1");
          String labelDR1 = nd.getNodeValue() + ":";
         
          //Texto do valor DR1 - Número de Ocorrencias (valor)
          nd = map.getNamedItem("valorDR1");
          String valorDR1 = nd.getNodeValue();
          
          adicionaOrgao (s1, orgao, nomeItem, coordenador);
          
          if (valorDR1 != "") {          
	          //montagem do label DR1
	          TextBox ctLabelDR1 = adicionaTextBox(s1, labelDR1, null, 0, 156, 198, 16, 1);
	          ctLabelDR1.setVerticalAlignment(1);
	          formataFonte(ctLabelDR1, 12, "Arial", true, false, false, Color.black, TextBox.AlignLeft);
	          
	          //montagem do valor DR1
	          TextBox ctValorDR1 = adicionaTextBox(s1, valorDR1, null, 160, 155, 198, 16, 1);
	          ctValorDR1.setVerticalAlignment(1);
	          formataFonte(ctValorDR1, 12, "Arial", false, false, false, Color.black, TextBox.AlignLeft);
          }
          
          // ************** Pareceres (Situação) **************** //
          
          Iterator pareceresItem = pareceres.iterator();
          
          while(pareceresItem.hasNext()) { 
          
        	  noParecerCorrente = (Node)pareceresItem.next();
        	  
        	  // adicionando novo slide caso o parecer corrente não caiba no slide atual:
              if ((174 + incrementoParaOutroParecer) >= posicaoYQuebraSlide) {
            	  
            	  adicionaRodape(root, s1);
            	  
            	  incrementoParaOutroParecer = 0;
            	  
            	  // criando o novo slide
            	  s1 = ppt.createSlide();
            	  slides.add(quantidadeSlides, s1);
            	  quantidadeSlides++;
            	  
            	  // a partir daqui estamos escrevendo num novo slide            	  
            	  adicionaTitulo(request, root, s1, ppt, caminhoImagens);
            	  adicionaOrgao (s1, orgao, nomeItem, coordenador);
              }
        	                
	          //montagem do label situação
	          TextBox ctSituacao = adicionaTextBox(s1, "Situação:", null, 0, 174 + incrementoParaOutroParecer, 99, 17, 1);
	          ctSituacao.setVerticalAlignment (1);
	          formataFonte(ctSituacao, 12, "Arial", true, false, false, Color.black, TextBox.AlignLeft);
	
	          //Texto da situação
	          String situacao = noParecerCorrente.getAttributes().getNamedItem("situacaoParecer").getNodeValue().toString();
	          //Deve-se remover as quebras de linha das strings exibidas para que não percam a formatação de fonte.
	          situacao = situacao.replace("\n", " ");
	          
	          //montagem do valor Situação
	          TextBox ctValorSituacao = adicionaTextBox(s1, situacao, null, 58, 174 + incrementoParaOutroParecer, 99, 17, 1);
	          ctValorSituacao.setVerticalAlignment(1);
	          formataFonte(ctValorSituacao, 12, "Arial", false, false, false, Color.black, TextBox.AlignLeft);
	         
	          String pathImagemParecer = "";
	          //imagem da situação
	          try {
		          pathImagemParecer = noParecerCorrente.getAttributes().getNamedItem("caminhoImagem").getNodeValue().toString();
		          int beginIndex = pathImagemParecer.lastIndexOf("/");
		          pathImagemParecer = caminhoImagens + pathImagemParecer.substring(beginIndex, pathImagemParecer.length());
		          int idImagemParecer = ppt.addPicture(new File(pathImagemParecer), Picture.PNG);
		          Picture imagemParecer = new Picture(idImagemParecer);
		          imagemParecer.setAnchor(new java.awt.Rectangle(12, 190 + incrementoParaOutroParecer, 21,21));          
		          s1.addShape(imagemParecer);
	          } catch (Exception e) {
	        	  System.out.println("Imagem não econtrada:" + pathImagemParecer);
	          }
	         
	          //Texto do Label Parecer
	          String labelParecer = noParecerCorrente.getAttributes().getNamedItem("labelParecer").getNodeValue().toString()
	          						+ noParecerCorrente.getAttributes().getNamedItem("dataUltParecer").getNodeValue().toString() + ":";
	         
	          //montagem do labelParecer
	          TextBox ctLabelParecer = adicionaTextBox(s1, labelParecer, null, 48, 193 + incrementoParaOutroParecer, 330, 16, 1);
	          ctLabelParecer.setVerticalAlignment(1);
	          formataFonte(ctLabelParecer, 12, "Arial", true, false, false, Color.black, TextBox.AlignLeft);
	
	          //Descrição do parecer          
	          String descricaoParecer = noParecerCorrente.getAttributes().getNamedItem("descricao").getNodeValue().toString();
	          //Deve-se remover as quebras de linha das strings exibidas para que não percam a formatação de fonte.
	          descricaoParecer = descricaoParecer.replace("\n", " ");
	         
	          //montagem do labelParecer	          
	          TextBox ctDescricaoParecer = adicionaTextBox(s1, descricaoParecer, null, 0, 214 + incrementoParaOutroParecer, 390, 36, 1);	          	          
	          formataFonte(ctDescricaoParecer, 10, "Arial", false, false, false, Color.black, TextBox.AlignLeft);
	         
	          //montagem do label observações
	          TextBox ctLabelObservacoes = adicionaTextBox(s1, "Observações:", null, 0, 235 + incrementoParaOutroParecer, 99, 17, 1);
	          ctLabelObservacoes.setVerticalAlignment (1);
	          formataFonte(ctLabelObservacoes, 12, "Arial", true, false, false, Color.black, TextBox.AlignLeft);
	         
	          //observações          
	          String observacoes = "";
	          
	          if (noParecerCorrente.getAttributes().getNamedItem("observacoes") != null){
	        	  observacoes = noParecerCorrente.getAttributes().getNamedItem("observacoes").getNodeValue().toString();
	          }
	          //Deve-se remover as quebras de linha das strings exibidas para que não percam a formatação de fonte.
	          observacoes = observacoes.replace("\n", " ");
	         
	          //montagem do textbox de observacoes
	          TextBox ctObservacoes = adicionaTextBox(s1, observacoes, null, 0, 250 + incrementoParaOutroParecer, 390, 36, 1);
	          ctObservacoes.setVerticalAlignment(0);
	          formataFonte(ctObservacoes, 12, "Arial", false, false, false, Color.black, TextBox.AlignLeft);
          
	          incrementoParaOutroParecer = incrementoParaOutroParecer + 100;
          }
	          
          //************** Lado Direito - tag labelFuncao *************************//
         
          //Texto do label Função
          if (noOcorrenciaCorrente != null) {
	          String labelFuncao = noOcorrenciaCorrente.getAttributes().getNamedItem("labelFuncao").getNodeValue().toString();
	
	          //Montagem do label Função
	          TextBox ctLabelFuncao = adicionaTextBox(s1, labelFuncao, null, 397, 156, 198, 16, 1);
	          ctLabelFuncao.setVerticalAlignment(1);
	          formataFonte(ctLabelFuncao, 12, "Arial", true, false, false, Color.black, TextBox.AlignLeft);
	        
	          //Preenchimento da função
	          TextBox ctCabecalhoFuncao = adicionaTextBox(s1, "Data", Color.LIGHT_GRAY, 396, 173, 70, 14, 1);
	          ctCabecalhoFuncao.setVerticalAlignment (1);
	          formataFonte(ctCabecalhoFuncao, 8, "Arial", true, false, false, Color.black, TextBox.AlignLeft);
	         
	          TextBox ctCabecalhoFuncao2 = adicionaTextBox(s1, "Descrição", Color.LIGHT_GRAY, 456, 173, 333, 14, 1);
	          ctCabecalhoFuncao2.setVerticalAlignment(1);
	          formataFonte(ctCabecalhoFuncao2, 8, "Arial", true, false, false, Color.black, TextBox.AlignLeft);
	          
	          // tag ocorrencia         
	                          
	          NodeList ocorrenciaNodeList = noOcorrenciaCorrente.getChildNodes();
	                    
	          int posicaoYOcorrencia = 187;
	          
	          for (int o = 0; o < ocorrenciaNodeList.getLength(); o++) {
	              Node ocorrenciaNode = ocorrenciaNodeList.item(o);
	             
	              NamedNodeMap mapOcorrencia = ocorrenciaNode.getAttributes();
	              
	              if (mapOcorrencia != null) {
	            	  Node ndOcorrenciaData = mapOcorrencia.getNamedItem("data");              
		              Node ndOcorrenciaDescricao = mapOcorrencia.getNamedItem("descricao");
		              String dataOcorrencia = ndOcorrenciaData.getNodeValue();
		              String descricaoOcorrencia = ndOcorrenciaDescricao.getNodeValue();		            
		              //Deve-se remover as quebras de linha das strings exibidas para que não percam a formatação de fonte.
		              descricaoOcorrencia = descricaoOcorrencia.replace("\n", " ");
		              
		              // Caixa de texto de cada "ocorrencia" - Data
		              TextBox ctOcorrenciaData = adicionaTextBox(s1, dataOcorrencia, null, 396, posicaoYOcorrencia, 70, 12, 1);
		              ctOcorrenciaData.setVerticalAlignment(1);
		              formataFonte(ctOcorrenciaData, 10, "Arial", false, false, false, Color.BLACK, TextBox.AlignLeft);
		                            
		              // Caixa de texto de cada "ocorrencia" - Descrição
		              TextBox ctOcorrenciaDescricao = adicionaTextBox(s1, descricaoOcorrencia, null, 456, posicaoYOcorrencia, 333, 12, 1);
		              ctOcorrenciaDescricao.setVerticalAlignment(1);
		              formataFonte(ctOcorrenciaDescricao, 10, "Arial", false, false, false, Color.BLACK, TextBox.AlignLeft);
		             
		              posicaoYOcorrencia = posicaoYOcorrencia + new Double(ctOcorrenciaData.getAnchor().getHeight()).intValue();
	              }
	          }
	          
	          // Caso exista um outro slide com Diário de Bordo, resetamos a posicaoYOcorrencia 
	          posicaoYOcorrencia = 187;
      		}
         
          // ************ ETAPAS *************** //
          
          // tag etapas
          int posicaoYEtapas = 320;
          
          // caso tenhamos mais de 1 parecer, devemos deslocar as etapas para baixo:
          // (4 é a quantidade máxima de pareceres que cabem em um slide)
          if (pareceres.size() > 1 && pareceres.size() <= 4) {          
        	  posicaoYEtapas = posicaoYEtapas + (pareceres.size() * 58);        	  
          } else if (desceEtapas > 0) {
        	  posicaoYEtapas = posicaoYEtapas + (desceEtapas * 58);
        	  desceEtapas = 0;
          }
                              
          int espacoEntreTextBox = 20;
          Color corColuna = new Color(238,233,233); // cinza clarinho  
          NodeList etapasNodeList = root.getElementsByTagName("etapas");          
          NodeList etapaNodeList = root.getElementsByTagName("etapa");
                    
          for (int x = 0; (etapaNodeList.getLength() > 0) && (x < etapasNodeList.getLength()); x++) {
              
        	  Node etapasNode = etapasNodeList.item(x);
             
              NamedNodeMap mapEtapas = etapasNode.getAttributes();
              
              if (mapEtapas != null && (etapasNode.getParentNode().equals(item) )) {
	              Node ndEtapas = mapEtapas.getNamedItem("labelFuncao");
	              String nomeEtapa = ndEtapas.getNodeValue();
	              
	              // adicionando novo slide caso as Etapas não caibam no slide atual:
	              if (posicaoYEtapas >= posicaoYQuebraSlide) {
	            	  
	            	  adicionaRodape(root, s1);
	            	  
	            	  // resetando a posicaoYEtapas para ser utlizada no próximo slide
	            	  posicaoYEtapas = 174;
	            	  
	            	  // criando novo slide
	            	  s1 = ppt.createSlide();
	            	  slides.add(quantidadeSlides, s1);
	            	  quantidadeSlides++;
	            	  
	            	  // a partir de agora escrevemos em outro slide
	            	  adicionaTitulo(request, root, s1, ppt, caminhoImagens);
	            	  adicionaOrgao (s1, orgao, nomeItem, coordenador);
	              }
	              	             
	              // Caixa de texto do Label "Etapas:"
	              TextBox ctNomeEtapa = adicionaTextBox(s1, nomeEtapa, null, 3, posicaoYEtapas, 403, 15, 1);
	              ctNomeEtapa.setVerticalAlignment(1);
	              formataFonte(ctNomeEtapa, 12, "Arial", true, false, false, Color.BLACK, TextBox.AlignLeft);
	              posicaoYEtapas = posicaoYEtapas + espacoEntreTextBox;
	              
	              // tag etapa    
	              for (int p = 0; p < etapaNodeList.getLength(); p++) {
	            	  
	                  Node etapaNode = etapaNodeList.item(p);             
	                  NamedNodeMap mapEtapa = etapaNode.getAttributes();
	                  
	                  // dentre as tags etapa do xml, queremos apenas as que são filhas do nó
	                  // etapas corrente:
	                  if (mapEtapa != null && etapaNode.getParentNode().equals(etapasNode)) {	                	  
	                  	                  
		                  Node ndEtapa = mapEtapa.getNamedItem("nomeEtapa");
		                  String nomeEtapas = ndEtapa.getNodeValue();
		                 
		                  // Caixa de texto do Nome da Etapa
		                  TextBox ctNomeEtapas = adicionaTextBox(s1, nomeEtapas, null, 3, posicaoYEtapas, 403, 15, 1);
		                  ctNomeEtapas.setVerticalAlignment(1);
		                  formataFonte(ctNomeEtapas, 12, "Arial", true, false, false, Color.BLACK, TextBox.AlignLeft);
		                  posicaoYEtapas = posicaoYEtapas + (espacoEntreTextBox - 6);
		                  
		                  // tag etapasColuna          
		                  NodeList etapasColunaNodeList = etapaNode.getChildNodes();
		                  String nomeEtapasColuna = "";                  
		                  ArrayList linhasValor = new ArrayList();
		                  String valor = "";
		              
		                  for (int q = 0; q < etapasColunaNodeList.getLength(); q++) {                  
		                	  
		                	  Node etapasColunaNode = etapasColunaNodeList.item(q);
		                	  
		                	  valor = "";
		                	                	  
		                	  if (etapasColunaNode.getNodeName().equals("etapasColuna")) {
		    	                  NamedNodeMap mapEtapasColuna = etapasColunaNode.getAttributes();
		    	                  Node ndEtapasColuna = mapEtapasColuna.getNamedItem("label");
		    	                  nomeEtapasColuna = nomeEtapasColuna + 
		    	                  						ndEtapasColuna.getNodeValue() + "\t ";
		    	                  
		                	  } else if (etapasColunaNode.getNodeName().equals("itemEtapa")) {
		                		  
		                		  NodeList estapasValorNodeList = etapasColunaNode.getChildNodes();              		                		  
		                		                 		  
		                		  for (int s = 0; s < estapasValorNodeList.getLength(); s++) {		                			  
		                			  Node noEtapaValor = estapasValorNodeList.item(s);
		                			  NamedNodeMap mapEtapaValores = noEtapaValor.getAttributes();		                			  
		                			  if (mapEtapaValores != null){
		                				  Node ndEtapasValor = mapEtapaValores.getNamedItem("valor");
		            	                  valor = valor + ndEtapasValor.getNodeValue().replace("\n", "") + "\t ";                				   
		                			  }
		                		  }
		                		  
		                		  linhasValor.add(valor);		                		  
		                	  }  
		                  }                 
		                
		                  // Caixa de texto com Labels de coluna da Etapa
		                  TextBox ctEtapasColuna = adicionaTextBox(s1, nomeEtapasColuna, Color.LIGHT_GRAY, 5, posicaoYEtapas, 783, 15, 1);
		                  ctEtapasColuna.setVerticalAlignment(1);
		                  formataFonte(ctEtapasColuna, 8, "Arial", true, false, false, Color.BLACK, TextBox.AlignLeft);
		                  posicaoYEtapas = posicaoYEtapas + new Double(ctEtapasColuna.getAnchor().getHeight()).intValue();
		                  
		                  // Adiciona as linhas com valores das etapas:
		                  for (int posi = 0; posi < linhasValor.size(); posi++) {
		                	  
		                	  // Determinando a cor da linha atual (pode ser cinzinha claro ou branco)
		                	  corColuna = new Color(238,233,233);
		                	  if (posi % 2 != 0) corColuna = null;
		                	  
		                	  // adicionando novo slide caso a linha de valor da etapa não caiba no slide atual:
			                  if (posicaoYEtapas >= posicaoYQuebraSlide) {
			                	  
			                	  adicionaRodape(root, s1);
			                	  
			                	  // resetando a posicaoYEtapas para o outro slide 
			                	  posicaoYEtapas = 174;
			                	  
			                	  // criando o novo slide
			                	  s1 = ppt.createSlide();
			                	  slides.add(quantidadeSlides, s1);
			                	  quantidadeSlides++;
			                	  
			                	  // a partir de agora estamos escrevendo no novo slide
			                	  adicionaTitulo(request, root, s1, ppt, caminhoImagens);
			                	  adicionaOrgao (s1, orgao, nomeItem, coordenador);
			                  }
		            		                  	  
		                	  TextBox ctColunasValor = adicionaTextBox(s1, linhasValor.get(posi).toString(), corColuna, 5, posicaoYEtapas, 783, 12, 1);
		                      ctColunasValor.setVerticalAlignment(1);
		                      formataFonte(ctColunasValor, 9, "Arial", false, false, false, Color.BLACK, TextBox.AlignLeft);
		                      posicaoYEtapas = posicaoYEtapas + new Double(ctColunasValor.getAnchor().getHeight()).intValue();
		                      valor = "";                      
		                  }
		                  
		                  // espaço entre linhas do conteúdo de cada etapa (linhas brancas e cinzinha claro);
		                  // 5 = altura de cada linha dessas.
		                  posicaoYEtapas = posicaoYEtapas + 5;
		                  linhasValor.clear();
		                  
		              }
	              }
              }                                 
                           
          }          
         
          adicionaRodape(root, s1);
      }
                      
      ppt.write(out);
      exibePPT(request, response, out, caminhoCompleto);
       
    }
     
	// Método para formatar a fonte do texto de um TextBox
     /**
      *
      * @param ct
      * @param tamanho
      * @param nome
      * @param ehNegrito
      * @param ehItalico
      * @param ehSublinhado
      * @param cor
      * @param alinhamento
      */
     public static void formataFonte(TextBox ct, int tamanho, String nome,
							 boolean ehNegrito, boolean ehItalico,
							 boolean ehSublinhado, Color cor, int alinhamento) {
	    	      	  
		  RichTextRun rt = ct.getTextRun().getRichTextRuns()[0];
		  rt.setFontSize(tamanho);
		  rt.setFontName(nome);
		  rt.setBold(ehNegrito);
		  rt.setItalic(ehItalico);
		  rt.setUnderlined(ehSublinhado);
		  rt.setFontColor(cor);
		  rt.setAlignment(alinhamento);
		      	  	  
	}
     
    // Método que adiciona um TextBox em um Slide
        /**
         *
         * @param s1
         * @param texto
         * @param cor
         * @param posicaoX
         * @param posicaoY
         * @param largura
         * @param altura
         * @param formato
         * @return
         */
        public static TextBox adicionaTextBox(Slide s1, String texto, Color cor, int posicaoX,
    								   int posicaoY, int largura, int altura, int formato) {
    	TextBox ct = new TextBox();           
        ct.setText(texto);
        ct.setShapeType(formato);
        ct.setMarginBottom(0);
        ct.setMarginTop(0);
        ct.setAnchor(new java.awt.Rectangle(posicaoX, posicaoY, largura, altura));
        if (cor != null) ct.setFillColor(cor);
        s1.addShape(ct); 
        
        return ct;
    }
    
    // Método que adiciona uma Linha em um Slide
    /**
     *
     * @param s
     * @param posicaoX
     * @param posicaoY
     * @param largura
     * @param altura
     * @param cor
     */
    public static void adicionaLinha(Slide s, int posicaoX, int posicaoY, int largura,
    								  int altura,Color cor) {
    	
        AutoShape sh = new AutoShape(ShapeTypes.Line);
        sh.setAnchor(new java.awt.Rectangle(posicaoX, posicaoY, largura, altura));           
        sh.setLineColor(cor);            
        s.addShape(sh);
    	
    }
   
    // Método que retorna o atributo desejado do nó
    /**
     *
     * @param xml
     * @param noh
     * @param atributo
     * @return
     */
    public static String pegarAtributo(Document xml, String noh, String atributo) {
        String valorAtributo = null;
        // Obtendo o elemento raiz do XML
        Element root = xml.getDocumentElement();
        valorAtributo = root.getElementsByTagName(noh).item(0).getAttributes().getNamedItem(atributo).getNodeValue();
       
        return valorAtributo;
    }
            
    // Método que adiciona o Rodapé a um Slide
    /**
     *
     * @param root
     * @param s1
     */
    public static void adicionaRodape(Element root, Slide s1) {
    
	    // Obtendo o Rodape no XML
	    String rodape = root.getAttributeNode("rodape").getNodeValue();
	                  
	    // Adicionado o TextBox do Rodapé
	    TextBox ctRodape = adicionaTextBox(s1, rodape, null, 4, 590, 400, 15, 1);
	    ctRodape.setVerticalAlignment(1);
	    formataFonte(ctRodape, 10, "Arial", false, false, false, Color.BLACK, TextBox.AlignLeft);
	    
    }
    
    // Método que adiciona o Título a um Slide
    /**
     *
     * @param request
     * @param root
     * @param s1
     * @param ppt
     * @param caminhoImagens
     */
    @SuppressWarnings("empty-statement")
    public static void adicionaTitulo(HttpServletRequest request, Element root, Slide s1, SlideShow ppt, String caminhoImagens) {
    	
    	try {
    		
    		ConfiguracaoDao configDao = new ConfiguracaoDao(request);
			ConfiguracaoCfg config = configDao.getConfiguracao();;
			String pathRaiz = config.getRaizUpload();
			EmpresaDao empresaDao = new EmpresaDao(request);
			List confg = empresaDao.listar(EmpresaEmp.class, null);
			EmpresaEmp empresa = new EmpresaEmp();
			if(confg != null && confg.size() > 0){
				empresa = (EmpresaEmp) confg.iterator().next();
			}
    		
    		try {

    			// Inserindo a imagem do cabeçalho do PPT
		        int idx = ppt.addPicture(new File(pathRaiz + empresa.getLogotipoRelatorioEmp()), Picture.PNG);
		        Picture pict = new Picture(idx);
		        pict.setAnchor(new java.awt.Rectangle(0, 0, 790,69));           
		        s1.addShape(pict);
    		} catch (Exception e) {
				System.out.println("Imagem não econtrada: "+ pathRaiz + empresa.getLogotipoRelatorioEmp());
			}
	    	
	    	// Obtendo o título do PPT no XML (root.getAttribute("mesReferencia"))
	        String titulo = "Relatório de Acompanhamento - " + root.getAttribute("mesReferencia");
	      
	        // Caixa de texto do Título do Relatório
	        TextBox ctTitulo = adicionaTextBox(s1, titulo, null, 0, 78, 468, 24, 0);
	        formataFonte(ctTitulo, 14, "Arial", false, false, false, Color.black, TextBox.AlignLeft);
	             
	        // Obtendo o modelo no XML
	        String codModelo = root.getAttribute("codModelo");
	      
	        // Caixa de texto do Modelo do Relatório     
	        TextBox ctModelo = adicionaTextBox(s1, codModelo.toString(), null, 425, 82, 340, 18, 0);
	        formataFonte(ctModelo, 9, "Arial", true, false, false, Color.black, TextBox.AlignRight);
	       
	        // Linha do Título do Relatório
	        adicionaLinha(s1, 0, 100, 790, 0, Color.LIGHT_GRAY);
    	
    	} catch (Exception e) {
    		
    	}
    }
    
    // Método que adiciona as informações sobre o Órgão e Coordenador
    /**
     *
     * @param s1
     * @param orgao
     * @param nomeItem
     * @param coordenador
     */
    public static void adicionaOrgao(Slide s1, String orgao, String nomeItem, String coordenador) {

    	Color corOrgao = new Color(0,0,139);
    	
        // Caixa de texto do Órgao
        TextBox ctOrgao = adicionaTextBox(s1, orgao, null, 0, 102, 519, 20, 0);
        formataFonte(ctOrgao, 12, "Arial", true, false, false, corOrgao, TextBox.AlignLeft);
      
        TextBox ctItem = adicionaTextBox(s1, nomeItem, Color.LIGHT_GRAY, 5, 122, 390, 30, 1);
        ctItem.setVerticalAlignment(1);
        formataFonte(ctItem, 12, "Arial", false, false, false, corOrgao, TextBox.AlignLeft);
      
        //Caixa de texto do coordenador         
        TextBox ctCoordenador = adicionaTextBox(s1, coordenador, Color.LIGHT_GRAY, 396, 122, 392, 30, 1);
        ctCoordenador.setVerticalAlignment(1);
        formataFonte(ctCoordenador, 8, "Arial", false, false, false, corOrgao, TextBox.AlignLeft);      
        
    }
    
    // Método responsável pelo download do Powerpoint na tela do cliente
    /**
     *
     * @param request
     * @param response
     * @param out
     * @param caminho
     */
    public static void exibePPT(HttpServletRequest request, HttpServletResponse response,
    							 FileOutputStream out, String caminho) {
    	   
    	try {
    	
    		UsuarioUsu usuarioImagem = null;  
    		String hashNomeArquivo = null;
    		
    		hashNomeArquivo = Util.calcularHashNomeArquivo(caminho, caminho);
    		usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
    		Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, caminho, caminho);
    		
	    	response.setHeader("Content-Disposition","inline");
	        response.setContentType("application/ppt");        
	        response.sendRedirect(request.getContextPath() + "/DownloadFile?RemoteFile=" + hashNomeArquivo);
        	    	
        } catch (Exception e) {
        	System.out.println("Ocorreu um erro na exibição do Powerpoint.");
        }
    }
}


