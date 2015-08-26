package su.shev4enkostr.easycode;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by stas on 25.08.15.
 */
public class RVCoursesAdapter extends RecyclerView.Adapter<RVCoursesAdapter.CoursesViewHolder>
{
    public static class CoursesViewHolder extends RecyclerView.ViewHolder
    {

        CardView cvCourses;
        TextView coursesName;
        TextView coursesAbout;
        ImageView coursesPicture;

        CoursesViewHolder(View itemView)
        {
            super(itemView);
            cvCourses = (CardView)itemView.findViewById(R.id.cv_courses);
            coursesName = (TextView)itemView.findViewById(R.id.courses_name);
            //coursesAbout = (TextView)itemView.findViewById(R.id.courses_about);
            coursesPicture = (ImageView)itemView.findViewById(R.id.courses_picture);
        }
    }

    List<Courses> persons;

    RVCoursesAdapter(List<Courses> persons)
    {
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_courses, viewGroup, false);
        CoursesViewHolder pvh = new CoursesViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CoursesViewHolder personViewHolder, int i)
    {
        personViewHolder.coursesName.setText(persons.get(i).getName());
        //personViewHolder.coursesAbout.setText(persons.get(i).getAbout());
        personViewHolder.coursesPicture.setImageResource(persons.get(i).getPictureId());
    }

    @Override
    public int getItemCount()
    {
        return persons.size();
    }
}
