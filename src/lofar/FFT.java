/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

/**
 * Codigo adaptado de:
 *
 * Copyright 2006-2007 Columbia University.
 *
 * This file is part of MEAPsoft.
 *
 * MEAPsoft is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 2 as published by the Free
 * Software Foundation.
 *
 * MEAPsoft is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * MEAPsoft; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * See the file "COPYING" for the text of the license.
 */
public class FFT {

    int n, m;
    double[] cos;
    double[] sin;

    public FFT() {

   }

    public double[] FFT(double[] audio) {
        int N = 1024;
        //FFT fft = new 
        FFT(N);
        double[] re = new double[N];
        double[] im = new double[N];
        //Vector con el audio por procesar

        //Vector con el resultado final
        double[] magnitudFFT = new double[N];
        //En este for se introduce el audio
        for (int i = 0; i < N; i++) {
            re[i] = audio[i];
            im[i] = 0;
        }
        FFT(re, im);		//Envio mis datos a ejecutar la FFT
        for (int i = 0; i < (re.length) / 2; i++) //Se divide entre dos porque solo se usa 512 datos
        {
            magnitudFFT[i] = (Math.sqrt(Math.pow(re[i] / N, 2) + Math.pow(im[i] / N, 2)));
        }
        magnitudFFT[0] = magnitudFFT[1];		//eliminar la componente de cd
        printReIm(magnitudFFT);
        magnitudFFT = LOG(magnitudFFT);
        return magnitudFFT;
    }

    public void FFT(int n) {
        this.n = n;
        this.m = (int) (Math.log(n) / Math.log(2));
        if (n != (1 << m)) {
            throw new RuntimeException("FFT length must be power of 2");
        }
        // precompute tables
        cos = new double[n / 2];
        sin = new double[n / 2];

        for (int i = 0; i < n / 2; i++) {
            cos[i] = Math.cos(-2 * Math.PI * i / n);
            sin[i] = Math.sin(-2 * Math.PI * i / n);
        }
    }

    public void FFT(double[] x, double[] y) {
        int i, j, k, n1, n2, a;
        double c, s, e, t1, t2;
        j = 0;
        n2 = n / 2;
        for (i = 1; i < n - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j = j - n1;
                n1 = n1 / 2;
            }
            j = j + n1;

            if (i < j) {
                t1 = x[i];
                x[i] = x[j];
                x[j] = t1;
                t1 = y[i];
                y[i] = y[j];
                y[j] = t1;
            }
        }
        n1 = 0;
        n2 = 1;
        for (i = 0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;
            for (j = 0; j < n1; j++) {
                c = cos[a];
                s = sin[a];
                a += 1 << (m - i - 1);
                for (k = j; k < n; k = k + n2) {
                    t1 = c * x[k + n1] - s * y[k + n1];
                    t2 = s * x[k + n1] + c * y[k + n1];
                    x[k + n1] = x[k] - t1;
                    y[k + n1] = y[k] - t2;
                    x[k] = x[k] + t1;
                    y[k] = y[k] + t2;
                }
            }
        }
    }

    protected static void printReIm(double[] im) {
        System.out.print("FFT Absoluto: [");
        for (int i = 0; i < (im.length) / 2; i++) {
            System.out.print(((int) (im[i] * 1000) / 1000.0) + ",");
        }
        System.out.println("]");
    }

    public double[] LOG(double[] audio) {
        for (int i = 0; i < audio.length; i++) {
            audio[i] = (20 * Math.log10((3.3 * audio[i]) / 4096));
        }
        return audio;
    }
}
