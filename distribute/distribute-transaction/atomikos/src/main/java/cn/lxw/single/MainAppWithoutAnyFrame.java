package cn.lxw.single;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.core.toolkit.IOUtils;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

public class MainAppWithoutAnyFrame {
    public static AtomikosDataSourceBean studentDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean() ;
        ds.setUniqueResourceName("studentDataSource") ;
        ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource") ;
        Properties xaProperties = new Properties() ;
        xaProperties.setProperty("url", "jdbc:mysql://192.168.0.64:3306/rcs_info?useSSL=false&serverTimezone=UTC") ;
        xaProperties.setProperty("user", "rcs_user") ;
        xaProperties.setProperty("password", "rcs@202103291104.") ;
        ds.setXaProperties(xaProperties) ;
        ds.setMinPoolSize(10) ;
        ds.setMaxPoolSize(10) ;
        ds.setBorrowConnectionTimeout(30) ;
        ds.setMaxLifetime(60) ;
        ds.setMaintenanceInterval(60) ;
        return ds ;
    }

    public static AtomikosDataSourceBean courseDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean() ;
        ds.setUniqueResourceName("courseDataSource") ;
        ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource") ;
        Properties xaProperties = new Properties() ;
        xaProperties.setProperty("url", "jdbc:mysql://localhost:3306/coursedb?useSSL=false&serverTimezone=UTC") ;
        xaProperties.setProperty("user", "root") ;
        xaProperties.setProperty("password", "123456") ;
        ds.setXaProperties(xaProperties) ;
        ds.setMinPoolSize(5) ;
        ds.setMaxPoolSize(10) ;
        ds.setBorrowConnectionTimeout(30) ;
        ds.setMaxLifetime(60) ;
        ds.setMaintenanceInterval(60) ;
        return ds ;
    }

    public static void main(String[] args) {
        // get the datasource of studentdb
        AtomikosDataSourceBean studentDataSource = studentDataSource() ;
        Connection studentConn = null;
        PreparedStatement studentPs = null ;
        // get the datasource of coursedb
        AtomikosDataSourceBean courseDataSource = courseDataSource() ;
        Connection courseConn = null ;
        PreparedStatement coursePs = null ;
        // transaction
        UserTransaction tx = new UserTransactionImp() ;
        try {
            // #1 begin transaction
            tx.begin() ;
            // studentdb execute sql
            studentConn = studentDataSource.getConnection() ;
            studentPs = studentConn.prepareStatement("INSERT INTO `tbl_student` (`name`, `age`) VALUES ('1', '1')") ;
            studentPs.executeUpdate() ;

            // coursedb execute sql
            courseConn = courseDataSource.getConnection() ;
            coursePs = courseConn.prepareStatement("INSERT INTO `tbl_course` (`name`) VALUES ('7777');") ;
            coursePs.executeUpdate();
            // guess will the exception whether occur or not
//            if(Math.random() > 0.5){
//                throw new RuntimeException("User-defined exception occurred");
//            }
            // #2 commit transaction
            tx.commit() ;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // #3 rollback transaction
                tx.rollback();
            }catch (SystemException sex){
                // do some compensation

            }
        } finally {
            IOUtils.closeQuietly(studentConn);
            IOUtils.closeQuietly(studentPs);
            IOUtils.closeQuietly(courseConn);
            IOUtils.closeQuietly(coursePs);
        }
    }
}
