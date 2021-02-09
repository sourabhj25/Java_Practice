/**
 * 
 */
package com.agsft.authservice.utility;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.agsft.authservice.constant.HttpStatusCode;
import com.agsft.authservice.exception.AuthenticationServiceException;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;


/**
 * @author nilesh
 *
 */
@Component
public class EmailUtility 
{
	
	SendGrid sendGrid;
	
    public EmailUtility(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }
  
   //TODO Work
   public void sendMail(String emailTo,String emailContent,String subject) throws AuthenticationServiceException 
   {
        Email from = new Email("no-rply@agsft.com");
        Email to = new Email(emailTo);
        Content content = new Content("text/html", emailContent);		
        Mail mail = new Mail(from, subject, to, content);
  
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (IOException ex)
        {
        	throw new AuthenticationServiceException(HttpStatusCode.SERVICE_FAILED.getStatusCode(), "Failed to verify access key details");		
        }
    }
   

}
