package lpsolve;

import android.util.Log;

import com.sofeed.myapp.HasilData;
import com.sofeed.myapp.ProsesData;

import java.util.ArrayList;
import java.util.Arrays;

class Animal
{
    private final double[] minValues = new double[9];
    private final double[] maxValues = new double[9];

    public Animal(double[] x)
    {
        for(int i=0;i<18;i++)
        {
            if(i<9){
                minValues[i] = x[i];
            }else{
                maxValues[i-9] = x[i];
            }
        }
    }

    public double getMinValue(int index) {
        return minValues[index];
    }

    public double getMaxValue(int index) {
        return maxValues[index];
    }
};

class Bahan {
    private final String namaBahan;
    private final double[] komposisi = new double[12];
    private final double harga;
    private final double rasio;

    public Bahan(String namaBahan, double[] data) {
        this.namaBahan = namaBahan;
        System.arraycopy(data, 0, komposisi, 0, 12);
        this.harga = data[12];
        this.rasio = data[13];
    }

    public double getRasio() {
        return rasio;
    }

    public double getKomposisi(int index) {
        return komposisi[index];
    }

    public String getNamaBahan() {
        return namaBahan;
    }
}


public class LinPro {
    public static ArrayList<HasilData> calculate(double[] hewan, ArrayList<ProsesData> pakan) {
        ArrayList<HasilData> result = new ArrayList<>();
        Log.d("LinPro", "Starting calculation");
        // Log the input data
        Log.d("LinPro", "Received hewan: " + Arrays.toString(hewan));
        Log.d("LinPro", "Received pakan: " + pakan);
        // Size bahan
        int jumlahBahan = pakan.size();
        Log.d("LinPro", "Jumlahnya: " + jumlahBahan);


        //nutrisi
        Animal nutrisi = new Animal(hewan);

        // Input ke class
        ArrayList<Bahan> bahanList = new ArrayList<>();
        pakan.forEach(i -> {
            String nama = i.getNama();
            double[] data = new double[14];
            data[0] = i.getMinJumlah();
            data[1] = i.getMaxJumlah();
            data[2] = i.getBahanKering();
            data[3] = i.getAbu();
            data[4] = i.getPk();
            data[5] = i.getLk();
            data[6] = i.getSk();
            data[7] = i.getBetn();
            data[8] = i.getTdn();
            data[9] = i.getCa();
            data[10] = i.getP();
            data[11] = i.getMetana();
            data[12] = i.getHarga();
            data[13] = i.getRasio();
            bahanList.add(new Bahan(nama, data));
        });

        // Set problem dari lp
        LpSolve problem;
        try {
            problem = LpSolve.makeLp(0, jumlahBahan);
            problem.setMinim();
            // Objective function
            for (int i = 0; i < jumlahBahan; i++) {
                problem.setObj(i + 1, bahanList.get(i).getRasio() / 100.0);
            }

            // Constraints
            for (int i = 0; i <= 1; i++) {
                for (int j = 0; j < 9; j++) {
                    double[] constraint = new double[jumlahBahan + 1];
                    for (int k = 0; k < jumlahBahan; k++) {
                        constraint[k + 1] = bahanList.get(k).getKomposisi(j + 2) / 100.0;
                    }
                    if (i == 1) {
                        problem.addConstraint(constraint, LpSolve.LE, nutrisi.getMaxValue(j));
                    } else {
                        problem.addConstraint(constraint, LpSolve.GE, nutrisi.getMinValue(j));
                    }
                }
            }

            // Boundaries
            for (int i = 0; i < jumlahBahan; i++) {
                problem.setLowbo(i + 1, bahanList.get(i).getKomposisi(0));
                problem.setUpbo(i + 1, bahanList.get(i).getKomposisi(1));
            }

            // Total 100 percent
            double[] totalConstraint = new double[jumlahBahan + 1];
            for (int i = 0; i < jumlahBahan; i++) {
                totalConstraint[i + 1] = 1.0;
            }
            problem.addConstraint(totalConstraint, LpSolve.EQ, 100);
            // Solve
            int cek = problem.solve();

            if (cek == 0 || cek == 1) {
                double[] sol = problem.getPtrVariables();
                for (int i = 0; i < sol.length; i++) {
                    result.add(new HasilData(pakan.get(i), sol[i]));
                }
                problem.deleteLp();
                Log.d("LinPro", "Calculation successful, result: " + result);
                return result;
            }else if(cek == 2)
            {
                Log.d("LinPro", "Infeasible");
            }
        } catch (LpSolveException e) {
            e.printStackTrace();
        }
        Log.d("LinPro", "Calculation result is empty");
        return result;
    }
}
