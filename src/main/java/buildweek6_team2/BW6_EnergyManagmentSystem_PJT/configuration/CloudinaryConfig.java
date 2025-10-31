package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.configuration;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.CloudinaryException;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary getAvatarImage(@Value("${cloudinary.name}") String cloudname, @Value("${cloudinary.key}") String apiKey, @Value("${cloudinary.secret}") String apiSecret) {

        // Eseguo un controllo sui dati
        try {
            System.out.println("| Cloudname: " + cloudname);
            System.out.println("| ApiKey: " + apiKey);
            System.out.println("| ApiSecret: " + apiSecret);

        } catch (CloudinaryException ex) {
            System.out.println("Attenzione, alcuni dati di Cloudinary non sono stati caricati correttamente");
        }

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudname);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);

    }
}
