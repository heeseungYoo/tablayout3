package com.example.project1_test13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Toast;

import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerPopup;
import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawView drawView;
    Button btnShape;
    Button btnSize;
    int tColor, n = 0;
    int color =0;
    float width = 3.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = findViewById(R.id.sub_layout);
        tColor = ContextCompat.getColor(MainActivity.this, R.color.colorAccent);

        Button btnColor = findViewById(R.id.btnColor);
        btnSize = findViewById(R.id.btnSize);
        btnShape = findViewById(R.id.btnShape);
        Button btnUndo = findViewById(R.id.btnUndo);
        Button btnRedo = findViewById(R.id.btnRedo);
        Button btnErase = findViewById(R.id.btnErase);
        Button btnSave = findViewById(R.id.btnSave);

        btnColor.setOnClickListener(this);
        btnSize.setOnClickListener(this);
        btnShape.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnRedo.setOnClickListener(this);
        btnErase.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnColor:
 //               n = 2;
                openColorPicker();
                break;
            case R.id.btnSize:
                openSizeChange();
                break;
            case R.id.btnShape:
                openShape();
                break;
            case R.id.btnUndo:
                drawView.onClickUndo();
                break;
            case R.id.btnRedo:
                drawView.onClickRedo();
            case R.id.btnErase:
                drawView.reset();
                //두께 두껍게..?
                break;
            case R.id.btnSave:

                break;

        }
    }

    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
  //              tColor = color;
//                if (n == 1) {
                    //paint color 바꾸기
  //              }
   //             else if (n == 2) {
                    //drawView.setBackgroundColor(tColor);
                    drawView.setColor(color, getWidth());

            }
        });
        colorPicker.show();
    }

    public void openSizeChange() {
        final PenSetting penSetting = new PenSetting(this, new PenSetting.PenSettingEventListener() {
            public void penSettingEvent(float value) {
                String btnText = value + "";
                btnSize.setText(btnText);
                drawView.setWidth(value);
            }
        });
        penSetting.show();
    }

    public void openShape() {
        ShapeSetting shapeSetting = new ShapeSetting(this);
        shapeSetting.show();
    }

    public void getColor() {
    }

    public float getWidth() {
        String btnText;
        final StringBuilder sb = new StringBuilder(btnSize.getText().length());
        sb.append(btnSize.getText());
        btnText = sb.toString();
        return Float.valueOf(btnText);
    }


    /*
    메뉴에 쓰였던거 혹시 몰라서 남겨둠
    public void openShape() {
        PopupMenu popupMenu = new PopupMenu(this, drawView);
        getMenuInflater().inflate(R.menu.shape_setting, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this, "popup" + item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shape_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == 1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
