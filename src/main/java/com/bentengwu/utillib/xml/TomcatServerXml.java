/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.xml;

import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.file.PathUtil;
import com.bentengwu.utillib.reflection.UtilReflection;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 *
 * @author 伟宏
 */
public class TomcatServerXml {

    private String server_xml;
    private String shutDownPort;
    private List<TomcatConnector> connectors = Lists.newArrayList();
    private List<TomcatContext> contexts = Lists.newArrayList();
    private static TomcatServerXml tomcatServer;

    private TomcatServerXml(String path) {
        server_xml = path;
        initConnector();
        initContext();
    }

    public static synchronized TomcatServerXml getInstance(String path) {
        if (tomcatServer == null) {
            tomcatServer = new TomcatServerXml(path);
        }
        return tomcatServer;
    }

    /**
     * 读取关闭端口.
     *
     * @return 关闭端口.
     * @throws DocumentException
     */
    public String getShutDownPort() {
        if (StringUtils.isBlank(shutDownPort)) {
            try {
                SAXReader reader = new SAXReader();
                Document doc = reader.read(new File(server_xml));
                Element root = doc.getRootElement();
                if (root.getName().equals("Server")) {
                    shutDownPort = root.attributeValue("port");
                }
            } catch (DocumentException ex) {
                log.info(ex);
            }

        }
        return shutDownPort;
    }

    private void initConnector() {
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(server_xml));
            List nodes = doc.selectNodes("//Server/Service/Connector");
            TomcatConnector connector = null;
            for (Object node : nodes) {
                Element nd = (Element) node;
                List attris = nd.attributes();
                 connector = new TomcatConnector();
                for (Object ob : attris) {
                    set(connector, ob);
                }
                connectors.add(connector);
            }
        } catch (Exception ex) {
            log.info(ex);
        }
    }

    private void initContext() {
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(server_xml));
            List nodes = doc.selectNodes("//Server/Service/Engine/Host/Context");
             TomcatContext context = null;
            for (Object node : nodes) {
                Element nd = (Element) node;
                List attris = nd.attributes();
                context = new TomcatContext();
                for (Object ob : attris) {
                    set(context, ob);
                }
                contexts.add(context);
            }
        } catch (Exception ex) {
            log.info(ex);
        }
    }

    private void set(Object t, Object ob) {
        try {
            Attribute attr = (Attribute) ob;
            UtilReflection.setFieldValue(t, attr.getName(), attr.getValue());
        } catch (Exception ex) {
            log.info(ex);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("shutdown     : ").append(getShutDownPort()).append(PathUtil.getLineSeparator());
        sb.append("connector    : ").append(StrUtils.list2Str(connectors)).append(PathUtil.getLineSeparator());
        String con = StrUtils.list2Str(contexts);
        if(org.apache.commons.lang.StringUtils.isBlank(con)){
            con = "No config.";
        }
        sb.append("context      : ").append(con);
        return sb.toString();
    }
    
   
    private Log log = LogFactory.getLog(getClass());
}
