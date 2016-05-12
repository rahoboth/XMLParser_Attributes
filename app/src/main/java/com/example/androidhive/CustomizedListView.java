package com.example.androidhive;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class CustomizedListView extends Activity {
	// All static variables
	static final String URL = "https://d2405b0jymm2dk.cloudfront.net/program/prod/d90b16c5be35e48dc25d1a49344d40bf/home.xml?id=ad21dbf98a1b7fe8203c9a506d5e1dd7";
	// XML node keys
	static final String KEY_SONG = "container"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "language";
	static final String KEY_DURATION = "start_date";
	static final String KEY_THUMB_URL = "urlpath";
	
	ListView list;
    LazyAdapter adapter;
	SitesList sitesList = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		try {

			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Send URL to parse XML Tags */
			java.net.URL sourceUrl = new URL(
					"https://d2405b0jymm2dk.cloudfront.net/program/prod/d90b16c5be35e48dc25d1a49344d40bf/home.xml?id=ad21dbf98a1b7fe8203c9a506d5e1dd7");


			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			MyXMLHandler myXMLHandler = new MyXMLHandler();
			xr.setContentHandler(myXMLHandler);
			xr.parse(new InputSource(sourceUrl.openStream()));

		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}

		/** Get result from MyXMLHandler SitlesList Object */
		sitesList = MyXMLHandler.sitesList;
		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML from URL
		Document doc = parser.getDomElement(xml); // getting DOM element

		sitesList = MyXMLHandler.sitesList;
		// looping through all song nodes <song>
		for (int i = 0; i < sitesList.getCategory().size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();

			// adding each child node to HashMap key => value
			map.put(KEY_ID, sitesList.getCategory().get(i));
			map.put(KEY_TITLE, sitesList.getCategory().get(i));
			map.put(KEY_ARTIST, sitesList.getCategory().get(i));
			map.put(KEY_DURATION, sitesList.getCategory().get(i));
			map.put(KEY_THUMB_URL, sitesList.getCategory().get(i));

			// adding HashList to ArrayList
			songsList.add(map);
		}
		

		list=(ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);        
        list.setAdapter(adapter);
        

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
							

			}
		});



		/** Get result from MyXMLHandler SitlesList Object */




	}	
}