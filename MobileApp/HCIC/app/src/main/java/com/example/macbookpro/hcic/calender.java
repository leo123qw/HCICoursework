package com.example.macbookpro.hcic;

import android.os.Bundle;

public class calender extends MainActivity {
//    CompactCalendarView compactCalendarView;
//    private SimpleDateFormat dateFormatMonth =new SimpleDateFormat("MMMM -yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

//        final ActionBar actionBar =getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setTitle(null);
//        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
//        compactCalendarView.setUseThreeLetterAbbreviation(true);

//        //set an event for Draw
//        Event event = new Event(Color.GREEN, 1543253873L, "played Draw for three mins");
//        compactCalendarView.addEvent(event);
//
//        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
//            @Override
//            public void onDayClick(Date dateClicked) {
//                Context context = getApplicationContext();
//                if (dateClicked.toString().compareTo("Mon Nov 26 09:00:00 AST 2018") == 0){
//                    Toast.makeText(context,"Play draw game",Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(context,"dont play any games", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onMonthScroll(Date firstDayOfNewMonth) {
//                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
//            }
//        });
    }
}
