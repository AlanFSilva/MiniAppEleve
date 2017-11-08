package tecnology.now.miniappgoals;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alan on 07/11/2017.
 */

public class GoalItem2 extends RealmObject {

    private String title;


    @PrimaryKey
    private long id;

    public GoalItem2(){}

    public GoalItem2(String titles) {
        this.id = setId();
        this.title = title;

    }

    public long getId() {
        return id;
    }

    private long setId(){
        long id = 0;
        Realm realm = Realm.getDefaultInstance();
        try {
            id = realm.where(GoalItem2.class).max("id").longValue() + 1;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
