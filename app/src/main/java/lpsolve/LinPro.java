package lpsolve;

import android.util.Log;
import android.util.TimingLogger;

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
    private final String jenis;

    public Bahan(String namaBahan, double[] data, String jenis) {
        this.namaBahan = namaBahan;
        System.arraycopy(data, 0, komposisi, 0, 12);
        this.harga = data[12];
        this.rasio = data[13];
        this.jenis = jenis;
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

    public String getJenis() { return jenis;}
}


public class LinPro {
    public static ArrayList<HasilData> calculate(double[] hewan, ArrayList<ProsesData> pakan) {
        long startTime = System.currentTimeMillis();
        ArrayList<HasilData> result = new ArrayList<>();
        Log.d("LinPro", "Starting calculation");
        // Log the input data
        Log.d("LinPro", "Received hewan: " + Arrays.toString(hewan));
        Log.d("LinPro", "Received pakan: " + pakan);
        // Size bahan
        int jumlahBahan = pakan.size();
        Log.d("LinPro", "Jumlahnya: " + jumlahBahan);


        //Menerima Input Jenis Hewan dan Bahan Pakan
        Animal nutrisi = new Animal(hewan);

        ArrayList<Bahan> bahanList = new ArrayList<>();

        pakan.forEach(i -> {
            String nama = i.getNama();
            String jenis = i.getJenis();
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
            bahanList.add(new Bahan(nama, data, jenis));
        });

        // Deklarasi problem
        LpSolve problem;
        try {
            /* Mendeklarasi Linear Program Baru dan Mengatur
               Mode Masalah Minimasi */
            problem = LpSolve.makeLp(0, jumlahBahan);
            problem.setMinim();

            // Objective function
            for (int i = 0; i < jumlahBahan; i++) {
                problem.setObj(i + 1, bahanList.get(i).getRasio() / 100.0);
            }

            // Memasukan Konstrain Masing Masing Nutrisi
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

            // Minimum dan Maksimum persen Bahan Pakan
            for (int i = 0; i < jumlahBahan; i++) {
                problem.setLowbo(i + 1, bahanList.get(i).getKomposisi(0));
                problem.setUpbo(i + 1, bahanList.get(i).getKomposisi(1));
            }

            // Semua Pakan 100 percent
            double[] totalConstraint = new double[jumlahBahan + 1];
            for (int i = 0; i < jumlahBahan; i++) {
                totalConstraint[i + 1] = 1.0;
            }
            problem.addConstraint(totalConstraint, LpSolve.EQ, 100);

            //Semua hijauan total minimal 40%
            double[] totalHijau = new double[jumlahBahan + 1];
            for (int i = 0; i < jumlahBahan; i++) {
                String tipe = bahanList.get(i).getJenis();
                if(tipe.equalsIgnoreCase("Hijauan")) {
                    totalHijau[i + 1] = 1.0;
                }else{
                    totalHijau[i+1] = 0.0;
                }
                Log.d("Peruen", tipe + " " + i + ": " + totalHijau[i + 1]);
            }
            problem.addConstraint(totalHijau, LpSolve.GE, 40);


            // Solve
            int cek = problem.solve();

            if (cek == 0 || cek == 1) {
                double[] sol = problem.getPtrVariables();
                for (int i = 0; i < sol.length; i++) {
                    result.add(new HasilData(pakan.get(i), sol[i]));
                }
                problem.deleteLp();
                Log.d("LinPro", "Calculation successful, result: " + result);
                long endTime = System.currentTimeMillis();
                Log.d("Waktu LinPro","Time: " + (endTime-startTime) + "ms");
                return result;
            }else if(cek == 2)
            {
                Log.d("LinPro", "Infeasible");
            }


        } catch (LpSolveException e) {
            e.printStackTrace();
        }
        Log.d("LinPro", "Calculation result is empty");

        long endTime = System.currentTimeMillis();
        Log.d("Waktu LinPro","Time: " + (endTime-startTime) + "ms");
        return result;
    }
}
