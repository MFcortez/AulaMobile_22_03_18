package com.example.mobile2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    //Declarando variaveis
    private ImageView imageView;
    private Button btnGeo;

    //Encontrando xml e definindo funções da aplicação
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relacionando variavel com object no main
        btnGeo = (Button) findViewById(R.id.btn_geo);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        //Chamando comando de clique
        btnGeo.setOnClickListener((new View.OnClickListener()
        {
            //Clica botão de geolocalizacao, ativa classe que retorna localizacao
            @Override
            public void onClick(View view)
            {
                GPSbusca g = new GPSbusca(getApplication());
                Location l = g.getLocation();
                //Verifica se location nao é null e, caso nao seja, retorna seu valor para o usuario
                if(l != null)
                {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LATITUDE: " + lat +"\n LONGITUDE: " + lon, Toast.LENGTH_LONG).show();
                }
            }
        }));
        //Verifica se o app esta permitido a utilizar a camera e, caso nao esteja, solicita a permissao
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);
        }
        //Relaciona var imageView com o objeto image_view do xml
        imageView = (ImageView) findViewById(R.id.image_view);
        findViewById(R.id.btn_foto).setOnClickListener(new View.OnClickListener()
        {
            //Chama o metodo tirar foto ao ter o botao de tirar foto clicado
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });
    }
    //O metodo tirar foto captura a foto e inicia o metodo Activity Result
    private void tirarFoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }
    //Aqui o metodo Activity Result é sobreescrito para guardar as informacoes da foto tirada
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        //Pega todas as infos da foto e extrai o Bitmap de dentro da foto, depois exibe no imageView
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}