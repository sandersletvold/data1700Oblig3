package oslomet.data1700.oblig3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BillettController {
    @Autowired
    private BillettRepository repository;

    @PostMapping("/tilServer")
    public void lagreBillett(Billett billett) {
        repository.lagreBillett(billett);
    }

    @GetMapping("/tilKlient")
    public List<Billett> sendLagring() {
        return repository.hentBilletter();
    }

    @GetMapping("/slettLagring")
    public void slettLagring() {
        repository.slettBilletter();
    }

    @DeleteMapping("/slettEnBillett")
    public void slettEnBillett(@RequestParam int id) {
        repository.slettEnBillett(id);
    }

    @PostMapping("/tilDatabase")
    public void tilDatabase(Billett billett) {
        repository.lagreBillett(billett);
    }

    @PostMapping("/oppdaterStudent")
    public void endreEnBillett(Billett billett) {
        repository.endreBillett(billett);
    }
}