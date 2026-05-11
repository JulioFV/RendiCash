package com.ingjcfv.rendicash11.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.IpSecManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.ingjcfv.rendicash11.R;

public class Alerta {
    private Dialog alerta;
    private TextView txt_titulo;
    private TextView txt_mensaje;
    private TextView alerta_btn_cerrar_texto;
    private CardView btnCerrar, btnConfirmar;
    private LinearLayout bg_circulo;
    private ImageView imgAlerta;
    private ProgressBar progressBar;
    private Context contexto;
    public Alerta(Context contexto) {
        this.contexto = contexto;
        alerta = new Dialog(contexto);
        alerta.setContentView(R.layout.alerta);
        alerta.setCancelable(false);
        alerta.setCanceledOnTouchOutside(false);
        alerta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        txt_titulo = alerta.findViewById(R.id.alerta_txt_titulo);
        txt_mensaje = alerta.findViewById(R.id.alerta_txt_mensaje);
        alerta_btn_cerrar_texto = alerta.findViewById(R.id.alerta_btn_cerrar_texto);
        btnCerrar = alerta.findViewById(R.id.alerta_btn_cerrar);
        btnConfirmar = alerta.findViewById(R.id.alerta_btn_confirmar);
        bg_circulo = alerta.findViewById(R.id.ln_bg_circulo);
        imgAlerta = alerta.findViewById(R.id.img_bg_circulo);
        progressBar = alerta.findViewById(R.id.alerta_progress);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });

    }

    public void AlertaConfirmacion(String titulo, String mensaje, Runnable onEliminar){
        alerta_btn_cerrar_texto.setText("Cancelar");
        alerta_btn_cerrar_texto.setTextColor(ContextCompat.getColor(contexto, R.color.black));
        btnCerrar.setBackgroundColor(ContextCompat.getColor(contexto, R.color.white));
        bg_circulo.setBackground(ContextCompat.getDrawable(contexto, R.drawable.bg_circulo_amarillo));
        imgAlerta.setImageResource(R.drawable.ic_warning);
        txt_titulo.setText(titulo);
        txt_mensaje.setText(mensaje);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
                if(onEliminar != null)onEliminar.run();
            }
        });
        alerta.show();
    }
    public void AlertaCarga(String titulo, String mensaje){
        btnCerrar.setVisibility(View.GONE);
        btnConfirmar.setVisibility(View.GONE);
        imgAlerta.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        bg_circulo.setBackground(ContextCompat.getDrawable(contexto, R.drawable.bg_circulo_azul));
        txt_titulo.setText(titulo);
        txt_mensaje.setText(mensaje);
        alerta.show();
    }
    public void AlertaError(String titulo, String mensaje){
        btnConfirmar.setVisibility(View.GONE);
        bg_circulo.setBackground(ContextCompat.getDrawable(contexto, R.drawable.bg_circulo_rojo));
        btnCerrar.setBackgroundColor(ContextCompat.getColor(contexto, R.color.rojo_oscuro));
        txt_titulo.setText(titulo);
        txt_mensaje.setText(mensaje);
        imgAlerta.setImageResource(R.drawable.ic_error);
        alerta.show();
    }
    public void AlertaExitosa(){
        btnConfirmar.setVisibility(View.GONE);
        alerta.show();
    }
    public void cerrarDialogo(){
        alerta.dismiss();
    }

}
