package com.jan.beta.util.string;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.jan.beta.util.ClassLoaderUtil;

public class XmlUtil
{

	public static Document parseXmlText(String xml) throws Exception
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes("utf-8"));
		return parseXmlInputSource(new InputSource(bais));
	}

	public static Document parseXmlResource(String resource) throws Exception
	{
		InputStream inputStream = ClassLoaderUtil.getStream(resource);
		InputSource inputSource = new InputSource(inputStream);
		return parseXmlInputSource(inputSource);
	}

	public static Document parseXmlInputStream(InputStream inputStream) throws Exception
	{
		Document document = null;
		try
		{
			document = getDocumentBuilder().parse(inputStream);
		}
		catch (Exception e)
		{
			throw new Exception("couldn't parse xml", e);
		}
		return document;
	}

	public static Document parseXmlInputSource(InputSource inputSource) throws Exception
	{
		Document document = null;
		try
		{
			document = getDocumentBuilder().parse(inputSource);
		}
		catch (Exception e)
		{
			throw new Exception("couldn't parse xml", e);
		}
		return document;
	}

	public static DocumentBuilder getDocumentBuilder() throws FactoryConfigurationError, ParserConfigurationException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		return factory.newDocumentBuilder();
	}

	public static Iterator elementIterator(Element element, String tagName)
	{
		return elements(element, tagName).iterator();
	}

	public static List elements(Element element, String tagName)
	{
		NodeList nodeList = element.getElementsByTagName(tagName);
		List elements = new ArrayList(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node child = nodeList.item(i);
			if (child.getParentNode() == element)
			{
				elements.add(child);
			}
		}
		return elements;
	}

	public static String getElementValueByTagName(Document doc, String tagName)
	{
		NodeList nodeList = doc.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0)
		{
			return nodeList.item(0).getFirstChild().getNodeValue();
		}
		else
		{
			return null;
		}
	}

	public static Element element(Element element, String name)
	{
		Element childElement = null;
		NodeList nodeList = element.getElementsByTagName(name);
		if (nodeList.getLength() > 0)
		{
			childElement = (Element) nodeList.item(0);
		}
		return childElement;
	}

	public static Iterator elementIterator(Element element)
	{
		return elements(element).iterator();
	}

	public static List elements(Element element)
	{
		List elements = new ArrayList();
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if ((node instanceof Element) && (element == node.getParentNode()))
			{
				elements.add(node);
			}
		}
		return elements;
	}

	public static Element element(Element element)
	{
		Element onlyChild = null;
		List elements = elements(element);
		if (!elements.isEmpty())
		{
			onlyChild = (Element) elements.get(0);
		}
		return onlyChild;
	}

	public static String toString(Element element) throws Exception
	{
		if (element == null)
			return "null";

		Source source = new DOMSource(element);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		Result result = new StreamResult(printWriter);

		try
		{
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
		}
		catch (Exception e)
		{
			throw new Exception("couldn't write element '" + element.getTagName() + "' to string", e);
		}

		printWriter.close();

		return stringWriter.toString();
	}

	public static String getContentText(Element element)
	{
		StringBuffer buffer = new StringBuffer();
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if (node instanceof CharacterData)
			{
				CharacterData characterData = (CharacterData) node;
				buffer.append(characterData.getData());
			}
		}
		return buffer.toString();
	}

	public static String getTagValue(String xmlmsg, String tag)
	{
		String tagValue = "";
		if (!xmlmsg.startsWith("<?xml"))
		{
			String xmlHeader = "<?xml version='1.0' encoding='utf-8'?>";
			xmlmsg = xmlHeader + xmlmsg;
		}
		try
		{
			Document doc = new XmlUtil().parseXmlText(xmlmsg);
			doc.normalize();
			Element root = (Element) doc.getElementsByTagName(tag).item(0);
			tagValue = root.getAttribute("value");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tagValue;
	}

	public static Map<String,String> xmlBodyDecode(String xmlmsg)
	{
		Map<String,String> map = new HashMap<String,String>();
		if (!xmlmsg.startsWith("<?xml"))
		{
			String xmlHeader = "<?xml version='1.0' encoding='utf-8'?>";
			xmlmsg = xmlHeader + xmlmsg;
		}
		try
		{
			Document doc = parseXmlText(xmlmsg);
			doc.normalize();
			Element root = (Element) doc.getElementsByTagName("Body").item(0);
			NodeList paramList = root.getChildNodes();
			Node child = paramList.item(0).getFirstChild();
			String value = child == null ? "" : child.getNodeValue();
			System.out.println("############" + value);
//			boss��������{}���ж������ڣ���ȥ��LiuPeipeng
			if(value.indexOf("{") == 0){
				value = value.substring(1,value.length() - 1);//ȥ��{}
			}
			String[] bodyArray = value.split(",");

			for (String str : bodyArray)
			{
				try
				{
					String[] strArray = str.split("=");
					String keyStr = strArray[0].trim();
					String valueStr = strArray[1].trim();
					map.put(keyStr, valueStr.substring(2, valueStr.length() - 2));
				}
				catch (Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return map;
	}
	public static String getClassid(String xmlmsg)
	{
		if (!xmlmsg.startsWith("<?xml"))
		{
			String xmlHeader = "<?xml version='1.0' encoding='utf-8'?>";
			xmlmsg = xmlHeader + xmlmsg;
		}
		try
		{
			Document doc = new XmlUtil().parseXmlText(xmlmsg);
			doc.normalize();
			Element root = (Element) doc.getElementsByTagName("Body").item(0);
			NodeList paramList = root.getChildNodes();
			Node child = paramList.item(0).getFirstChild();
			String value = child == null ? "" : child.getNodeValue();

			
			String[] bodyArray = value.split(",");
			for (String str : bodyArray)
			{
				try
				{
					String[] strArray = str.split("=");
					String keyStr = strArray[0].trim();
					String valueStr = strArray[1].trim();
					if("CLASS_ID".equals(keyStr))
					{
						return valueStr.substring(2, valueStr.length() - 2);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
	public static void main(String[] args)
	{
		Map map = new HashMap();
		map.put("CLASS_ID", "1");
		map.put("CLASS_NAME", "2");
		System.out.println(map);
		String classid = (String) map.get("CLASS_ID");
		System.out.println(classid);
	}

}