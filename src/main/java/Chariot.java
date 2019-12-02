import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chariot {

    private int poidsMax;
    private int poidsCourant=0;
    private int nbMax;
    private int nbCourant=0;
    private boolean plein;
    private List<AleaObjet> charges;
    private boolean stop = false;


    private final Lock l =new ReentrantLock();
    private Condition nonPlein = l.newCondition();
    private Condition emptyCondition = l.newCondition();

    public Chariot(int poidsMax, int nbMax) {
        this.poidsMax = poidsMax;
        this.nbMax = nbMax;
        charges= new ArrayList<>(nbMax);
    }

    public List<AleaObjet> getCharges() {
        return charges;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Chariot : Poids = " + getPoids() + ", NbObjets = " + getNbObjet());
        return sb.toString();
    }

    public int getPoids(){
        int poids = 0;
        for (AleaObjet x:
             charges) {
            poids += x.getPoids();
        }
        return poids;
    }

    public int getNbObjet(){
        return charges.size();
    }

    public boolean isPlein() {
        return plein;
    }

    public boolean deposer(AleaObjet aDeposer) {
        try {
            l.lock();
            if((poidsCourant+aDeposer.getPoids() < poidsMax) && (nbCourant <nbMax))
            {
                poidsCourant=poidsCourant+aDeposer.getPoids();
                nbCourant++;
                charges.add(aDeposer);
                System.out.println("Depot d'un objet de poids "+aDeposer.getPoids());
                System.out.println("Poids total :"+poidsCourant+", nombre total : "+nbCourant);
                return true;
            }
            else return false;
        }
        finally {
            l.unlock();
        }
    }
	
    public void setPlein() {
        try{
            l.lock();
            plein=true;
        }
        finally {
            l.unlock();
        }
    }

    public void setVide(){
        try{
            l.lock();
            plein= false;
        }
        finally {
            l.unlock();
        }
    }

    public void attendreVide() throws InterruptedException {
        try {
            l.lock();
            while (isPlein())
            {
                System.out.println("*** Signaler au dechargeur");
                emptyCondition.signalAll();
                System.out.println("*** Chargeur est en attente ---- ");
                nonPlein.await();

            }

        }
        finally {
            l.unlock();
        }
    }

    public Condition getNonPlein(){return nonPlein;};
    public Condition getEmptyCondition(){return emptyCondition;};


    public void attendrePlein() throws InterruptedException{
        try {
            l.lock();
            nonPlein.signalAll();
            while (!plein)
            {
                System.out.println("*** Dechargeur est en attente ----");
                emptyCondition.await();
            }


        }
        finally {
            l.unlock();
        }
    }
    public boolean decharge(){

        try{
            l.lock();
            charges.clear();
            plein = false;
            poidsCourant = 0;
            nbCourant = 0;

        }
        finally {
            l.unlock();
        }
        return true;
    }

    public boolean getStop(){
        return stop;
    }

    public void setStop(){
        try{
            l.lock();
            stop= true;
        }
        finally {
            l.unlock();
        }
    }

}
