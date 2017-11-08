package tecnology.now.miniappgoals;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alan on 07/11/2017.
 */

public class GoalItem extends RealmObject {


    private String title;
    private String fundamentals;
    private String type;
    private long repeat;
    private Date finalDate;
    private float totalDays;
    private float progress;

    @PrimaryKey
    private String id;

    public GoalItem(){}

     public GoalItem( String title, String fundamentals, String type, long repeat, Date finalDate, float totalDays, float progress) {
        this.title = title;
        this.fundamentals = fundamentals;
        this.type = type;
        this.repeat = repeat;
        this.finalDate = finalDate;
        this.totalDays = totalDays;
        this.progress = progress;
        this.id =  UUID.randomUUID().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFundamentals() {
        return fundamentals;
    }

    public void setFundamentals(String fundamentals) {
        this.fundamentals = fundamentals;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRepeat() {
        return repeat;
    }

    public void setRepeat(long repeat) {
        this.repeat = repeat;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public float getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(float totalDays) {
        this.totalDays = totalDays;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
