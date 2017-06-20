package interval.com.intervalapp.activity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import interval.com.intervalapp.R;
import interval.com.intervalapp.database.RealmSongsDataBase;
import interval.com.intervalapp.model.Song;

/**
 * Created by m.losK on 19.05.2017.
 */

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static int REQUEST_PICK = 2;
    private String name;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);
        ButterKnife.bind(this);
        initToolbar();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @OnClick(R.id.fab)
    protected void buttonClicked(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_music) {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("audio/*");
            chooseFile.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(chooseFile, "Choose a file"), REQUEST_PICK);

            Toast.makeText(getApplicationContext(), "picker", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), SongDragAndDropActivity.class));
            Toast.makeText(getApplicationContext(), "drag and drop", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_settings) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_PICK) {

            if (resultCode == RESULT_OK) {
                List<Song> model = new ArrayList<>();
                RealmSongsDataBase list = new RealmSongsDataBase();
                Uri uri = data.getData();
                if (uri != null) {
                    String tittle = getMP3Id(uri);
                    model.add(new Song(tittle, uri.toString()));


                } else {
                    int count = data.getClipData().getItemCount();

                    for (int x = 0; x < count; x++) {
                        ClipData.Item item = data.getClipData().getItemAt(x);
                        String tittle = getMP3Id(item.getUri());
                        model.add(new Song(tittle, item.getUri().toString()));


                    }

                }
                list.saveSongs(model);
                Intent intent = new Intent(this, SongDragAndDropActivity.class);
                startActivity(intent);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public String getMP3Id(Uri uri) {


        Cursor c = getContentResolver().query(
                uri,

                null,
                null,
                null,
                "");

        if (null == c) {
            // ERROR
        }

        while (c.moveToNext()) {
            name = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
        }
        return name;
    }
}