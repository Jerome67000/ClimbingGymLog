package fr.jerome.climbinggymlog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

import fr.jerome.climbinggymlog.database.ClientDB;
import fr.jerome.climbinggymlog.model.Client;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClientDB clientDB = new ClientDB(this);

        Client client1 = new Client(0, "GULLY", "Jérome", 27, 20484851, new Date(), 0);
        Client client2 = new Client(1, "TAT2", "TAT", 27, 80484851, new Date(), 0);
        Client client3 = new Client(2, "CAM", "CAM", 28, 90484851, new Date(), 0);

        clientDB.insert(client1);
        clientDB.insert(client2);
        clientDB.insert(client3);

        clientDB.delete(client2);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
