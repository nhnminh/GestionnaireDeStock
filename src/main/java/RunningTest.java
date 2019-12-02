import java.util.ArrayList;

public class RunningTest {
    public static void main(String[] args){
        int randomMin;
        int randomMax;
        int taille = 10;
        int poidsMin = 1;
        int poidsMax = 50;
        AleaStock stock = new AleaStock(taille);
        stock.remplir(poidsMin, poidsMax);

        System.out.println(stock);

        Chariot c = new Chariot(100, 5);

        Chargeur chargeurRunnable = new Chargeur(stock, c);
        Dechargeur dechargeurRunnable = new Dechargeur(c, stock);
        Thread chargeur = new Thread(chargeurRunnable);
        Thread dechargeur = new Thread(dechargeurRunnable);
        chargeur.start();
        dechargeur.start();

        if(chargeurRunnable.getStop()){
            dechargeurRunnable.stop();
            chargeurRunnable.stop();

        }


    }
}
