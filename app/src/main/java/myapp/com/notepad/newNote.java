package myapp.com.notepad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ankit on 9/4/17.
 */

public class newNote extends Activity {

    private DBHelper mydb ;

    TextView fileName;
    TextView message;
    int id_to_update=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);
        fileName = (TextView) findViewById(R.id.fileName);
        message = (TextView) findViewById(R.id.note);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            int Value = extras.getInt("id");

            if (Value>0) {
                Cursor rs = mydb.getData(Value);
                id_to_update = Value;
                rs.moveToFirst();

                String titl = rs.getString(rs.getColumnIndex(DBHelper.NOTEPAD_COLUMN_TITLE));
                String mssg = rs.getString(rs.getColumnIndex(DBHelper.NOTEPAD_COLUMN_MSG));

                if (!rs.isClosed()) {
                    rs.close();
                }
            }
        }

    }

    public void save(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            int Value = extras.getInt("id");
            if (Value>0){
                if (mydb.updateNotes(id_to_update,fileName.getText().toString(),message.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"Not Updated",Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (mydb.insertNotes(fileName.getText().toString(),message.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Not Saved",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
