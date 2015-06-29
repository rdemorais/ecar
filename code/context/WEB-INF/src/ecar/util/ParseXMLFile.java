package ecar.util;


import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 *
 * @author 70744416353
 */
public class ParseXMLFile
{

	private String xmlFileName;

        /**
         *
         * @param values
         * @param xmlFile
         * @return
         */
        public  Map<String,String> getPropertiesXMLFile(Map<String, String> values, String xmlFile)
	{
		this.xmlFileName = xmlFile; 
		// parse XML file -> XML document will be build
		Document doc = parseFile(xmlFileName);
		// get root node of xml tree structure
		Node root = doc.getDocumentElement();				
		writeDocumentToOutput(root, 0, values);
		return values; 
	}

        /**
         *
         * @param elem
         * @return
         */
        public final static String getElementValue(Node elem)
	{
		Node kid;
		if (elem != null)
		{
			if (elem.hasChildNodes())
			{
				for (kid = elem.getFirstChild(); kid != null; kid = kid
						.getNextSibling())
				{
					if (kid.getNodeType() == Node.TEXT_NODE)
					{
						return kid.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Writes node and all child nodes into System.out
	 * 
	 * @param node
	 *            XML node from from XML tree wrom which will output statement
	 *            start
	 * @param indent
         *            number of spaces used to indent output
         * @param values
	 */
	public void writeDocumentToOutput(Node node, int indent, Map<String, String> values)
	{
		String nodeValue = getElementValue(node);
		// get attributes of element
		NamedNodeMap attributes = node.getAttributes();
		//System.out.println(getIndentSpaces(indent) + "NodeName: " + nodeName + ", NodeValue: " + nodeValue);
		for (int i = 0; i < attributes.getLength(); i++)
		{
			Node attribute = attributes.item(i);			
			
			// Verifica se Algum elemento da Lista foi encontrado
			if (attribute.getNodeValue() != null) 
			{
				// && attribute.getNodeValue().equals("hibernate.connection.driver_class"))
				if (values.get(attribute.getNodeValue()) != null 
						  && values.get(attribute.getNodeValue()).equals(""))
				{
				    values.put(attribute.getNodeValue(), nodeValue);   	
				}								
			}		
		}
		
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE)
			{				
				writeDocumentToOutput(child, indent + 2, values);
			}
		}		
	}


        /**
         *
         * @param fileName
         * @return
         */
        public Document parseFile(String fileName)
	{
		System.out.println("Parsing XML file... " + fileName);
		DocumentBuilder docBuilder;
		Document doc = null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		try
		{
			docBuilder = docBuilderFactory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			return null;
		}
		File sourceFile = new File(fileName);
		try
		{
			doc = docBuilder.parse(sourceFile);
		}
		catch (SAXException e)
		{
			return null;
		}
		catch (IOException e)
		{
			System.out.println("Could not read source file: " + e.getMessage());
		}
		return doc;
	}	
}