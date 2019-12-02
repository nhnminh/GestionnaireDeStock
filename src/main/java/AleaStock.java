import java.util.ArrayList;
import java.util.List;

public class AleaStock {

    private int taillle;
    private List<AleaObjet> listMarchandises;

    public AleaStock(int t)
    {
        taillle=t;
        listMarchandises = new ArrayList<>(t);
    }

    public int getTaillle() {
        return taillle;
    }

    public List<AleaObjet> getListMarchandises() {
        return listMarchandises;
    }

    public void remplir(int poidsMin, int poidsMax)
    {
        for(int i=0;i<taillle;i++)
        {
            listMarchandises.add(new AleaObjet(poidsMin, poidsMax));
        }
    }

    public  synchronized boolean estVide()
    {
        return listMarchandises.isEmpty();
    }

    public  synchronized AleaObjet extraire()
    {
        return listMarchandises.remove(0);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < listMarchandises.size(); ++i){
            sb.append(listMarchandises.get(i).toString() + "\n ");
        }
        return sb.toString();
    }
}
