package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.runners;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Comune;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Provincia;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.ComuneService;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
//@Order(1)
public class runnersCSV implements CommandLineRunner {

    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;

    @Override
    public void run(String... args) throws Exception {
        if (provinciaService.count() == 0) {
            System.out.println("Caricamento province da CSV...");
            caricaProvince();
            System.out.println("Province caricate: " + provinciaService.count());
        } else {
            System.out.println("Province già presenti nel database: " + provinciaService.count());
        }

        if (comuneService.count() == 0) {
            System.out.println("Caricamento comuni da CSV...");
            caricaComuni();
            System.out.println("Comuni caricati: " + comuneService.count());
        } else {
            System.out.println("Comuni già presenti nel database: " + comuneService.count());
        }
    }

    private void caricaProvince() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/buildweek6_team2/BW6_EnergyManagmentSystem_PJT/csv/datiprovince-ita.csv");

        if (inputStream == null) {
            throw new RuntimeException("File CSV province non trovato");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length == 3) {
                    String sigla = values[0].trim();
                    String nomeProvincia = values[1].trim();
                    String regione = values[2].trim();

                    Provincia provincia = new Provincia(nomeProvincia, sigla, regione);
                    provinciaService.save(provincia);
                }
            }
        }
    }

    private void caricaComuni() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/buildweek6_team2/BW6_EnergyManagmentSystem_PJT/csv/daticomuni-ita.csv");

        if (inputStream == null) {
            throw new RuntimeException("File CSV comuni non trovato");
        }

        Map<String, Provincia> provinceCache = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length >= 4) {
                    String nomeComune = values[2].trim();
                    String nomeProvincia = values[3].trim();

                    Provincia provincia = provinceCache.get(nomeProvincia);
                    if (provincia == null) {
                        provincia = trovaProvincia(nomeProvincia);
                        if (provincia != null) {
                            provinceCache.put(nomeProvincia, provincia);
                        }
                    }

                    if (provincia != null) {
                        Comune comune = new Comune(nomeComune, provincia);
                        comuneService.save(comune);
                    } else {
                        System.out.println("Provincia non trovata per comune: " + nomeComune + " - " + nomeProvincia);
                    }
                }
            }
        }
    }

    private Provincia trovaProvincia(String nomeProvincia) {
        // Prova prima a cercare per sigla (molti comuni hanno la sigla nel campo provincia)
        if (nomeProvincia.length() == 2) {
            return provinciaService.findBySigla(nomeProvincia).orElse(null);
        }

        // Altrimenti cerca per nome completo
        // Questo richiede una query personalizzata o iterazione
        // Per ora cerchiamo per sigla che è il caso più comune
        String siglaApprossimativa = estraiSigla(nomeProvincia);
        return provinciaService.findBySigla(siglaApprossimativa).orElse(null);
    }

    private String estraiSigla(String nomeProvincia) {
        // Conversione nome provincia -> sigla per i casi più comuni
        Map<String, String> mappaProvincia = new HashMap<>();
        mappaProvincia.put("Torino", "TO");
        mappaProvincia.put("Milano", "MI");
        mappaProvincia.put("Roma", "RM");
        mappaProvincia.put("Napoli", "NA");
        // Aggiungi altre mappature se necessario

        return mappaProvincia.getOrDefault(nomeProvincia, nomeProvincia);
    }
}
