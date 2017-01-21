package com.example.vicky.paul;



import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.app.assist.AssistStructure;
import android.os.Build;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener ,GestureDetector.OnDoubleTapListener{
   ListView listView;
    SubjectAdapter adapter;
    ArrayList<Data> list;
    Myhelperadapter helper;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new Myhelperadapter(this);
        list = helper.getAllData();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new SubjectAdapter(MainActivity.this, list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) this);
        listView.setOnItemLongClickListener(this);
       int i= list.size();


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, " item selected", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MainActivity.this, StudentSection.class);
                    startActivity(i);

                }
            });


    }








    public void close(View v) {
        finish();
        System.exit(0);
    }




    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
      k++;


        final String[] array = {"Edit class Info", "Edit Attendance", "Delete class"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, array);
        final int id1 = position;
        final Data data = helper.getData(position);
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                if (index == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setTitle("Edit Class details");
                    LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view_Et = inflater.inflate(R.layout.edit_class, null);
                    final EditText editText = (EditText) view_Et.findViewById(R.id.etclassname);
                    editText.setText(data.name);
                    editText.setHeight(40);
                    editText.setSelection(data.name.length());
                    builder1.setView(view_Et);
                    builder1.setPositiveButton("update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            String str = editText.getEditableText().toString();
                            adapter.update(id1, str, null, null, 0);// checking
                            listView.setAdapter(adapter);
                            dialog.dismiss();


                        }
                    });
                    builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    final AlertDialog dialog1 = builder1.create();
                    dialog1.show();
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (editable.toString().length() == 0) {
                                dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            } else {
                                dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                            }
                        }
                    });

                } else if (index == 1) {
                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    try{
                        builder.setTitle("Change Attendance");
                        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view_et = inflater.inflate(R.layout.modifyattendance, null);
                        final EditText et_a = (EditText) view_et.findViewById(R.id.et_a);
                        final EditText et_t = (EditText) view_et .findViewById(R.id.et_t);
                        et_a.setText(String.valueOf(data.a));
                        et_t.setText(String.valueOf(data.t));
                        builder.setView(view_et);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                int a = Integer.parseInt(et_a.getText().toString());
                                int t = Integer.parseInt(et_t.getText().toString());
                                if(a>t){
                                    Toast toast=Toast.makeText(getApplicationContext(), "Attendance cannot be greater then Total Attendance", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }else{
                                    adapter.update(id1,a,t,null);
                                    listView.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int id) {dialog.dismiss();}});
                        final AlertDialog dialog1=builder.create();
                        dialog1.show();
                        et_a.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (editable.toString().length() == 0) {
                                    dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                } else {
                                    dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                }
                            }
                        });
                        et_t.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (editable.toString().length() == 0) {
                                    dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                } else {
                                    dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                }
                            }
                        });
                    }catch(Exception e){Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_SHORT).show();}

                    */
                }
                else {
                      Data d= list.get(id1);
                            String s= d.name;



                    adapter.removeOne(s,id1);
                    listView.setAdapter(adapter);

                }
            }
        });
        builder.show();

        return true;
    }



    @TargetApi(Build.VERSION_CODES.M)
    public void showAlertBox(View view) {
        final Dialog d = new Dialog(MainActivity.this);
        d.setTitle("Class Info");
        d.setContentView(R.layout.edit_class);
        final EditText classname = (EditText) d.findViewById(R.id.etclassname);
        final EditText classtype = (EditText) d.findViewById(R.id.etclassname);
        final DatePicker dp= (DatePicker) d.findViewById(R.id.datePicker);
        final TimePicker tp=(TimePicker)d.findViewById(R.id.timePicker);
        dp.setCalendarViewShown(false);
        final Button btnadd = (Button) d.findViewById(R.id.btnadd);
        Button btncancel = (Button) d.findViewById(R.id.btncancel);
        btnadd.setEnabled(false);



        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = classname.getEditableText().toString();
                int day=dp.getDayOfMonth();
                int m= dp.getMonth();
                int ye=dp.getYear();
                m++;
                String S= day+":"+m+":"+ye;
                int t=tp.getCurrentHour();
              int min =tp.getCurrentMinute();

                tp.setIs24HourView(true);


                String am_pm =null;
                if(tp.getCurrentHour()>12)
                    am_pm="PM";
                else
                am_pm="AM";
                String time= t+":"+min+":" + am_pm;
                Data data = new Data(str, str,S, time);
                adapter.add(data);
                listView.setAdapter(adapter);
                d.cancel();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                d.cancel();
            }
        });
        classname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0) {
                    btnadd.setEnabled(false);
                } else {
                    btnadd.setEnabled(true);
                }
            }
        });
  d.show();

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {


        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


    //  if (editable.toString().length() ==0 || classtypr.length() == 0)


    class SubjectAdapter extends BaseAdapter {

        Context context;
        ArrayList<Data> list;

        public SubjectAdapter(Context context, ArrayList<Data> list) {
            this.context = context;
            this.list = list;
        }

        public void update(int id, String classname, String classtype, String date, int time) {
           if (classname != null) {
                list.get(id).name = classname;
        }
            list.get(id).date = date;
            list.get(id).type = classtype;
            helper.update(id, classname, classtype, date, time);
     }

        public void add(Data data) {
            list.add(data);
            helper.storeData(data);
        }

        public void removeOne(String name,int id) {
            list.remove(id);
            helper.remove(name); // problem here delete single row
        }

        public void removeAll() {
            list.clear();
            helper.removeAll();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position,View convertView, ViewGroup parent) {
            View row = null;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.class_row, null);
            }
            final Data data = list.get(position);

            TextView classsnmae = (TextView) row.findViewById(R.id.tv_class_name);
            TextView classtype = (TextView) row.findViewById(R.id.tvclasstype);
            TextView date = (TextView) row.findViewById(R.id.tvstartdate);
            TextView time = (TextView) row.findViewById(R.id.tvTime);





            classsnmae.setText(data.name + " ");
           classtype.setText(data.type  + "");

            date.setText(data.date + "");

            time.setText(data.time + "");
            // classsnmae.setText(data.name+"");
         //   classtype.setText(data.type+ " ");

            return row;
        }
    }


}





class Data {


    String name;
    String type;
    String time;
    String date;


    public Data(String name, String type, String date, String time) {

        this.type = type;  // replace t with date started
        this.name = name;
        this.date = date;
        this.time = time;


    }

}


class stud {

    String name;
    String mobile;
    boolean fee;

    public stud(String name,String mobile ,boolean fee) {


        this.fee = fee;
        this.mobile = mobile;
        this.name = name;

    }
}


