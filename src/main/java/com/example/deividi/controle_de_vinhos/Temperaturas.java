package com.example.deividi.controle_de_vinhos;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class Temperaturas extends AppCompatActivity {
    Button bt_Conect,bt_Fechar;
    EditText et_l1,et_c1,et_l2,et_c2,et_l3,et_c3,et_op,et_amb;
    String broker="tcp://iot.eclipse.org:1883";//"tcp://192.168.1.102:1883";
    String clientId = MqttClient.generateClientId();
    MqttAndroidClient client = new  MqttAndroidClient(Temperaturas.this, broker,clientId);//"tcp://192.168.1.102:1883"
    String topico1 ="vinho1/liquido";
    String topico2="vinho1/chapeu";
    String topico3="vinho2/liquido";
    String topico4="vinho2/chapeu";
    String topico5="vinho3/liquido";
    String topico6="vinho3/chapeu";
    String topico7="sala2/ambiente";
    String topico8="sala/ambiente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperaturas);
        et_l1=(EditText) findViewById(R.id.et_l1);
        et_c1=(EditText) findViewById(R.id.et_c1);
        et_l2=(EditText) findViewById(R.id.et_l2);
        et_c2=(EditText) findViewById(R.id.et_c2);
        et_l3=(EditText) findViewById(R.id.et_l3);
        et_c3=(EditText) findViewById(R.id.et_c3);
        et_op=(EditText) findViewById(R.id.et_op);
        et_amb=(EditText) findViewById(R.id.et_amb);
        bt_Conect=(Button) findViewById(R.id.bt_conect);
        bt_Conect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               conectar();
            }
        });
        bt_Fechar=(Button) findViewById(R.id.bt_fechar);
        bt_Fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Fechar();
            }
        });
        getSupportActionBar().hide();
    }
    void Fechar(){
        finish();
        System.exit(0);
    }
    private void subscribe(String topico) {
        int qos = 0;
        try {
            IMqttToken subToken1 = client.subscribe(topico, qos);
            subToken1.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
        private void conectar() {
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    bt_Conect.setText("Conectado");
                    bt_Conect.setBackgroundResource(R.color.material_grey_600);
                    subscribe(topico1);
                    subscribe(topico2);
                    subscribe(topico3);
                    subscribe(topico4);
                    subscribe(topico5);
                    subscribe(topico6);
                    subscribe(topico7);
                    subscribe(topico8);
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(Temperaturas.this,"Falha",Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    if(topic.equals(topico1)){
                        et_l1.setText(new String(message.getPayload()));
                    }
                    if(topic.equals(topico2)){
                        et_c1.setText(new String(message.getPayload()));
                    }
                    if(topic.equals(topico3)){
                        et_l2.setText(new String(message.getPayload()));
                    }
                    if(topic.equals(topico4)){
                        et_c2.setText(new String(message.getPayload()));
                    }
                    if(topic.equals(topico5)){
                        et_l3.setText(new String(message.getPayload()));
                    }
                    if(topic.equals(topico6)){
                        et_c3.setText(new String(message.getPayload()));
                    }
                    if(topic.equals(topico7)){
                        et_op.setText(new String(message.getPayload()));
                    }
                    if(topic.equals(topico8)){
                        et_amb.setText(new String(message.getPayload()));
                    }

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
    }

    }

