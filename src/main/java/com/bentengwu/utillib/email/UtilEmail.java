package com.bentengwu.utillib.email;

import com.bentengwu.utillib.String.StrUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;

/**
 * 用于发送邮件的简单提取.
 * 需要Apache email的支持. 目前使用1.3.2版本.
 * 参考地址:  http://commons.apache.org/proper/commons-email/userguide.html
 *
 * @Author thender email: bentengwu@163.com
 * @Date 2020/5/28 23:32.
 */
public abstract class UtilEmail {

    public static void send(String username, String password, String host,String from, String[] to, String title, String content,String file){
        System.out.println("==================================");
        System.out.println("Send email:");
        System.out.println(StrUtils.arrayToString(to));
        System.out.println(title);
        System.out.println(content);
        System.out.println(file);
        System.out.println("====================================");

        HtmlEmail email = new HtmlEmail();
        email.setHostName(host);
        email.setAuthentication(from, password);
        try {
            if (to != null && to.length >= 1) {
                for (int i = 0; i < to.length; i++) {
                    if (i < 2) {
                        email.addTo(to[i]);
                    } else {
                        email.addBcc(to[i]);
                    }
                }
            }
            email.setFrom(from, username);// 发送的邮箱
            email.setSubject(title);
            email.setCharset("UTF-8");// 编码格式
//            email.setHtmlMsg("<html>" + content + "</html>");
//            email.setTextMsg("Your email client does not support HTML messages");

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html>");
            emailContent.append("<H1>");
            emailContent.append(content);
            emailContent.append("</H1>");
            emailContent.append("<img src=\"cid:image\">");
            emailContent.append("</html>");

            email.setHtmlMsg(emailContent.toString());

            if (StrUtils.isNotEmpty(file)) {
                email.embed(new File(file), "image");
            }


            String aa = email.send();
            System.out.println(aa);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void send(String username, String password, String host,String from, String[] to, String title, String content) {
        send(username,password,host,from,to,title,content,null);
    }


    public static void main(String[] args) {
        send("826002087@qq.com","","smtp.qq.com","826002087@qq.com",new String[]{"bentengwu@163.com"},"test","content test!","E:\\Pictures\\saguadan\\piclab\\108.png");
    }
}
