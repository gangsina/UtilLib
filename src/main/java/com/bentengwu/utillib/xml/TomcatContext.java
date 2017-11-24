/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.xml;

import com.bentengwu.utillib.reflection.UtilReflection;

/**
 *
 * @author 伟宏
 */
public class TomcatContext {
    private String path;
    private String docBase;
    private String reloadable;
    private String debug;

    

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getDocBase() {
        return docBase;
    }

    public void setDocBase(String docBase) {
        this.docBase = docBase;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReloadable() {
        return reloadable;
    }
    
    public void setReloadable(String reloadable) {
        this.reloadable = reloadable;
    }
    
   @Override
    public String toString() {
        return UtilReflection.toStringRefl(this);
    }
}
