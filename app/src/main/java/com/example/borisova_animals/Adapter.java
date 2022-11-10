package com.example.borisova_animals;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {

    private final Context mContext;
    List<Animal> animList;

    public Adapter (Context mContext, List<Animal> listAnim)
    {
        this.mContext = mContext;
        this.animList = listAnim;
    }

    @Override
    public int getCount() {
        return animList.size();
    }

    @Override
    public Object getItem(int i) {
        return animList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return animList.get(i).getID();
    }

    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            return null;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        @SuppressLint("ViewHolder") View v = View.inflate(mContext,R.layout.item_layout,null);
        TextView Title = v.findViewById(R.id.tv_Title);
        TextView Kingdom = v.findViewById(R.id.tv_Kingdom);
        TextView Type = v.findViewById(R.id.tv_Type);
        TextView Class = v.findViewById(R.id.tv_Class);
        TextView Detachment = v.findViewById(R.id.tv_Detachment);
        TextView Family = v.findViewById(R.id.tv_Family);
        ImageView imageView = v.findViewById(R.id.Img);
        Animal animal = animList.get(i);
        Title.setText(animal.getTitle());
        Kingdom.setText(animal.getKingdom());
        Type.setText(animal.getType());
        Class.setText(animal.getClas());
        Detachment.setText(animal.getDetachment());
        Family.setText(animal.getFamily());
        DecodeImg DI = new DecodeImg(mContext);
        imageView.setImageBitmap(DI.getUserImage(animal.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdateDelete.class);
                intent.putExtra(Animal.class.getSimpleName(), animal);
                mContext.startActivity(intent);
            }
        });
        return v;


    }
}
