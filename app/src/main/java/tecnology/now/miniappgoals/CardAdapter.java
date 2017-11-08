package tecnology.now.miniappgoals;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup.LayoutParams;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Alan on 07/11/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final Context context;
    private final List<GoalItem> elements;

    public CardAdapter(final List<GoalItem> elements, Context context) {
        this.context = context;
        this.elements = elements;

        this.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position, View btn) {
                float progress = (elements.get(position).getProgress() + 1);
                float newValue = progress - elements.get(position).getTotalDays();
                if(newValue == 0){
                    btn.setVisibility(View.INVISIBLE);
                }
                elements.get(position).setProgress(progress);
                Realm realm  = Realm.getDefaultInstance();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(elements.get(position));

                realm.commitTransaction();
                realm.close();

                notifyDataSetChanged();
            }
        });
    }

    private static ItemClickListener itemClickListener;
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_goal, parent, false);

        ViewHolder holder = new CardAdapter.ViewHolder(view, parent.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GoalItem item = elements.get(position);
        View card = holder.goalCard;

        TextView title = (TextView) card.findViewById(R.id.card_title);
        TextView date = (TextView) card.findViewById(R.id.card_finalDate);
        TextView fundamentals = (TextView) card.findViewById(R.id.card_fundamentals);
        TextView percent = (TextView) card.findViewById(R.id.card_percent);
        ProgressBar progress = (ProgressBar)card.findViewById(R.id.card_progress);

        title.setText(item.getTitle());
        fundamentals.setText(item.getFundamentals());

        String formatedDate = new SimpleDateFormat("dd MMM yyyy").format(item.getFinalDate());
        date.setText(formatedDate);

        float value = ((item.getProgress() *100) / item.getTotalDays()) ;
        percent.setText(value+"%");
        progress.setProgress((int) value);
        if(value == 100){
            ((View)card.findViewById(R.id.card_btnDone)).setVisibility(View.INVISIBLE);
        }

        String type = item.getType();
        FrameLayout shape = (FrameLayout)card.findViewById(R.id.card_type);

        switch (type){
            case "FEEDING":
                shape.setBackground(ContextCompat.getDrawable(context,R.drawable.feeding_icon));
                break;
            case "WORKOUT":
                shape.setBackground(ContextCompat.getDrawable(context,R.drawable.workout_icon));
                break;
            case "SLEEP":
                shape.setBackground(ContextCompat.getDrawable(context,R.drawable.sleep_icon));
                break;
            case "STRESS":
                shape.setBackground(ContextCompat.getDrawable(context,R.drawable.stress_icon));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return elements.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public View goalCard;
        Context context;

        public ViewHolder(View v, Context context) {
            super(v);
            goalCard = v;
            this.context = context;
            Button doneBtn = (Button) v.findViewById(R.id.card_btnDone);
            Button expandBtn = (Button) v.findViewById(R.id.card_btnExpand);
            doneBtn.setOnClickListener(this);
            expandBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.card_btnDone){

                if(itemClickListener != null) {
                    itemClickListener.onItemClick(getAdapterPosition(), view);
                }

            }
            else if(view.getId() == R.id.card_btnExpand){
                View cardView = (View) view.getParent().getParent().getParent();
                LinearLayout colapsePanel = (LinearLayout)cardView.findViewById(R.id.card_colapsePanel);

                ViewGroup.LayoutParams params = colapsePanel.getLayoutParams();
                if(colapsePanel.getVisibility() == View.INVISIBLE){
                    params.height = LayoutParams.WRAP_CONTENT;
                    colapsePanel.setVisibility(View.VISIBLE);
                    view.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_keyboard_arrow_up_black_24dp));
                }
                else{
                    params.height = 0;
                    colapsePanel.setVisibility(View.INVISIBLE);
                    view.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_keyboard_arrow_down_black_24dp));
                }
                colapsePanel.setLayoutParams(params);

            }
        }
    }

    public interface ItemClickListener {

        void onItemClick(int position, View btn);
    }

}
