package com.pixelignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.servlet.http.HttpSession;

@SpringBootApplication
@EnableCaching
public class ApacheIgniteWebsessionsApplication {

	@Autowired
	private WebController webController;
	public static void main(String[] args) {

		SpringApplication.run(ApacheIgniteWebsessionsApplication.class, args);

		try (Ignite ignite = Ignition.start("F:\\apache-ignite\\apache-ignite-2.7.0-bin\\examples\\config\\example-ignite.xml")) {
			CacheConfiguration<Integer, String> cacheCfg = new CacheConfiguration<>();
			cacheCfg.setName("demoCache");



			// Enabling on-heap caching for this distributed cache.
			cacheCfg.setOnheapCacheEnabled(true);

           // Set the maximum cache size to 1 million (default is 100,000).
			cacheCfg.setEvictionPolicy(new LruEvictionPolicy(1000000));

			//cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
			// Put values in cache.
			IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cacheCfg);
			for (int i = 0; i < 10; i++) {
				cache.put(i + 1, i + "Vv");

			}
			System.out.println(cache);
            /*cache.put(1, "Hello");
            cache.put(2, "World!");*/
			// Get values from cache
			// Broadcast 'Hello World' on all the nodes in the cluster.
			ignite.compute().broadcast(() -> System.out.println(cache.get(1) + " " + cache.get(1)));
			ignite.compute().broadcast(() -> System.out.println(cache.get(2) + " " + cache.get(2)));
			ignite.compute().broadcast(() -> System.out.println(cache.get(3) + " " + cache.get(3)));
			ignite.compute().broadcast(() -> System.out.println(cache.get(4) + " " + cache.get(4)));
			ignite.compute().broadcast(() -> System.out.println(cache.get(5) + " " + cache.get(5)));
			ignite.compute().broadcast(() -> System.out.println(cache.get(6) + " " + cache.get(6)));
			ignite.compute().broadcast(() -> System.out.println(cache.get(7) + " " + cache.get(7)));
			ignite.compute().broadcast(() -> System.out.println(cache.get(8) + " " + cache.get(8)));
		}
	}

}
