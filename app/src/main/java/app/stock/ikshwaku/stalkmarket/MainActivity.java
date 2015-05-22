package app.stock.ikshwaku.stalkmarket;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

           /* SearchView search;
            RelativeLayout r1;
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            search = new SearchView(MainActivity.this);
            r1 = (RelativeLayout) findViewById(R.id.r1);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 30, 0, 0);
            search.setQueryHint("SearchView");
            search.setLayoutParams(params);
            r1.addView(search);

            //***setOnQueryTextFocusChangeListener***
            search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub

                    Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
                            Toast.LENGTH_SHORT).show();
                }
            });

            //***setOnQueryTextListener***
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    // TODO Auto-generated method stub

                    Toast.makeText(getBaseContext(), query,
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // TODO Auto-generated method stub

                    Toast.makeText(getBaseContext(), newText,Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            });
*/
            return rootView;
        }
    }
}
