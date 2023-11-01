package com.example.anhvu.gioithieunhahangkhachsan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Activity_Fragment_Ad;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Activity_Fragment_Homepage;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Khachhang;
import com.example.anhvu.gioithieunhahangkhachsan.Interface.Callback_setOnclick;
import com.example.anhvu.gioithieunhahangkhachsan.LoginAndSignup.LoginOrSignupActivity;
import com.example.anhvu.gioithieunhahangkhachsan.Menu.MenuBandadatADMINActivity;
import com.example.anhvu.gioithieunhahangkhachsan.Menu.MenuBandadatActivity;
import com.example.anhvu.gioithieunhahangkhachsan.Menu.MenuPhongdadatADMINActivity;
import com.example.anhvu.gioithieunhahangkhachsan.Menu.MenuPhongdadatActivity;
import com.example.anhvu.gioithieunhahangkhachsan.Menu.MyHomeActivity;
import com.example.anhvu.gioithieunhahangkhachsan.MutilThreading.AsyncTaskLoadphantram;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//phải để implements NavigationView.OnNavigationItemSelectedListener vì như thế mới dùng được hàm onNavigationselected để tìm id menu
public class MainActivity extends AppCompatActivity implements Callback_setOnclick{
    Intent intentLoginOrSignup,intentWellcome;
    ImageView img1,imgMember,imgAdmin;
    TextView txtName,txtNumberphone,txtMakh,txtLoginorSignout,txt_phantram;
    ProgressBar progressBar1;
    LinearLayout scroll_lin1,scroll_lin2,scroll_lin3,scroll_lin4,scroll_lin5,scroll_lin6,scroll_lin7,scroll_lin8,scroll_lin9,scroll_lin10,lin1_main;
    View.OnClickListener onClickListenerLinearlayout;
    Intent intentMenuMyhome,intentMenuPhongdadat,intentMenuBandadat,intentMenuPhongdadatAdmin,intentMenuBandadatAdmin,intentMenuSupport,intentMenuSupportAdmin,intentQuangcao;

    public static SQLiteDatabase databaseNhaHangKhachSan;
    public static ArrayList<Class_Table_Khachhang> khachhangtamthoi;
//    public static String urlpublic ="http://anhvu4643.000webhostapp.com/";
    public static String urlpublic ="http://192.168.1.4:8888/";
    //vì vị trí hình quảng cáo phải bắt đầu từ mảng thứ 0
    //nên muốn bên kia hiện thị từ mảng thứ 0 đến mảng thứu n thì phải là set -1
    //rồi xuống dòng kia set vitrihinhquangcao=vitrihinhquangcao+1
    public static int vitrihinhquangcao=-1;
    public static int demhinh=0;

    private Class_Table_Khachhang class_table_khachhang;
    private DrawerLayout drawerLayout;
    //hàm này dùng để gọi cái mũi tên trên cùng trên layout bên tay trái
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private ArrayList<String> numberphoneAdministrator;

    private TabLayout tabLayout_ad,tabLayout_homepage;
    private ViewPager viewPager_ad,viewPager_hompage;
    private Adapter_Activity_Fragment_Ad adapter_activity_fragment_Ad;
    private Adapter_Activity_Fragment_Homepage adapter_activity_fragment_homepage;

    public static String maphienban="1.0";
    private boolean duocphepsudungphienbannay;

    private AsyncTaskLoadphantram asyncTaskLoadphantram;

    Cursor cursorKH;

    private int dem,demloadthongtin,demloadthongtin2;
    private int demthoigianquangcao=5;
    private int demloadthongtinkhikhoidong=0;
    private int positionFragmentquangcao=0;
    private Handler handlerAd=new Handler();
    private Runnable runnableAd=new Runnable() {
        @Override
        public void run() {
            handlerAd.postDelayed(runnableAd,1000);
            // Toast.makeText(getApplicationContext(),demthoigianquangcao+"",Toast.LENGTH_SHORT).show();
            demthoigianquangcao =demthoigianquangcao-1;
            if(demthoigianquangcao==0){
                positionFragmentquangcao=positionFragmentquangcao+1;
                viewPager_ad.setCurrentItem(positionFragmentquangcao);
                demthoigianquangcao=5;
                if(positionFragmentquangcao==4){
                    positionFragmentquangcao=0;
                }
            }
        }
    };
    //hàm dữ liệu nền này dùng để đẩy dữ liệu khách hàng lên giao diện
    private Handler handlerTime= new Handler();
    private Runnable runTime =new Runnable() {
        @Override
        public void run() {
            dem =dem-1;
            Toast.makeText(getApplicationContext(), dem+"" , Toast.LENGTH_SHORT).show();
            //sau 2s se cap nhat
            handlerTime.postDelayed(this,1000);
            if(dem==0) {
                handlerTime.removeCallbacks(runTime);
                 cursorKH=databaseNhaHangKhachSan.query("KHACHHANG",null,null,null,null,null,null) ;
                // Toast.makeText(getApplicationContext(),c.getMatkhau()+" " +getPassword ,Toast.LENGTH_SHORT).show();
                if(cursorKH.getCount()==0 && khachhangtamthoi.size()==0){
                  startActivity(intentLoginOrSignup);
                  overridePendingTransition(R.anim.chuyentrangtutrenxuongduoi,R.anim.giunguyentuthe);
                }else {
                    openAccount();
                    //duyệt lại lần nữa
                    handlerLoadhinh.postDelayed(runnableLoadhinh,1000);
                }

            }
        }
    };

