package com.ri.mycalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.objecthunter.exp4j.*;

import java.util.Arrays;

public class MainActivity extends ActionBarActivity {

    TextView tInput, tAnswer;
    Button bCancel, bEqual, bPlus, bMinus, bTimes, bDim, bDot, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    int ansCheck = 0;
    int calCheck = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tInput = (TextView) findViewById(R.id.input);
        tAnswer = (TextView) findViewById(R.id.answer);
        bCancel = (Button) findViewById(R.id.cancel);
        bEqual = (Button) findViewById(R.id.equal);
        bPlus = (Button) findViewById(R.id.plus);
        bMinus = (Button) findViewById(R.id.minus);
        bTimes = (Button) findViewById(R.id.times);
        bDim = (Button) findViewById(R.id.dim);
        bDot = (Button) findViewById(R.id.dot);
        b0 = (Button) findViewById(R.id.n0);
        b1 = (Button) findViewById(R.id.n1);
        b2 = (Button) findViewById(R.id.n2);
        b3 = (Button) findViewById(R.id.n3);
        b4 = (Button) findViewById(R.id.n4);
        b5 = (Button) findViewById(R.id.n5);
        b6 = (Button) findViewById(R.id.n6);
        b7 = (Button) findViewById(R.id.n7);
        b8 = (Button) findViewById(R.id.n8);
        b9 = (Button) findViewById(R.id.n9);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMath(View view) {
        Button button = (Button) view;
        CharSequence bt = button.getText();
        CharSequence ip = tInput.getText();
        String btCheck = bt.toString();
        String[] sNum = {b0.getText().toString(),b1.getText().toString(),b2.getText().toString(),b3.getText().toString(),b4.getText().toString(),b5.getText().toString(),b6.getText().toString(),b7.getText().toString(),b8.getText().toString(),b9.getText().toString()};
        String[] sCal = {bPlus.getText().toString(),bMinus.getText().toString(),bTimes.getText().toString(),bDim.getText().toString()};
        String[] sCalMinus = {bPlus.getText().toString(),bTimes.getText().toString(),bDim.getText().toString()};
        String[] sDot = {bDot.getText().toString()};
        int len = ip.length();
        if(len == 0){
            if(Arrays.asList(sCalMinus).contains(btCheck)){
                return;
            }
            if(Arrays.asList(sDot).contains(btCheck)){
                tInput.setText(bt.toString());
                calCheck = 0;
                return;
            }
            tInput.setText(bt.toString());
        } else{
            if((ansCheck == 1) && (Arrays.asList(sNum).contains(btCheck))) {
                tInput.setText(bt.toString());
                ansCheck = 0;
            }else if((ansCheck == 1) && (Arrays.asList(sDot).contains(btCheck))){
                tInput.setText(bt.toString());
                ansCheck = 0;
                calCheck = 0;
            } else if((ansCheck == 1) && (Arrays.asList(sCal).contains(btCheck))){
                tInput.setText(ip.toString().concat(bt.toString()));
                ansCheck = 0;
                calCheck = 1;
            } else if((ansCheck == 0) && Arrays.asList(sNum).contains(btCheck)){
                tInput.setText(ip.toString().concat(bt.toString()));
            } else if((ansCheck == 0) && Arrays.asList(sDot).contains(btCheck)){
                if(calCheck == 1){
                    tInput.setText(ip.toString().concat(bt.toString()));
                    calCheck = 0;
                }
            } else if((ansCheck == 0) && (Arrays.asList(sCal).contains(btCheck))){
                String check = String.valueOf(ip.charAt(len-1));
                calCheck = 1;
                if(Arrays.asList(sCal).contains(check) || Arrays.asList(sDot).contains(check)){
                    tInput.setText(ip.subSequence(0,len-1).toString().concat(bt.toString()));
                } else{
                    tInput.setText(ip.toString().concat(bt.toString()));
                }
            }
        }
    }

    public void sendClear(View view){
        ansCheck = 0;
        calCheck = 1;
        tInput.setText("");
        tAnswer.setText("");
    }

    public void sendDelete(View view){
        String[] sCal = {bPlus.getText().toString(),bMinus.getText().toString(),bTimes.getText().toString(),bDim.getText().toString()};
        CharSequence ip = tInput.getText();
        if(ip.length() == 0){
            return;
        }
        tInput.setText(ip.subSequence(0,ip.length()-1));
        if(String.valueOf(ip.charAt(ip.length()-1)).contentEquals(bDot.getText().toString())){
            calCheck = 1;
        }
        if((Arrays.asList(sCal).contains(String.valueOf(ip.charAt(ip.length()-1))))){
            calCheck = 0;
        }
    }

    public void sendAnswer(View view){
        ansCheck = 1;
        CharSequence ip = tInput.getText();
        int len = ip.length();
        if(len == 0){
            return;
        }
        String check = String.valueOf(ip.charAt(len-1));
        String[] sCal = {bPlus.getText().toString(),bMinus.getText().toString(),bTimes.getText().toString(),bDim.getText().toString()};
        String[] sDot = {bDot.getText().toString()};
        if(Arrays.asList(sCal).contains(check) || Arrays.asList(sDot).contains(check)){
            if(len == 1){
                tInput.setText("");
            } else{
                tInput.setText(ip.subSequence(0,len-1));
            }
            return;
        }
        Expression e = new ExpressionBuilder(tInput.getText().toString()).build();
        double dResult = e.evaluate();
        String sResult = String.valueOf(dResult);
        tAnswer.setText(sResult);
        tInput.setText(sResult);
        calCheck = 1;
    }
}
