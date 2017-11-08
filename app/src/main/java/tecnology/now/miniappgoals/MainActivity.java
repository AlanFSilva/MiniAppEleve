package tecnology.now.miniappgoals;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<GoalItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAndPopulateMenu();

        RecyclerView cardsView = findViewById(R.id.recycler_view);
        cardsView.setItemAnimator(new DefaultItemAnimator());
        cardsView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter cardAdapter = new CardAdapter(items, this);
        cardsView.setAdapter(cardAdapter);

        FloatingActionButton addGoal = (FloatingActionButton) findViewById(R.id.addGoal);
        addGoal.setOnClickListener(this);

    }

    private void  createAndPopulateMenu(){
       // items = new ArrayList<GoalItem>();
        Realm realm = Realm.getDefaultInstance();

        try{
            RealmResults<GoalItem> realmItems = realm.where(GoalItem.class).findAll();
            items = realm.copyFromRealm(realmItems);
            realm.close();
        }
        catch (Exception e){
            realm.deleteAll();
        }


    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.addGoal){
            Intent intent = new Intent(MainActivity.this, CreatGoalActivity.class);
            startActivity(intent);
        }

    }
}
