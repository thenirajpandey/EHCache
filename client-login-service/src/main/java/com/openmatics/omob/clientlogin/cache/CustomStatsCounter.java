package com.openmatics.omob.clientlogin.cache;

import java.util.Map;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import net.sf.ehcache.hibernate.management.api.EhcacheStats;

public class CustomStatsCounter implements EhcacheStats {

	public void removeNotificationListener(NotificationListener arg0, NotificationFilter arg1, Object arg2)
			throws ListenerNotFoundException {
		// TODO Auto-generated method stub
		
	}

	public void addNotificationListener(NotificationListener arg0, NotificationFilter arg1, Object arg2)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	public MBeanNotificationInfo[] getNotificationInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeNotificationListener(NotificationListener arg0) throws ListenerNotFoundException {
		// TODO Auto-generated method stub
		
	}

	public String getOriginalConfigDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOriginalConfigDeclaration(String region) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generateActiveConfigDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}

	public String generateActiveConfigDeclaration(String region) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isTerracottaHibernateCache(String region) {
		// TODO Auto-generated method stub
		return false;
	}

	public String[] getTerracottaHibernateCacheRegionNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> getRegionCacheAttributes(String regionName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Map<String, Object>> getRegionCacheAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRegionCacheEnabled(String region) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRegionCacheEnabled(String region, boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	public boolean isRegionCachesEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRegionCachesEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	public int getRegionCacheMaxTTISeconds(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRegionCacheMaxTTISeconds(String region, int maxTTISeconds) {
		// TODO Auto-generated method stub
		
	}

	public int getRegionCacheMaxTTLSeconds(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRegionCacheMaxTTLSeconds(String region, int maxTTLSeconds) {
		// TODO Auto-generated method stub
		
	}

	public int getRegionCacheTargetMaxInMemoryCount(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRegionCacheTargetMaxInMemoryCount(String region, int targetMaxInMemoryCount) {
		// TODO Auto-generated method stub
		
	}

	public int getRegionCacheTargetMaxTotalCount(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRegionCacheTargetMaxTotalCount(String region, int targetMaxTotalCount) {
		// TODO Auto-generated method stub
		
	}

	public boolean isRegionCacheLoggingEnabled(String region) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRegionCacheLoggingEnabled(String region, boolean loggingEnabled) {
		// TODO Auto-generated method stub
		
	}

	public boolean isRegionCacheOrphanEvictionEnabled(String region) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getRegionCacheOrphanEvictionPeriod(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void flushRegionCache(String region) {
		// TODO Auto-generated method stub
		
	}

	public void flushRegionCaches() {
		// TODO Auto-generated method stub
		
	}

	public long getCacheHitCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getCacheHitSample() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getCacheHitRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getCacheMissCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getCacheMissSample() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getCacheMissRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getCachePutSample() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getCachePutCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getCachePutRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map<String, int[]> getRegionCacheSamples() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumberOfElementsInMemory(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfElementsOffHeap(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfElementsOnDisk(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getMinGetTimeMillis() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getMaxGetTimeMillis() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getAverageGetTimeMillis(String region) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getMinGetTimeMillis(String cacheName) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getMaxGetTimeMillis(String cacheName) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
