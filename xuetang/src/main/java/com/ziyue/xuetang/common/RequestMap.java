package  com.ziyue.xuetang.common;

import java.util.HashMap;

public class RequestMap<K, V> extends HashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2067562915630849626L;
	
	public int getInt(Object key) {
        Object value = super.get(key);
        if(null != value &&!value.equals("") && !key.equals("")){
        	if(value instanceof String){
        		return Integer.valueOf(value.toString());
        	}else{
        		return (Integer)value;
        	}
        }
        return 0;
    }
	
	public long  getLong(Object key) {
        Object value = super.get(key);
        try{
        if(null != value && !value.equals("")&& !key.equals("")){
        	if(value instanceof String){
        		return Long.getLong(value.toString());
        	}else{
        		return (long)value;
        	}
        }
        }catch(Exception e){
        	
        }
        return 0;
    }
	
	
	
	
	public String getString(Object key) {
        Object value = super.get(key);
        if(null != value){
        	if(value instanceof String){
        		return String.valueOf(value);
        	}else{
        		return String.valueOf(value);
        	}
        }
        return null;
    }
	
	
	public String getString(String key) {
        Object value = super.get(key);
        if(null != value){
        	if(value instanceof String){
        		return String.valueOf(value);
        	}else{
        		return String.valueOf(value);
        	}
        }
        return "";
    }
	
	public float getFloat(Object key) {
        Object value = super.get(key);
        if(null != value&&!value.equals("")){
        	if(value instanceof String){
        		return Float.valueOf(value.toString());
        	}else{
        		return (Float)value;
        	}
        }
        return 0f;
    }
	
	public double getDouble(Object key) {
        Object value = super.get(key);
        if(null != value&&!value.equals("")){
        	if(value instanceof String){
        		return Double.valueOf(value.toString());
        	}else{
        		return (Double)value;
        	}
        }
        return 0d;
    }
	
	public boolean getBoolean(Object key) {
        Object value = super.get(key);
        if(null != value&&!value.equals("")){
        	if(value instanceof String){
        		return Boolean.valueOf(value.toString());
        	}else{
        		return (Boolean)value;
        	}
        }
        return false;
    }
	
	public static void main(String[] args) {
		
		RequestMap m=new RequestMap<>();
		
		System.out.println(m.getInt(null));
		
	}
}
