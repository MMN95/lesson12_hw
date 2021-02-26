public class Main {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        Main m = new Main();
        m.method1();
        m.method2();
    }

    public void method1(){
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) arr[i] = 1;

        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
        System.out.println("Время выполнения 1-го метода: " + (System.currentTimeMillis() - a));
    }

    public void method2(){
        float[] arr = new float[SIZE];
        float[] half1 = new float[HALF];
        float[] half2 = new float[HALF];
        for (int i = 0; i < SIZE; i++) arr[i] = 1;

        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, half1, 0, HALF);
        System.arraycopy(arr, HALF, half2, 0, HALF);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF; i++) {
                    half1[i] = (float)(half1[i] * Math.sin(0.2f + i / 5) *
                            Math.cos(0.2f + i / 5) *
                            Math.cos(0.4f + i / 2));
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF; i++) {
                    half2[i] = (float)(half2[i] * Math.sin(0.2f + i / 5) *
                            Math.cos(0.2f + i / 5) *
                            Math.cos(0.4f + i / 2));
                    }
                }
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(half1, 0, arr, 0, HALF);
        System.arraycopy(half2, 0, arr, HALF, HALF);
        System.out.println("Время выполнения 2-го метода: " + (System.currentTimeMillis() - a));

    }


}
