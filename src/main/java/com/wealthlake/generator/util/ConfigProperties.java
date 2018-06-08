package com.wealthlake.generator.util;

import cn.org.rapid_framework.generator.util.ClassHelper;
import cn.org.rapid_framework.generator.util.FileHelper;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Created By Rsh
 *
 * @author rsh
 * @Description
 * @Date: 2018/6/7
 * @Time: 19:38
 */
public class ConfigProperties {

    private static String configPropertiesFile = "config.properties";
    private static Properties p = new Properties();

    public static void reload() {
        try {
            File configFile = FileHelper.getFile(configPropertiesFile);
            if (!configFile.exists()) {
                try {
                    Properties prop = new Properties();
                    InputStream in = getGeneratorFileInputStream();
                    prop.load(in);

                    FileOutputStream oFile = new FileOutputStream(configFile, false);
                    prop.store(oFile, "new properties file");
                    oFile.close();
                } catch (IOException e) {
                    System.out.println(e);
                }

                /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputStream in = getGeneratorFileInputStream();
                Document doc = db.parse(in);
                in.close();

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(doc);
                // 设置编码类型
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                // 添加docType
                transformer.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_SYSTEM, doc.getDoctype().getSystemId());
                FileOutputStream out = new FileOutputStream(configFile);
                StreamResult result = new StreamResult(out);
                transformer.transform(domSource, result);
                out.close();*/
            }
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(configFile));
            p.load(in);
        } catch (IOException var2) {
            System.out.println(var2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateConfigFile() {
        try {
            File configFile = FileHelper.getFile(configPropertiesFile);
            FileOutputStream oFile = new FileOutputStream(configFile, false);
            p.store(oFile, "update properties file");
            oFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String getProperty(String key) {
        return p.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return p.getProperty(key, defaultValue);
    }

    public static void setProperty(String key, String defaultValue) {
        p.setProperty(key, defaultValue);
    }

    public static InputStream getGeneratorFileInputStream() {
        try {
            URL url = ClassHelper.getDefaultClassLoader().getResource(configPropertiesFile);
            URLConnection con = url.openConnection();
            con.setUseCaches(false);
            InputStream input = con.getInputStream();
            return input;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
