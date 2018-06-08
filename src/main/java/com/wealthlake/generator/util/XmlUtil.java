package com.wealthlake.generator.util;

import cn.org.rapid_framework.generator.util.ClassHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created By Rsh
 *
 * @author rsh
 * @Description
 * @Date: 2018/6/7
 * @Time: 13:37
 */
public class XmlUtil {

    public static void modifySon(String newPackage) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmldoc = db.parse(XmlUtil.getGeneratorFileInputStream());

            Element root = xmldoc.getDocumentElement();
            System.out.println(root.toString());

            Element per = (Element) selectSingleNode("/properties/entry[@key='outRoot']", root);
            per.setTextContent("${user.dir}/generator-output/" + newPackage);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
//            former.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_PUBLIC, xmldoc.getDoctype().getPublicId());
            former.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_SYSTEM, xmldoc.getDoctype().getSystemId());
            //former.transform(new DOMSource(xmldoc), new StreamResult(new File(XmlUtil.getCurrentPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Node selectSingleNode(String express, Element source) {
        Node result = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try {
            result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static InputStream getGeneratorFileInputStream() {
        try {
            URL url = ClassHelper.getDefaultClassLoader().getResource("generator.xml");
            URLConnection con = url.openConnection();
            //String path = XmlUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            //path = URLDecoder.decode(path, "utf-8");
            //String rootPath = path + "/generator.xml";
            con.setUseCaches(false);
            InputStream input = con.getInputStream();
            return input;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OutputStream getGeneratorFileOutputStream() {
        try {
            URL url = ClassHelper.getDefaultClassLoader().getResource("generator.xml");
            URLConnection con = url.openConnection();
            con.setUseCaches(false);
            return con.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPath() {
        try {
           /* String path = XmlUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = URLDecoder.decode(path, "utf-8");
            String rootPath = path + "/generator.xml";
            return rootPath;*/
            URL url = ClassHelper.getDefaultClassLoader().getResource("generator.xml");
            return url.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