    //hàm dữ liệu nền này dùng để kiểm tra dữ liệu
    private Handler handlerLoadlientuc =new Handler();
    private Runnable runnableLoadlientuc =new Runnable() {
        @Override
        public void run() {
            //ẩn cái này đi sau khi đăng xuất bước vô thread này
            progressBar1.setVisibility(View.INVISIBLE);

            cursorKH=databaseNhaHangKhachSan.query("KHACHHANG",null,null,null,null,null,null) ;
            demloadthongtin=demloadthongtin-1;
            //Toast.makeText(getApplicationContext(),"dem load lien tuc : "+demloadthongtin+"",Toast.LENGTH_SHORT).show();
            handlerLoadlientuc.postDelayed(runnableLoadlientuc,1000);
            //vì là trường hợp load thông tin ==0 rồi trở về 2 rồi tiếp tục
            //cho tới khi có data trong arraylist ,hoặc nếu có trong database thì nó sẽ dừng tránh dùng nhiều tài nguyên
            if(demloadthongtin==0){
                if(khachhangtamthoi.size()>0){
                    openAccount();
                    //nếu tìm thấy tài khoản thì load hình ra bên ngoài
                    handlerLoadhinh.postDelayed(runnableLoadhinh,1000);
                    handlerLoadlientuc.removeCallbacks(runnableLoadlientuc);

                }else{
                    //vòng lặp vô hạn ,chừng nào nó thấy được khach hang tạm thời có dữ liệu thì mới ngừng thread này
                    demloadthongtin=3;
                }
            }
            //hàm dưới không cần thiết vì là kiểm tra liên tục tới khi nào có thì thôi
            /*
            else if(cursorKH.getCount()>0 && dem==0){
                //nếu trong database của khách hàng có tồn tại dữ liệu thì trên kia nó runtime nó chạy rồi ,không cần phải chạy lại
                //tránh hao phí tài nguyên
                handlerLoadlientuc.removeCallbacks(runnableLoadlientuc);
            }
            */
           //Toast.makeText(getApplicationContext(),demloadthongtin+"",Toast.LENGTH_SHORT).show();
        }
    };
    //thread load hình
    private Handler handlerLoadhinh=new Handler();
    private Runnable runnableLoadhinh=new Runnable() {
        @Override
        public void run() {
            demloadthongtin2=demloadthongtin2-1;
            handlerLoadhinh.postDelayed(runnableLoadhinh,1000);
            if(demloadthongtin2==0){
            if(numberphoneAdministrator.size()>0){
                //img1.setImageResource(R.drawable.admin);

                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.admin);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                roundedBitmapDrawable.setCircular(true);
                img1.setImageDrawable(roundedBitmapDrawable);

                txtLoginorSignout.setText("Đăng xuất");

            }else if(khachhangtamthoi.size()>0){
                //img1.setImageResource(R.drawable.member);
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.member);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                roundedBitmapDrawable.setCircular(true);
                img1.setImageDrawable(roundedBitmapDrawable);

                txtLoginorSignout.setText("Đăng xuất");
            }

            else{
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.guest);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                roundedBitmapDrawable.setCircular(true);
                img1.setImageDrawable(roundedBitmapDrawable);
            }



