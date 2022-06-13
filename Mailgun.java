package com.replace.replace.api.mail;

import com.replace.replace.api.environment.Environment;
import com.replace.replace.configuration.environment.Variable;
import kong.unirest.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class Mailgun implements MailSender {

    protected Environment environment;


    public Mailgun( Environment environment ) {
        this.environment = environment;
    }


    @Override
    public boolean send( String from, List< String > to, String subject, String message ) {
        MultipartBody multipartBody = this.init( from );

        this.addRecipient( multipartBody, to )
            .addSubject( multipartBody, subject )
            .addMessage( multipartBody, message );

        HttpResponse< JsonNode > response = multipartBody.asJson();


        return response.isSuccess();
    }


    @Override
    public boolean send( String from, List< String > to, String subject, String message, List< File > files ) {
        MultipartBody multipartBody = this.init( from );

        this.addRecipient( multipartBody, to )
            .addSubject( multipartBody, subject )
            .addFile( multipartBody, files )
            .addMessage( multipartBody, message );

        HttpResponse< JsonNode > response = multipartBody.asJson();

        return response.isSuccess();
    }


    @Override
    public boolean send( String from, String to, String subject, String message ) {
        MultipartBody multipartBody = this.init( from );

        this.addRecipient( multipartBody, to )
            .addSubject( multipartBody, subject )
            .addMessage( multipartBody, message );

        HttpResponse< JsonNode > response = multipartBody.asJson();

        return response.isSuccess();
    }


    @Override
    public boolean send( String from, String to, String subject, String message, List< File > files ) {
        MultipartBody multipartBody = this.init( from );

        this.addRecipient( multipartBody, to )
            .addSubject( multipartBody, subject )
            .addFile( multipartBody, files )
            .addMessage( multipartBody, message );

        HttpResponse< JsonNode > response = multipartBody.asJson();

        return response.isSuccess();
    }


    /**
     * Create request
     *
     * @return
     */
    protected MultipartBody init( String from ) {
        HttpRequestWithBody requestWithBody =
                Unirest.post( "https://api.mailgun.net/v3/" + this.environment.getEnv( Variable.MAIL_DOMAIN ) + "/messages" );

        return requestWithBody
                .basicAuth( "api", this.environment.getEnv( Variable.MAIL_PRIVATE_KEY ) )
                .field( "from", from )
                .field( "o:require-tls", "true" )
                .field( "o:skip-verification", "false" )
                .field( "encoding", "utf-8" );
    }


    /**
     * Add multiple recipients
     *
     * @param multipartBody
     * @param recipients
     * @return
     */
    protected Mailgun addRecipient( MultipartBody multipartBody, List< String > recipients ) {
        String redirectMail = environment.getEnv( Variable.REDIRECT_MAIL );

        if ( redirectMail != null && !redirectMail.isBlank() && !redirectMail.toUpperCase().equals( "NONE" ) ) {
            multipartBody.field( "to", redirectMail );
            return this;
        }

        for ( String recipient : recipients ) {
            multipartBody.field( "to", recipient );
        }

        return this;
    }


    /**
     * Add recipient
     *
     * @param multipartBody
     * @param recipient
     * @return
     */
    protected Mailgun addRecipient( MultipartBody multipartBody, String recipient ) {
        String redirectMail = environment.getEnv( Variable.REDIRECT_MAIL );

        if ( redirectMail != null && !redirectMail.isBlank() && !redirectMail.toUpperCase().equals( "NONE" ) ) {
            multipartBody.field( "to", redirectMail );
            return this;
        }

        multipartBody.field( "to", recipient );

        return this;
    }


    /**
     * Add subject to mail
     *
     * @param multipartBody
     * @param subject
     * @return
     */
    protected Mailgun addSubject( MultipartBody multipartBody, String subject ) {
        multipartBody.field( "subject", subject );

        return this;
    }


    /**
     * @param multipartBody
     * @param message       Message on HTML or TEXT
     * @return
     */
    protected Mailgun addMessage( MultipartBody multipartBody, String message ) {
        if ( message.contains( "!DOCTYPE html" ) ) {
            multipartBody.field( "html", message );
        } else {
            multipartBody.field( "text", message );
        }

        return this;
    }


    /**
     * @param multipartBody
     * @param files         List of attachment
     * @return
     */
    protected Mailgun addFile( MultipartBody multipartBody, List< File > files ) {

        for ( File file : files ) {
            multipartBody.field( "attachment", file );
        }

        return this;
    }
}
