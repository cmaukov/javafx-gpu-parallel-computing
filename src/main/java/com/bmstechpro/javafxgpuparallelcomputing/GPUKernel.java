package com.bmstechpro.javafxgpuparallelcomputing;
/* javafx-gpu-parallel-computing
 * @created 10/02/2022
 * @author Konstantin Staykov
 */

import com.aparapi.Kernel;

public class GPUKernel extends Kernel {
    int[] array1;
    int[] array2;
    int[] result;

    public GPUKernel(int[] array1, int[] array2, int[] result) {
        this.array1 = array1;
        this.array2 = array2;
        this.result = result;
        setExplicit(true);
        put(array1);
        put(array2);
        put(result);
    }

    @Override
    public void run() {
        int index = getGlobalId();

        result[index] = array1[index] + array2[index];
    }
}
