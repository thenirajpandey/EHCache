package com.openmatics.omob.clientlogin.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;
import net.sf.ehcache.statistics.StatisticsGateway;

@Component
public class CustomRemovalListener extends CacheEventListenerAdapter {

	
	@Autowired
	CacheManager cacheConfig;

	@Autowired
	CustomRemovalListener(CacheManager cacheManager) {
		this.cacheConfig = cacheManager;
	}

	public Map<String, String> getStats() {
		Map<String, String> eStatsMap = new HashMap<String, String>(10);

		if (cacheConfig != null) {
			System.out.println("inside getstats");
			StatisticsGateway statistic = ((Ehcache) cacheConfig.getCache("userCache")).getStatistics();
			
			eStatsMap.put("Local DIsk Size", String.valueOf(statistic.getLocalDiskSize()));
			eStatsMap.put("Evicted Count", String.valueOf(statistic.cacheEvictedCount()));
			System.out.println("statistic.getLocalDiskSize()" + statistic.getLocalDiskSize());
			System.out.println("statistics.evictionCount()" + statistic.getLocalHeapSize());
			System.out.println("statistics.evictionWeight()" + statistic.getSize());
			System.out.println("statistic.getLocalDiskSize()" + statistic.cacheGetOperation());
			System.out.println("statistics.evictionCount()" + statistic.cacheExpiredCount());
			System.out.println("statistics.evictionWeight()" + statistic.cacheRemoveCount());
			System.out.println("statistic.getLocalDiskSize()" + statistic.getAssociatedCacheName());
			System.out.println("statistics.evictionCount()" + statistic.cacheEvictedCount());
			System.out.println("statistics.evictionWeight()" + statistic.cacheEvictionOperation());
			System.out.println("statistic.getLocalDiskSize()" + statistic.cacheMissCount());
			System.out.println("statistics.evictionCount()" + statistic.cacheExpiredCount());
			System.out.println("statistics.evictionWeight()" + statistic.cachePutCount());
		}
		return eStatsMap;
	}

@Override
public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
	// TODO Auto-generated method stub
	System.out.println("cache element removed---->"+element);
}

@Override
public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
	// TODO Auto-generated method stub
	System.out.println("cache element updated---->"+element);
}

@Override
public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
	// TODO Auto-generated method stub
	System.out.println("cache element updated---->"+element);
}

@Override
public void notifyElementExpired(Ehcache cache, Element element) {
	// TODO Auto-generated method stub
	System.out.println("cache element expired---->"+element);
}

@Override
public void notifyElementEvicted(Ehcache cache, Element element) {
	// TODO Auto-generated method stub
	System.out.println("cache element evicted---->"+element);
}

@Override
public void notifyRemoveAll(Ehcache cache) {
	// TODO Auto-generated method stub
}

@Override
public void dispose() {
	// TODO Auto-generated method stub
}

}
