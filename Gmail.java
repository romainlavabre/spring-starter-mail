package com.replace.replace.api.mail;

import com.replace.replace.api.environment.Environment;
import com.replace.replace.configuration.environment.Variable;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service( "mailSenderGmail" )
public class Gmail implements MailSender {

    protected Environment environment;


    public Gmail( final Environment environment ) {
        this.environment = environment;
    }


    @Override
    public boolean send( String from, List< String > to, String subject, String message ) {
        boolean res = false;

        for ( String t : to ) {
            res = send( from, t, subject, message );
        }

        return res;
    }


    @Override
    public boolean send( String from, final List< String > to, final String subject, final String message, final List< File > files ) {
        return false;
    }


    @Override
    public boolean send( String from, final String to, final String subject, final String message ) {
        Properties prop = new Properties();
        prop.put( "mail.smtp.host", "smtp.gmail.com" );
        prop.put( "mail.smtp.port", "587" );
        prop.put( "mail.smtp.auth", "true" );
        prop.put( "mail.smtp.starttls.enable", "true" );

        Session session = Session.getInstance( prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication( environment.getEnv( Variable.GMAIL_USERNAME ), environment.getEnv( Variable.GMAIL_PASSWORD ) );
                    }
                } );

        try {

            Message mes = new MimeMessage( session );
            mes.setFrom( new InternetAddress( environment.getEnv( Variable.GMAIL_USERNAME ) ) );
            mes.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse( to )
            );
            mes.setSubject( subject );
            mes.setContent( message, "text/html" );

            Transport.send( mes );

            return true;
        } catch ( MessagingException e ) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean send( String from, final String to, final String subject, final String message, final List< File > files ) {
        return false;
    }

}
