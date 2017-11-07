package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.DataBase;
import javax.inject.Inject;

/**
 * Controller responsável pela criação da base de dados
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
    
    /**
     * Executa criação da base de dados
     */
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
    
    /**
     * Atualiza score de atendimentos
     */
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
