# Mail

The mail library permit to send mail, (the implementation is mailgun).

Service:

```java
com.replace.replace.api.mail.MailSender;
```

All documentation is available on interface.

### Requirements

- Module git@github.com:romainlavabre/spring-starter-environment.git

Maven dependency :

```xml
<!-- HTTP CLIENT -->
<dependencies>
    <dependency>
        <groupId>com.konghq</groupId>
        <artifactId>unirest-java</artifactId>
        <version>3.11.09</version>
        <classifier>standalone</classifier>
    </dependency>
    <dependency>
        <groupId>jakarta.mail</groupId>
        <artifactId>jakarta.mail-api</artifactId>
        <version>2.1.2</version>
    </dependency>
    <dependency>
        <groupId>org.eclipse.angus</groupId>
        <artifactId>jakarta.mail</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

If you use gmail :

- Enable 2FA on your account
- Search "application password"
- Generate password and provide it

### Versions

##### 1.0.0

INITIAL