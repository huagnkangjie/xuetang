import java.util.Collection;
import java.util.Date;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;  
public class Ognl {
	/**
	 * 可以用于判断 Map,Collection,String,Array是否为空
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object o) throws IllegalArgumentException {
		if (o == null)
			return true;
		if (o instanceof String) {
			return StringUtils.isEmpty(o);
		} else if (o instanceof Collection) {
			return CollectionUtils.isEmpty((Collection) o);
		} else if (o.getClass().isArray()) {
			return ArrayUtils.isEmpty((Object[]) o);
		}
		
		else if (o instanceof Date) {
			return o == null;
		} else if (o instanceof Number) {
			return o == null;
		} else if (o instanceof Boolean) {
			return o == null;
		} else {
			throw new IllegalArgumentException("Illegal argument type,must be : Map,Collection,Array,String. but was:"
					+ o.getClass());
		}


	}

	/**
	 * 可以用于判断 Map,Collection,String,Array是否不为空
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Object... objects) {
		if (objects == null)
			return false;
		for (Object obj : objects) {
			if (isEmpty(obj)) {
				return false;
			}
			;
		}
		return true;
	}


	public static boolean isNumber(Object o) {
		if (o instanceof Number) {
			return true;
		} else if (o instanceof String) {
			return NumberUtils.isNumber((String) o);
		} else {
			return false;
		}
	}


}
