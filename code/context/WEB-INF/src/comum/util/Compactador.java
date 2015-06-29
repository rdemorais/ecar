package comum.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author 70744416353
 */
public class Compactador {
	
	private transient ByteArrayOutputStream os;
	private transient GZIPOutputStream gos;
	private transient ByteArrayInputStream is;
	private transient ByteArrayInputStream in;
	private transient GZIPInputStream gis;
	private transient ByteArrayOutputStream out;
	static final int TAMANHO_BUFFER = 2048; // 1MB
	
        /**
         *
         * @param xml
         * @return
         */
        public byte[] compactar(String xml) {
		byte[] dados = new byte[TAMANHO_BUFFER];
		try {
			byte[] axml = xml.getBytes();
			os = new ByteArrayOutputStream();
			gos = new GZIPOutputStream(os);
			is = new ByteArrayInputStream(axml);
			int bytesLidos = 0;
			while ((bytesLidos = is.read(dados, 0, TAMANHO_BUFFER)) > 0) {
				gos.write(dados, 0, bytesLidos);
			}
			is.close();
			gos.close();
			os.close();
			return os.toByteArray();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
        /**
         *
         * @param xml
         * @return
         */
        public byte[] compactar(byte[] xml) {
		byte[] dados = new byte[TAMANHO_BUFFER];
		try {
			System.out.println("Inicial: " + xml.length + " Bytes");
			os = new ByteArrayOutputStream();
			gos = new GZIPOutputStream(os);
			is = new ByteArrayInputStream(xml);
			int bytesLidos = 0;
			while ((bytesLidos = is.read(dados, 0, TAMANHO_BUFFER)) > 0) {
				gos.write(dados, 0, bytesLidos);
			}
			is.close();
			gos.close();
			os.close();
			System.out.println("Final : " + os.size() + " Bytes");
			return os.toByteArray();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
        /**
         *
         * @param dado
         * @return
         */
        public String descompactar(byte[] dado) {
		byte[] dados = new byte[TAMANHO_BUFFER];
		try {
			System.out.println("Inicial: " + dado.length + " Bytes");
			in = new ByteArrayInputStream(dado);
			gis = new GZIPInputStream(in);
			out = new ByteArrayOutputStream();
			int bytesLidos = 0;
			while ((bytesLidos = gis.read(dados, 0, TAMANHO_BUFFER)) > 0) {
				out.write(dados, 0, bytesLidos);
			}
			out.close();
			gis.close();
			in.close();
			System.out.println("Final: " + out.toString().length() + " Bytes");
			return out.toString();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-";
	}
	
        /**
         *
         * @param dado
         * @return
         */
        public byte[] descompactarBytes(byte[] dado) {
		byte[] dados = new byte[TAMANHO_BUFFER];
		try {
			in = new ByteArrayInputStream(dado);
			gis = new GZIPInputStream(in);
			out = new ByteArrayOutputStream();
			int bytesLidos = 0;
			while ((bytesLidos = gis.read(dados, 0, TAMANHO_BUFFER)) > 0) {
				out.write(dados, 0, bytesLidos);
			}
			out.close();
			gis.close();
			in.close();
			return out.toByteArray();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}