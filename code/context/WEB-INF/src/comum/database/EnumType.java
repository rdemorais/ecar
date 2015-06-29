package comum.database;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.type.NullableType;
import org.hibernate.type.TypeFactory;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 *
 * @author 70744416353
 */
public class EnumType implements EnhancedUserType, ParameterizedType  {
    private Class<? extends Enum> enumClass;
    private Class<?> identifierType;

    private Method identifierMethod;
    private Method valueOfMethod;
    
    private static final String defaultIdentifierMethodParameter = "name";
    private static final String enumClassParameter = "enumClass";
    private static final String identifierMethodParameter = "identifierMethod";
    private static final String valueMethodParameter = "valueOfMethod";
    private static final String defaultValueParameter = "valueOf";
    
    private NullableType type;
    private int [] sqlTypes;
    
    /**
     *
     * @param _parameters
     */
    public void setParameterValues(Properties _parameters) {
        String enumClassName = _parameters.getProperty(enumClassParameter);
        try {
            enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
        }
        catch (ClassNotFoundException exception) {
            throw new HibernateException("Enum class not found", exception);
        }
        
        String identifierMethodName = 
            _parameters.getProperty(identifierMethodParameter, defaultIdentifierMethodParameter);

        try {
            identifierMethod = enumClass.getMethod(identifierMethodName, new Class[0]);
            
            //Identifica o tipo retorno deste type e o parametro do metodo que retorna o metodo do Enum
            identifierType = identifierMethod.getReturnType();
        }
        catch(Exception exception) {
            throw new HibernateException("Failed to optain identifier method", exception);
        }
        
        
        type = (NullableType)TypeFactory.basic(identifierType.getName());
        
        if(type == null)
            throw new HibernateException("Unsupported identifier type " + identifierType.getName());
        
        //Cria o objeto type que será retornado no método sqlTypes()
        sqlTypes = new int [] {type.sqlType()};

        String valueOfMethodName = 
            _parameters.getProperty(valueMethodParameter, defaultValueParameter);
        
        try {
            valueOfMethod = enumClass.getMethod(
                    valueOfMethodName, new Class[] { identifierType });
        } 
        catch(Exception exception) {
            throw new HibernateException("Failed to optain valueOf method", exception);
        }
    }
    
    /**
     *
     * @return
     */
    public Class returnedClass() {
        return enumClass;
    }
    
    /**
     *
     * @param _rs
     * @param _names
     * @param owner
     * @return
     * @throws HibernateException
     * @throws SQLException
     */
    public Object nullSafeGet(ResultSet _rs, String[] _names, Object owner)
                        throws HibernateException, SQLException {
        Object identifier = type.nullSafeGet(_rs, _names[0]);
        Object result = null;
        try {
        	if (identifier != null) {
        		result = valueOfMethod.invoke(enumClass, new Object [] {identifier});
        	}
        } catch(Exception exception) {
            throw new HibernateException(
                    "Exception while invoking valueOfMethod of enumeration class: ", exception);
        } 
        return result;
    }

    /**
     *
     * @param st
     * @param value
     * @param index
     * @throws HibernateException
     * @throws SQLException
     */
    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {
        try {
			//Object identifier = value != null ? identifierMethod.invoke(value, new Object[0]) : null;
        	//Object identifier = value != null ? identifierMethod.invoke(value, null) : null;
        	//st.setObject(index, identifier);
        	if(identifierMethod.getReturnType().getName().equals("int")) {
        		if(value instanceof Integer) {
        			Hibernate.INTEGER.nullSafeSet(st, value, index);
        		} else {
        			Object identifier = value != null ? identifierMethod.invoke(value) : null;
        			Hibernate.INTEGER.nullSafeSet(st, identifier, index);
        		}
        	} else if(identifierMethod.getReturnType().getName().equals("char")) {
        		if(value instanceof Character) {
        			Hibernate.CHARACTER.nullSafeSet(st, value, index);
        		} else {        		
        			Object identifier = value != null ? identifierMethod.invoke(value) : null;
        			Hibernate.CHARACTER.nullSafeSet(st, identifier, index);
        		}
            } else if(identifierMethod.getReturnType().getName().equals("long")) {
                if(value instanceof Long) {
                    Hibernate.LONG.nullSafeSet(st, value, index);
                } else {                
                    Object identifier = value != null ? identifierMethod.invoke(value) : null;
                    Hibernate.LONG.nullSafeSet(st, identifier, index);
                }                
            } else if(identifierMethod.getReturnType().getName().equals("java.lang.String")) {
                if(value instanceof String) {
                    Hibernate.STRING.nullSafeSet(st, value, index);
                } else {                
                    Object identifier = value != null ? identifierMethod.invoke(value) : null;
                    Hibernate.STRING.nullSafeSet(st, identifier, index);
                }                

            } else {
				Object identifier = value != null ? identifierMethod.invoke(value) : null;
				st.setObject(index, identifier);
        	}
/*
        	String c = identifierMethod.getReturnType().getName(); //value.getClass().getName() 
        	System.out.println("=================================== Tipo de dado da classe: " + c);
            
            if( c.indexOf("char") == 0 ) {
                st.setObject(index, new Character('E'));
            }
            else {
				Object identifier = value != null ? identifierMethod.invoke(value, new Object[0]) : null;
				st.setObject(index, identifier);
            }
*/
        }
        catch(Exception exception) {
            throw new HibernateException(
                    "Exception while invoking identifierMethod of enumeration class: ", exception);

        } 
    }
    /**
     *
     * @return
     */
    public int[] sqlTypes() {
        //return sqlTypes;
        return new int [] {Types.VARCHAR};
    }
 
    /**
     *
     * @param cached
     * @param owner
     * @return
     * @throws HibernateException
     */
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    /**
     *
     * @param value
     * @return
     * @throws HibernateException
     */
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /**
     *
     * @param value
     * @return
     * @throws HibernateException
     */
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     * @throws HibernateException
     */
    public boolean equals(Object x, Object y) throws HibernateException {
        return x==y;
    }

    /**
     *
     * @param x
     * @return
     * @throws HibernateException
     */
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    /**
     *
     * @return
     */
    public boolean isMutable() {
        return false;
    }

    /**
     *
     * @param original
     * @param target
     * @param owner
     * @return
     * @throws HibernateException
     */
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    /**
     *
     * @param _value
     * @return
     */
    public String objectToSQLString(Object _value) {
		
		return '\'' + ( (Enum) _value ).name() + '\'';
	}

        /**
         *
         * @param _value
         * @return
         */
        public String toXMLString(Object _value) {
		return ( (Enum) _value ).name();
	}

        /**
         *
         * @param _xmlValue
         * @return
         */
        public Object fromXMLString(String _xmlValue) {
		return Enum.valueOf(enumClass, _xmlValue);
	}
}
