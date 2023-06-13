package com.replace.replace.api.mail;

import com.replace.replace.api.environment.Environment;
import com.replace.replace.configuration.environment.Variable;
import com.replace.replace.exception.HttpInternalServerErrorException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service( "mailSender" )
public class MailSenderImpl implements MailSender {
    protected final MailSender  mailSenderMailgun;
    protected final MailSender  mailSenderGmail;
    protected final Environment environment;


    public MailSenderImpl( MailSender mailSenderMailgun, MailSender mailSenderGmail, Environment environment ) {
        this.mailSenderMailgun = mailSenderMailgun;
        this.mailSenderGmail   = mailSenderGmail;
        this.environment       = environment;
    }


    @Override
    public boolean send( List< String > to, String subject, String message ) {
        return getInstance().send( to, subject, message );
    }


    @Override
    public boolean send( List< String > to, String subject, String message, List< File > files ) {
        return getInstance().send( to, subject, message, files );
    }


    @Override
    public boolean send( String to, String subject, String message ) {
        return getInstance().send( to, subject, message );
    }


    @Override
    public boolean send( String to, String subject, String message, List< File > files ) {
        return getInstance().send( to, subject, message, files );
    }


    protected MailSender getInstance() {
        if ( environment.getEnv( Variable.GMAIL_USERNAME ) != null
                && !environment.getEnv( Variable.GMAIL_USERNAME ).isBlank() ) {
            return mailSenderGmail;
        }

        if ( environment.getEnv( Variable.MAILGUN_DOMAIN ) != null
                && !environment.getEnv( Variable.MAILGUN_DOMAIN ).isBlank() ) {
            return mailSenderMailgun;
        }

        throw new HttpInternalServerErrorException( "MAIL_CONFIGURATION_NOT_FOUND" );
    }
}
