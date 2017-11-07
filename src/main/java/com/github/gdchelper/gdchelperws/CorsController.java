package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Options;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import javax.inject.Inject;

/**
 * Controller responsável pelo Cors Validation
 */
@Controller
public class CorsController {

    private final Result result;

    /**
     * @deprecated CDI eyes only
     */
    public CorsController() {
        this(null);
    }

    @Inject
    public CorsController(Result result) {
        this.result = result;
    }   

    @Options("/*")
    public void options() {
        result.use(Results.status()).ok();
    }
}