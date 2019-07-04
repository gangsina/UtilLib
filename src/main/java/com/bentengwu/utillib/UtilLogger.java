package com.bentengwu.utillib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/3/13 12:50.
 */
public class UtilLogger {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger("UtilLib");


    public static final void log(Logger _logger, Exception ex, String appendMessage) {
        StringBuilder message = new StringBuilder(appendMessage);
        _logger.warn("{}==>{}", message, ex.getMessage());
        _logger.info("{}==>{}",message, ex.getMessage(), ex);
    }
}
