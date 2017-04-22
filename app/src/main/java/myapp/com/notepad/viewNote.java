package myapp.com.notepad;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ankit on 16/4/17.
 */

public class viewNote extends AppCompatActivity {

    TextView t;
    TextView m;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note);

        t = (TextView)findViewById(R.id.viewTitle);
        m = (TextView)findViewById(R.id.viewMessage);
        MainActivity act = new MainActivity();
        DBHelper mydb = new DBHelper(this);

        String i = act.clickedItemPosition;
        int position = act.pos;

        String tit = i;
        ArrayList<String> msgList = mydb.getAllMsg();
        String mm = msgList.get(position);

        t.setText(tit);
        m.setText(mm);
    }
}
