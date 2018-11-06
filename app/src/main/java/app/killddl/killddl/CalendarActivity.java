package app.killddl.killddl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;


public class CalendarActivity extends AppCompatActivity {
    User user;
    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    ScrollView calendarTasks;
    int tasksViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        FirebaseUser currUser = MainActivity.getAuth().getCurrentUser();
        Timestamp tsp = Timestamp.now();


        calendarTasks = (ScrollView) findViewById(R.id.calendar_tasks);
        calendarTasks.removeAllViews();
        calendarTasks.addView(displayTaskList(tsp));
        mCalendarView = (CalendarView) findViewById(R.id.calendar_calendarview);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String date = (month+1) + "/" + (day) + "/" + year;
                Calendar calendar = new GregorianCalendar(year,month,day);
                Timestamp tsp = new Timestamp(calendar.getTime());
                calendarTasks.removeAllViews();
                calendarTasks.addView(displayTaskList(tsp));
                //ll.addView(displayTaskList(tasksList));
                //example tasks TODO need adding vector of tasks
                //java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0");
                //Tasks example = new Tasks(1,timestamp);
                //example.EditName("Hello");


                //Dynamically display task in that day
                //ll.addView(addTaskView(example));

            }
        });
        //Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_menu:
                        Intent menuIntent = new Intent(getApplicationContext(),MenuActivity.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.action_calendar:
                        break;
                    case R.id.action_profile:
                        Intent profileIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(profileIntent);
                        break;
                }
            }
        });


    }
    LinearLayout displayTaskList(Timestamp tsp){

        List<Tasks> tasksList = MainActivity.getDatabase().getTaskListByTime(tsp);
        System.out.println("REQUIRED DATE IS: " + tsp.toDate());
        LinearLayout rl = new LinearLayout(this);
        tasksViewId = View.generateViewId();
        rl.setId(tasksViewId);
        rl.setOrientation(LinearLayout.VERTICAL);
        for(int i=0; i<tasksList.size(); i++){
            if(!tasksList.get(i).isFinished) {
                //create new View
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);
                float density = this.getResources().getDisplayMetrics().density;
                int paddingPixel = (int) (30 * density);
                ll.setPadding(paddingPixel, 0, 0, 0);
                //add color

                final int id = tasksList.get(i).getId();
                TextView tx = new TextView(this);
                tx.setText(tasksList.get(i).getName());
                tx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickTask(view, id);
                    }
                });
                ll.addView(tx);
                rl.addView(ll);

            }

            //add days_remaining
        }
        return rl;

    }

    LinearLayout addTaskView(Tasks task) {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ImageView circle = new ImageView(this);
        circle.setMaxWidth(30);
        circle.setMaxHeight(30);
        circle.setImageResource(R.drawable.ic_person_black_24dp);
        ll.addView(circle);

        TextView taskName = new TextView(this);
        //taskName.setText(task.name);
        ll.addView(taskName);

        return ll;
    }

    public void AddTask(View v){
        Intent addTask = new Intent(getApplicationContext(),AddTaskActivity.class);
        //TODO add extra info
        startActivity(addTask);
    }
    public void clickTask(View v, int id){
        Intent newIntent = new Intent(getApplicationContext(), EditTaskActivity.class);
        newIntent.putExtra("edit_taskId",id);
        startActivity(newIntent);
    }
}




