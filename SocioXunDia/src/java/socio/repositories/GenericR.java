package socio.repositories;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class GenericR<E> {
    private Connection conn;
    private E e=null;

    public GenericR(Connection conn,Class clazz) {
        this.conn = conn;
        try{ this.e= (E)clazz.newInstance(); } catch(Exception ex){ System.out.println(ex); }
    }
    
    public void save(E e){
        try {
            PreparedStatement ps=conn.prepareStatement(createQuery(e),1);
            Field[] fields=e.getClass().getDeclaredFields();
            for(int a=0;a<fields.length;a++){
                String name=fields[a].getName();  
                String method="get"+name.substring(0,1).toUpperCase()+name.substring(1, name.length());
                //System.out.println(method);
                if(a==0) ps.setObject(1, null);
                else ps.setObject(a+1, e.getClass().getMethod(method, null).invoke(e));
            }
            ps.execute();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()) e.getClass().getMethod("setId", int.class).invoke(e,rs.getInt(1));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private String createQuery(E e) {
        //String table=e.getClass().getSimpleName();
        String query="";
        try {
            String table=(String)e.getClass().getMethod("getTable", null).invoke(e);
            //System.out.println(table);
            query="insert into "+table+" (";
            Field[] fields=e.getClass().getDeclaredFields();
            //for(Field f:fields) System.out.println(f);
            for(int a=0;a<fields.length;a++){
                if(a!=0) query+=",";
                query+=fields[a].getName();
            }
            query+=") values (";
            for(int a=0;a<fields.length;a++){
                if(a!=0) query+=",";
                query+="?";
            }
            query+=")";
            System.out.println(query);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return query;
    }
    
    public void remove(E e){
        if (e==null) return;
        try{
            String query="delete from "+e.getClass().getMethod("getTable", null).invoke(e)
                    +" where id="+e.getClass().getMethod("getId", null).invoke(e);
            conn.createStatement().execute(query);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public void update(E e){
        if(e ==null) return;
        try {
            String query= "update "+e.getClass().getMethod("getTable", null).invoke(e)+ " set ";
            Field[] fields=e.getClass().getDeclaredFields();
            for(int a=1;a<fields.length;a++){
                if(a!=1) query+=",";
                query+=fields[a].getName()+"=?";
            }
            query+=" where id=?";
            //System.out.println(query);
            PreparedStatement ps=conn.prepareStatement(query);
            for(int a=1;a<fields.length;a++){
                String name=fields[a].getName();
                String method="get"+name.substring(0,1).toUpperCase()+name.substring(1);
                ps.setObject(a, e.getClass().getMethod(method, null).invoke(e));
            }
            ps.setObject(fields.length, e.getClass().getMethod("getId", null).invoke(e));
            ps.execute();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public List<E>getByFiltro(String filtro){
        String query="select * from ";
        List<E>lista=new ArrayList();
        try {
            String tabla=(String)e.getClass().getMethod("getTable", null).invoke(e);
            query+=tabla+" where "+filtro;
            //System.out.println(query);
            ResultSet rs=conn.createStatement().executeQuery(query);
            ResultSetMetaData rsmd=rs.getMetaData();
            while(rs.next()){
                e=(E)e.getClass().newInstance();
                for(int a=1;a<=rsmd.getColumnCount();a++){
                    String field=rsmd.getColumnName(a);
                    String method="set"+field.substring(0, 1).toUpperCase()+field.substring(1);
                    //System.out.println(method);
                    if(rsmd.getColumnTypeName(a).equals("INT"))
                        e.getClass().getMethod(method, int.class).invoke(e, rs.getInt(field));
                    if(rsmd.getColumnTypeName(a).equals("VARCHAR"))
                        e.getClass().getMethod(method, String.class).invoke(e, rs.getString(field));
                }
                lista.add(e);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lista;
    }
    
    public E getById(int id){
        List<E> lista=getByFiltro("id="+id);
        return (lista==null || lista.isEmpty())?null:lista.get(0);
    }
    
    public List<E> getAll(){
        return getByFiltro("1=1");
    }
    
}
