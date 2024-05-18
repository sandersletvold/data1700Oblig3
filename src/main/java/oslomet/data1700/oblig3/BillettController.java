package oslomet.data1700.oblig3;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class BillettController {
    @Autowired
    private BillettRepository repository;

    @Autowired
    private HttpSession session;

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
    public void loggUt() {
        session.removeAttribute("loggetInn");
    }

    @PostMapping("/signUp")
    public void signUp(Kunde kunde) {
        if (validerKunde(kunde)) {
            repository.signUp(kunde);
        }
    }

    private boolean validerKunde(Kunde nyKunde){
        String regexNavn = "/[a-zA-ZæøåÆØÅ-]{2,20}/";
        String regexPassord = "(?=.[a-zA-ZæøåÆØÅ])(?=.)[a-zA-ZæøåÆØÅ]{8,}"; // minimum 8 tegn, en bokstav og et tall
        boolean navnOK = nyKunde.getBrukernavn().matches(regexNavn);
        boolean passordOK = nyKunde.getPassord().matches(regexPassord);
        if (navnOK && passordOK){
            return true;
        } else {
            System.out.println("Feil");
            return false;
        }
    }
}