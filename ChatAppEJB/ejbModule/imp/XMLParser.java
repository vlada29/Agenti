package imp;

import java.io.File;
import java.util.LinkedList;

import javax.ejb.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import interfaces.Parser;
import model.Host;

@Singleton
public class XMLParser implements Parser {

	private Document document;
	private String filepath = "xml_config/configuration.xml";
	
	
	public XMLParser() {
		try {
			File inputFile = new File(filepath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        document = dBuilder.parse(inputFile);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public LinkedList<Host> getAllHosts() {
		LinkedList<Host> hosts = new LinkedList<Host>();
		NodeList nList = document.getElementsByTagName("host");
		for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               String alias =  eElement.getElementsByTagName("alias").item(0).getTextContent().trim();
               String address = eElement.getElementsByTagName("ip").item(0).getTextContent().trim()+ ":" + eElement.getElementsByTagName("port").item(0).getTextContent().trim();
               hosts.add(new Host(alias,address));
            }
         }
		return hosts;
	}

	@Override
	public void addNewHost(Host h) {
		Node cluster = document.getFirstChild();
		Element newHost = document.createElement("host");
		Element newHostAlias = document.createElement("alias");
		newHostAlias.appendChild(document.createTextNode(h.getAlias()));
		Element newHostIp = document.createElement("ip");
		String split[] = h.getAddress().split(":");
		newHostIp.appendChild(document.createTextNode(split[0]));
		Element newHostPort = document.createElement("port");
		newHostPort.appendChild(document.createTextNode(split[1]));
	
		newHost.appendChild(newHostAlias);
		newHost.appendChild(newHostIp);
		newHost.appendChild(newHostPort);
		
		cluster.appendChild(newHost);
		saveDocument();
	}

	@Override
	public Host getHostByAlias(String alias) {
		NodeList nList = document.getElementsByTagName("host");
		
		for(int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				if(eElement.getElementsByTagName("alias").item(0).getTextContent().trim().equals(alias)) {
		              String address = eElement.getElementsByTagName("ip").item(0).getTextContent().trim()+ ":" + eElement.getElementsByTagName("port").item(0).getTextContent().trim();
		              return new Host(alias,address);
				}
			}
		}
		return null;
	}

	@Override
	public void removeAllHosts() {
		Node cluster = document.getFirstChild();
		NodeList nList = cluster.getChildNodes();
		
		for(int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				if(!eElement.getElementsByTagName("alias").item(0).getTextContent().trim().equals("Master")) {
					cluster.removeChild(node);
				}
			}
		}
		
		saveDocument();
	}

	private void saveDocument() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
