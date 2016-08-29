package luiz.exeestagio;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The main and only activity for this app
 * @author Luiz
 */
public class MainActivity extends AppCompatActivity {

    protected JSONObject inFile;
    protected ListView users;
    protected ArrayAdapter<String> usersAdapter;
    EditText FileName;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> usersList = new ArrayList<>();
        FileName = (EditText) findViewById(R.id.FileName);
        users = (ListView) findViewById(R.id.Users);

        FileName.setHint("Enter name of file");
        usersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);
        users.setAdapter(usersAdapter);
    }

    /**
     * Method that opens, reads, and process file input (json file) after button clicked
     * @param v the View
     */
    public void newFile (View v) {
        JSONArray data;
        String content, json;
        TextView text;

        Log.d(TAG, "button pressed");

        try {
            // Open and read whole file
            AssetManager assetManager = getAssets();
            InputStream reader = assetManager.open(FileName.getText().toString());
            byte[] buffer = new byte[reader.available()];
            reader.read(buffer);
            reader.close();

            // Parse file to string
            json = new String(buffer, "UTF-8");

            inFile = new JSONObject(json);// Parse String to json
            data = inFile.getJSONArray("data");// Get array from json

            FileName.setText("");// Clear input field

            processJson(data);

        }catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    /**
     * Method get content from json file and set it to the listView
     * @param data the json array
     */
    private void processJson(JSONArray data) {
        String str = "";
        for (int i = 0; i < data.length(); i++) {
            try {
                str = createString(data.getJSONObject(i).getString("id"), data.getJSONObject(i).getString("name"),
                        data.getJSONObject(i).getString("pwd"));
                usersAdapter.add(str);
            } catch (JSONException e){
                Log.d(TAG, e.getMessage());
            }
        }
    }

    /**
     * Method to create the string to be displayed for each item in listView
     * @param id String containing with value for json.key id
     * @param name String containing with value for json.key name
     * @param pwd String containing with value for json.key pwd
     * @return the formed string
     */
    private String createString (String id, String name, String pwd) {
        String str;

        str = "\nID: " + id + "\n\n";;
        str += "Name: " + name + "\n\n";
        str += "PWD: " + pwd + "\n";

        return str;
    }
}
