package davidemancini.CapstoneBE.tools;

import davidemancini.CapstoneBE.entities.Utenti;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailGun {
    private String domain;
    private String apikey;

    //COSTRUTTORE CON VALORI LETTI DAL APPLICATION.PROPERTIES
    public MailGun(@Value("${mailgun.domain}") String apikey,@Value("${mailgun.apiKey}") String domain) {
        this.apikey = apikey;
        this.domain = domain;
    }

    //INVIO EMAIL ALLA REGISTRAZIONE
    public void sendWelcomeEmail(Utenti recipient){
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages").basicAuth("api",apikey)
                .queryString("from","mancinidavide73@gmail.com")
                .queryString("to",recipient.getEmail())
                .queryString("subject","BENVENUTO A BORDO!")
                .queryString("text","Ciao " + recipient.getNome()+" benvenuto a bordo, ora potrai godere di tutte le funzionalità del nostro gestionale aste!")
                .asJson();
        System.out.println(response.getBody());
    }
}
