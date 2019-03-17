package com.proto.raagaguru;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.LessonItemViewHolder> {

    private Context context;
    private ArrayList<Lesson> lessons;
    private LessonViewModel lessonViewModel;

    LessonListAdapter(Context context, LessonViewModel lessonViewModel) {
        this.context = context;
        this.lessonViewModel = lessonViewModel;
    }

    @NonNull
    @Override
    public LessonItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.layout_lesson_item, viewGroup, false);
        return new LessonItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonItemViewHolder lessonItemViewHolder, int position) {
        if (lessons != null) {
            Lesson current = lessons.get(position);
            lessonItemViewHolder.lessonNameView.setText(current.getAudioFileName());

            lessonItemViewHolder.lessonOptions.setOnClickListener(view -> {
                PopupMenu popup = new PopupMenu(context, view);
                popup.inflate(R.menu.actions);
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            Toast.makeText(
                                context,
                            "Updated " + lessons.get(position).getAudioFileName(),
                                Toast.LENGTH_LONG).show();

                            int newTagData = lessons.get(position).getTagData() + 1;
                            lessons.get(position).setTagData(newTagData);
                            lessonViewModel.update(lessons.get(position));

                            return true;

                        case R.id.menu_delete:
                            lessonViewModel.delete(lessons.get(position));
                            return true;
                        default:
                            return false;
                    }
                });
                popup.show();
            });

        } else {
            // Covers the case of data not being ready yet.
            lessonItemViewHolder.lessonNameView.setText(context.getString(R.string.noLessons));
        }

        lessonItemViewHolder.parentLayout.setOnClickListener(
            view -> {
                int tagData = lessons.get(position).getTagData();
                Toast.makeText(
                    context,
                    "TODO: StudentView (i.e. play lesson) " + tagData,
                    Toast.LENGTH_SHORT).show();
            });
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

    class LessonItemViewHolder extends RecyclerView.ViewHolder{

        private TextView lessonNameView;
        private ImageButton lessonOptions;
        private RelativeLayout parentLayout;

        private LessonItemViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonNameView = itemView.findViewById(R.id.lessonName);
            lessonOptions = itemView.findViewById(R.id.lessonOptions);
            parentLayout = itemView.findViewById(R.id.lessonItemParent);
        }
    }
}
