package mx.edu.ittepic.hilos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button inicio,inicio2;
    EditText editNum,editNum2;
    TextView num,num2;
    ProgressBar pb,pb2;
    int numero,numero2;
    Boolean ej1,ej2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicio=findViewById(R.id.btnIniciar);
        editNum=findViewById(R.id.editNum);
        num=findViewById(R.id.num);
        pb=findViewById(R.id.PB);
        numero=0;

        inicio2=findViewById(R.id.btnIniciar2);
        editNum2=findViewById(R.id.editNum2);
        num2=findViewById(R.id.num2);
        pb2=findViewById(R.id.PB2);
        numero2=0;


        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editNum.getText().toString().isEmpty()){
                    ej1=true;
                    numero=Integer.parseInt(editNum.getText().toString());
                    pb.setMax(numero);
                    pb.setProgress(0);
                    num.setText("0");
                    new AsyncTarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,1,numero);
                }
            }
        });

        inicio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editNum.getText().toString().isEmpty()){
                    ej2=true;
                    numero2=Integer.parseInt(editNum2.getText().toString());
                    pb2.setMax(numero2);
                    pb2.setProgress(0);
                    num2.setText("0");
                    new AsyncTarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,2,numero2);
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

    private class  AsyncTarea extends AsyncTask<Integer, Integer,Boolean> {
        int tipo;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            tipo=params[0];
            for (int i=1; i<=params[1]; i++){
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
            switch (tipo){
                case 1:
                    num.setText(values[0]+"");
                    pb.setProgress(values[0].intValue());
                    break;
                case 2:
                    num2.setText(values[0]+"");
                    pb2.setProgress(values[0].intValue());
                    break;
            }
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            //super.onPostExecute(aVoid);
            if (aVoid){
                Toast.makeText(getApplicationContext(),"Tarea"+tipo+" finaliza AsyncTask",Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getApplicationContext(),"Tarea NO finaliza AsyncTask",Toast.LENGTH_SHORT).show();
        }
    }
}