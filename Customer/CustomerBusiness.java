package Customer;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.DataConnection;

public class CustomerBusiness {
    public static ArrayList<Customer> getListCustomers() {
        ArrayList<Customer> list = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DataConnection.setConnect(); 
        
            String sql = "Select idCustomer, name, phone from customer";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            Customer obj = new Customer();
            while (rs.next()) {
                obj.setId(rs.getString("idCustomer"));
                obj.setName(rs.getString("name"));
                obj.setPhone(rs.getString("phone"));

                list.add(obj);
            }
        } catch (SQLException e) {
            Logger.getLogger(CustomerBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return list;
    }

    public static boolean addCustomer(Customer obj) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();
            
            String sql = "insert into customer(idCustomer, name, phone) values(?, ?, ?)";
            PreparedStatement psm = conn.prepareStatement(sql);
            
            psm.setString(1, obj.getId());
            psm.setString(2, obj.getName());
            psm.setString(3, obj.getPhone());

            return psm.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(CustomerBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return false;
    }

    public static boolean deleteCustomer(String idCustomer) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();

            String sql = "delete from customer where idCustomer = '" + idCustomer + "'";
            Statement stm = conn.createStatement();

            return stm.executeUpdate(sql) > 0;
        } catch(SQLException e) {
            Logger.getLogger(CustomerBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                System.out.println("Cannot close connection");
            }
        }
        
        return false;
    }

    public static boolean updateCustomer(Customer obj) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();

            String sql = "update customer set name = ?, phone = ? where idCustomer = ?";
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(3, obj.getId());
            psm.setString(1, obj.getName());
            psm.setString(2, obj.getPhone());
            
            return psm.executeUpdate() > 0;

        } catch(SQLException e) {
            Logger.getLogger(CustomerBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return false;
    }

    public static Customer showCustomerDetail(String idCustomer) {
        Connection conn = null;
        
        Customer obj = new Customer();
        try {
            conn = DataConnection.setConnect();

            String sql = "select * from customer where idCustomer = '" + idCustomer + "'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            
            while (rs.next()) {
                obj.setId(rs.getString("idCustomer"));
                obj.setName(rs.getString("name"));
                obj.setPhone(rs.getString("phone"));
            }
        } catch(SQLException e) {
            Logger.getLogger(CustomerBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return obj;
    }
}