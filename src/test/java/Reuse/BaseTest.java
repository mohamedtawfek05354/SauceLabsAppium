package Reuse;

import Device.Device;
import appiumServer.appiumServer;

public class BaseTest {

    protected static boolean beforeAll =true;
    public static appiumServer server =new appiumServer();
    public static Device device=new Device();
}
