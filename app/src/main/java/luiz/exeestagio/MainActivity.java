package luiz.exeestagio;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    protected JSONObject inFile;
    protected TableLayout users;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users = (TableLayout) findViewById(R.id.Users);
    }

    public void newFile (View v) {
        JSONArray data;
        JSONParser parser = new JSONParser();
        String id, name, pwd;
        TextView ID, Name, Pwd;
        EditText FileName = (EditText) findViewById(R.id.FileName);

        Log.d(TAG, "button pressed");

        try {
            AssetManager assetManager = getAssets();
            InputStream reader = assetManager.open(FileName.getText().toString());
            BufferedReader buffer = new BufferedReader(new InputStreamReader(reader));
            JsonReader JReader = new JsonReader(buffer);

            inFile = new JSONObject(JReader.toString());
            data = inFile.getJSONArray("data");

            FileName.setText("");
            for (int i = 0; i < data.length(); i++) {
                TableRow user = new TableRow(this);
                id = data.getJSONObject(i).getString("id");
                name = data.getJSONObject(i).getString("name");
                pwd = data.getJSONObject(i).getString("pwd");

                Log.d(TAG, "passou");

                ID = new TextView(this);
                Name = new TextView(this);
                Pwd = new TextView(this);

                ID.setText("ID: " + id);
                Name.setText("Name: " + name);
                Pwd.setText("PWD: " + pwd);

                user.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                user.addView(ID);
                user.addView(Name);
                user.addView(Pwd);

                user.addView(user);

            }
        }catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
