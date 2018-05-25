package com.e3mall.search.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.search.common.pojo.SearchItem;
import com.e3mall.search.common.pojo.SearchResult;
import com.e3mall.search.common.utils.E3Result;
import com.e3mall.search.domain.TbItem;
import com.e3mall.search.repository.ItemReposiroty;
import com.e3mall.search.service.SearchService;


@RestController
public class SearchController {
	@Autowired
	private SearchService searchService;
	@Autowired
	private SolrClient solrClient;
	@Autowired
	private ItemReposiroty itemReposiroty;
	
	@RequestMapping("/")
	public void query() throws SolrServerException, IOException{
		SolrQuery query = new SolrQuery();
		query.set("q", "手机");
		query.set("df", "item_title");
		query.setStart(0);
		query.setRows(500);
		query.setHighlight(true);
		
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		QueryResponse queryResponse = solrClient.query(query);
		SolrDocumentList results = queryResponse.getResults();
		long numFound = results.getNumFound();
		System.out.println(numFound);
		for (SolrDocument solrDocument : results) {
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list != null && list.size() > 0){
				title = list.get(0);
			}else{
				title= (String) solrDocument.get("item_title");
			}
			System.out.println((String) solrDocument.get("id"));
			System.out.println(title);
		}
	}
	
	@RequestMapping(name = "/admin/rest/index/item/import", method = RequestMethod.POST)
	public E3Result importIndex(){
		E3Result result = searchService.findItemList();
		return result;
	}
	
	@RequestMapping(name = "/front/search", method = RequestMethod.GET)
	public SearchResult search(@RequestParam("keyword") String keyword, @RequestParam("page") int page, @RequestParam("rows") int rows){
		SearchResult result = searchService.search(keyword, page, rows);
		return result;
	}

}
