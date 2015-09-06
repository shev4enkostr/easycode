package su.shev4enkostr.easycode;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
public class CoursesFragment extends Fragment{
    private RecyclerView recyclerView;
    private List<Courses> courses;

    private final static String URL_JAVA = "http://easycode.com.ua/java.html";
    private final static String URL_LAYOUT = "http://easycode.com.ua/css.html";
    private final static String URL_PHP = "http://easycode.com.ua/php.html";
    private final static String URL_JAVA_SCRIPT = "http://easycode.com.ua/#";
    private final static String URL_PHOTOSHOP = "http://easycode.com.ua/#";
    private final static String URL_SEO = "http://easycode.com.ua/#";
    private final static String URL_WORDPRESS = "http://easycode.com.ua/#";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initializeData();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_courses);

        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llManager);

        RVCoursesAdapter adapter = new RVCoursesAdapter(courses);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initializeData() {
        courses = new ArrayList<>();
        courses.add(new Courses(getString(R.string.course_java), R.drawable.course_java, URL_JAVA));
        courses.add(new Courses(getString(R.string.course_layout), R.drawable.course_layout, URL_LAYOUT));
        courses.add(new Courses(getString(R.string.course_php), R.drawable.course_php, URL_PHP));
        courses.add(new Courses(getString(R.string.course_java_script), R.drawable.course_javascript, URL_JAVA_SCRIPT));
        courses.add(new Courses(getString(R.string.course_photoshop), R.drawable.course_photoshop, URL_PHOTOSHOP));
        courses.add(new Courses(getString(R.string.course_seo), R.drawable.course_seo, URL_SEO));
        courses.add(new Courses(getString(R.string.course_wordpress), R.drawable.course_wordpress, URL_WORDPRESS));
    }

    public static class RVCoursesAdapter extends RecyclerView.Adapter<RVCoursesAdapter.CoursesViewHolder> {
        public static class CoursesViewHolder extends RecyclerView.ViewHolder {

            private View itemView;

            CardView cvCourses;
            TextView coursesName;
            ImageView coursesPicture;

            CoursesViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                cvCourses = (CardView) itemView.findViewById(R.id.cv_courses);
                coursesName = (TextView) itemView.findViewById(R.id.courses_name);
                coursesPicture = (ImageView) itemView.findViewById(R.id.courses_picture);
            }
        }

        List<Courses> courses;

        RVCoursesAdapter(List<Courses> courses) {
            this.courses = courses;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public CoursesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_courses, viewGroup, false);
            CoursesViewHolder pvh = new CoursesViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(final CoursesViewHolder personViewHolder, final int i) {
            personViewHolder.coursesName.setText(courses.get(i).getName());
            personViewHolder.coursesPicture.setImageResource(courses.get(i).getPictureId());

            personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // starting CourseAboutActivity
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CourseAboutActivity.class);
                    intent.putExtra(CourseAboutActivity.ARG_URL, courses.get(i).getUrl());
                    intent.putExtra(CourseAboutActivity.ARG_TITLE, courses.get(i).getName());
                    intent.putExtra(CourseAboutActivity.ARG_PICTURE_ID, courses.get(i).getPictureId());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }
    }

    class Courses {
        private String name;
        private int pictureId;
        private String url;

        public Courses(String name, int pictureId, String url) {
            this.name = name;
            this.pictureId = pictureId;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public int getPictureId() {
            return pictureId;
        }

        public String getUrl() {
            return url;
        }
    }
}
