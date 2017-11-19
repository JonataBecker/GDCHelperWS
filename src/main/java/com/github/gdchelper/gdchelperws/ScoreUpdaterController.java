package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.events.VRaptorInitialized;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Tarefa para atualização de scores
 */
@ApplicationScoped
public class ScoreUpdaterController {

    private static final long PERIOD = 24 * 60 * 60 * 1000;
    private final ScoreUpdater scoreUpdater;

    public ScoreUpdaterController() {
        this(null);
    }

    /**
     * Necessário para iniciar logo com a aplicação
     *
     * @param vRaptorInitialized
     */
    public void whenApplicationStarts(@Observes VRaptorInitialized vRaptorInitialized) {
    }

    @Inject
    public ScoreUpdaterController(ScoreUpdater scoreUpdater) {
        this.scoreUpdater = scoreUpdater;
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                execute();
//            }
//        }, PERIOD, PERIOD);
    }

    public void execute() {
        try {
            scoreUpdater.updateAtendimentosSemScore();
        } catch (Exception ex) {
            Logger.getLogger(ScoreUpdaterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
