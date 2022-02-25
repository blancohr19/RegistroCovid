package com.example.registrocovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registros extends AppCompatActivity {

    private SharedPreferences preferences_2;
    private SharedPreferences.Editor editor_2;

    Spinner spinner_d;
    String atencion[]={"Casa","Hospitalizacion","UCI","Ninguno"};
    ArrayAdapter<String> arrayAdapter;

    EditText nombreP, idP, barrioP, direccionP, fechaP;
    RadioButton positivoP,negativoP;
    Button ingresar, listado;

    String pId,pNombre, pBarrio,pDireccion,pFecha;

    ProgressDialog pd;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Data");

        preferences_2 = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor_2 = preferences_2.edit();

        spinner_d=(Spinner)findViewById(R.id.spinner_atencion);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,atencion);
        spinner_d.setAdapter(arrayAdapter);

        nombreP = findViewById(R.id.edt_nombre);
        idP = findViewById(R.id.edt_id);
        barrioP = findViewById(R.id.edt_barrio);
        direccionP = findViewById(R.id.edt_direccion);
        fechaP = findViewById(R.id.edt_fecha);

        positivoP =findViewById(R.id.r_positivo);
        negativoP = findViewById(R.id.r_negativo);

        ingresar = findViewById(R.id.btn_ingresar_r);
        listado = findViewById(R.id.btn_listado);

        pd= new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        final Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            actionBar.setTitle("Update Data");
            ingresar.setText("Actualizar");

            pId = bundle.getString("pId");
            pNombre = bundle.getString("pNombre");
            pBarrio = bundle.getString("pBarrio");
            pDireccion = bundle.getString("pDireccion");
            pFecha = bundle.getString("pFecha");

            idP.setText(pId);
            nombreP.setText(pNombre);
            barrioP.setText(pBarrio);
            direccionP.setText(pDireccion);
            fechaP.setText(pFecha);

        }else{
            actionBar.setTitle("Add Data");
            ingresar.setText("Ingresar");
        }


        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle1 = getIntent().getExtras();

                if(bundle != null){

                    String nombre = nombreP.getText().toString().trim();
                    String id = pId;
                    String barrio = barrioP.getText().toString().trim();
                    String direccion = direccionP.getText().toString().trim();
                    String fecha = fechaP.getText().toString().trim();
                    String atencion=spinner_d.getSelectedItem().toString().trim();

                    updateData(nombre,barrio,id,direccion,fecha,atencion);

                }else {

                    String nombre = nombreP.getText().toString().trim();
                    String id = idP.getText().toString().trim();
                    String barrio = barrioP.getText().toString().trim();
                    String direccion = direccionP.getText().toString().trim();
                    String fecha = fechaP.getText().toString().trim();
                    String atencion=spinner_d.getSelectedItem().toString().trim();
                    String Rresultado="";

                    if (positivoP.isChecked()){
                        Rresultado="positivo";
                    }else if(negativoP.isChecked()){
                        Rresultado="negativo";
                    }

                    if(nombre.equals("")|| barrio.equals("")||direccion.equals("")||fecha.equals("")||atencion.equals("")
                            || id.equals("")){

                        validacion();

                    }else {

                        uploadData(nombre,barrio,Rresultado,id,direccion,fecha,atencion);
                        limpiar();

                    }

                }



            }
        });


        listado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registros.this,Listado.class));
                finish();
            }
        });



    }

    private void updateData(String nombre, String barrio, String id, String direccion, String fecha,String atencion) {
        pd.setTitle("Actualizando Registro");
        pd.show();

        db.collection("Documents").document(id).
                update("nombre",nombre,"barrio",barrio,"direccion",direccion,"fecha",fecha,"atencion",atencion,"search",atencion.toLowerCase())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        pd.dismiss();
                        Toast.makeText(Registros.this, "Actualizado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();

                        Toast.makeText(Registros.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData(String nombre, String barrio, String rresultado, String id, String direccion, String fecha, String atencion) {

        pd.setTitle("Añadiendo Registro");
        pd.show();

        Map<String, Object> doc = new HashMap<>();
        doc.put("nombre",nombre);
        doc.put("barrio",barrio);
        doc.put("resultado",rresultado);
        doc.put("id",id);
        doc.put("direccion",direccion);
        doc.put("fecha",fecha);
        doc.put("atencion",atencion);
        doc.put("search",atencion.toLowerCase());


        db.collection("Documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                            
                        pd.dismiss();
                        Toast.makeText(Registros.this, "Ingresado", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Registros.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void limpiar(){
        nombreP.setText("");
        idP.setText("");
        barrioP.setText("");
        direccionP.setText("");
        fechaP.setText("");
    }

    private void validacion() {

        String nombre = nombreP.getText().toString();
        String id = idP.getText().toString();
        String barrio = barrioP.getText().toString();
        String direccion = direccionP.getText().toString();
        String fecha = fechaP.getText().toString();


        if(nombre.equals("")){
            nombreP.setError("Requerido");
        }else if(id.equals("")){
            idP.setError("Requerido");
        }else if(barrio.equals("")){
            barrioP.setError("Requerido");
        }else if(direccion.equals("")){
            direccionP.setError("Requerido");
        }else if(fecha.equals("")){
            fechaP.setError("Requerido");
        }

    }




    public void CerrarSesion(View view){
        editor_2.clear();
        editor_2.commit();

        Intent intent2 = new Intent(this,MainActivity.class);
        startActivity(intent2);
        finish();
    }
}