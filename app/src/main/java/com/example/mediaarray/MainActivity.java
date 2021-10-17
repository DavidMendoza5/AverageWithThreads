package com.example.mediaarray;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int[] numbers = new int[1000];
    MiThread thread, thread2, thread3, thread4;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
    }

    public void startOperation(View view) {
        fillArray();

        thread = new MiThread(numbers, 0, 250);
        thread2 = new MiThread(numbers, 250, 500);
        thread3 = new MiThread(numbers, 500, 750);
        thread4 = new MiThread(numbers, 750, 1000);

        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread.join();
            thread2.join();
            thread3.join();
            thread4.join();

            double result1 = thread.getResult();
            double result2 = thread2.getResult();
            double result3 = thread3.getResult();
            double result4 = thread4.getResult();

            double average = (result1 + result2 + result3 + result4) / 4;

            System.out.println("Media: " + average);
            result.setText("Resultado: " + average);
        } catch (InterruptedException ex) {
            System.out.println("Programa interrumpido");
        }
    }

    public void fillArray() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }
    }
}

class MiThread extends Thread {
        private int[] numbers;
        private int start;
        private int end;
        private double result;

        public MiThread(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
            this.result = 0;
        }

        public void run() {
            int sum = 0;

            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }

            this.result = (double) sum / (this.end - this.start);
        }

        public double getResult() {
            return result;
        }
}