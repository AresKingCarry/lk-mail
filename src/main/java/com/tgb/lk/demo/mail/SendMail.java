package com.tgb.lk.demo.mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendMail {

	public SendMail() {
		// 获取上下文

	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/spring-util-mail.xml");

		// 获取JavaMailSender bean
		JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");
		SimpleMailMessage mail = new SimpleMailMessage(); // <span
		// style="color: rgb(255, 0, 0);">注意SimpleMailMessage只能用来发送text格式的邮件</span>

		try {
			mail.setTo("test@test.com");// 接受者
			mail.setFrom("test@test.com");// 发送者,这里还可以另起Email别名，不用和xml里的username一致
			mail.setSubject("spring mail test!");// 主题
			mail.setText("springMail的简单发送测试");// 邮件内容
			sender.send(mail);
			System.out.println("发送邮件成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
