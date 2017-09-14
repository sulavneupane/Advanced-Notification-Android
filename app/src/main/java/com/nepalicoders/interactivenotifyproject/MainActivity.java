package com.nepalicoders.interactivenotifyproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MainFragment extends Fragment {
        public final static String ACTION_DISPLAY_COURSE_FROM_NOTIFICATION = "com.nepalicoders.action.DISPLAY_COURSE_FROM_NOTIFICATION";
        public final static String COURSE_INDEX = "course index";

        private final static int NOTIFY_ID = 1;


        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            setupButtons(rootView);
            return rootView;
        }

        void setupButtons(View rootView) {
            rootView.findViewById(R.id.btnCourseNotification).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnCourseNotficationOnClick((Button) v);
                }
            });
            rootView.findViewById(R.id.btnStartService).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStartServiceOnClick((Button) v);
                }
            });
            rootView.findViewById(R.id.btnCustomNotify).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnCustomNotifyOnClick((Button) v);
                }
            });
        }

        private void btnCourseNotficationOnClick(Button v) {
            Context context = getActivity();

            Intent intent = new Intent(ACTION_DISPLAY_COURSE_FROM_NOTIFICATION);
            intent.putExtra(COURSE_INDEX, 0); // display the first course

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(intent);

            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.ic_stat_notify_kitty_round)
                    .setAutoCancel(true)
                    .setContentTitle("New Video")
                    .setContentText("Android Fragments Course Updated")
                    .setContentIntent(pendingIntent);
            Notification notification = builder.build();

            NotificationManager nm = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            nm.notify(NOTIFY_ID, notification);
        }

        private void btnStartServiceOnClick(Button v) {
            Context context = getActivity();

            Intent intent = new Intent(context, SimpleKittyService.class);
            intent.setAction(SimpleKittyService.START_ACTION);
            context.startService(intent);
        }

        private void btnCustomNotifyOnClick(Button v) {
            Context context = getActivity();

            RemoteViews notificationViews = new RemoteViews(context.getPackageName(), R.layout.notification_simple);

            Intent stopIntent = SimpleKittyService.getStopIntent(context);
            PendingIntent stopPendingIntent = PendingIntent.getService(context, 0, stopIntent, 0);
            notificationViews.setOnClickPendingIntent(R.id.btnStop, stopPendingIntent);

            Intent startIntent = SimpleKittyService.getStartIntent(context);
            PendingIntent startPendingIntent = PendingIntent.getService(context, 0, startIntent, 0);
            notificationViews.setOnClickPendingIntent(R.id.btnStart, startPendingIntent);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setSmallIcon(R.drawable.ic_stat_notify_kitty_round)
                    .setContent(notificationViews);

            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFY_ID, notification);
        }


    }

}
