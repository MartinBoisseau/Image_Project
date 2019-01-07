package com.example.tinra.imageeditor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TimingLogger;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView img, img2;
    private Bitmap bmp, bmp2;
    private int kernelSize;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.fruits);
        img2 = findViewById(R.id.imageView2);
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.legumes);
        img.setImageBitmap(bmp);
        img2.setImageBitmap(bmp2);
        kernelSize = 0;

        Button btn_reset = (Button)findViewById(R.id.button_reset);
        btn_reset.setText("Reset");
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(bmp);
                img2.setImageBitmap(bmp2);
            }
        });

        Button btn_gray = (Button)findViewById(R.id.button_gray);
        btn_gray.setText("Gray");
        btn_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(colorFilter(bmp, Filter.GRAY));
                img2.setImageBitmap(colorFilter(bmp2, Filter.GRAY));
            }
        });
        Button btn_red = (Button)findViewById(R.id.button_red);
        btn_red.setText("Red_Filter");
        btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(colorFilter(bmp, Filter.RED));
                img2.setImageBitmap(colorFilter(bmp2, Filter.RED));
            }
        });
        Button btn_green = (Button)findViewById(R.id.button_green);
        btn_green.setText("Green_Filter");
        btn_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(colorFilter(bmp, Filter.GREEN));
                img2.setImageBitmap(colorFilter(bmp2, Filter.GREEN));
            }
        });
        Button btn_blue = (Button)findViewById(R.id.button_blue);
        btn_blue.setText("Blue_Filter");
        btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(colorFilter(bmp, Filter.BLUE));
                img2.setImageBitmap(colorFilter(bmp2, Filter.BLUE));
            }
        });

        Button btn_random_color = (Button)findViewById(R.id.button_random_color);
        btn_random_color.setText("Random_Color");
        btn_random_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(randomColor(bmp));
                img2.setImageBitmap(randomColor(bmp2));
            }
        });

        Button btn_hist = (Button)findViewById(R.id.button_hist);
        btn_hist.setText("Histogram");
        btn_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(equilizeHist(bmp));
                img2.setImageBitmap(equilizeHist(bmp2));
            }
        });

        Button btn_size1 = (Button)findViewById(R.id.button_size1);
        btn_size1.setText("kernel 3*3");
        btn_size1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kernelSize = 3;
            }
        });

        Button btn_size2 = (Button)findViewById(R.id.button_size2);
        btn_size2.setText("kernel 11*11");
        btn_size2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kernelSize = 11;
            }
        });

        Button btn_size3 = (Button)findViewById(R.id.button_size3);
        btn_size3.setText("kernel 21*21");
        btn_size3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kernelSize = 21;
            }
        });

        Button btn_average = (Button)findViewById(R.id.button_average);
        btn_average.setText("Average");
        btn_average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kernelSize != 0){
                    img.setImageBitmap(applyConvolution(bmp, Convolution.AVERAGE, kernelSize));
                    img2.setImageBitmap(applyConvolution(bmp2, Convolution.AVERAGE, kernelSize));
                }
            }
        });
    }

    static Bitmap colorFilter(Bitmap bmp, Filter filter){
        Bitmap tmp = bmp.copy(bmp.getConfig(), true);
        int w = tmp.getWidth();
        int h = tmp.getHeight();
        int[] pixels = new int[w * h];
        tmp.getPixels(pixels, 0, w, 0, 0, w, h);
        //Set filter according to the choice
        switch(filter){
            case GRAY:
                for (int i = 0; i < pixels.length; i++){
                    int grey = (int)(0.3 * Color.red(pixels[i]) + 0.59 * Color.green(pixels[i]) + 0.11 * Color.blue(pixels[i]));
                    pixels[i] = Color.rgb(grey, grey, grey);
                }
                break;
            case RED:
                for (int i = 0; i < pixels.length; i++){
                    int grey = (int)(0.3 * Color.red(pixels[i]) + 0.59 * Color.green(pixels[i]) + 0.11 * Color.blue(pixels[i]));
                    pixels[i] = Color.rgb(Color.red(pixels[i]), grey, grey);
                }
                break;
            case GREEN:
                for (int i = 0; i < pixels.length; i++){
                    int grey = (int)(0.3 * Color.red(pixels[i]) + 0.59 * Color.green(pixels[i]) + 0.11 * Color.blue(pixels[i]));
                    pixels[i] = Color.rgb(grey, Color.green(pixels[i]), grey);
                }
                break;
            case BLUE:
                for (int i = 0; i < pixels.length; i++){
                    int grey = (int)(0.3 * Color.red(pixels[i]) + 0.59 * Color.green(pixels[i]) + 0.11 * Color.blue(pixels[i]));
                    pixels[i] = Color.rgb(grey, grey, Color.blue(pixels[i]));
                }
                break;
                default:
                    break;
        }
        tmp.setPixels(pixels, 0, w, 0, 0, w, h);
        return tmp;
    }

    static Bitmap randomColor(Bitmap bmp){
        Bitmap tmp = bmp.copy(bmp.getConfig(), true);
        int w = tmp.getWidth();
        int h = tmp.getHeight();
        int[] pixels = new int[w * h];
        tmp.getPixels(pixels, 0, w, 0, 0, w, h);
        int rand = (int)(Math.random() * ((360) + 1));
        for (int i = 0; i < pixels.length; i++){
            float[] hsv = new float[3];
            //go to HSV version to modify the hue value
            Color.RGBToHSV(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]), hsv);
            hsv[0] = rand;
            //come back to the color version
            pixels[i] = Color.HSVToColor(hsv);
        }
        tmp.setPixels(pixels, 0, w, 0, 0, w, h);
        return tmp;
    }

    static Bitmap equilizeHist(Bitmap bmp){
        Bitmap tmp = bmp.copy(bmp.getConfig(), true);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w * h;
        int[] pixels = new int[size];
        int[] colors = new int[size];
        int[][] hist = new int[256][2];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, w, h);
        //initialization of histogram
        for (int i = 0; i < 256; i++) {
            hist[i][0] = 0;
            hist[i][1] = 0;
        }
        //set colors which is the greyScale array of bmp
        for (int i = 0; i < size; i++) {
            int grey = (int)(0.3 * Color.red(pixels[i]) + 0.59 * Color.green(pixels[i]) + 0.11 * Color.blue(pixels[i]));
            colors[i] = grey;
            hist[grey][0]++;
        }
        //set histogram
        for (int i = 1; i < 256; i++) {
            hist[i][1] = hist[i - 1][1] + hist[i][0];
        }

        int average = size / 256;

        //set image according to histogram
        for (int i = 0; i < size; i++) {
            colors[i] = hist[colors[i]][1] / average;
            pixels[i] = Color.rgb(colors[i], colors[i], colors[i]);
        }
        tmp.setPixels(pixels, 0, w, 0, 0, w, h);
        return tmp;
    }

    static Bitmap applyConvolution(Bitmap bmp, Convolution type, int filterSize) {
        Bitmap tmp = bmp.copy(bmp.getConfig(), true);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w * h;
        int[] pixels = new int[size];
        int[][] pixelsIn = new int[w][h];
        int[][] pixelsOut = new int[w][h];
        int margin = (filterSize - 1) / 2;
        //creation of bitmap
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < size; i++) {
            pixelsIn[i % w][i / w] = pixels[i];
        }
        //creation of kernel
        float[][] kernel = new float[filterSize][filterSize];
        int division = 0;
        switch (type) {
            case GAUSSIAN:
                for (int x = 0; x < filterSize; x++) {
                    for (int y = 0; y < filterSize; y++) {
                        //G(x,y) = (1 /  (2 * PI * (SIGMA (deviation standart)²))) * exp ((- x² + y²) /  (2 * sigma²))
                    }
                }
                break;
            case AVERAGE:
                for (int x = 0; x < filterSize; x++) {
                    for (int y = 0; y < filterSize; y++) {
                        kernel[x][y] = 1;
                    }
                }
                division = filterSize * filterSize;
                break;
        }
        //apply kernel to the image
        for (int i = margin; i < w - margin; i++) {
            for (int j = margin; j < h - margin; j++) {
                int r = 0;
                int g = 0;
                int b = 0;
                for (int x = 0; x < filterSize; x++) {
                    for (int y = 0; y < filterSize; y++) {
                        r += Color.red(pixelsIn[i - margin + x][j - margin + y]) * kernel[x][y];
                        g += Color.green(pixelsIn[i - margin + x][j - margin + y]) * kernel[x][y];
                        b += Color.blue(pixelsIn[i - margin + x][j - margin + y]) * kernel[x][y];
                    }
                }
                   pixelsOut[i][j] = Color.rgb(r / division, g / division, b / division);
            }
        }
        for (int i = 0; i < size; i++) {
            pixels[i] = pixelsOut[i % w][i / w];
        }
        tmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return tmp;
    }
}

