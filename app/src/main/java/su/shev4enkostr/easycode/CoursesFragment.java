package su.shev4enkostr.easycode;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
