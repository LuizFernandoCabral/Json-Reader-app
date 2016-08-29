package luiz.exeestagio;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    protected JSONObject inFile;
    protected LinearLayout users;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users = (LinearLayout) findViewById(R.id.Users);
    }

    public void newFile (View v) {
        JSONArray data;
        String id, name, pwd, json;
        TextView ID, Name, Pwd;
        EditText FileName = (EditText) findViewById(R.id.FileName);

        Log.d(TAG, "button pressed");

        try {
            AssetManager assetManager = getAssets();
            InputStream reader = assetManager.open(FileName.getText().toString());
            byte[] buffer = new byte[reader.available()];
            reader.read(buffer);
            reader.close();
            json = new String(buffer, "UTF-8");

            Log.d(TAG, json);

            //inFile = JReader.readObject();

            inFile = new JSONObject(json);
            data = inFile.getJSONArray("data");

            FileName.setText("");
            for (int i = 0; i < data.length(); i++) {
                LinearLayout user = new LinearLayout(this);
                id = "ID: ";
                name = "Name: ";
                pwd = "PWD: ";
                id += data.getJSONObject(i).getString("id");
                name += data.getJSONObject(i).getString("name");
                pwd += data.getJSONObject(i).getString("pwd");

                id += "\n";
                name += "\n";
                pwd += "\n";

                ID = new TextView(this);
                Name = new TextView(this);
                Pwd = new TextView(this);

                ID.setText(id);
                Name.setText(name);
                Pwd.setText(pwd);

                user.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                user.setMinimumHeight(100);
                user.addView(ID);
                user.addView(Name);
                user.addView(Pwd);

                users.addView(user);
            }
        }catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
