package su.shev4enkostr.easycode;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by stas on 15.09.15.
 */
public abstract class CustomAppCompatActivity extends AppCompatActivity
{
    // fix crash when open the NavigationDrawer (on MainActivity) and click on the CollapsingToolbar (on CourseAboutActivity)
    protected void initializeCoordinatorLayout()
    {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        coordinatorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return super.onTouchEvent(event);
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        }
        catch (Exception e){
            return false;
        }
    }
}
