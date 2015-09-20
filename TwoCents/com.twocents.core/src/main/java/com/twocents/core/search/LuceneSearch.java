package com.twocents.core.search;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.twocents.bovespa.stock.ListStockInBovespa;
import com.twocents.bovespa.stock.StockSuggestion;

/**
 * http://snippets.dzone.com/posts/show/8965
 * @author Adriano Gonella
 */

public class LuceneSearch {

	private static Logger logger = Logger.getLogger(LuceneSearch.class);

	private static LuceneSearch luceneSearch;  

	private Document doc;
	private QueryParser parser;
	private IndexSearcher isearcher;
	private IndexWriter iwriter;
	private Directory directory;

	public static void main(String args[]){
	
		ListStockInBovespa listStockInBovespa = new ListStockInBovespa();
		Map<String, StockSuggestion> map = listStockInBovespa.getList();
		
		Set<String> keySet = map.keySet();
		for (String string : keySet) {
			StockSuggestion stockSuggestion = map.get(string);
			logger.info(stockSuggestion);
		}
		
		String searchString = "P*";

		String[] search = LuceneSearch.getInstance(map).searchAllOccurrences(searchString);
		logger.info(search.length);		
		for (String code : search) {					
			StockSuggestion stockSuggestion = map.get(code);
			logger.info(stockSuggestion);					
		}
		
			
	}
	
	public static LuceneSearch getInstance(Map<String,StockSuggestion> map){
		
		if(luceneSearch==null){
			luceneSearch = new LuceneSearch(map);
		}
		return luceneSearch;
	}
	
	public LuceneSearch(Map<String,StockSuggestion> map){
		
		try{
			setDirectory(new RAMDirectory());
	
			// To store an index on disk, use this instead (note that the
			// parameter true will overwrite the index in that directory
			// if one exists):
			// Directory directory = FSDirectory.open(new File("/tmp/testindex"));
			
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			setIwriter(new IndexWriter(getDirectory(), analyzer, true,new IndexWriter.MaxFieldLength(25000)));
			
			setDoc(new Document());
			
			Set<String> keyValues = map.keySet();
			for (Iterator iterator = keyValues.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				addDict(key);
			}
			//addDict(stockSuggestion.getCode());			
							
			getIwriter().close();
	
			setIsearcher(new IndexSearcher(getDirectory()));
	
			setParser(new QueryParser(Version.LUCENE_30,"fieldname", analyzer));
		
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (LockObtainFailedException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	// function
	public String[] searchAllOccurrences(String searchString){
		String[] hitsFound=null;
		
		searchString=searchString+"*";
		
		try{
			Query query = getParser().parse(searchString);
	
			TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
			getIsearcher().search(query, collector);
			
			int totalHits = collector.getTotalHits();
			logger.debug("collector.getTotalHits()="+ totalHits);
			// assertEquals(2, collector.getTotalHits());
	
			// Iterate through the results:
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			logger.debug("Real hits="+ hits.length);

			hitsFound=new String[hits.length];
					
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = getIsearcher().doc(hits[i].doc);
				String hitFound = hitDoc.get("fieldname");
				logger.debug("Termo encontrado - hitDoc.get(\"fieldname\")="+ hitFound);
				hitsFound[i] = hitFound;
			}
	
			//getIsearcher().close();
			//getDirectory().close();
		
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return hitsFound;
	}
	
	public void addDict(String str) throws CorruptIndexException, IOException{
		setDoc(new Document());
		getDoc().add(new Field("fieldname", str, Field.Store.YES,Field.Index.ANALYZED));
		getIwriter().addDocument(getDoc());
	}

	public void setIwriter(IndexWriter iwriter) {
		this.iwriter = iwriter;
	}

	public IndexWriter getIwriter() {
		return iwriter;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setIsearcher(IndexSearcher isearcher) {
		this.isearcher = isearcher;
	}

	public IndexSearcher getIsearcher() {
		return isearcher;
	}

	public void setParser(QueryParser parser) {
		this.parser = parser;
	}

	public QueryParser getParser() {
		return parser;
	}
	
}
