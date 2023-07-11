package br.com.omotor.labelcreatorproject.resource;

import br.com.omotor.labelcreatorproject.model.LabelReturn;
import br.com.omotor.labelcreatorproject.model.Matches;
import br.com.omotor.labelcreatorproject.model.Quotes;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class LabelResource {

    @PostMapping
    public List<LabelReturn> teste(@RequestBody Quotes quotesList){
        RestTemplate template = new RestTemplate();
        List<LabelReturn> lista = new ArrayList<>();
        quotesList.getQuotes().forEach(quote ->{
            quote = quote.trim();
            String url = "https://api.mymemory.translated.net/get?q="+quote+"&langpair=pt|en";
            Matches matches = template.getForObject(url, Matches.class);
            assert matches != null;
            String translation = matches.getMatches().get(0).getTranslation();
            String labelNick = "label_" + translation.replace(" ", "_").toLowerCase();
            String typescript = "{{"+ "'" + labelNick + "'" + " | translate}}";
            LabelReturn labelReturn = new LabelReturn(typescript, quote, translation, labelNick);
            lista.add(labelReturn);
        });

        return lista;
    }
}
