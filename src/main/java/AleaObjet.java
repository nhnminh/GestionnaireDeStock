import java.util.Random;

public class AleaObjet {

    private Random rand = new Random();
    private int poids;

    public AleaObjet(int min, int max)
    {
      poids=rand.nextInt(max-min+1)+min;
    }

    public int getPoids()
    {
        return  this.poids;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AleaObjet{");
        sb.append("poids=").append(poids);
        sb.append('}');
        return sb.toString();
    }
}
