package tecnology.now.miniappgoals;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class CreatGoalActivity extends AppCompatActivity implements View.OnClickListener, RealmChangeListener {

    private Calendar pickerCalendar;
    private DatePickerDialog.OnDateSetListener datePicker;
    private String typeSelected;
   // private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_goal);

        datePickerConfig();
        spinnerConfig();

        Button btnSave = (Button) findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(this);
    }

    private  void  datePickerConfig(){

        pickerCalendar = Calendar.getInstance();
        EditText dateText = (EditText) findViewById(R.id.dateText) ;
        dateText.setShowSoftInputOnFocus(false);

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                pickerCalendar.set(Calendar.YEAR, year);
                pickerCalendar.set(Calendar.MONTH, monthOfYear);
                pickerCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        dateText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(CreatGoalActivity.this, datePicker, pickerCalendar.get(Calendar.YEAR),
                            pickerCalendar.get(Calendar.MONTH), pickerCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
        EditText calendarPicker = (EditText) findViewById(R.id.dateText) ;
        calendarPicker.setText(sdf.format(pickerCalendar.getTime()));
    }

    private  void  spinnerConfig(){
        Spinner spinner = (Spinner) findViewById(R.id.typesSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_goal,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

               String[] types = getResources().getStringArray(R.array.types_goal);
               String spinnerType = types[pos];

               if(types[0].equals(spinnerType)){
                   typeSelected = "FEEDING";
               }
               else if(types[1].equals(spinnerType)){
                   typeSelected = "WORKOUT";
               }
               else if(types[2].equals(spinnerType)){
                   typeSelected = "SLEEP";
               }
               else if(types[3].equals(spinnerType)){
                   typeSelected = "STRESS";
               }

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getFormData(){

        String title = String.valueOf(((EditText)findViewById(R.id.titleText)).getText());
        String fundamentals = String.valueOf(((EditText)findViewById(R.id.fundamentalsText)).getText());
        int repeat = Integer.parseInt(((EditText)findViewById(R.id.repeatText)).getText().toString());
        Date initialDate = Calendar.getInstance().getTime();
        Date finalDate = pickerCalendar.getTime();
        long difference = finalDate.getTime() - initialDate.getTime();
        float totalDays = (difference / (1000*60*60*24));
        String id =  UUID.randomUUID().toString();


        Realm realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);
        realm.beginTransaction();

        GoalItem temp = new GoalItem(title,fundamentals,typeSelected,repeat,finalDate,totalDays,0);
       /* GoalItem newItem = realm.createObject(GoalItem.class, id);

        newItem.setTitle(title);
        newItem.setFundamentals(fundamentals);
        newItem.setType(typeSelected);
        newItem.setRepeat(repeat);
        newItem.setFinalDate(finalDate);
        newItem.setTotalDays(totalDays);
        newItem.setProgress(0);*/

        GoalItem realmGoal  = realm.copyToRealm(temp);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void onClick(View view) {
        try{

            getFormData();

            goHome();
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(CreatGoalActivity.this, "Falhou!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // realm.removeChangeListener(this);

    }

    public void goHome(){
        Intent i = new Intent(CreatGoalActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onChange(Object o) {

    }
}
