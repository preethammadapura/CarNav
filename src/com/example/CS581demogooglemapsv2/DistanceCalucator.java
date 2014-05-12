package com.example.CS581demogooglemapsv2;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;


public class DistanceCalucator {
	
	 public final static String MODE_DRIVING = "driving";
	 public final static String MODE_WALKING = "walking";

	    public DistanceCalucator() { }
	    public Document getDocument(LatLng start, LatLng end){
	    	return getDocument(start,end,"walking");
	    }
	    public Document getDocument(LatLng start, LatLng end, String mode) {
	        String url = "http://maps.googleapis.com/maps/api/directions/xml?" 
	                + "origin=" + start.latitude + "," + start.longitude  
	                + "&destination=" + end.latitude + "," + end.longitude 
	                + "&sensor=false&units=metric&mode="+mode;

	        try {
	            HttpClient httpClient = new DefaultHttpClient();
	            HttpContext localContext = new BasicHttpContext();
	            HttpPost httpPost = new HttpPost(url);
	            HttpResponse response = httpClient.execute(httpPost, localContext);
	            InputStream in = response.getEntity().getContent();
	            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	            Document doc = builder.parse(in);
	            return doc;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    public String getDistanceText (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("distance");
	       // NodeList test = doc.getElementsByTagName("steps");
	        Node node1 = nl1.item(nl1.getLength() - 1);
	        NodeList nl2 = node1.getChildNodes();
	        Node node2 = nl2.item(getNodeIndex(nl2, "text"));
	        Log.i("DistanceText", node2.getTextContent());
	        
	        return node2.getTextContent();
	    }
	    public String getDirectionsText (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("html_instructions");
	       // NodeList test = doc.getElementsByTagName("steps");
	        Node node1 = nl1.item(nl1.getLength() - 2);
	        String returnval = node1.getTextContent(); 
	        return returnval;
	       // NodeList nl2 = node1.getChildNodes();
	        //Node node2 = nl2.item(getNodeIndex(nl2, "text"));
	        //Log.i("DistanceText", node2.getTextContent());
	        
	      //  return node2.getTextContent();
	    }
	    private int getNodeIndex(NodeList nl, String nodename) {
	        for(int i = 0 ; i < nl.getLength() ; i++) {
	            if(nl.item(i).getNodeName().equals(nodename))
	                return i;
	        }
	        return -1;
	    }
	    public int getDurationValue (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("duration");
	        Node node1 = nl1.item(0);
	        NodeList nl2 = node1.getChildNodes();
	        Node node2 = nl2.item(getNodeIndex(nl2, "value"));
	        Log.i("DurationValue", node2.getTextContent());
	        return Integer.parseInt(node2.getTextContent());
	    }
	   

	    
}
