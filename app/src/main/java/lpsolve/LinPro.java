package lpsolve;

import android.util.Log;

import com.sofeed.myapp.HasilData;
import com.sofeed.myapp.ProsesData;

import java.util.ArrayList;
import java.util.Arrays;

class Animal
{
    public Animal(double bahan_kering_min, double abu_min, double PK_min,
           double LK_min,
           double SK_min,
           double BETN_min,
           double TDN_min,
           double Ca_min,
           double P_min,
           double metana_min,
           double bahan_kering_max,
           double abu_max,
           double PK_max,
           double LK_max,
           double SK_max,
           double BETN_max,
           double TDN_max,
           double Ca_max,
           double P_max,
           double metana_max)
    {

        this.bahan_kering_min  = bahan_kering_min;
        this.abu_min = abu_min;
        this.PK_min  = PK_min;
        this.LK_min = LK_min;
        this.SK_min = SK_min;
        this.TDN_min  = TDN_min;
        this.Ca_min = Ca_min;
        this.P_min = P_min;
        this.bahan_kering_max = bahan_kering_max;
        this.abu_max = abu_max;
        this.PK_max = PK_max;
        this.LK_max = LK_max;
        this.SK_max = SK_max;
        this.TDN_max = TDN_max;
        this.Ca_max = Ca_max;
        this.P_max = P_max;
        this.metana_min = metana_min;
        this.metana_max = metana_max;
        this.BETN_min = BETN_min;
        this.BETN_max =  BETN_max;

    }

    double komposisi(int pilih1, int pilih2)
    {
        if(pilih1==1)
            switch(pilih2)
            {
                case 0: return bahan_kering_max;
                case 1: return abu_max;
                case 2: return PK_max;
                case 3: return LK_max;
                case 4: return SK_max;
                case 5: return BETN_max;
                case 6: return TDN_max;
                case 7: return Ca_max;
                case 8: return P_max;
                case 9: return metana_max;
                default: return -1;
            }
        else
            switch(pilih2)
            {
                case 0: return bahan_kering_min;
                case 1: return abu_min;
                case 2: return PK_min;
                case 3: return LK_min;
                case 4: return SK_min;
                case 5: return BETN_min;
                case 6: return TDN_min;
                case 7: return Ca_min;
                case 8: return P_min;
                case 9: return metana_min;
                default: return -1;
            }
    }

    private double bahan_kering_min;
    private double abu_min;
    private double PK_min;
    private double LK_min;
    private double SK_min;
    private double BETN_min;
    private double TDN_min;
    private double Ca_min;
    private double P_min;
    private double metana_min;
    private double bahan_kering_max;
    private double abu_max;
    private double PK_max;
    private double LK_max;
    private double SK_max;
    private double BETN_max;
    private double TDN_max;
    private double Ca_max;
    private double P_max;
    private double metana_max;
};

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
    public static ArrayList<HasilData> calculate(double[] hewan, ArrayList<ProsesData> pakan) {
        ArrayList<HasilData> result = new ArrayList<>();
        try {
            Log.d("LinPro", "Starting calculation");
            // Log the input data
            Log.d("LinPro", "Received hewan: " + Arrays.toString(hewan));
            Log.d("LinPro", "Received pakan: " + pakan);

            // Size bahan
            int jumlah_bahan = pakan.size();
            Log.d("LinPro", "Jumlahnya: " + jumlah_bahan);


            //nutrisi
            Animal nutrisi = new Animal(hewan[0],hewan[1],hewan[2], hewan[3],
                    hewan[4], hewan[5], hewan[6], hewan[7], hewan[8], hewan[9], hewan[10],
                    hewan[11], hewan[12], hewan[13], hewan[14], hewan[15], hewan[16],
                    hewan[17], hewan[18], hewan[19]);

            // Input ke class
            ArrayList<Bahan> bahan = new ArrayList<>();
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

            // Set problem dari lp
            LpSolve problem = LpSolve.makeLp(0, jumlah_bahan);
            problem.setMinim();

            // Buat objective function
            double[] objective = new double[jumlah_bahan + 1];
            Log.d("LinPro", "Objective: " + Arrays.toString(objective));
            for (int i = 0; i < jumlah_bahan; i++) {
                objective[i + 1] = bahan.get(i).rasio();
                objective[i + 1] /= 100;
            }
            Log.d("LinPro", "Objective: " + Arrays.toString(objective));
            problem.setObjFn(objective);


            //boundary
            for (int i = 0; i <= 1; i++)
            {
                if(i==1)
                {
                    for (int j = 0; j < 9; j++)
                    {
                        double[] boundary = new double[jumlah_bahan+1];
                        for(int k = 0; k<jumlah_bahan; k++)
                        {
                            boundary[k+1] = bahan.get(k).komposisi(j+2);
                            boundary[k+1] /= 100;
                        }
                        problem.addConstraint(boundary, LpSolve.LE, nutrisi.komposisi(i,j));
                    }

                }
                else
                {
                    for (int j = 0; j < 9; j++)
                    {
                        double[] boundary = new double[jumlah_bahan+1];
                        for(int k = 0; k<jumlah_bahan; k++)
                        {
                            boundary[k+1] = bahan.get(k).komposisi(j+2);
                            boundary[k+1] /= 100;

                        }
                        problem.addConstraint(boundary, LpSolve.GE, nutrisi.komposisi(i,j));
                    }
                }
            }

            // Minimal maksimal komposisi masing-masing
            for (int i = 0; i < jumlah_bahan; i++) {
                problem.setLowbo(i+1, bahan.get(i).komposisi(0));
                problem.setUpbo(i+1, bahan.get(i).komposisi(1));
            }

            // Maksimal semua 100 persen
            double[] total = new double[jumlah_bahan + 1];
            for (int i = 0; i < jumlah_bahan; i++) {
                total[i + 1] = 1.0;
            }
            Log.d("LinPro", "total: "+ Arrays.toString(total));
            problem.addConstraint(total, LpSolve.EQ, 100);

            // Solve
            int cek = problem.solve();

            if (cek == 0 || cek == 1) {
                double[] answer = new double[jumlah_bahan];
                problem.getVariables(answer);
                for (int i = 0; i < answer.length; i++) {
                    result.add(new HasilData(pakan.get(i), answer[i]));
                }
                problem.deleteLp();
                Log.d("LinPro", "Calculation successful, result: " + result);
                return result;
            }else if(cek == 2)
            {
                Log.d("LinPro", "Infeasible");
            }

            problem.deleteLp();

        } catch (Exception e) {
            Log.e("LinPro", "Error in LP calculation", e);
        }

        Log.d("LinPro", "Calculation result is empty");
        return result;
    }
}
