package com.tgb.lk.demo.mail;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class SendMailTest {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"spring/spring-util-mail.xml");
	// 获取JavaMailSender bean
	JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");

	@Test
	public void testSimpleMail() {
		SimpleMailMessage mail = new SimpleMailMessage(); // <span
		try {
			mail.setTo("lk123@126.com");// 接受者
			mail.setFrom("lk123@126.com");// 发送者,
			mail.setSubject("spring mail test!");// 主题
			mail.setText("springMail的简单发送测试");// 邮件内容
			sender.send(mail);
			System.out.println("发送邮件成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testHtmlMail() {
		try {
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			// 设置utf-8或GBK编码，否则邮件会有乱码
			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mailMessage, true, "utf-8");

			messageHelper.setTo("15732621728@163.com");// 接受者
			messageHelper.setFrom("18333602097@163.com");// 发送者
			messageHelper.setSubject("测试邮件");// 主题
			String filesrc = "E:/Ares.html";
			String content =readTextFile(filesrc,"utf-8");
			// 邮件内容，注意加参数true，表示启用html格式
			messageHelper
					.setText(
							content,
							true);
			sender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取freemarker生成的html文件html内容，并转换成为字符串输出
	 * @param sFileName
	 * @param sEncode
	 * @return
	 */
	public static String readTextFile(String sFileName,String sEncode){
		StringBuffer sbstr = new StringBuffer();
		try {
			File ff = new File(sFileName);
			InputStreamReader read =new InputStreamReader(new FileInputStream(ff),sEncode);
			BufferedReader ins = new BufferedReader(read);
			String dataLine="";
			while (null!=(dataLine=ins.readLine())) {
				sbstr.append(dataLine);
				//sbstr.append("/r/n");
			}
			ins.close();
		} catch (Exception e) {
		}
		return sbstr.toString();
	}



	@Test
	public void testInlineMail() {
		try {
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			// 设置utf-8或GBK编码，否则邮件会有乱码
			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mailMessage, true, "utf-8");
			messageHelper.setTo("lk123@126.com");// 接受者F
			messageHelper.setFrom("lk123@126.com");// 发送者
			messageHelper.setSubject("测试邮件");// 主题
			// 邮件内容，注意加参数true
			messageHelper
					.setText(
							"<html><head></head><body><h1>hello!!李坤</h1></body></html>",
							true);
			// 附件内容
			messageHelper.addInline("a", new File("d:/table.xls"));
			messageHelper.addInline("b", new File("d:/success1.xls"));
			File file = new File("d:/测试中文文件.rar");
			// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
			messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()),file);
			sender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
