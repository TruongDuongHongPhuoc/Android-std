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
import com.example.yogaapplicationapp.R;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    ArrayList<Course> courses;
    CourseRepo repo;
    Context context;
    public CourseAdapter(ArrayList<Course> courses, CourseRepo repo, Context context) {
        this.courses = courses;
        this.repo = repo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtCourseName.setText("Course Name: " + String.valueOf(courses.get(position).getId()));
        holder.txtStartTime.setText("Start Time: " + courses.get(position).getStartTime());
        holder.txtDuration.setText( "Duration: " + String.valueOf(courses.get(position).getDuration()));
        holder.txtPriceAClass.setText("Price: "+ String.valueOf(courses.get(position).getPricePerClass()));
        holder.txtCap.setText("Total capability: "+ courses.get(position).getCapability());
        holder.txtDay.setText(" Working day(s): " + courses.get(position).getDaysOfWeek());
        //ON CLICK EVENT AND IMAGE
        if(courses.get(position) != null && courses.get(position).getImage() != null && courses.get(position).getImage().length >0) {
            holder.courseImage.setImageBitmap(BitmapFactory.decodeByteArray(courses.get(position).getImage(), 0, courses.get(position).getImage().length));
            holder.courseImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseID", courses.get(holder.getAdapterPosition()).getId());
                    context.startActivity(intent);
//                    Toast.makeText(context, "HOLDER POSITION: " + courses.get(holder.getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView courseImage;
        public TextView txtCourseName, txtStartTime, txtDuration,txtPriceAClass,txt_type,txtDay, txtCap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCourseName = itemView.findViewById(R.id.course_name);
            txtStartTime = itemView.findViewById(R.id.start_time);
            txtDuration = itemView.findViewById(R.id.duration);
            txtPriceAClass = itemView.findViewById(R.id.price);
//            txt_type = itemView.findViewById(R.id.type);
            txtDay = itemView.findViewById(R.id.day_of_week);
            txtCap = itemView.findViewById(R.id.capability);
            courseImage = itemView.findViewById(R.id.courseDetailImage);
        }
    }
}