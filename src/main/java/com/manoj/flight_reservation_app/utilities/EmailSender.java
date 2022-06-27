package com.manoj.flight_reservation_app.utilities;

import java.io.File;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	@Autowired
	private JavaMailSender sender;
	
	public void sendEmail(String toAddress, String filePath) {
		
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setTo(toAddress);
			messageHelper.setText("Flight Schedule");
			messageHelper.setSubject("Please Find the Attached");
			
			messageHelper.addAttachment("Flight Ticket", new File(filePath));
			
			sender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}