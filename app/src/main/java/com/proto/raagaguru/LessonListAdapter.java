package com.proto.raagaguru;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Lesson> lessons;

    LessonListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.layout_lesson_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (lessons != null) {
            Lesson current = lessons.get(position);
            viewHolder.lessonNameView.setText(current.getAudioFileName());
        } else {
            // Covers the case of data not being ready yet.
            viewHolder.lessonNameView.setText(context.getString(R.string.noLessons));
        }

        viewHolder.parentLayout.setOnClickListener(
            view -> Toast.makeText(
                context,
                "TODO: StudentView (i.e. play lesson)",
                Toast.LENGTH_SHORT).show());
    }

    void setLessons(ArrayList<Lesson> lessons){
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (lessons != null)
            return lessons.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView lessonNameView;
        private RelativeLayout parentLayout;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonNameView = itemView.findViewById(R.id.lessonName);
            parentLayout = itemView.findViewById(R.id.lessonItemParent);
        }
    }
}
