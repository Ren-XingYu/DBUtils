package x.y.dbutils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBUtilsTest {
    @Test
    public void testInsert(){
        try {
            ComboPooledDataSource dataSource=new ComboPooledDataSource();
            QueryRunner queryRunner=new QueryRunner(dataSource);
            queryRunner.update("insert into account values (?,?,?)",3,"wangwu",1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDelete(){
        try {
            ComboPooledDataSource dataSource=new ComboPooledDataSource();
            QueryRunner queryRunner=new QueryRunner(dataSource);
            queryRunner.update("delete from account where id=?",3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testModify(){
        try {
            ComboPooledDataSource dataSource=new ComboPooledDataSource();
            QueryRunner queryRunner=new QueryRunner(dataSource);
            queryRunner.update("update account set money=? where id=?",1500,3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testQuery_1(){
        try {
            ComboPooledDataSource dataSource=new ComboPooledDataSource();
            QueryRunner queryRunner=new QueryRunner(dataSource);
            Account account=queryRunner.query("select * from account where id=?", new ResultSetHandler<Account>() {
                @Override
                public Account handle(ResultSet resultSet) throws SQLException {
                    Account account=new Account();
                    while (resultSet.next()){
                        int id=resultSet.getInt("id");
                        String name=resultSet.getString("name");
                        int money=resultSet.getInt("money");
                        account.setId(id);
                        account.setName(name);
                        account.setMoney(money);
                    }
                    return account;
                }
            }, 1);
            System.out.println(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testQuery_2(){
        try {
            ComboPooledDataSource dataSource=new ComboPooledDataSource();
            QueryRunner queryRunner=new QueryRunner(dataSource);
            Account account=queryRunner.query("select * from account where id=?", new BeanHandler<>(Account.class), 1);
            System.out.println(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testQuery_3(){
        try {
            ComboPooledDataSource dataSource=new ComboPooledDataSource();
            QueryRunner queryRunner=new QueryRunner(dataSource);
            List<Account> accounts=queryRunner.query("select * from account", new BeanListHandler<>(Account.class));
            for (Account account:accounts){
                System.out.println(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
