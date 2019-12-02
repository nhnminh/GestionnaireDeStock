public class Dechargeur implements Runnable{
    private Chariot chariot;
    private AleaStock stock;
    private volatile boolean exit = false;

    public Dechargeur(Chariot c, AleaStock s)
    {
        chariot=c;
        stock = s;
        this.trace("Initialized");
    }

    private void trace (String m) {
        System.out.println (" --- Dechargeur : " + m);
        Thread.yield(); // rendre la commutation possible
    }
    @Override
    public void run() {

        if(!exit){
            this.trace("Dechargeur est lancé ");
            while(!chariot.getStop()){

                this.trace("Should stop = " + chariot.getStop());
                if(!chariot.isPlein()){
                    try {
                        chariot.attendrePlein();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        this.trace("Dechargeur est en cours de décharger  =  ");
                        this.trace("Avant le dechargement : " + chariot.getCharges().size());
                        chariot.decharge();
                        chariot.setVide();
                        this.trace("Apres le dechargement : " + chariot.getCharges().size());
                        this.trace("Stock = " + stock.toString());
                        chariot.attendrePlein();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        this.trace("Dechargeur is stopped ...");

    }

    public void stop(){
        exit = true;
    }
}

