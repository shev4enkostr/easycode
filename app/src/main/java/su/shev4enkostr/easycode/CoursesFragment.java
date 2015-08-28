package su.shev4enkostr.easycode;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stas on 25.08.15.
 */
public class CoursesFragment extends Fragment
{
    private RecyclerView recyclerView;
    private List<Courses> courses;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        initializeData();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_courses, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_courses);

        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llManager);

        RVCoursesAdapter adapter = new RVCoursesAdapter(courses);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initializeData()
    {
        courses = new ArrayList<>();
        courses.add(new Courses(getString(R.string.course_java), "", R.drawable.course_java));
        courses.add(new Courses(getString(R.string.course_layout), "", R.drawable.course_layout));
        courses.add(new Courses(getString(R.string.course_php), "", R.drawable.course_php));
        courses.add(new Courses(getString(R.string.course_java_script), "", R.drawable.course_javascript));
        courses.add(new Courses(getString(R.string.course_photoshop), "", R.drawable.course_photoshop));
        courses.add(new Courses(getString(R.string.course_seo), "", R.drawable.course_seo));
        courses.add(new Courses(getString(R.string.course_wordpress), "", R.drawable.course_wordpress));
    }
}

class RVCoursesAdapter extends RecyclerView.Adapter<RVCoursesAdapter.CoursesViewHolder>
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

class Courses
{
    private String name;
    private String about;
    private int pictureId;

    public Courses(String name, String about, int pictureId)
    {
        this.name = name;
        this.about = about;
        this.pictureId = pictureId;
    }

    public String getName()
    {
        return name;
    }

    public String getAbout()
    {
        return about;
    }

    public int getPictureId()
    {
        return pictureId;
    }
}
