package com.professional_info.portfolio;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class Email_Srv {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendContactEmail(String recipientEmail, String name, String email, String phoneNo, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Resume enquiry from " + name);
            helper.setFrom(email);
            helper.setText(getHtmlContent(name, email, phoneNo, message), true); // true = HTML

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // Ideally, use logging
        }
    }

    private String getHtmlContent(String name, String email, String phoneNo, String message) {
        return String.format("""
            <html>
                <body style="margin: 0; padding: 0; background: linear-gradient(to bottom right,#3B2ED0, #A33DD8); font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">
                    <div style="max-width: 600px; margin: 40px auto; background: white; border-radius: 10px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); overflow: hidden;">
                        <div style="padding: 20px; border-radius: 10px; color: #333;">
                            <div style="background: linear-gradient(to top left, #3B2ED0, #A33DD8); border-radius: 10px; padding: 20px; color: white; text-align: center;">
                                <h2 style="margin: 0;border-radius: 10px">New Contact Request</h2>
                            </div>
                            <p><strong>Name:</strong> %s</p>
                            <p><strong>Email:</strong> %s</p>
                            <p><strong>Phone:</strong> %s</p>
                            <p><strong>Message:</strong><br/>%s</p>
                        </div>
                        <div style="background: #f4f4f4; text-align: center; padding: 10px; font-size: 12px; color: #999;">
                            © 2025 Dhinithya Verma | Portfolio Notification
                        </div>
                    </div>
                </body>
            </html>
            """, name, email, phoneNo, message);
    }
}
