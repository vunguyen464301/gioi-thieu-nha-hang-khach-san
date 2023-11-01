package com.example.anhvu.gioithieunhahangkhachsan.MutilThreading;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import com.example.anhvu.gioithieunhahangkhachsan.R;

public class AsyncTaskLoadphantram extends AsyncTask<Void,String,String> {
    Activity activity;
    TextView textViewthongtinphantram;
    String thongtin;
    int phantram=0;
    int tongphantramcanphaiRUN;
    double timeRun;

    public AsyncTaskLoadphantram(Activity activity,String thongtin,int phantram ,double timeRun) {
        this.activity = activity;
        //cái này hiện thị thông tin phần trăm
        this.phantram=phantram;
        //cái này hiện thị ghi chú như là "đang kiểm tra..."
        this.thongtin=thongtin;
        //cái này là thời gian run
        this.timeRun=timeRun;
        //cái này là tiếp theo phần trăm cần phải run ,ví dụ như là đang run ở 50 xong rồi ,thì ko lẽ
        //dừng lại run lại  0,là phải run tiếp 50 đến 100
        tongphantramcanphaiRUN=phantram+50;
        //ánh xạ bình thường thôi !
        textViewthongtinphantram=activity.findViewById(R.id.txt_phantram_main);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        //phần trăm phải bé hơn 50 nếu bằng thì khi vô vòng lặp thì nó lại +1 nữa là 51
        while(phantram<tongphantramcanphaiRUN){
            phantram=phantram+1;
            SystemClock.sleep((long) timeRun);
            publishProgress(phantram+"");
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        textViewthongtinphantram.setText(values[0]+"% "+thongtin);
    }
}
