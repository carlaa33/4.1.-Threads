package mx.edu.ittepic.hilos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button inicio;
    EditText editNum;
    TextView num;
    ProgressBar pb;
    int numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicio=findViewById(R.id.btnIniciar);
        editNum=findViewById(R.id.editNum);
        num=findViewById(R.id.num);
        pb=findViewById(R.id.progressBar3);
        numero=0;

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editNum.getText().toString().isEmpty()){
                    numero=Integer.parseInt(editNum.getText().toString());
                    pb.setProgress(0);
                    new AsyncTarea().execute();
                }
            }
        });



    }

    public void espera(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class  AsyncTarea extends AsyncTask<Void, Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMax(numero);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            for (int i=1; i<=numero; i++){
                espera();
                publishProgress(i);
                if (isCancelled()){
                    break;
                }
            }
            return true;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            //Actualizar la barra de progreso
            num.setText(values[0]+"");
            pb.setProgress(values[0].intValue());
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            //super.onPostExecute(aVoid);
            if (aVoid){
                Toast.makeText(getApplicationContext(),"Tarea finaliza AsyncTask",Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getApplicationContext(),"Tarea NO finaliza AsyncTask",Toast.LENGTH_SHORT).show();

        }


    }
}
