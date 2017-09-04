package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.DataBase;
import javax.inject.Inject;

/**
 *
 * @author JonataBecker
 */
@Controller
public class DataBaseController {

    private final Result result;
    private final DataBase dataBase;
    
    /**
     * @deprecated CDI eyes only
     */
    public DataBaseController() {
        this(null, null);
    }

    @Inject
    public DataBaseController(Result result, DataBase dataBase) {
        this.result = result;
        this.dataBase = dataBase;
    }

    @Get("/database/create")
    public void create() {
        try {
            dataBase.create();
            result.use(Results.status()).accepted();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.http()).body(e.getMessage()).setStatusCode(501);
        }
    }

    @Get("/database/updateScore")
    public void updateScore() {
        try {
            dataBase.updateScore();
            result.use(Results.status()).accepted();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.http()).body(e.getMessage()).setStatusCode(501);
        }
    }
    
}
