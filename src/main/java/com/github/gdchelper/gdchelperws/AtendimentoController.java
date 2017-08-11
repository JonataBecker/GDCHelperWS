package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author JonataBecker
 */
@Controller
public class AtendimentoController {

    private final Result result;

    /**
     * @deprecated CDI eyes only
     */
    public AtendimentoController() {
        this.result = null;
    }

    @Inject
    public AtendimentoController(Result result) {
        this.result = result;
    }

    
    @Get("/atendimentos")
    public void atendimento() {
        List<String> obj = Arrays.asList("atm 1", "2 atm");
        result.use(Results.json()).withoutRoot().from(obj).serialize();
    }
}
