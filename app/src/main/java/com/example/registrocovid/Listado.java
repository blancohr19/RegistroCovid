package com.example.registrocovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Listado extends AppCompatActivity {



    List<Modelo> modeloList = new ArrayList<>();
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;

    FloatingActionButton mAddbtn,mAddback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List Data");


        mAddbtn = findViewById(R.id.addbtn);
        mAddback = findViewById(R.id.addback);


        db = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);

        showData();

        mAddback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Listado.this,Registros.class));
                finish();
            }
        });

        mAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Listado.this, Registros.class));
                finish();
            }
        });


    }


    private void showData() {

        pd.setTitle("Cargando Informacion");

        pd.show();

        db.collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            modeloList.clear();

                            pd.dismiss();

                            for(DocumentSnapshot doc: task.getResult()){

                                Modelo modelo= new Modelo(doc.getString("nombre"),
                                        doc.getString("barrio"),
                                        doc.getString("resultado"),
                                        doc.getString("id"),
                                        doc.getString("direccion"),
                                        doc.getString("fecha"),
                                        doc.getString("atencion"));

                                modeloList.add(modelo);
                            }

                            adapter = new CustomAdapter(Listado.this,modeloList);

                            mRecyclerView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Listado.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteData(int index){

        pd.setTitle("Borrando Informacion");

        pd.show();

        db.collection("Documents").document(modeloList.get(index).getDoc())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(Listado.this, "Borrado", Toast.LENGTH_SHORT).show();

                        showData();
                        
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();

                        Toast.makeText(Listado.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void searchData(String s) {

        pd.setTitle("Buscando");

        pd.show();

        db.collection("Documents").whereEqualTo("search",s.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modeloList.clear();
                        pd.dismiss();
                        for(DocumentSnapshot doc: task.getResult()){
                            Modelo modelo= new Modelo(doc.getString("nombre"),
                                    doc.getString("barrio"),
                                    doc.getString("resultado"),
                                    doc.getString("id"),
                                    doc.getString("direccion"),
                                    doc.getString("fecha"),
                                    doc.getString("atencion"));

                            modeloList.add(modelo);
                        }

                        adapter = new CustomAdapter(Listado.this,modeloList);

                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();

                        Toast.makeText(Listado.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.configuracion){
            Toast.makeText(this, "Configuracion", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}