package com.example.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {

    private List<Alumno> listaAlumnos;
    private OnButtonClickListener onButtonClickListener;



    public AlumnoAdapter(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        return new AlumnoViewHolder(view);
    }


    public interface OnButtonClickListener {
        void onButtonClick(Alumno alumno, int position);
        void onButtonClick2(Alumno alumno, int position);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }




    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);
        holder.textNc.setText(alumno.getNc());
        holder.textN.setText("Nombre: " + alumno.getN());
        holder.textpAp.setText("Primer Ap: " + alumno.getpAp());
        holder.textsAp.setText("Segundo Ap: " + alumno.getsAp());
        holder.textS.setText("Semestre: " + alumno.getS());
        holder.textC.setText("Carrera: " + alumno.getC());
        holder.textF.setText("Fecha: " + alumno.getF());
        holder.textT.setText("Telefono: " + alumno.getT());
        holder.btnAccion.setOnClickListener(v -> {
            if (onButtonClickListener != null) {
                onButtonClickListener.onButtonClick(alumno, position);
            }
        });
        holder.btnAccion2.setOnClickListener(v -> {
            if (onButtonClickListener != null) {
                onButtonClickListener.onButtonClick2(alumno, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public static class AlumnoViewHolder extends RecyclerView.ViewHolder {
        TextView textNc, textN, textpAp,textsAp,textS,textC,textF,textT,btnAccion,btnAccion2;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNc = itemView.findViewById(R.id.textNc);
            textN = itemView.findViewById(R.id.textN);
            textpAp = itemView.findViewById(R.id.textpAp);
            textsAp = itemView.findViewById(R.id.textsAp);
            textS = itemView.findViewById(R.id.textS);
            textC = itemView.findViewById(R.id.textC);
            textF = itemView.findViewById(R.id.textF);
            textT = itemView.findViewById(R.id.textT);
            btnAccion = itemView.findViewById(R.id.btnAccion);
            btnAccion2 = itemView.findViewById(R.id.btnAccion2);
        }
    }
}
