package com.nepalicoders.interactivenotifyproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ServiceControlActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_control);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ServiceControlFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.service_control, menu);
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
    public static class ServiceControlFragment extends Fragment {

        public ServiceControlFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_service_control, container, false);

            setupButtons(rootView);
            return rootView;
        }

        void setupButtons(View rootView) {
            rootView.findViewById(R.id.btnStopService).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStopServiceOnClick((Button) v);
                }
            });
        }

        private void btnStopServiceOnClick(Button v) {
//            Intent intent = new Intent(getActivity(), SimpleKittyService.class);
//            intent.setAction(SimpleKittyService.STOP_ACTION);

            Intent intent = SimpleKittyService.getStopIntent(getActivity());

            getActivity().startService(intent);
        }

    }

}
