package com.e3mall.item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.search.domain.TbItem;
import com.e3mall.search.repository.ItemReposiroty;

@RunWith(SpringRunner.class)
@SpringBootTest


public class E3mallSearchServiceApplicationTests {

	@Autowired
	private SolrClient client;
	
	@Test
	public void query() throws SolrServerException, IOException{
		SolrQuery query = new SolrQuery();
		query.set("q", "*:*");
		QueryResponse queryResponse = client.query(query);
		SolrDocumentList results = queryResponse.getResults();
		long numFound = results.getNumFound();
		System.out.println(numFound);
		for (SolrDocument solrDocument : results) {
			long object = (long) solrDocument.get("id");
			System.out.println(object);
		}
	}

}
