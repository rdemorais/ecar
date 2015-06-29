/**
 * 
 */
package comum.database;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

/**
 * @author gekson.silva
 *
 */
public class CacheManagerImpl {

	private static final String DEFAULT_CACHE = "cache";
	private static final Logger LOGGER = Logger.getLogger(CacheManagerImpl.class);
	
	private CacheManagerImpl(){
	}
	
	private static CacheManager getInstance() {
		if (CacheManager.getInstance().getCache(DEFAULT_CACHE) == null ) {
			CacheManager.getInstance().addCache(DEFAULT_CACHE);
			LOGGER.info("ADICIONANDO CACHE");
		}
		return CacheManager.getInstance();
	}
	
	public static void remove(String key) {
		Cache cache = CacheManagerImpl.getInstance().getCache(DEFAULT_CACHE);
		boolean remove = cache.remove(key);
		if ( remove ) {
			LOGGER.info("ELEMENTO " + key + " REMOVIDO");
		}
	}

	public static void removeAll() {
		Cache cache = CacheManagerImpl.getInstance().getCache(DEFAULT_CACHE);
		cache.removeAll();
		LOGGER.info("REMOVENDO TODOS OS ELEMENTOS");
	}

	public static Object get(String key) {
		Cache cache = CacheManagerImpl.getInstance().getCache(DEFAULT_CACHE);
		Element element = cache.get(key);
		if ( element != null ) {
			LOGGER.info("RETORNANDO ELEMENTO: " + key);
		}
		return element == null ? element : element.getValue();
	}

	public static void add(String key, Object value) {
		Cache cache = CacheManagerImpl.getInstance().getCache(DEFAULT_CACHE);
		Element element = new Element(key, value);
		cache.put(element);
		LOGGER.info("ADICIONANDO ELEMENTO: " +  key);
	}
}
