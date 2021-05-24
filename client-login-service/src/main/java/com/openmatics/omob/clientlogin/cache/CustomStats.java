package com.openmatics.omob.clientlogin.cache;

import java.util.function.Supplier;

public class CustomStats implements   Supplier<CustomStatsCounter>  {

	@Override
	public CustomStatsCounter get() {
		
		return new CustomStatsCounter() ;
	}

}
