package com.example.flipr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

import javax.mail.internet.InternetAddress;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;

import static javax.mail.Message.*;

public class EmailActivity extends AppCompatActivity {
    DataBaseHelper openhelper;
    EditText receiverMail,_subject,_message,_cc;
    Button sendMail;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
     //   getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        openhelper = new DataBaseHelper(this);
        receiverMail = findViewById(R.id.residentEmail);
        _subject = findViewById(R.id.subject);
        _message = findViewById(R.id.message);
        radioGroup = (RadioGroup)findViewById(R.id.recurring);
        _cc = findViewById(R.id.cc);
        sendMail = findViewById(R.id.buttonSend);

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String senderMAil = "heytherehowareu12@gmail.com";
                final String password = "1@Kkokay";
                String messageToSend = _message.getText().toString();
                Properties props = new Properties();
                props.put("mail.smtp.auth","true");
                props.put("mail.smtp.starttls.enable","true");
                props.put("mail.smtp.host","stmp.gmail.com");
                props.put("mail.smtp.port","587");
                Session session = Session.getInstance(props,
                        new Authenticator(){
                            protected PasswordAuthentication getPasswordAuthuntication(){
                                return new PasswordAuthentication(senderMAil,password);
                            }
                        });
                LoginActivity l = new LoginActivity();
                String sm = l.getEmail();
                switch (rec){
                    case "None":
                        setSendMail(session,senderMAil,messageToSend,sm);
                        break;
                    case "Week":
                        Date d = getNewDate(0);
                        setsendMail(session,senderMAil,messageToSend, d,sm);
                        d = getNewDate(7);
                        setsendMail(session,senderMAil,messageToSend,d,sm);
                        d = getNewDate(14);
                        setsendMail(session,senderMAil,messageToSend,d,sm);
                        Toast.makeText(getApplicationContext(),"email is set to send successfully",Toast.LENGTH_LONG).show();

                        break;
                    case "Month":
                        Date d1 = getNewMonth(0);
                        setsendMail(session,senderMAil,messageToSend,d1,sm);
                        d1 = getNewMonth(1);
                        setsendMail(session,senderMAil,messageToSend,d1,sm);
                        d1 = getNewMonth(2);

                        setsendMail(session,senderMAil,messageToSend,d1,sm);
                        Toast.makeText(getApplicationContext(),"email is set to send successfully",Toast.LENGTH_LONG).show();

                        break;
                    case "Year":
                        Date d3 = getNewYear(0);
                        setsendMail(session,senderMAil,messageToSend,d3,sm);
                        d3 = getNewYear(1);
                        setsendMail(session,senderMAil,messageToSend,d3,sm);
                        d3 = getNewYear(2);
                        setsendMail(session,senderMAil,messageToSend,d3,sm);
                        Toast.makeText(getApplicationContext(),"email is set to send successfully",Toast.LENGTH_LONG).show();

                        break;

                }


            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void onRadioButtonClicked(View view) {
        int selectedId=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(selectedId);
     //   Toast.makeText(MainActivity.this,radioButton.getText(), Toast.LENGTH_SHORT).show();
        rec = (String) radioButton.getText();
    }
    public void setSendMail(Session session,String senderMAil,String messageToSend,String sm){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderMAil));
            message.setRecipients(RecipientType.TO,InternetAddress.parse(receiverMail.getText().toString()));
            message.setSubject(_subject.getText().toString());
            message.setText(messageToSend);
            boolean isInserted = openhelper.insertMail(receiverMail.getText().toString(),_cc.getText().toString(),_subject.getText().toString(),messageToSend,sm);
            Toast.makeText(getApplicationContext(),"email sent successfully",Toast.LENGTH_LONG).show();
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }
    public void setsendMail(Session session, String senderMAil, String messageToSend, Date date,String sm){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderMAil));
            message.setRecipients(RecipientType.TO,InternetAddress.parse(receiverMail.getText().toString()));
            message.setSubject(_subject.getText().toString());
            message.setText(messageToSend);
            message.setSentDate(date);
            boolean isInserted = openhelper.insertMail(receiverMail.getText().toString(),_cc.getText().toString(),_subject.getText().toString(),messageToSend,sm);

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }
    public Date getNewDate(int noOfDays) {
        //int noOfDays = 14; //i.e two weeks
        Calendar calendar = Calendar.getInstance();
        Date dateOfOrder = new Date();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = calendar.getTime();
        return date;
    }
    public Date getNewMonth(int noOfDays) {
        //int noOfDays = 14; //i.e two weeks
        Calendar calendar = Calendar.getInstance();
        Date dateOfOrder = new Date();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.MONTH, noOfDays);
        Date date = calendar.getTime();
        return date;
    }
    public Date getNewYear(int noOfDays) {
        //int noOfDays = 14; //i.e two weeks
        Calendar calendar = Calendar.getInstance();
        Date dateOfOrder = new Date();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.YEAR, noOfDays);
        Date date = calendar.getTime();
        return date;
    }
}