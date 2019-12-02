import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.List;

public class Chargeur implements Runnable {

    private AleaStock stock;
    private Chariot chariot;
    private volatile boolean exit = false;

    public Chargeur(AleaStock s, Chariot c)
    {
        stock=s;
        chariot=c;
    }
    private void trace (String m) {
        System.out.println (" --- Chargeur : " + m);
        Thread.yield(); // rendre la commutation possible
    }
    public void run()  {
        if(!exit){
            AleaObjet aDeposer;
            boolean depotOK;
            try {
                while(!stock.estVide())
                {
                    System.out.println(" ============= NOUVEAU CHARGEMENT =============");
                    this.trace(chariot.toString());
                    aDeposer=stock.extraire();
                    System.out.println("Extraction d'un objet." + aDeposer.toString());
                    depotOK=chariot.deposer(aDeposer);
                    if(!depotOK)
                    {
                        chariot.setPlein();
                        System.out.println("Objet ne peut pas être deposé");
                        System.out.println("Plein = " + chariot.isPlein());
                        this.trace(chariot.toString()) ;
                        chariot.attendreVide();
                    }
                }
                //on signale la fin du dernier chargement, possiblement vide
                this.trace("Should stop = " + chariot.getStop());
                chariot.setPlein();
                chariot.attendreVide();
                chariot.setStop();

                this.trace("Should stop = " + chariot.getStop());
                this.trace("Chargeur stops !");


            }catch (InterruptedException e)
            {
                System.out.println("Chargeur interrompu, Terminaison");
            }
        }

        this.trace("Chargeur is stopped ...");

    }

    public boolean stockVide(){
        return stock.estVide();
    }

    public boolean getStop(){
        return chariot.getStop();
    }

    public void stop(){
        exit = true;

    }

}
