package de.himbiss.klim.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.jfxmedia.logging.Logger;
import de.himbiss.klim.servlets.vo.Article;
@WebServlet(urlPatterns = { "jsonservlet" })
public class JSONServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// This will store all received articles
	List<Article> articles = new LinkedList<Article>();
	
	/***************************************************
	 * URL: /jsonservlet
	 * doPost(): receives JSON data, parse it, map it and send back as JSON
	 ****************************************************/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException{
	    
		// 1. get received JSON data from request
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if(br != null){
			json = br.readLine();
		}

		String method = request.getHeader("method");
		Logger.logMsg(Logger.WARNING, method);
        System.out.println("ASDF: "+method);
		// 2. initiate jackson mapper
    	ObjectMapper mapper = new ObjectMapper();
    	
    	// 3. Convert received JSON to Article
    	Article article = mapper.readValue(json, Article.class);

		// 4. Set response type to JSON
		response.setContentType("application/json");		    
    	
    	// 5. Add article to List<Article>
		articles.add(article);

		// 6. Send List<Article> as JSON to client
    	mapper.writeValue(response.getOutputStream(), articles);
	}
}
