package com.example.forcars;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import helpers.MQTTHelper;

public class MainActivity extends AppCompatActivity {

    MQTTHelper mqttHelper;
    private TextView dataReceived;
    private Button change_check;
    private Button change_oil;
    private Button change_fuel;
    private Button change_battery;
    private Button change_light;
    private Button change_SB;
    private Button change_cond;
    private Button change_SH;
    private Button st_rand;
    //private int pulse;

     boolean check = false;
     boolean oil = false;
     boolean fuel = false;
     boolean battery = false;
     boolean SH = false;
     boolean SB = false;
     boolean light = false;
     boolean cond = false;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setInitialData();
        dataReceived = (TextView) findViewById(R.id.Data);
        change_check = (Button) findViewById(R.id.change_check);
        change_oil = (Button) findViewById(R.id.change_oil);
        change_fuel = (Button) findViewById(R.id.change_fuel);
        change_battery = (Button) findViewById(R.id.change_battery);
        change_SH = (Button) findViewById(R.id.change_SH);
        change_light = (Button) findViewById(R.id.change_light);
        change_SB = (Button) findViewById(R.id.change_SB);
        change_cond = (Button) findViewById(R.id.change_cond);
        st_rand = (Button) findViewById(R.id.st_rand);


        change_check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(check) //если был неиспр, то испр
                {
                    Send_mes("false","check");
                    check=false;
                }
                else
                {
                    Send_mes("true","check");
                    check=true;
                }
            }
        });
        change_oil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(oil) //если был неиспр, то испр
                {
                    Send_mes("false", "oil");
                    oil=false;
                }
                else
                {
                    Send_mes("true", "oil");
                    oil=true;
                }
            }
        });
        change_fuel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(fuel) //если был неиспр, то испр
                {
                    Send_mes("false","fuel");
                    fuel=false;
                }
                else
                {
                    Send_mes("true","fuel");
                    fuel=true;
                }
            }
        });
        change_battery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(battery)//если был неиспр, то испр
                {
                    Send_mes("false", "battery");
                    battery=false;
                }
                else
                {
                    Send_mes("true", "battery");
                    battery=true;
                }
            }
        });
        change_SH.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SH) //если был неиспр, то испр
                {
                    Send_mes("false", "seatheat");
                    SH=false;
                }
                else
                {
                    Send_mes("true", "seatheat");
                    SH=true;
                }
            }
        });
        change_SB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SB)//если был неиспр, то испр
                {
                    Send_mes("false","seatbelt");
                    SB=false;
                }
                else
                {
                    Send_mes("true","seatbelt");
                    SB=true;
                }
            }
        });
        change_light.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(light)//если был неиспр, то испр
                {
                    Send_mes("false","light");
                    light=false;
                }
                else
                {
                    Send_mes("true","light");
                    light=true;
                }
            }
        });
        change_cond.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(cond) //если был неиспр, то испр
                {
                    Send_mes("false","cond");
                    cond=false;
                }
                else
                {
                    Send_mes("true","cond");
                    cond=true;
                }
            }
        });


        startMQTT();
    }

    private void Send_mes(String mes, String from){

        MqttMessage msg = new MqttMessage(mes.getBytes());
        msg.setQos(0);
        msg.setRetained(true);
        String topic = "base/state/" + from;
        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void startMQTT(){
        mqttHelper = new MQTTHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean b, String s) {
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
               // notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                if (topic.equals("base/state/check")) {
                    if(mqttMessage.toString().equals("true")) {
                        dataReceived.setText("Check is norm!");
                        check=true;
                    }
                    else if (mqttMessage.toString().equals("false")) {
                        dataReceived.setText("Check is down!");
                        check=false;
                    }
                }

                if (topic.equals("base/state/fuel")) {
                    if(mqttMessage.toString().equals("true")) {
                        dataReceived.setText("Fuel is norm!");
                        fuel=true;
                    }
                    else if (mqttMessage.toString().equals("false")) {
                        dataReceived.setText("Fuel is down!");
                        fuel=false;
                    }
                }

                if (topic.equals("base/state/oil")) {
                    if(mqttMessage.toString().equals("true")) {
                        dataReceived.setText("Oil is norm!");
                        oil=true;
                    }
                    else if (mqttMessage.toString().equals("false")) {
                        dataReceived.setText("Oil is down!");
                        oil=false;
                    }
                }

                if (topic.equals("base/state/battery")) {
                    if(mqttMessage.toString().equals("true")) {
                        dataReceived.setText("Battery is norm!");
                        battery=true;
                    }
                    else if (mqttMessage.toString().equals("false")) {
                        dataReceived.setText("Battery is down!");
                        battery=false;
                    }
                }

                if (topic.equals("base/state/light")) {
                    if(mqttMessage.toString().equals("true")) {
                        dataReceived.setText("Light is norm!");
                        light=true;
                    }
                    else if (mqttMessage.toString().equals("false")) {
                        dataReceived.setText("Light is down!");
                        light=false;
                    }
                }

                if (topic.equals("base/state/seatbelt")) {
                    if(mqttMessage.toString().equals("true")) {
                        dataReceived.setText("Seatbelt is norm!");
                        SB=true;
                    }
                    else if (mqttMessage.toString().equals("false")) {
                        dataReceived.setText("Seatbelt is down!");
                        SB=false;
                    }
                }

                if (topic.equals("base/state/cond")) {
                    if(mqttMessage.toString().equals("true")) {
                        dataReceived.setText("Condition is norm!");
                        cond=true;
                    }
                    else if (mqttMessage.toString().equals("false")) {
                        dataReceived.setText("Condition is down!");
                        cond=false;
                    }
                }

                if (topic.equals("base/state/seatheat")) {
                    if(mqttMessage.toString().equals("true")) {
                        dataReceived.setText("Seat Heat is norm!");
                        SH=true;
                    }
                    else if (mqttMessage.toString().equals("false")) {
                        dataReceived.setText("Seat Heat is down!");
                        SH=false;
                    }
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }
}