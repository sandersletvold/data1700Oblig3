package oslomet.data1700.oblig3;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BillettController {
    @Autowired
    private BillettRepository repository;

    @Autowired
    private HttpSession session;

    private Logger logger = LoggerFactory.getLogger(BillettRepository.class);

    @PostMapping("/tilServer")
    public void tilServer(Billett billett) {
        repository.tilServer(billett);
    }

    @GetMapping("/tilKlient")
    public List<Billett> tilKlient() {
        return repository.tilKlient();
    }

    @GetMapping("/slettLagring")
    public void slettLagring() {
        repository.slettLagring();
    }

    @DeleteMapping("/slettEnBillett")
    public void slettEnBillett(@RequestParam Long billettNr) {
        repository.slettEnBillett(billettNr);
    }

    @GetMapping("/hentEnBillett")
    public Billett hentEnBillett(@RequestParam Long billettNr){
        return repository.hentEnBillett(billettNr);
    }

    @PostMapping("/endreBillett")
    public void endreEnBillett(Billett billett){
        repository.endreEnBillett(billett);
    }

    @GetMapping("/loggInn")
    public boolean loggInn(Kunde kunde) {
        if (repository.sjekkNavnOgPassord(kunde)) {
            session.setAttribute("loggetInn", kunde);
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/loggUt")
    public void signOut() {
        session.removeAttribute("loggetInn");
    }

    @PostMapping("/signUp")
    public void signUp(@RequestBody Kunde kunde) {
        if (validerKunde(kunde)) {
            repository.signUp(kunde);
        }
    }

    private boolean validerKunde(Kunde kunde){
        String regexNavn = "^[a-zA-Z\\s]+";
        String regexPassord = "(?=.*[a-zA-ZæøåÆØÅ])(?=.*\\d)[a-zA-ZæøåÆØÅ\\d]{8,}";
        boolean navnOK = kunde.getBrukernavn().matches(regexNavn);
        boolean passordOK = kunde.getPassord().matches(regexPassord);
        if (navnOK && passordOK){
            return true;
        } else {
            return false;
        }
    }
}