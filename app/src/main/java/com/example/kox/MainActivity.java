package com.example.kox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.IconCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button B1, B2, B3, EN, PL, exit, inforeturn;
    Files file;
    String path = "Ustawienia.txt";
    String lange = "jezyk.txt";
    String temp = "temp.txt";
    String kolor = "kolor.txt";
    String IDs = "id.txt";
    String ikony = "ikona.txt";
    private ImageView myButton,SAVE,help,kolor_change;
    String infoPL =
                    "* Aplikacja uruchamia strony zapisane w ustawieniach  \n" +
                    "* Wykrywa czy na początku jest https://www. , jeśli jest nie dodaje niczego i przechodzi pod link \n" +
                    "* Wykrywa czy na początku jest www. jeśli jest dodaje tylko https:// na początku \n" +
                    "* Wykrywa czy na początku jest https://, i dodaje www. \n" +
                    "* jeśli na końcu jest .pl lub .com, a na początku nie ma https:// ani www. sama to dodaje \n" +
                    "* Jeśli nie ma .pl ani .com oraz https://.. wyszukuje frazę w wyszukiwarce";
    String infoEN =
                    "* The application runs the pages saved in the settings \n" +
                    "* Detects if https: // www is at the beginning. if it is, it doesn't add anything and goes to the link \n" +
                    "* Detects if there is www at the beginning. if it is, it only adds https: // at the beginning \n" +
                    "* It detects if https: // is first, and adds www. \n" +
                            "* if there is .pl or .com at the end and neither https: // nor www is at the beginning. she adds it herself \n" +
                            "* If there is no .pl or .com and https: // .. it looks for the phrase in the search Google";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        file = new Files(this);
        if(file.isEmpty(kolor))
             file.write(kolor,String.valueOf(Color.WHITE));

        if(file.isEmpty(path)) {
            String[] PLL = {"", "", "", "", "", "", "", "","END"};// "Ustawienia", "Tytuł", "Strona", "Przechodzę na stronę:  ", "Zapis", "Brak", "Brak Strony!", "Wyjście", "Powrót"};
            file.write(path, PLL);
            jezyk();
            String[] not = {"not","not","not","END"};
            file.write(ikony,not);
           /* String[] language = {"Ustawienia", "Tytuł", "Strona", "Przechodzę na stronę:  ", "Zapis", "Brak","Brak Strony!","Wyjście","Powrót"};
            file.write(lange, language);*/
            file.write(IDs,"1");

        }
        setContentView(R.layout.activity_main);
        jezyk();
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            System.exit(0);
            }
        });
        B1 = findViewById(R.id.B1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(read("B1"));
            }
        });
        B2 = findViewById(R.id.B2);
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(read("B2"));
            }
        });
        B3 = findViewById(R.id.B3);
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(read("B3"));
            }
        });

        final Integer[] icons = new Integer[] {
                R.drawable.ic_baseline_accessible_forward_24,
                R.drawable.ic_help,
                R.drawable.cancel,
                R.drawable.ic_kolor
        };

            String[] test = file.read(ikony);
        if(test[0].equals("not")){}
        else{
            int numerek = Integer.parseInt(file.read(ikony)[0]);
            Drawable img = MainActivity.this.getResources().getDrawable(icons[numerek]);
            img.setBounds(0, 0, 100, 100);
            B1.setCompoundDrawables(img, null, null, null);
        }
        if(test[1].equals("not")){}
        else{
            int numerek = Integer.parseInt(file.read(ikony)[1]);
            Drawable img = MainActivity.this.getResources().getDrawable(icons[numerek]);
            img.setBounds(0, 0, 100, 100);
            B2.setCompoundDrawables(img, null, null, null);
        }
        if(test[2].equals("not")){}
        else {
            int numerek = Integer.parseInt(file.read(ikony)[2]);
            Drawable img = MainActivity.this.getResources().getDrawable(icons[numerek]);
            img.setBounds(0, 0, 100, 100);
            B3.setCompoundDrawables(img, null, null, null);
        }

        int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
        getWindow().getDecorView().setBackgroundColor(jaki_kolor);

        run();
        myButton = findViewById(R.id.change);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog mydialog = new AlertDialog.Builder(MainActivity.this).create();
                TextView Tytul = new TextView(MainActivity.this);
                Tytul.setText(file.read(lange)[0]);
                Tytul.setPadding(20, 30, 20, 30);
                Tytul.setTextSize(20F);
                Tytul.setBackgroundColor(Color.WHITE);
                Tytul.setTextColor(Color.BLACK);
                Tytul.setGravity(Gravity.CENTER);
                //mydialog.setCustomTitle(Tytul);
                View layout_dialog = getLayoutInflater().inflate(R.layout.layout_dialog, null);
                TextView gui_set = layout_dialog.findViewById(R.id.gui_set);
                        gui_set.setText(file.read(lange)[0]);

                EditText B1_T = layout_dialog.findViewById(R.id.B1_T);
                EditText B1_B = layout_dialog.findViewById(R.id.B1_B);
                EditText B2_T = layout_dialog.findViewById(R.id.B2_T);
                EditText B2_B = layout_dialog.findViewById(R.id.B2_B);
                EditText B3_T = layout_dialog.findViewById(R.id.B3_T);
                EditText B3_B = layout_dialog.findViewById(R.id.B3_B);
                Button B1_S = layout_dialog.findViewById(R.id.B1_S);
                Button B2_S = layout_dialog.findViewById(R.id.B2_S);
                Button B3_S = layout_dialog.findViewById(R.id.B3_S);
                Button SAVE = layout_dialog.findViewById(R.id.SAVE);
                SAVE.setHint(file.read(lange)[4]);
                Button B1_I = layout_dialog.findViewById(R.id.B1_I);
                Button B2_I = layout_dialog.findViewById(R.id.B2_I);
                Button B3_I = layout_dialog.findViewById(R.id.B3_I);
                B1_I.setText(file.read(lange)[23]);
                B2_I.setText(file.read(lange)[23]);
                B3_I.setText(file.read(lange)[23]);



                ImageButton cancel = layout_dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mydialog.dismiss();
                            }
                        });

               /* kolor_change = layout_dialog.findViewById(R.id.kolor_change);
                kolor_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Zmiana_koloru();
                    }
                });*/
                mydialog.setView(layout_dialog);
                String[] odczyt = file.read(path);
                B1_T.setText(odczyt[0]);
                B1_B.setText(odczyt[1]);
                B2_T.setText(odczyt[2]);
                B2_B.setText(odczyt[3]);
                B3_T.setText(odczyt[4]);
                B3_B.setText(odczyt[5]);
                String[] jaki = file.read(lange);
                B1_T.setHint(jaki[1]);
                B1_B.setHint(jaki[2]);
                B1_S.setText(jaki[22]);
                B2_T.setHint(jaki[1]);
                B2_B.setHint(jaki[2]);
                B2_S.setText(jaki[22]);
                B3_T.setHint(jaki[1]);
                B3_B.setHint(jaki[2]);
                B3_S.setText(jaki[22]);
                if(file.isEmpty(temp)){}
                else{
                    String[] TempZ = file.read(temp);
                    B1_T.setText(TempZ[0]);
                    B1_B.setText(TempZ[1]);
                    B2_T.setText(TempZ[2]);
                    B2_B.setText(TempZ[3]);
                    B3_T.setText(TempZ[4]);
                    B3_B.setText(TempZ[5]);
                    file.clear(temp);
                }
                help = layout_dialog.findViewById(R.id.help);
                help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydialog.dismiss();
                        View layout_info = getLayoutInflater().inflate(R.layout.layout_info, null);
                        Button inforeturn = layout_info.findViewById(R.id.inforeturn);
                        inforeturn.setText(file.read(lange)[8]);
                        String[] TempZ = {B1_T.getText().toString(), B1_B.getText().toString(), B2_T.getText().toString(), B2_B.getText().toString(), B3_T.getText().toString(), B3_B.getText().toString(), "END"};
                        file.write(temp,TempZ);
                        AlertDialog INFO = new AlertDialog.Builder(MainActivity.this).create();

                        INFO.setView(layout_info);
                        INFO.setCancelable(false);
                        TextView infoT = layout_info.findViewById(R.id.infoT); //8

                        inforeturn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                INFO.dismiss();
                                myButton.callOnClick();
                            }
                        });
                        if(file.read(lange)[0].equals("Ustawienia"))
                            infoT.setText(infoPL);
                        else
                            infoT.setText(infoEN);

                        INFO.show();

                    }
                });
                SAVE = layout_dialog.findViewById(R.id.SAVE);
                SAVE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] myText = {B1_T.getText().toString(), B1_B.getText().toString(), B2_T.getText().toString(), B2_B.getText().toString(), B3_T.getText().toString(), B3_B.getText().toString(), "END"};
                        file.write(path, myText);
                        Toast.makeText(MainActivity.this, file.read(lange)[4], Toast.LENGTH_LONG).show();
                        run();
                        mydialog.dismiss();
                    }
                });
                EN = layout_dialog.findViewById(R.id.EN);
                EN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Change to EN", Toast.LENGTH_LONG).show();
                        NaEN();
                        //String[] TempZ = {B1_T.getText().toString(),B1_B.getText().toString(),B2_T.getText().toString(),B2_B.getText().toString(),B3_T.getText().toString(),B3_B.getText().toString(),"END"};
                        String[] TempZ = {B1_T.getText().toString(), B1_B.getText().toString(), B2_T.getText().toString(), B2_B.getText().toString(), B3_T.getText().toString(), B3_B.getText().toString(), "END"};
                        file.write(temp,TempZ);
                        mydialog.dismiss();
                        myButton.callOnClick();
                    }
                });
                PL = layout_dialog.findViewById(R.id.PL);
                PL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Zmieniono język na PL", Toast.LENGTH_LONG).show();
                        NaPL();
                        String[] TempZ = {B1_T.getText().toString(), B1_B.getText().toString(), B2_T.getText().toString(), B2_B.getText().toString(), B3_T.getText().toString(), B3_B.getText().toString(), "END"};
                        file.write(temp,TempZ);
                        mydialog.dismiss();
                        myButton.callOnClick();

                    }
                });
                B1_S.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(B1_T.getText().toString().equals("") || B1_B.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, file.read(lange)[21], Toast.LENGTH_LONG).show();
                        }
                        else
                        es(B1_T.getText().toString(),getUrl(B1_B.getText().toString()),0);
                    }
                });
                B2_S.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(B2_T.getText().toString().equals("") || B2_B.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, file.read(lange)[21], Toast.LENGTH_LONG).show();
                        }
                        else
                        es(B2_T.getText().toString(),getUrl(B2_B.getText().toString()),1);
                    }
                });
                B3_S.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(B3_T.getText().toString().equals("") || B3_B.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, file.read(lange)[21], Toast.LENGTH_LONG).show();
                        }
                        else
                        es(B3_T.getText().toString(),getUrl(B3_B.getText().toString()),2);
                    }
                });
                B1_I.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Ikona(1);
                    }
                });
                B2_I.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Ikona(2);
                    }
                });
                B3_I.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Ikona(3);
                    }
                });

                rest();
                mydialog.show();
            }
        });
        jezyk();
    }

    private void run() {
        String[] fileText = file.read(path);
        B1.setText(fileText[0]);
        B2.setText(fileText[2]);
        B3.setText(fileText[4]);
    }

    private String read(String B) {
        String path = "Ustawienia.txt";
        String[] fileText = file.read(path);
        if (B == "B1")
            return fileText[1];
        else if (B == "B2")
            return fileText[3];
        else
            return fileText[5];
    }

    private void gotoUrl(String s) {
        if(s.equals("")){
            Toast.makeText(MainActivity.this, file.read(lange)[6], Toast.LENGTH_LONG).show();
            return;
        }
        if (s.length() < 4) {
            Toast.makeText(MainActivity.this, file.read(lange)[3] + s.trim(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.google.com/search?q=" + s)));
            return ;
        } // krótki
        if (s.length() > 5) {
            if (s.substring(s.length() - 4, s.length()).equals(".pl/") || s.substring(s.length() - 3, s.length()).equals(".pl") || s.substring(s.length() - 4, s.length()).equals(".com") || s.substring(s.length() - 5, s.length()).equals(".com/")) {
                if (s.length() > 16) {
                    if (s.substring(0, 12).equals("https://www.")) {
                        Toast.makeText(MainActivity.this, file.read(lange)[3] + s.substring(12,s.length()), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(s)));
                        return;
                    }
                }
                if (s.length() > 7) {
                    if (s.substring(0, 4).equals("www.")) {
                        Toast.makeText(MainActivity.this, file.read(lange)[3] + s.substring(4,s.length()), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://" + s)));
                        return;
                    }
                }
                if(s.substring(s.length() - 4, s.length()).equals(".pl/") || s.substring(s.length() - 3, s.length()).equals(".pl") || s.substring(s.length() - 4, s.length()).equals(".com") || s.substring(s.length() - 5, s.length()).equals(".com/")) {
                     Toast.makeText(MainActivity.this, file.read(lange)[3] + s, Toast.LENGTH_LONG).show();
                     startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www." + s)));
                     return;
                }
            } else if (s.length() > 16) {
                if (s.substring(0, 12).equals("https://www.")) {
                    Toast.makeText(MainActivity.this, file.read(lange)[3] + s.substring(12,s.length()), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(s)));
                }
            }
        } else
            Toast.makeText(MainActivity.this, file.read(lange)[3] + s, Toast.LENGTH_LONG).show();
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.google.com/search?q=" + s)));
    }

    private String getUrl(String s){
        if(s.equals("")){
            Toast.makeText(MainActivity.this, file.read(lange)[6], Toast.LENGTH_LONG).show();
            return "0";
        }
        if (s.length() < 4) {
            Toast.makeText(MainActivity.this, file.read(lange)[3] + s.trim(), Toast.LENGTH_LONG).show();
           // startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.google.com/search?q=" + s)));
            return "https://www.google.com/search?q=" + s;
        } // krótki
        if (s.length() > 5) {
            if (s.substring(s.length() - 4, s.length()).equals(".pl/") || s.substring(s.length() - 3, s.length()).equals(".pl") || s.substring(s.length() - 4, s.length()).equals(".com") || s.substring(s.length() - 5, s.length()).equals(".com/")) {
                if (s.length() > 16) {
                    if (s.substring(0, 12).equals("https://www.")) {
                        Toast.makeText(MainActivity.this, file.read(lange)[3] + s.substring(12,s.length()), Toast.LENGTH_LONG).show();
                     //   startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(s)));
                        return s;
                    }
                }
                if (s.length() > 7) {
                    if (s.substring(0, 4).equals("www.")) {
                        Toast.makeText(MainActivity.this, file.read(lange)[3] + s.substring(4,s.length()), Toast.LENGTH_LONG).show();
                   //     startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://" + s)));
                        return "https://" + s;
                    }
                }
                if(s.substring(s.length() - 4, s.length()).equals(".pl/") || s.substring(s.length() - 3, s.length()).equals(".pl") || s.substring(s.length() - 4, s.length()).equals(".com") || s.substring(s.length() - 5, s.length()).equals(".com/")) {
                    Toast.makeText(MainActivity.this, file.read(lange)[3] + s, Toast.LENGTH_LONG).show();
                 //   startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www." + s)));
                    return "https://www." + s;
                }
            } else if (s.length() > 16) {
                if (s.substring(0, 12).equals("https://www.")) {
                    Toast.makeText(MainActivity.this, file.read(lange)[3] + s.substring(12,s.length()), Toast.LENGTH_LONG).show();
                //    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(s)));
                    return s;
                }
            }
        } else
            Toast.makeText(MainActivity.this, file.read(lange)[3] + s, Toast.LENGTH_LONG).show();
     //   startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.google.com/search?q=" + s)));
        return "https://www.google.com/search?q=" + s;

    }

    public void jezyk() {
        if (file.isEmpty(lange)) {
            String[] PL = {"Ustawienia", "Tytuł", "Strona", "Przechodzę na stronę:  ", "Zapis", "Brak","Brak Strony!","Wyjście","Powrót","Niebieski","Czerwony", "Czarny","Żółty","Zielony","Wybierz kolor","Twój wybrany kolor to","Cyjan","Magenta","Szary","Pomarańczowy","Różowy","Brak Danych","Skrót","Ikona"};
            file.write(lange, PL);
        }
        rest();
    }

    public void NaPL() {
        String[] PL = {"Ustawienia", "Tytuł", "Strona", "Przechodzę na stronę:  ", "Zapis", "Brak","Brak Strony!","Wyjście","Powrót","Niebieski","Czerwony", "Czarny","Żółty","Zielony","Wybierz kolor","Twój wybrany kolor to","Cyjan","Magenta","Szary","Pomarańczowy","Różowy","Brak Danych","Skrót","Ikona"};
        file.write(lange, PL);
        rest();
    }

    public void NaEN() {
        String[] EN = {"Settings", "Title", "Website", "Going to the page:  ", "Saving", "empty", "No Page!","exit","return","Blue","Red","Black","Yellow","Green","Select One Color","Your Selected Color is","Cyan","Magenta","Gray","Orange","Pink","No Data","Shortcut","Icon"};
        file.write(lange, EN);
        rest();
    }

    public void rest() {

        View layout_dialog = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        EditText B1_T = layout_dialog.findViewById(R.id.B1_T);
        EditText B1_B = layout_dialog.findViewById(R.id.B1_B);
        Button B1_S = layout_dialog.findViewById(R.id.B1_S);
        EditText B2_T = layout_dialog.findViewById(R.id.B2_T);
        EditText B2_B = layout_dialog.findViewById(R.id.B2_B);
        Button B2_S = layout_dialog.findViewById(R.id.B2_S);
        EditText B3_T = layout_dialog.findViewById(R.id.B3_T);
        EditText B3_B = layout_dialog.findViewById(R.id.B3_B);
        Button B3_S = layout_dialog.findViewById(R.id.B3_S);

        String[] jaki = file.read(lange);
        B1_T.setHint(jaki[1]);
        B1_B.setHint(jaki[2]);
        B1_S.setText(jaki[22]);
        B2_T.setHint(jaki[1]);
        B2_B.setHint(jaki[2]);
        B2_S.setText(jaki[22]);
        B3_T.setHint(jaki[1]);
        B3_B.setHint(jaki[2]);
        B3_S.setText(jaki[22]);


   //     exit.setHint(file.read(lange)[6]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Ikona(int jaki){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_baseline_accessible_forward_24);
        builderSingle.setTitle(file.read(lange)[14]);
        //final ArrayAdapter<Icon> arrayAdapter = new ArrayAdapter<Icon>(MainActivity.this,android.R.layout.select_dialog_item);
        final String [] items = new String[] {
                "es",
                "es",
                "es",
                "es"
                };
        final Integer[] icons = new Integer[] {
                R.drawable.ic_baseline_accessible_forward_24,
                R.drawable.ic_help,
                R.drawable.cancel,
                R.drawable.ic_kolor
                };

        final ListAdapter arrayAdapter = new ArrayAdapterWithIcon(MainActivity.this, items, icons);



        builderSingle.setNegativeButton(file.read(lange)[8], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);

                String[] zapisane = file.read(ikony);

                for(int i = 1; i < 4; i++)
                    if(jaki == i)
                        zapisane[i-1] = String.valueOf(which);
                file.write(ikony, zapisane);
                if(jaki == 1){
                    int numerek = Integer.parseInt(file.read(ikony)[0]);
                    Drawable img = MainActivity.this.getResources().getDrawable(icons[numerek]);
                    img.setBounds(0, 0, 100, 100);
                    B1.setCompoundDrawables(img, null, null, null);
                }
                else if(jaki == 2){
                    int numerek = Integer.parseInt(file.read(ikony)[1]);
                    Drawable img = MainActivity.this.getResources().getDrawable(icons[numerek]);
                    img.setBounds(0, 0, 100, 100);
                    B2.setCompoundDrawables(img, null, null, null);
                }
                else if(jaki == 3){
                    int numerek = Integer.parseInt(file.read(ikony)[2]);
                    Drawable img = MainActivity.this.getResources().getDrawable(icons[numerek]);
                    img.setBounds(0, 0, 100, 100);
                    B3.setCompoundDrawables(img, null, null, null);
                }

                builderInner.setMessage(String.valueOf(which));
                Toast.makeText(MainActivity.this, "Item Selected: " + which, Toast.LENGTH_SHORT).show();

                builderInner.setTitle(file.read(lange)[15]);
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    public void Zmiana_koloru(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_baseline_accessible_forward_24);
        builderSingle.setTitle(file.read(lange)[14]);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        for(int i = 9; i< 21; i++) {
            arrayAdapter.add(file.read(lange)[i]);
            if( i == 13)
                i+=2;
        }
        builderSingle.setNegativeButton(file.read(lange)[8], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                if(strName.equals(file.read(lange)[9])){
                    file.write(kolor,String.valueOf(Color.BLUE));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[10])){
                    file.write(kolor,String.valueOf(Color.RED));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[11])){
                    file.write(kolor,String.valueOf(Color.BLACK));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[12])){
                    file.write(kolor,String.valueOf(Color.YELLOW));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[13])){
                    file.write(kolor,String.valueOf(Color.GREEN));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[16])){
                    file.write(kolor,String.valueOf(Color.CYAN));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[17])){
                    file.write(kolor,String.valueOf(Color.MAGENTA));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[18])){
                    file.write(kolor,String.valueOf(Color.GRAY));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[19])){
                    file.write(kolor,String.valueOf(Color.rgb(255, 165, 0)));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                if(strName.equals(file.read(lange)[20])){
                    file.write(kolor,String.valueOf(Color.rgb(255, 73, 152)));
                    int jaki_kolor = Integer.parseInt(file.read(kolor)[0]);
                    getWindow().getDecorView().setBackgroundColor(jaki_kolor);
                }
                builderInner.setMessage(strName);
                builderInner.setTitle(file.read(lange)[15]);
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    public void es(String nazwa,String link,int jaki){

        int id = Integer.parseInt(file.read(IDs)[0]);
        id++;
        file.write(IDs,String.valueOf(id));
        Context mContext = MainActivity.this;
        ShortcutManager shortcutManager = null;
        int ikona = R.mipmap.luncher;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            shortcutManager = mContext.getSystemService(ShortcutManager.class);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (shortcutManager != null) {
                if (shortcutManager.isRequestPinShortcutSupported()) {
                    ShortcutInfo shortcut = new ShortcutInfo.Builder(mContext,String.valueOf(id))
                            .setShortLabel(String.valueOf(id))
                            .setLongLabel(nazwa)
                            .setIcon(Icon.createWithResource(mContext,ikona))
                            .setIntent(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(link)))
                            .build();

                    shortcutManager.requestPinShortcut(shortcut, null);
                } else
                    Toast.makeText(mContext, "Pinned shortcuts are not supported!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

