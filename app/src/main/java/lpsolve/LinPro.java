package lpsolve;

import com.sofeed.myapp.HasilData;
import com.sofeed.myapp.ProsesData;

import java.util.ArrayList;

class Bahan{
    public Bahan(String nama_bahan, double minimal, double maximal, double bahan_kering, double abu, double PK, double LK, double SK, double BETN, double TDN, double Ca, double P, double metana, double rasio)
    {
        this.rasio = rasio;
        this.nama_bahan = nama_bahan;
        this.minimal = minimal;
        this.maximal = maximal;
        this.bahan_kering = bahan_kering;
        this.abu = abu;
        this.PK = PK;
        this.LK = LK;
        this.SK = SK;
        this.BETN = BETN;
        this.TDN = TDN;
        this.Ca = Ca;
        this.P = P;
        this.metana = metana;
    }

    public double rasio()
    {
        return rasio;
    }

    public double komposisi(int pilih)
    {
        switch(pilih)
        {
            case 0: return minimal;
            case 1: return maximal;
            case 2: return bahan_kering;
            case 3: return abu;
            case 4: return PK;
            case 5: return LK;
            case 6: return SK;
            case 7: return BETN;
            case 8: return TDN;
            case 9: return Ca;
            case 10: return P;
            case 11: return metana;
            default: return -1;
        }
    }

    public String name()
    {
        return nama_bahan;
    }

    private final String nama_bahan;
    private final double minimal;
    private final double maximal;
    private final double bahan_kering;
    private final double abu;
    private final double PK;
    private final double LK;
    private final double SK;
    private final double BETN;
    private final double TDN;
    private final double Ca;
    private final double P;
    private final double rasio;
    private final double metana;
}


public class LinPro {
    public static ArrayList<HasilData> calculate(double[] hewan, ArrayList<ProsesData> pakan)
    {
        ArrayList<HasilData> result = new ArrayList<HasilData>();
        try {
            //size bahan
            int jumlah_bahan = pakan.size();

            //input ke class
            ArrayList<Bahan> bahan = new ArrayList<Bahan>();
            pakan.forEach(i -> {
                bahan.add(new Bahan(
                        i.getNama(),
                        i.getMinJumlah(),
                        i.getMaxJumlah(),
                        i.getBahanKering(),
                        i.getAbu(),
                        i.getPk(),
                        i.getLk(),
                        i.getSk(),
                        i.getBetn(),
                        i.getTdn(),
                        i.getCa(),
                        i.getP(),
                        i.getMetana(),
                        i.getRasio()
                ));
            });

            //set problem dari lp
            LpSolve problem = LpSolve.makeLp(0,jumlah_bahan);
            problem.setMinim();

            //Buat objective function
            double[] objective = new double[jumlah_bahan+1];
            for (int i = 0; i < jumlah_bahan; i++)
            {
                objective[i+1] = bahan.get(i).rasio();
                objective[i+1]/=100;
            }
            problem.setObjFn(objective);

            //Boundary, minimal dan maksimal
            for (int i = 0; i <24; i++)
            {
                if(i<12)
                {
                    for (int j = 0; j < 9; j++)
                    {
                        double[] boundary= new double[jumlah_bahan+1];
                        for(int k = 0; k<jumlah_bahan; k++)
                        {
                            boundary[k+1] = bahan.get(k).komposisi(j+2);
                            boundary[k+1] /= 100;
                        }
                        problem.addConstraint(boundary,LpSolve.GE,hewan[i]);
                    }

                }
                else
                {
                    for (int j = 0; j < 9; j++)
                    {
                        double[] boundary= new double[jumlah_bahan+1];
                        for(int k = 0; k<jumlah_bahan; k++)
                        {
                            boundary[k+1] = bahan.get(k).komposisi(j+2);
                            boundary[k+1] /= 100;
                        }
                        problem.addConstraint(boundary,LpSolve.LE,hewan[i]);
                    }
                }
            }

            //minimal maksimal komposisi maxing masing
            for (int i = 0; i < jumlah_bahan; i++)
            {
                double[] mimax = new double[jumlah_bahan+1];
                mimax[i+1]=1;
                problem.addConstraint(mimax, LpSolve.GE, bahan.get(i).komposisi(0));
                problem.addConstraint(mimax, LpSolve.LE, bahan.get(i).komposisi(1));
            }

            //maksimal semua 100 persen
            double[] total = new double[jumlah_bahan+1];
            for (int i = 0; i < jumlah_bahan; i++)
            {
                total[i+1] = 1.0;
            }
            problem.addConstraint(total,LpSolve.EQ,100);

            //Solve
            int cek = problem.solve();

            if(cek==0||cek==1) {
                double[] answer = problem.getPtrVariables();
                for(int i =0; i< answer.length; i++){
                    result.add(new HasilData(pakan.get(i),answer[i]));
                }
            }
            return result;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
