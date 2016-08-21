package weather.newer.com.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity " ;
    IMathService proxy;//远程服务的代理对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        //绑定服务
        super.onResume();
        bindService(
             new Intent(this,MathService.class),
                conn,
                BIND_AUTO_CREATE


        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解除绑定
        unbindService(conn);
    }
    ServiceConnection   conn=new ServiceConnection() {
        @Override
        /**
         *
         * 该方法用于监听访问者与服务是否绑定成功
         */
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接远程服务，获得远程服务的代理对象
            proxy=IMathService.Stub.asInterface(service);
            try {
                String []data=  proxy.getData();
                Log.d(TAG, Arrays.toString(data) );
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                String s=proxy.sayHello("小仙女");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /**
         *
         * 当应用程序进程意外终止的时候回调该方法
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    public void doAdd(View view) throws RemoteException {
        int r=proxy.addd(1, 2);
        Button btn=(Button)view;
        btn.setText(String.valueOf(r));

    }
}
