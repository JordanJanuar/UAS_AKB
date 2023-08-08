package com.example.uas_10120013;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

// NIM : 10120013
// NAMA : Jordan Januar Ilhami G
// KELAS : IF 1
public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    ArrayList<Notes> list = new ArrayList<>();
    public RVAdapter(Context ctx)
    {
        this.context = ctx;
    }
    public void setItems(ArrayList<Notes> emp)
    {
        list.addAll(emp);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item,parent,false);
        return new EmployeeVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        Notes e = null;
        this.onBindViewHolder(holder,position,e);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Notes e)
    {
        EmployeeVH vh = (EmployeeVH) holder;
        Notes notes = e==null? list.get(position):e;
        vh.tv_title.setText(notes.getTitle());
        vh.tv_note.setText(notes.getNote());
        vh.tv_category.setText(notes.getCategory());
        vh.tv_date.setText(notes.getDate());
        vh.tv_option.setOnClickListener(v->
        {
            PopupMenu popupMenu =new PopupMenu(context,vh.tv_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                if (item.getItemId() == R.id.menu_edit){
                    Intent intent=new Intent(context,InputActivity.class);
                    intent.putExtra("EDIT",notes);
                    context.startActivity(intent);
                }else if(item.getItemId() == R.id.menu_remove){
                    DAONotes dao=new DAONotes();
                    dao.remove(notes.getKey()).addOnSuccessListener(suc->
                    {
                        Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
                        list.remove(notes);
                    }).addOnFailureListener(er->
                    {
                        Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
