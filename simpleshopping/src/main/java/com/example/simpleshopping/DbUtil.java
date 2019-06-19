package com.example.simpleshopping;

import com.example.dbdao.CollectBeanDao;
import com.example.dbdao.DaoMaster;
import com.example.dbdao.DaoSession;

public class DbUtil {
    private static DbUtil dbUtil;
    private final CollectBeanDao collectBeanDao;

    private DbUtil(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BaseApplication.getApplication(),"db_dao",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        collectBeanDao = daoSession.getCollectBeanDao();
    }

    public static DbUtil getDbUtil() {
        if (dbUtil == null){
            synchronized (DbUtil.class){
                if (dbUtil == null){
                    dbUtil = new DbUtil();
                }
            }
        }
        return dbUtil;
    }

    public CollectBeanDao getCollectBeanDao() {
        return collectBeanDao;
    }
}
