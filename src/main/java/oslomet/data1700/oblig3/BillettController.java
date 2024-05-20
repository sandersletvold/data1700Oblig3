package oslomet.data1700.oblig3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BillettController {
    @Autowired
    private BillettRepository repository;

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
}