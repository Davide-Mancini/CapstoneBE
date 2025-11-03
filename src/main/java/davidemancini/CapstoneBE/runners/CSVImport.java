package davidemancini.CapstoneBE.runners;


import davidemancini.CapstoneBE.entities.Calciatori;
import davidemancini.CapstoneBE.repositories.CalciatoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CSVImport implements CommandLineRunner {
    //UTILIZZO IL RUNNER PER FAR SI CHE ALL'AVVIO SIA SEMPRE PRESENTE LA LISTA CON TUTTI I CALCIATORI


    @Autowired
    private CalciatoriRepository calciatoriRepository;
    //PER POTER SALVARE TUTTA LA LISTA UNA VOLTA RECUPERATA

    private int parseIntSafe(String s, int defaultValue) {
        if (s == null) return defaultValue;
        s = s.trim();
        if (s.isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(s.replaceAll("[^0-9-]", ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private long parseLongSafe(String s, long defaultValue) {
        if (s == null) return defaultValue;
        s = s.trim();
        if (s.isEmpty()) return defaultValue;
        try {
            return Long.parseLong(s.replaceAll("[^0-9-]", ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


    private List<Calciatori> loadCalciatori(String nomeFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(//LEGGE IL FILE RIGA PER RIGA
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream(nomeFile),//GET RESOURCE APRE IL FILE E VIENE CONVERTITO DA BINARIO IN TESTO CON CODIFICA UTF-8
                        StandardCharsets.UTF_8))) {

            return reader.lines()
                    .skip(1)
                    .map(line -> { //TRASFORMO OGNI RIGA APPARTE LA PRIMA IN UN OGGETTO DI TIPO CALCIATORE
                        String[] parts = line.split(","); //SEPARO LE PARTI DELLA RIGA SEPARATE DALLA VIRGOLA
                        //SE QUALCHE CAMPO DOVESSE MANCARE VIENE SOSTITUITO DA STRINGA VUOTA COSI NON VA IN ERRORE
                        String idStr = parts.length > 0 ? parts[0].trim() : "";
                        String cognome = parts.length > 1 ? parts[1].trim() : "";
                        String nomeCompleto = parts.length > 2 ? parts[2].trim() : "";
                        String ruolo = parts.length > 3 ? parts[3].trim() : "";
                        String valoreStr = parts.length > 4 ? parts[4].trim() : "";
                        String squadra = parts.length > 5 ? parts[5].trim() : "";
                        String nazionalita = parts.length > 6 ? parts[6].trim() : "";

                        //VENGONO CONVERTITI ID E VALORE GIOCASTORE IN NUMERI
                        long id = parseLongSafe(idStr, 0L);
                        int valore = parseIntSafe(valoreStr, 0);

                        //CREO NUOVO GIOCATORE
                        return new Calciatori(
                                id,
                                cognome,
                                nomeCompleto,
                                ruolo,
                                valore,
                                squadra,
                                nazionalita
                        );
                    })
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        String csvName = "CalciatoriFanta.csv";
        List<Calciatori> lista = loadCalciatori(csvName);
        calciatoriRepository.saveAll(lista);
        System.out.println("Import completato: " + lista.size() + " giocatori salvati.");
    }
}