                handlerLoadhinh.removeCallbacks(runnableLoadhinh);
            }


        }
    };

    private void viewAdFragment(){
        //tableLayout_ad.setupWithViewPager(viewPager_ad);
        adapter_activity_fragment_Ad =new Adapter_Activity_Fragment_Ad(getSupportFragmentManager());
        viewPager_ad.setAdapter(adapter_activity_fragment_Ad);
    }
    private void viewHomepageFragment(){
        adapter_activity_fragment_homepage =new Adapter_Activity_Fragment_Homepage(getSupportFragmentManager());
        viewPager_hompage.setAdapter(adapter_activity_fragment_homepage);
        tabLayout_homepage.setupWithViewPager(viewPager_hompage);
      //  tabLayout_homepage.set
        tabLayout_homepage.getTabAt(0).setIcon(R.drawable.ic_hotel_black_24dp);
        tabLayout_homepage.getTabAt(1).setIcon(R.drawable.ic_restaurant_black_24dp);
        tabLayout_homepage.getTabAt(2).setIcon(R.drawable.ic_shop_black_24dp);
        tabLayout_homepage.getTabAt(3).setIcon(R.drawable.ic_room_service_black_24dp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        //cách set hình tròn
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.guest);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        img1.setImageDrawable(roundedBitmapDrawable);

        //để vô đây là tránh dược 2 trường hơp ,1 là đang load thì nó ko hiện thị lên ,2 là khi chuyển trang về cũ thfi phải load main activity
        if(duocphepsudungphienbannay==true){

            //checkAccount gọi đến hàm hanlderTime
            //chỉ check được 1 lần rồi chuyển trang
            checkAccount();
            //hàm này dùng để tạo bảng lưu số điện thoại khách hàng để hiển thị kiểu autcompletexview
            createTablesodienthoaiKH();

            //hàm hiện thị quảng cáo dạng fragment
            viewAdFragment();
            //hàm set view fragment ở homepage main trang chủ
            viewHomepageFragment();
            //navigationView.setNavigationItemSelectedListener(this);

            demloadthongtin = 3;
            demloadthongtin2 = 3;
            //hàm này để check liên tục để đẩy dữ liệu vào giao diện nhưng không cho phép tự ý chuyển trang
            handlerLoadlientuc.postDelayed(runnableLoadlientuc, 0);

            //set menu bên drawerlayout
            setOnclickLinearlayout();

            //thread hiện quảng cáo
            //handlerQuangcao.postDelayed(runnablequangcao,1000);

        }
     //  Toast.makeText(getApplicationContext(),duocphepsudungphienbannay+"",Toast.LENGTH_SHORT).show();

        //nguy hiểm vì để bên ngoài run hoài ,vì thoát ra vô lại activity này thì nó run lại
        //khiến tràn bộ nhớ
        handlerAd.postDelayed(runnableAd,0);
        //getSupportActionBar().setTitle("Trang chủ");
       handlerLoadthongtinkhikhoidong.postDelayed(runnableLoadthongtinkhikhoidong,0);
    }

    private void AnhXa(){
        khachhangtamthoi=new ArrayList<Class_Table_Khachhang>();

        drawerLayout=findViewById(R.id.drawer_layout_main);

       // tableLayout_ad=findViewById(R.id.tab_ad);
        viewPager_ad=findViewById(R.id.view_ad);
        tabLayout_homepage=findViewById(R.id.tab_home);
        viewPager_hompage=findViewById(R.id.view_homepage);
        navigationView =findViewById(R.id.nav_view_main);

        //ánh xạ header (chỉ khi dùng trong header mới dùng ánh xạ kiểu này)
        // View headerView = navigationView.getHeaderView(0);
        //ánh xạ truyền thống
        img1=findViewById(R.id.imgMain_user);
        txtName=findViewById(R.id.txtMain_name);
        txtNumberphone=findViewById(R.id.txtMain_numberphone);
        txtMakh=findViewById(R.id.txtMain_makh);

        txt_phantram=findViewById(R.id.txt_phantram_main);
        imgMember=findViewById(R.id.imgMain_Member);
        imgAdmin=findViewById(R.id.imgMain_Administrator);

        txtLoginorSignout=findViewById(R.id.txt_dangnhapOrdangxuatMain);
        scroll_lin1=findViewById(R.id.scroll_lin_menu_myhome);
        scroll_lin2=findViewById(R.id.scroll_lin_menu_phongdadat);
        scroll_lin3=findViewById(R.id.scroll_lin_menu_bandadat);
        scroll_lin4=findViewById(R.id.scroll_lin_menu_shopping);
        scroll_lin5=findViewById(R.id.scroll_lin_menu_minigame);
        scroll_lin6=findViewById(R.id.scroll_lin_menu_chamsockhachhang);
        scroll_lin7=findViewById(R.id.scroll_lin_menu_hotro);
        scroll_lin8=findViewById(R.id.scroll_lin_menu_dangnhap);
        scroll_lin9=findViewById(R.id.scroll_lin_menu_phanhoiungdung);
        scroll_lin10=findViewById(R.id.scroll_lin_menu_thoat);

        lin1_main=findViewById(R.id.lin1_main);
        progressBar1=findViewById(R.id.progressload_main);
        intentLoginOrSignup= new Intent(MainActivity.this, LoginOrSignupActivity.class);

        intentMenuMyhome=new Intent(MainActivity.this,MyHomeActivity.class);
        intentMenuPhongdadat =new Intent(MainActivity.this,MenuPhongdadatActivity.class);
        intentMenuBandadat=new Intent(MainActivity.this,MenuBandadatActivity.class);
        intentMenuPhongdadatAdmin=new Intent(MainActivity.this,MenuPhongdadatADMINActivity.class);
        intentMenuBandadatAdmin=new Intent(MainActivity.this,MenuBandadatADMINActivity.class);
        intentMenuSupport =new Intent(MainActivity.this,MenuCHATSupportActivity.class);
        intentMenuSupportAdmin=new Intent(MainActivity.this,MenuSupportAdminActivity.class);
        intentQuangcao=new Intent(MainActivity.this,QuangcaoActivity.class);
    }
    private void setOnclickLinearlayout(){

        //lần này set chung tất cả đã setonclick lấy id so sánh rồi xuất ra kết quả của case đó
        onClickListenerLinearlayout=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id){
                    case R.id.scroll_lin_menu_myhome:
                        if(khachhangtamthoi.size()>0){
                            startActivity(intentMenuMyhome);
                            overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                        }else {
                            Toast.makeText(getApplicationContext(),"Tính năng này không dành cho khách chưa đăng nhập !",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.scroll_lin_menu_phongdadat:
                        if(khachhangtamthoi.size()>0){
                            if(numberphoneAdministrator.size()>0){
                                startActivity(intentMenuPhongdadatAdmin);
                                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                                //quyền quản trị viên
                            }else{
                                startActivity(intentMenuPhongdadat);
                                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Tính năng này không dành cho khách chưa đăng nhập !",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.scroll_lin_menu_bandadat:
                        if(khachhangtamthoi.size()>0){
                            if(numberphoneAdministrator.size()>0){
                                startActivity(intentMenuBandadatAdmin);
                                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                            }
                            else {
                                startActivity(intentMenuBandadat);
                                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Tính năng này không dành cho khách chưa đăng nhập !",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.scroll_lin_menu_shopping:
                        Toast.makeText(getApplicationContext(),"Quý khách vui lòng liên hệ với Chăm sóc khách hàng để biết thêm chi tiết !",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.scroll_lin_menu_minigame:
                        if(khachhangtamthoi.size()>0){
                            Toast.makeText(getApplicationContext(),"Hãy chờ cuối tuần tham gia cùng chúng tôi nhé !",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Tính năng này không dành cho khách chưa đăng nhập !",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.scroll_lin_menu_chamsockhachhang:
                        if(khachhangtamthoi.size()>0){
                            String uriPhone = "tel:037-593-4643";
                            Intent intentCallphone = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse(uriPhone));
                            startActivity(intentCallphone);
                            overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                        }else {
                            Toast.makeText(getApplicationContext(),"Tính năng này không dành cho khách chưa đăng nhập !",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.scroll_lin_menu_hotro:
                        if(khachhangtamthoi.size()>0){
                            if(numberphoneAdministrator.size()>0){
                                startActivity(intentMenuSupportAdmin);
                                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                            }else{
                                startActivity(intentMenuSupport);
                                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Tính năng này không dành cho khách chưa đăng nhập !",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.scroll_lin_menu_dangnhap:
                        if(khachhangtamthoi.size()==0){
                            startActivity(intentLoginOrSignup);
                            overridePendingTransition(R.anim.chuyentrangtutrenxuongduoi,R.anim.giunguyentuthe);
                        }else {
                            Notification_thongbaodangxuat();
                        }
                        break;
                    case R.id.scroll_lin_menu_phanhoiungdung:

                        Intent  intentEmail = new Intent(Intent.ACTION_SEND);
                        intentEmail.setType("text/plain");
                        intentEmail.putExtra(Intent.EXTRA_EMAIL, "vunguyen464301@gmail.com");
                        intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi ứng dụng");
                        //nếu đăng nhập rồi thì lấy makh còn không thì lấy dạng guest
                        if(khachhangtamthoi.size()>0){
                            intentEmail.putExtra(Intent.EXTRA_TEXT, "Mã khách hàng: "+khachhangtamthoi.get(0).getMakhachhang());
                        }else{
                            intentEmail.putExtra(Intent.EXTRA_TEXT, "Guest : ");
                        }
                        startActivity(Intent.createChooser(intentEmail, "Send Email"));
                        overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
                        break;
                    case R.id.scroll_lin_menu_thoat:
                        Notification_thongbaothoat();
                        //System.exit(0);
                        break;
                        default:
                            Toast.makeText(getApplicationContext(),"Chưa set thuộc tính này !",Toast.LENGTH_SHORT).show();
                            break;

                }
                //hàm này dùng để khi click vào scrollview ở drawer thì nó tự động đóng lại
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
                drawer.closeDrawer(GravityCompat.START);
            }
        };

        //phải có tham số truyền vào nên là phải có id rồi mới set được nó
        //khởi tạo hàm và tham số truyền vào
        scroll_lin1.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin2.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin3.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin4.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin5.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin6.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin7.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin8.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin9.setOnClickListener(onClickListenerLinearlayout);
        scroll_lin10.setOnClickListener(onClickListenerLinearlayout);


    }


    private void checkAccount(){
        createDataBase();
        createTableKHACHHANG();
        dem =3;
        //hàm thread này dùng để kiểm tra khach hàng có đăng nhập hay chưa
        //với vấn đề là có chuyển trang ,sau 3s kiểm tra rồi thì nó chuyển trang nếu là chưua đăng nhập ,còn đăng nhập rồi thì lấy dữ liệu
        //ở thread tiếp theo
        handlerTime.postDelayed(runTime,0);
    }
    private void openAccount() {

        if (khachhangtamthoi.size() > 0 && cursorKH.getCount() == 0) {

            //lấy dữ liệu trong khachhangtamthoi arraylist
            //chỉ lưu duy nhất 1 khách hàng trên bộ nhớ tạm thời
            Class_Table_Khachhang kh = khachhangtamthoi.get(0);
            txtName.setText(kh.getTenkhachhang());
            //lỡ như người dùng đăng kí tên khách hàng bỏ trống thì hiển thị "chưa đặt tên"
            if (kh.getTenkhachhang().equals("")) {
              //  txtName.setText(R.string.chuadatten);
            }
            txtNumberphone.setText(kh.getSodienthoai());
            txtMakh.setText(kh.getMatkhau()+"");
            //giải thích ,sét tên đưa vô trước if ,nếu như là kiểm tra ko phải thì khỏi set lại ,nếu mà kiểm tra null thì set lại ,
            //tiết kiệm được 2 dòng
        } else {
            //ngược lại lấy từ database đổ vô arraylist

            //  Toast.makeText(getApplicationContext(),"tai khoan da duoc luu trong Database SQLite",Toast.LENGTH_SHORT).show();
            //ngoài ra người dùng đăng nhập lần 2 thì arraylist lưu trong ram sẽ bị xóa nên là kiểm tra trong database

            cursorKH.moveToFirst();
            class_table_khachhang = new Class_Table_Khachhang();
            class_table_khachhang.setMakhachhang(cursorKH.getInt(0));
            class_table_khachhang.setSodienthoai(cursorKH.getString(1));
            class_table_khachhang.setHinhanh(cursorKH.getString(2));
            class_table_khachhang.setTenkhachhang(cursorKH.getString(3));
            class_table_khachhang.setNgaythangnamsinh(cursorKH.getString(4));
            class_table_khachhang.setDiachi(cursorKH.getString(5));
            class_table_khachhang.setMatkhau(cursorKH.getString(6));
            //vì là thoát ra vô lại nên mới xét xuống trường hợp sử dụng trong sqlite nên ko cần phải xóa
            khachhangtamthoi.add(class_table_khachhang);

            cursorKH.close();
            //set ra ngoài giao diện
            Class_Table_Khachhang kh = khachhangtamthoi.get(0);
            txtName.setText(kh.getTenkhachhang());
            //lỡ như người dùng đăng kí tên khách hàng bỏ trống thì hiển thị "chưa đặt tên"
            if (kh.getTenkhachhang().equals("")) {
                txtName.setText(R.string.chuadatten);
            }
            txtNumberphone.setText(kh.getSodienthoai());
            txtMakh.setText(kh.getMatkhau()+"");
            }
            //vì là insert vô arraylist từ database lại nên không cần phải kiểm tra
            //khởi tạo lại get ra
        Class_Table_Khachhang kh = khachhangtamthoi.get(0);
        //set avatar adminitraster hay member
        //vì khi vô trường hợp này thì phải có tài khoản nó mới vô if else này
                    laycsdlKHACHHANG_ADMINISTRATOR(kh.getSodienthoai());
//        Toast.makeText(getApplicationContext(),numberphoneAdministrator.size()+" ",Toast.LENGTH_SHORT).show();

    }
    //hàm này dùng để kiểm tra mật khẩu ,nếu như lỡ như người dùng thay đổi mật khẩu thì phải thay đổi lại

    private void laycsdlKHACHHANGonMySQL(String url, final String matkhau) {
        khachhangtamthoi.clear();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Class_Table_Khachhang khachHang;
                        khachHang = new Class_Table_Khachhang(jsonObject.getInt("MAKHACHHANG"),
                                jsonObject.getString("SODIENTHOAI"),
                                jsonObject.getString("HINHANH"),
                                jsonObject.getString("TENKHACHHANG"),
                                jsonObject.getString("NGAYTHANGNAMSINH"),
                                jsonObject.getString("DIACHI"),
                                jsonObject.getString("MATKHAU"));

                        khachhangtamthoi.add(khachHang);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(matkhau==khachhangtamthoi.get(0).getMatkhau()){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công !",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại do tài khoản đã bị đổi mật khẩu!",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    private void createDataBase(){
        databaseNhaHangKhachSan =openOrCreateDatabase("qlnhahangkhachsan.db",MODE_PRIVATE,null);
    }
    private void createTableKHACHHANG(){
        String sql ="CREATE TABLE if not exists KHACHHANG(";
        sql +="makh INTEGER primary key,";
        sql +="sodienthoai TEXT ,";
        sql +="hinhanh TEXT,";
        sql +="tenkh TEXT,";
        sql +="NTNamsinh TEXT,";
        sql +="diachi TEXT,";
        sql +="matkhau TEXT );";
        databaseNhaHangKhachSan.execSQL(sql);
    }
    private void createTablesodienthoaiKH(){
        String sql ="CREATE TABLE if not exists NUMBERPHONE_KHACHHANG(";
        sql +="sothutu INTEGER primary key autoincrement,";
        sql +="sodienthoai TEXT );";
        databaseNhaHangKhachSan.execSQL(sql);
    }
    private void laycsdlKHACHHANG_ADMINISTRATOR(String str){
        numberphoneAdministrator=new ArrayList<String>();
        String url =urlpublic+"siteNhaHangvaKhachSan/read_khachhang_administrator.php?SODT="+str;
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        numberphoneAdministrator.add(jsonObject.getString("SODIENTHOAI"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    private void kiemtrasodienthoaivamatkhau(){
        cursorKH.moveToFirst();
        String sodienthoai=cursorKH.getString(1);
        String matkhau=cursorKH.getString(6);
        String url =urlpublic +"siteNhaHangvaKhachSan/read_khachhang_where.php?SODT="+sodienthoai;
        laycsdlKHACHHANGonMySQL(url,matkhau);

    }
    private void deleteDulieutrongdatabasevaKhachhangtamthoi(){
        Class_Table_Khachhang kh = khachhangtamthoi.get(0);
        databaseNhaHangKhachSan.delete("KHACHHANG", "makh=?", new String[]{kh.getMakhachhang() + ""});
        khachhangtamthoi.clear();
    }


    private void customActionbar(){
        toggle =new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //ở ngay hàm main gọi hàm này nên khỏi phải kiếm đâu xa
    private Handler handlerLoadthongtinkhikhoidong=new Handler();
    private Runnable runnableLoadthongtinkhikhoidong=new Runnable() {
        @Override
        public void run() {
            activity_an_progress_hien();
            demloadthongtinkhikhoidong=demloadthongtinkhikhoidong+1;
            handlerLoadthongtinkhikhoidong.postDelayed(runnableLoadthongtinkhikhoidong,4000);
            //3 nhân cho 17=51 ,dư 1 vẫn còn hơn thiếu

            if(demloadthongtinkhikhoidong<=1){
                //kiểm tra mạng
                String text="Đang kiểm tra mạng";
                //hiện thị phần trăm đầu tiên
                txt_phantram.setText(demloadthongtinkhikhoidong+"% "+text);
                //time run theo công thức 50%x=4000 ,để chạy trong quãng thời gian <4000milis thì là
                //thì s=4000/50 =80 ,thời gian này chạy dc 50 phần trăm cần phải chạy ,vì thế để không đạt tới 4000 thì nên là
                //số 79 ,78 ,77 là con số chính xác
                //78 là con số chính xác ,vì vô thread nó ngủ rồi mới khởi động ,tính từ lúc vô thì nó mới bắt đầu ngủ thì chưa cộng
                //gì hết nên là 1 con số 51 là số phần trăm cần phải chạy
                asyncTaskLoadphantram =new AsyncTaskLoadphantram(MainActivity.this,text,0,78);
                asyncTaskLoadphantram.execute();
                //hàm kiểm tra mạng là checkinternet
                if(checkInternet()==false){
                    Notification_noInternet();
                    handlerLoadthongtinkhikhoidong.removeCallbacks(runnableLoadthongtinkhikhoidong);
                    asyncTaskLoadphantram.cancel(true);
                }
            }else {
                //kiểm tra phiên bản hiện tại
                duocphepsudungphienbannay=false;
                String text="Kiểm tra phiên bản";
                //hiện thị phần trăm thứ 51
                txt_phantram.setText(demloadthongtinkhikhoidong+"% "+text);

                asyncTaskLoadphantram =new AsyncTaskLoadphantram(MainActivity.this,text,50,78);
                asyncTaskLoadphantram.execute();

                String url =urlpublic+"siteNhaHangvaKhachSan/read_app_phienban.php?MAPHIENBAN="+maphienban;
                //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                laycsdlAPP_PHIENBANonMySQL(url);
                handlerLoadthongtinkhikhoidong.removeCallbacks(runnableLoadthongtinkhikhoidong);

                handlerloadphienbanhientai.postDelayed(runnableloadphienbanhientai,1000);
            }

        }
    };
    //khi gọi thread này đã là 1s rồi
    //còn demloadphienban=demloadphienban-1=3
    //1s nữa là 2
    //1s nữa là 1
    //1s nữa là 0
    //tổng hết 4s thỏa yêu cầu run từ 51 đến 100
    private int demloadphienbanhientai=4;
    private Handler handlerloadphienbanhientai=new Handler();
    private Runnable runnableloadphienbanhientai=new Runnable() {
        @Override
        public void run() {
            demloadphienbanhientai=demloadphienbanhientai-1;
            //Toast.makeText(getApplicationContext(),txt_phantram.getText().toString(),Toast.LENGTH_SHORT).show();
            handlerloadphienbanhientai.postDelayed(runnableloadphienbanhientai,1000);
            if(demloadphienbanhientai==0){
                //Toast.makeText(getApplicationContext(),"ma phien ban :" +maphienban,Toast.LENGTH_SHORT).show();
                if(duocphepsudungphienbannay==false){
                    Notification_Apphethansudung();

                }else{
                   // Toast.makeText(getApplicationContext(),"Thanh cong",Toast.LENGTH_SHORT).show();
                    activity_hien_progress_an();


                    //checkAccount gọi đến hàm hanlderTime
                    //chỉ check được 1 lần rồi chuyển trang
                    checkAccount();
                    //hàm này dùng để tạo bảng lưu số điện thoại khách hàng để hiển thị kiểu autcompletexview
                    createTablesodienthoaiKH();

                    //hàm hiện thị quảng cáo dạng fragment
                    viewAdFragment();
                    //hàm set view fragment ở homepage main trang chủ
                    viewHomepageFragment();
                    //navigationView.setNavigationItemSelectedListener(this);

                    demloadthongtin = 3;
                    demloadthongtin2 = 3;
                    //hàm này để check liên tục để đẩy dữ liệu vào giao diện nhưng không cho phép tự ý chuyển trang
                    handlerLoadlientuc.postDelayed(runnableLoadlientuc, 0);
                    // handlerAd.postDelayed(runnableAd,0);
                    //set menu bên drawerlayout
                    setOnclickLinearlayout();

                    //phần hiện quàng cáo
                    String urlquangcao =urlpublic+"siteNhaHangvaKhachSan/read_quangcao.php";
                    laycsdlQUANGCAO_onMySQL(urlquangcao);
                    //thread hiện quảng cáo
                    handlerQuangcao.postDelayed(runnablequangcao,300000);


                    //tùy chỉnh action bar
                    customActionbar();

                }
                //Toast.makeText(getApplicationContext(),duocphepsudungphienbannay+"",Toast.LENGTH_SHORT).show();
                handlerloadphienbanhientai.removeCallbacks(runnableloadphienbanhientai);
            }
        }
    };

    private void laycsdlAPP_PHIENBANonMySQL(String url) {
        RequestQueue requestQueue = new Volley().newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        duocphepsudungphienbannay=true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Lỗi phiên bản: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    private boolean checkInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if( activeNetworkInfo != null && activeNetworkInfo.isConnected()){
            return true;
        }
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //nếu click vào thì nó sẽ xổ ra
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void Notification_thongbaothoat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thoát");
        builder.setMessage("Bạn muốn thoát ?");
        builder.setNegativeButton("Tôi đồng ý !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialogInterface.cancel();
                handlerAd.removeCallbacks(runnableAd);
                handlerLoadlientuc.removeCallbacks(runnableLoadlientuc);
                handlerQuangcao.removeCallbacks(runnablequangcao);
                System.exit(0);
            }
        });
        builder.setPositiveButton("Tôi không chắc !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Notification_thongbaodangxuat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn muốn đăng xuất ?");
        builder.setNegativeButton("Tôi đồng ý !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialogInterface.cancel();
               //xóa dữ liệu
                //xóa trong database
                //xóa trong arraylist
                Cursor cursor =databaseNhaHangKhachSan.query("KHACHHANG",null,null,null,null,null,null) ;
                //kiểm tra trong database có lưu dữ liệu hay không
                if(cursor.getCount()>0) {
                    if (databaseNhaHangKhachSan.delete("KHACHHANG", "makh=?", new String[]{khachhangtamthoi.get(0).getMakhachhang() + ""}) == 0) {
                        Toast.makeText(getApplicationContext(), "Đăng xuất thất bại", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    }
                }
                khachhangtamthoi.clear();
                //Toast.makeText(getApplicationContext(),"khachhangtamthoi = "+khachhangtamthoi.size()+"\n"+"khachhangdatabase = "+cursor.getCount(),Toast.LENGTH_SHORT).show();

                txtLoginorSignout.setText("Đăng nhập");


                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.guest);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                roundedBitmapDrawable.setCircular(true);
                img1.setImageDrawable(roundedBitmapDrawable);

                txtName.setText("Khách vô danh");
                txtNumberphone.setText("Không hiển thị");
                txtMakh.setText("Không hiển thị");

                //tạo biến đếm lại ,ko thì nó lại lấy biến đếm kia đã đếm ==0 rồi nó lại trừ tiếp qua bên kia chứ
                //không có xuống kia set lại =3 vì là chạy lần đầu tiên thì dòng đầu tiên
                demloadthongtin=3;
                demloadthongtin2=3;
                handlerLoadlientuc.postDelayed(runnableLoadlientuc,1000);

                Toast.makeText(getApplicationContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                progressBar1.setVisibility(View.VISIBLE);
            }
        });
        builder.setPositiveButton("Tôi không chắc !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void activity_an_progress_hien(){
        //invisible ẩn ,visible hiện
        lin1_main.setVisibility(View.INVISIBLE);
        navigationView.setVisibility(View.INVISIBLE);
        progressBar1.setVisibility(View.VISIBLE);
        txt_phantram.setVisibility(View.VISIBLE);
    }
    private void activity_hien_progress_an(){
        lin1_main.setVisibility(View.VISIBLE);
        navigationView.setVisibility(View.VISIBLE);
        progressBar1.setVisibility(View.INVISIBLE);
        txt_phantram.setVisibility(View.INVISIBLE);
    }
    private void Notification_noInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Kết nối mạng không khả dụng");
        builder.setMessage("Vui lòng kết nối lại");
        builder.setNegativeButton("Tôi đồng ý !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialogInterface.cancel();
               //System.exit(0);
                Intent intentSetting =new Intent(Settings.ACTION_HOME_SETTINGS);
                startActivity(intentSetting);
            }
        });
        builder.setPositiveButton("Tôi từ chối !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Notification_Apphethansudung() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Ứng dụng của bạn quá hạn !");
        builder.setMessage("Vui lòng tải phiên bản mới !");
        builder.setNegativeButton("Tôi đồng ý !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialogInterface.cancel();
                //System.exit(0);
                Intent intentPlayStore = new Intent(Intent.ACTION_VIEW);
                //https://developer.android.com/distribute/marketing-tools/linking-to-google-play?hl=vi
                intentPlayStore.setData(Uri.parse("market://"));
                startActivity(intentPlayStore);
            }
        });
        builder.setPositiveButton("Tôi từ chối !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        //nếu đóng băng hàm này thì có thể gọi những hàm khác mà vẫn trong tình trạng ở activity đó
        //nếu không đóng băng hàm này
        //thì sẽ vừa hiện hành động
        //vừa thoát ra
       // super.onBackPressed();
       Notification_thongbaothoat();
    }


    @Override
    public void setOnclick(int result) {
        viewPager_ad.setCurrentItem(result);
    }

    //phần quảng cáo
    private Handler handlerQuangcao =new Handler();
    private Runnable runnablequangcao=new Runnable() {
        @Override
        public void run() {
            if(demhinh>0) {
                //handlerQuangcao.postDelayed(runnablequangcao,30000);
                //vô quảng cáo này rồi phải thread này lại
                handlerQuangcao.removeCallbacks(runnablequangcao);
                vitrihinhquangcao=vitrihinhquangcao+1;
                //Toast.makeText(getApplicationContext(),"so luong hinh quang cao :"+demhinh,Toast.LENGTH_SHORT).show();
                intentQuangcao.putExtra("hinhanh",hinhanhquangcao.get(vitrihinhquangcao));
                startActivityForResult(intentQuangcao, codeQuangcao);
                overridePendingTransition(R.anim.phongtotu0ra1, R.anim.giunguyentuthe);
            }
        }
    };
    private int codeQuangcao=111;
    private ArrayList<String> hinhanhquangcao;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==codeQuangcao && resultCode==RESULT_OK){
           // Toast.makeText(getApplicationContext(),"da lay duoc du lieu "+data.getBooleanExtra("quangcao",false),Toast.LENGTH_SHORT).show();
            handlerQuangcao.postDelayed(runnablequangcao,300000);
        }

    }

    //lấy csdl bảng quảng cáo có tồn tại dữ liệu hay không ,nếu không tồn tại dữ liệu thì không cho chuyển trang
    private void laycsdlQUANGCAO_onMySQL(String url) {
        hinhanhquangcao=new ArrayList<>();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                       demhinh=demhinh+1;
                       hinhanhquangcao.add(jsonObject.getString("HINHANH"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Lỗi quảng cáo : "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
}
