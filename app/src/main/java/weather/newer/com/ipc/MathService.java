package weather.newer.com.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MathService extends Service {

ServiceImpl  service;
    @Override
    public void onCreate() {
        super.onCreate();
        service=new ServiceImpl();
}

    @Override
    public IBinder onBind(Intent intent) {
        //返回服务的实现（远程服务）
       return service.asBinder();
    }

    /**
     *
     * 继承存根，因为存根是一个抽象方法，实现服务的aidl接口,做真正的业务实现
     */
    class ServiceImpl extends IMathService.Stub{
        String [] data={"0","1","2"};
        @Override
        public int addd(int a, int b) throws RemoteException {
            return  a+b;
        }

        @Override
        public String sayHello(String name) throws RemoteException {
            return "hello"+name;
        }

        @Override
        public String[] getData() throws RemoteException {
            return new String[0];
        }
    }

}
