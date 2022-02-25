package com.example.registrocovid;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.constants.ListAppsActivityContract;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    Listado listActivity;
    List<Modelo> modeloList;


    public CustomAdapter(Listado listActivity,List<Modelo> modeloList){
        this.listActivity = listActivity;
        this.modeloList = modeloList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.modelo_layout, viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String nombre = modeloList.get(position).getNombre();
                String barrio = modeloList.get(position).getBarrio();
                String resultado = modeloList.get(position).getResultado();
                String id=modeloList.get(position).getDoc();
                String direccion=modeloList.get(position).getDireccion();
                String fecha=modeloList.get(position).getFecha();
                String atencion = modeloList.get(position).getLugar();


            }

            @Override
            public void onItemLongClick(View view, final int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);

                String [] options = {"Actualizar","Borrar"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            String id = modeloList.get(position).getDoc();
                            String nombre = modeloList.get(position).getNombre();
                            String barrio = modeloList.get(position).getBarrio();
                            String direccion=modeloList.get(position).getDireccion();
                            String fecha=modeloList.get(position).getFecha();

                            Intent intent = new Intent(listActivity,Registros.class);
                            intent.putExtra("pId",id);
                            intent.putExtra("pNombre",nombre);
                            intent.putExtra("pBarrio",barrio);
                            intent.putExtra("pDireccion",direccion);
                            intent.putExtra("pFecha",fecha);

                            listActivity.startActivity(intent);
                        }
                        if(which==1){

                            listActivity.deleteData(position);

                        }
                    }
                }).create().show();


            }
        });

        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.mnombre.setText(modeloList.get(i).getNombre());
            viewHolder.mbarrio.setText(modeloList.get(i).getBarrio());
            viewHolder.mresultadotv.setText(modeloList.get(i).getResultado());
            viewHolder.mid.setText(modeloList.get(i).getDoc());
            viewHolder.mdireccion.setText(modeloList.get(i).getDireccion());
            viewHolder.mfecha.setText(modeloList.get(i).getFecha());
            viewHolder.matencion.setText(modeloList.get(i).getLugar());
    }

    @Override
    public int getItemCount() {
        return modeloList.size();
    }
}
