package com.example.adivina_palabra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final int NUM_PALABRAS = 6;
    public static final int NUM_LETRAS_EN_PALABRA = 5;
    private final TextView[][] palabras = new TextView[NUM_PALABRAS][NUM_LETRAS_EN_PALABRA];
    String palabraAAdivinar = "";
    int numIntentoActual;
    int indiceletraActual;
    TableLayout tableLayout_teclado;
    TextView tv_resultado;
    BDpalabras bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout_teclado = findViewById(R.id.tableLayout_teclado);
        tv_resultado = findViewById(R.id.tv_resultado);
        inicializaArrayPalabras();

        bd = new BDpalabras(MainActivity.this);
        palabraAAdivinar = bd.obtenPalabraAleatoria();

        numIntentoActual = 0;
        indiceletraActual = 0;
        Toast.makeText(getApplicationContext(), palabraAAdivinar, Toast.LENGTH_SHORT).show();
    }


    void inicializaArrayPalabras() {
        palabras[0][0] = findViewById(R.id.textView_0_0);
        palabras[0][1] = findViewById(R.id.textView_0_1);
        palabras[0][2] = findViewById(R.id.textView_0_2);
        palabras[0][3] = findViewById(R.id.textView_0_3);
        palabras[0][4] = findViewById(R.id.textView_0_4);

        palabras[1][0] = findViewById(R.id.textView_1_0);
        palabras[1][1] = findViewById(R.id.textView_1_1);
        palabras[1][2] = findViewById(R.id.textView_1_2);
        palabras[1][3] = findViewById(R.id.textView_1_3);
        palabras[1][4] = findViewById(R.id.textView_1_4);

        palabras[2][0] = findViewById(R.id.textView_2_0);
        palabras[2][1] = findViewById(R.id.textView_2_1);
        palabras[2][2] = findViewById(R.id.textView_2_2);
        palabras[2][3] = findViewById(R.id.textView_2_3);
        palabras[2][4] = findViewById(R.id.textView_2_4);

        palabras[3][0] = findViewById(R.id.textView_3_0);
        palabras[3][1] = findViewById(R.id.textView_3_1);
        palabras[3][2] = findViewById(R.id.textView_3_2);
        palabras[3][3] = findViewById(R.id.textView_3_3);
        palabras[3][4] = findViewById(R.id.textView_3_4);

        palabras[4][0] = findViewById(R.id.textView_4_0);
        palabras[4][1] = findViewById(R.id.textView_4_1);
        palabras[4][2] = findViewById(R.id.textView_4_2);
        palabras[4][3] = findViewById(R.id.textView_4_3);
        palabras[4][4] = findViewById(R.id.textView_4_4);

        palabras[5][0] = findViewById(R.id.textView_5_0);
        palabras[5][1] = findViewById(R.id.textView_5_1);
        palabras[5][2] = findViewById(R.id.textView_5_2);
        palabras[5][3] = findViewById(R.id.textView_5_3);
        palabras[5][4] = findViewById(R.id.textView_5_4);

    }


    public void onPrintLetter(View view) {

        if (indiceletraActual <= NUM_LETRAS_EN_PALABRA - 1 && palabras[numIntentoActual][indiceletraActual].getText().length() == 0) {
            Button b = (Button) view;
            char letra = b.getText().charAt(0);
            palabras[numIntentoActual][indiceletraActual].setText(letra + "");
            palabras[numIntentoActual][indiceletraActual].setBackgroundResource(R.drawable.cuadrado_resaltado);
            if (indiceletraActual < NUM_LETRAS_EN_PALABRA - 1) indiceletraActual++;
        }

    }

    public void onSubmit(View view) {
        if (indiceletraActual != NUM_LETRAS_EN_PALABRA - 1 || palabras[numIntentoActual][indiceletraActual].getText().length() <= 0) {
            showShortToastMessage("Debe introducir todas las letras");
            return;
        }

        String palabraUsuario = obtenerPalabraUsuario();

        // Si la palabra no est?? en el diccionario
        if (!bd.palabraEnDiccionario(palabraUsuario)) {
            showShortToastMessage("La palabra no est?? en el diccionario");
            return;
        }

        colorearPalabraIntento(palabraUsuario);

        // Victoria
        if (palabraUsuario.equals(palabraAAdivinar)) {
            showShortToastMessage("HAS GANADO!!!!");
            tableLayout_teclado.setVisibility(View.GONE);
            return;
        }

        // Si no ha ganado
        showShortToastMessage("Ha fallado la palabra " + palabraUsuario + "," + palabraAAdivinar);

        // P??rdida - si es pre??ltimo intento y todav??a no ha ganado
        if (numIntentoActual >= NUM_PALABRAS - 1) {
            showShortToastMessage("Ha perdido la partida");
            tableLayout_teclado.setVisibility(View.GONE);
            tv_resultado.setText("PALABRA: " + palabraAAdivinar);
            return;
        }

        // Nuevo intento
        numIntentoActual++;
        indiceletraActual = 0;
    }

    public String obtenerPalabraUsuario() {
        String palabraUsuario = "";
        for (int i = 0; i < NUM_LETRAS_EN_PALABRA; i++) {
            palabraUsuario = palabraUsuario + palabras[numIntentoActual][i].getText();
        }
        return palabraUsuario;
    }

    public void showShortToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onRemoveLetter(View view) {
        if (palabras[numIntentoActual][indiceletraActual].getText().length() == 0) {
            if (indiceletraActual > 0) indiceletraActual--;
        }
        palabras[numIntentoActual][indiceletraActual].setText("");
        palabras[numIntentoActual][indiceletraActual].setBackgroundResource(R.drawable.cuadrado);

    }

    void colorearPalabraIntento(String palabraUsuario) {
        // Pasamos todas las letras de la palabra de usuario y establecemos un color
        for (int i = 0; i < palabraAAdivinar.length(); i++) {
            // Gris - caso por defecto
            int drawable = R.drawable.cuadrado_gris;

            // Verde - si letra coincide en la misma posici??n
            if (palabraAAdivinar.charAt(i) == palabraUsuario.charAt(i)) {
                drawable = R.drawable.cuadrado_verde;
            }
            // Amarillo - si letra existe pero en otra posici??n
            else if (palabraAAdivinar.contains(palabraUsuario.charAt(i) + "")) {
                drawable = R.drawable.cuadrado_amarillo;
            }

            // Establecer color a letra
            palabras[numIntentoActual][i].setBackgroundResource(drawable);
        }

    }


    @Override
    protected void onStop() {
        super.onStop();

        bd.cierraBD();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        bd.cierraBD();
    }
}