package com.e3mall.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.e3mall.search.common.pojo.SearchItem;
import com.e3mall.search.common.pojo.SearchResult;
import com.e3mall.search.common.utils.E3Result;
import com.e3mall.search.repository.ItemReposiroty;


@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ItemReposiroty itemReposiroty;
	@Autowired
	private SolrClient solrClient;
	@Override
	public E3Result findItemList() {
		E3Result result = new E3Result();
		try {
			 List<SearchItem> findItemList = itemReposiroty.findItemList();
			 for (SearchItem searchItem : findItemList) {
				 SolrInputDocument solrInputFields=new SolrInputDocument();
				 solrInputFields.addField("id", searchItem.getId());
				 solrInputFields.addField("item_title", searchItem.getTitle());
				 solrInputFields.addField("item_sell_point", searchItem.getSell_point());
				 solrInputFields.addField("item_price", searchItem.getPrice());
				 solrInputFields.addField("item_image", searchItem.getImage());
				 solrInputFields.addField("item_category_name", searchItem.getCategory_name());
				solrClient.add(solrInputFields);
			}
			 solrClient.commit();
			 return result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return result.build(500, "数据导入失败");
		} 
	}
	
	public SearchResult searchBySolrQuery(SolrQuery query) throws SolrServerException, IOException{
		SearchResult searchResult = new SearchResult();
		QueryResponse queryResponse = solrClient.query(query);
		SolrDocumentList results = queryResponse.getResults();
		long numFound = results.getNumFound();
		searchResult.setRecordCount(numFound);
		List<SearchItem> itemList = new ArrayList<>();
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : results) {
			SearchItem item = new SearchItem();
			item.setId(Long.parseLong((String) solrDocument.get("id")));
			item.setCategory_name((String) solrDocument.get("category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("sell_point"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list != null && list.size() > 0){
				title = list.get(0);
			}else{
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			itemList.add(item);
		}
		searchResult.setItemList(itemList);
		return searchResult;
	}
	
	@Override
	public SearchResult search(String keyword, int page, int rows) {
		
		try {
			SolrQuery query = new SolrQuery();
			query.setQuery(keyword);
			if(page <= 0) page = 1;
			query.setStart((page - 1) * rows);
			query.setRows(rows);
			query.set("df", "item_title");
			query.setHighlight(true);
			query.addHighlightField("item_title");
			query.setHighlightSimplePre("<font color='red'>");//标记，高亮关键字前缀  
		    query.setHighlightSimplePost("</font>");//后缀  
			SearchResult searchResult = searchBySolrQuery(query);
			long recordCount = searchResult.getRecordCount();
			int totalPages = (int) Math.ceil(1.0 * recordCount / rows);
			searchResult.setTotalPages(totalPages);
			return searchResult;
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		return null;
	}
	

}
