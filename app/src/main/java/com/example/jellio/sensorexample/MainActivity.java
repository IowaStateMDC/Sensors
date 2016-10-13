package com.example.jellio.sensorexample;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.R.attr.gravity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, barometer, compass, gyroscope, light, proximity;
    private TextView txtAccelerometerX, txtAccelerometerY, txtAccelerometerZ;
    private TextView txtBarometer, txtCompass, txtGyroscope, txtLight, txtProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*** Get the sensor manager ***/
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        /*** Get all the sensors that we will be using (these are all the sensors the Nexus 6 has ***/
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        barometer     = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        compass       = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroscope     = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        light         = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximity     = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        /*** Register all the sensors to the sensor manager ***/
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, barometer,     SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, compass,       SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope,     SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, light,         SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity,     SensorManager.SENSOR_DELAY_NORMAL);

        /*** Get all the views we will be putting data into ***/
        txtAccelerometerX = (TextView) findViewById(R.id.accelerometer_x);
        txtAccelerometerY = (TextView) findViewById(R.id.accelerometer_y);
        txtAccelerometerZ = (TextView) findViewById(R.id.accelerometer_z);
        txtBarometer = (TextView) findViewById(R.id.barometer_data);
        txtCompass   = (TextView) findViewById(R.id.compass_data);
        txtGyroscope = (TextView) findViewById(R.id.gyroscope_data);
        txtLight     = (TextView) findViewById(R.id.light_data);
        txtProximity = (TextView) findViewById(R.id.proximity_data);

    }

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                String[] xyz = toStringAccelerometerValues(event.values);
                txtAccelerometerX.setText(xyz[0]);
                txtAccelerometerY.setText(xyz[1]);
                txtAccelerometerZ.setText(xyz[2]);
                break;
            case Sensor.TYPE_PRESSURE:
                txtBarometer.setText(toStringPressureValues(event.values));
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                txtCompass.setText(toStringMagneticFieldValues(event.values));
                break;
            case Sensor.TYPE_GYROSCOPE:
                txtGyroscope.setText(toStringGyroscopeValues(event.values));
                break;
            case Sensor.TYPE_LIGHT:
                txtLight.setText(toStringLightValues(event.values));
                break;
            case Sensor.TYPE_PROXIMITY:
                txtProximity.setText(toStringProximityValues(event.values));
                break;
            default:
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, barometer,     SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, compass,       SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope,     SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, light,         SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity,     SensorManager.SENSOR_DELAY_NORMAL);
    }

    private String[] toStringAccelerometerValues(float[] values) {
        String[] string = {
                String.valueOf(values[0]),
                String.valueOf(values[1]),
                String.valueOf(values[2])
        };
        return string;
    }

    private String toStringPressureValues(float[] values) {
        return String.valueOf(values[0]);
    }

    private String toStringMagneticFieldValues(float[] values) {
        return "";
    }

    private String toStringGyroscopeValues(float[] values) {
        return "";
    }

    private String toStringLightValues(float[] values) {
        return String.valueOf(values[0]);
    }

    private String toStringProximityValues(float[] values) {
        if (values[0] == 0) {
            return "close";
        } else {
            return "far";
        }
    }
}
