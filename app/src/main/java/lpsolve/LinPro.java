package lpsolve;

import java.util.ArrayList;


public class LinPro {
    public static double[] calculate(double[] hewan, ArrayList<ArrayList<Double>> pakan)
    {
        try {
            //size bahan
            int jumlah_bahan = pakan.size();

            //set problem dari lp
            LpSolve problem = LpSolve.makeLp(0,jumlah_bahan);
            problem.setMinim();

            //Buat objective function
            double[] objective = new double[jumlah_bahan+1];
            for (int i = 0; i < jumlah_bahan; i++)
            {
                objective[i+1] = pakan.get(i).get(12);
                objective[i+1]/=100;
            }
            problem.setObjFn(objective);

            //Boundary, minimal dan maksimal
            for (int i = 0; i <24; i++)
            {
                if(i<12)
                {
                    for (int j = 0; j < 10; j++)
                    {
                        double[] boundary= new double[jumlah_bahan+1];
                        for(int k = 0; k<jumlah_bahan; k++)
                        {
                            boundary[k+1] = pakan.get(k).get(j+2);
                            boundary[k+1] /= 100;
                        }
                        problem.addConstraint(boundary,LpSolve.GE,hewan[i]);
                    }

                }
                else
                {
                    for (int j = 0; j < 10; j++)
                    {
                        double[] boundary= new double[jumlah_bahan+1];
                        for(int k = 0; k<jumlah_bahan; k++)
                        {
                            boundary[k+1] = pakan.get(k).get(j+2);
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
                problem.addConstraint(mimax, LpSolve.GE, pakan.get(i).get(0));
                problem.addConstraint(mimax, LpSolve.LE, pakan.get(i).get(1));
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

            return problem.getPtrVariables();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        double[] salah = {-1.0};
        return salah;
    }
}
