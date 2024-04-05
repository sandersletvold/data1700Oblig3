package oslomet.data1700.oblig3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BillettController {
    public final List<Billett> billettLagring = new ArrayList<>();

    @PostMapping("/tilServer")
    public void lagreBillett(Billett billett) {
        billettLagring.add(billett);
    }

    @GetMapping("/tilKlient")
    public List<Billett> sendLagring() {
        return billettLagring;
    }

    @GetMapping("/slettLagring")
    public void slettLagring() {
        billettLagring.clear();
    }
}