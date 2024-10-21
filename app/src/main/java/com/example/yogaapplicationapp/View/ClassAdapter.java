package com.example.yogaapplicationapp.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaapplicationapp.Model.Course;
import com.example.yogaapplicationapp.Model.CourseRepo;
import com.example.yogaapplicationapp.Model.YogaClass;
import com.example.yogaapplicationapp.Model.YogaClassRepo;
import com.example.yogaapplicationapp.R;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    ArrayList<YogaClass> yogaClasses;
    YogaClassRepo repo;
    CourseRepo courseRepo;
    Context context;

    public ClassAdapter(ArrayList<YogaClass> yogaClasses, YogaClassRepo repo, Context context) {
        this.yogaClasses = yogaClasses;
        courseRepo = new CourseRepo(context);
        this.repo = repo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,parent,false);
        return new ClassAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.className.setText("Class name: "+ yogaClasses.get(position).getName());
        holder.teacherName.setText("Teacher name: "+ yogaClasses.get(position).getTeacher());
        holder.classDescription.setText("comment: "+yogaClasses.get(position).getComment());
        holder.classDate.setText("Date: "+yogaClasses.get(position).getDate());
        holder.classCapability.setText("Capability: " + String.valueOf(yogaClasses.get(position).getCurrentCapability()));
        holder.classCourse.setText("Course: " + courseRepo.getCourseWithID(yogaClasses.get(position).getCourseID()).getName());
        //image handling
        holder.classImage.setImageBitmap(BitmapFactory.decodeByteArray(yogaClasses.get(position).getImage(),0,yogaClasses.get(position).getImage().length));
        holder.classImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, YogaClassDetail.class);
                intent.putExtra("classID", yogaClasses.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return yogaClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView className,teacherName, classDate, classDescription,classCapability,classCourse;
        public ImageView classImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            classImage = itemView.findViewById(R.id.classImage);
            className = itemView.findViewById(R.id.className);
            teacherName = itemView.findViewById(R.id.classTeacher);
            classDate = itemView.findViewById(R.id.ClassDate);
            classDescription = itemView.findViewById(R.id.classDescription);
            classCapability = itemView.findViewById(R.id.classCapability);
            classCourse = itemView.findViewById(R.id.classCourse);
        }
    }
    public void setYogaClasses(ArrayList<YogaClass> yogaClasses) {
        this.yogaClasses = yogaClasses;
    }
}
