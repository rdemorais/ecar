package ecar.util;

import java.util.HashMap;

/**
 * <P>Java Bean criado para armazenar possíveis configurações a serem utilizadas pelo Servlet DownloadFile
 * na hora de configurar o Response a ser enviado para o browser.</P>
 * 
 * <P>Essa classe foi criada para um teste específico e mantida para possíveis testes futuros.</P>
 * 
 * @author 05228805419
 *
 */
public class ConfiguracaoResponseBean {
	
	private boolean inUtilizarConfiguracaoResponse;
	
	private boolean inSetarContentLength;
	
	private String contentType;
	
	private HashMap<String, String> headers;
	
        /**
         *
         */
        public ConfiguracaoResponseBean() {
		this.inUtilizarConfiguracaoResponse = false;
		this.inSetarContentLength = false;
		this.headers = new HashMap<String, String>();
	}

        /**
         *
         * @return
         */
        public String getContentType() {
		return contentType;
	}

        /**
         *
         * @param contentType
         */
        public void setContentType(String contentType) {
		this.contentType = contentType;
	}

        /**
         *
         * @return
         */
        public HashMap<String, String> getHeaders() {
		return headers;
	}

        /**
         *
         * @param headers
         */
        public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

        /**
         *
         * @return
         */
        public boolean getInUtilizarConfiguracaoResponse() {
		return inUtilizarConfiguracaoResponse;
	}

        /**
         *
         * @param inUtilizarConfiguracaoResponse
         */
        public void setInUtilizarConfiguracaoResponse(
			boolean inUtilizarConfiguracaoResponse) {
		this.inUtilizarConfiguracaoResponse = inUtilizarConfiguracaoResponse;
	}

    @Override
	public String toString() {
		return "inUtilizarConfiguracaoResponse - " + this.inUtilizarConfiguracaoResponse + " | " + "inSetarContentLength - " + this.inSetarContentLength + " | " + "contentType - " + this.contentType + " | " + "headers - " + this.headers; 
	}

        /**
         *
         * @return
         */
        public boolean getInSetarContentLength() {
		return inSetarContentLength;
	}

        /**
         *
         * @param inSetarContentLength
         */
        public void setInSetarContentLength(boolean inSetarContentLength) {
		this.inSetarContentLength = inSetarContentLength;
	}
}
