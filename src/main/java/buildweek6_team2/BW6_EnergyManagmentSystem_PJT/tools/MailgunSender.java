package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.tools;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {

    private String domain;

    private String apiKey;

    public MailgunSender(@Value("${mailgun.domain}") String domain, @Value(("%{mailgun.apiKey}")) String apiKey) {
        this.domain = domain;
        this.apiKey = apiKey;
    }

    public void sendRegistrationEmail(Cliente recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://ap.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "cloudpost10@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione completata")
                .queryString("text", "Ciao, " + recipient.getNomeContatto() + " " + recipient.getCognomeContatto() + " grazie per esserti registrato")
                .asJson();
        System.out.println(response.getBody());

    }
}
