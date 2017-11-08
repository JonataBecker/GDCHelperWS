package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.ByteArrayDownload;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.FileDownload;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.Tecnico;
import java.io.File;
import java.io.FileNotFoundException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Controller responsável por informações de usuários do sistema
 */
@Controller
public class UsuarioController {

    private static final String IMAGE_CONTENT_TYPE = "image/jpg";
    private final Result result;
    private final PersistenceManager persistenceManager;

    /**
     * @deprecated CDI eyes only
     */
    public UsuarioController() {
        this(null, null);
    }

    @Inject
    public UsuarioController(Result result, PersistenceManager persistenceManager) {
        this.result = result;
        this.persistenceManager = persistenceManager;
    }

    /**
     * Retorna os usuários do sistema
     */
    @Get("/usuarios")
    public void usuarios() {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT u FROM Tecnico u WHERE u.setor = :setor");
            q.setParameter("setor", Tecnico.SETOR_GDC);
            result.use(Results.json()).withoutRoot().from(q.getResultList()).serialize();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna imagem do usuário
     * 
     * @param usuario
     * @return Download
     */
    @Get("/usuario/imagem/{usuario}")
    public Download usuarioImage(String usuario) {
        try { 
            File fileImage = new File(System.getProperty("user.home") + "/img/" + usuario);            
            if (!fileImage.isFile()) {
                fileImage = new File(getClass().getResource("/com/github/gdchelper/gdchelperws/rech.png").getFile());
            }
            return new FileDownload(fileImage, IMAGE_CONTENT_TYPE, fileImage.getName());
        } catch (FileNotFoundException ex) {
            return new ByteArrayDownload(new byte[0], IMAGE_CONTENT_TYPE, "blank");
        }
    }

}
