package visualk.hrz;

/*
 * 
 * generar dinamicament els css a partir de les propietats java
 * fer la finestra de dialeg
 * publica
 * fer Debug xivato 
 */
import java.io.IOException;

import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import visualk.hrz.modules.Artzar;
import visualk.hrz.modules.ListHorizons;
//import visualk.hrz.modules.Wizard;
import visualk.hrz.objects.Horizon;
import visualk.html.UniqueName;

/**
 * Servlet implementation class Hrz
 */
public class Hrz extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String SERVELT_URL="servlet";
	
	private static Artzar artzar; 		// artzar horitzons a l'atzar
	private static ListHorizons listH;  // galeria d'horitzons
	//private static Wizard wizard; 	 	// asistent per la ceracio
	
	
	Horizon hrz;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hrz() {
    	
        super();
        hrz=new Horizon("null");
        hrz.carrega("wellcome");
        
        listH = new ListHorizons("Galeria d'horitzons a l'atzar v0.2");
    	artzar = new Artzar("Generador d'horitzons a l'atzar v0.2");

        // TODO Auto-generated constructor stub
    }
    
    //signature for emails
    public void firma(String name,HttpServletResponse response) throws IOException{    	
    	
    	response.setContentType("image/JPEG");
    	Horizon hrz2 = new Horizon(new UniqueName(8).getName());
    	hrz2.setAuthorHrz(name);
    	hrz2.setHorizontal();
    	hrz2.setAureaProp(false);
    	hrz2.makeRandomCanvas(100,100,50,50);
    	hrz2.makeRandomAlçadaHoritzo();
    	hrz2.makeRandomPal();
    	hrz2.makeRandomHombra();
    	hrz2.makeRandomColors();
    	
    	ImageIO.write(hrz2.getHrzImage(),"gif",response.getOutputStream());
}
 
    
    
    
    
    
    //For the list to load. Small image
    public void peque(String name,HttpServletResponse response) throws IOException{    	
    	response.setContentType("image/JPEG");
		hrz.carrega(name);
		ImageIO.write(hrz.getHrzSmallImage(200,200),"gif",response.getOutputStream());
}
    
    //carrega un dibuix existent
    public void loadAtzar(String name,HttpServletResponse response) throws IOException{    	
    		response.setContentType("image/JPEG");
    		Horizon hrz2 = new Horizon(new UniqueName(8).getName());
    		hrz2.carrega(name);
    		ImageIO.write(hrz2.getHrzImage(),"gif",response.getOutputStream());
    }
    
    //retorna dibuix
    public void getAtzar(HttpServletResponse response) throws IOException{
    	response.setContentType("image/JPEG"); 
        ImageIO.write(hrz.getHrzImage(),"gif",response.getOutputStream()); 
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String option = request.getParameter("option");
		String namehrz = request.getParameter("namehrz");

		if (option == null)	option = "";

	
			if (option.equals("paint")) {// a mida real
				if (namehrz==null) {
					getAtzar(response);
				} else {
					loadAtzar(namehrz, response);
				}
			}
			if (option.equals("peque")) {// mida small
				peque(namehrz, response);
			}
			if (option.equals("firma")) {// firma petita
				firma("hrzmkr.com", response);
			}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String where = request.getParameter("where");
		String what = request.getParameter("what");
		String pino = request.getParameter("pino");
		
		String option = request.getParameter("option");
		String nom = request.getParameter("nom");
		
		if(pino==null)pino="0";		
		
		if (where == null)where="";
		if (what == null)what="";
		
		if (option == null)option="";
		if (nom == null)nom="";
			
		PrintWriter out;
		out = response.getWriter();
		response.setContentType("text/html");
		
		//option start	
		System.out.println("where:"+where);
		System.out.println("what:"+what);		
		System.out.println("opt:"+option);
		System.out.println("nom:"+nom);
		
		
		
		if(!what.equals("marxar")){	

		////////// control atzar
		if(where.equals("artzar")){
			if(what.equals("carrega")){ //entra a artzar
			}else{
				if(what.equals("gen_atzar")){
					hrz.setNameHrz(new UniqueName(8).getName());
					hrz.makeRandom();//random de tot
				}
				else if(what.equals("colorsRnd"))hrz.makeRandomColors(); //random de colors
					else if(what.equals("posicioRnd")) hrz.makeRandomPal(); // random del pal
						else if(what.equals("hombraRnd")) hrz.makeRandomHombra(); // random del pal
							else if(what.equals("superRnd")) hrz.makeRandomSuperNova(); // random del pal
								else if(what.equals("guarda")) hrz.saveToFile(option); // random del pal
			}
			if(pino.equals("0")){
				out.println(artzar.toHtml());
			}else{
				out.println("");
			}
			out.close();
			
		//////// control llista per carregar
		}else if(where.equals("listhorizons")){
			if(what.equals("carrega")){
			    out.println(listH.toHtml());
				out.close();
			}else if(what.equals("selecciona")){
				hrz.carrega(nom);
				out.println(artzar.toHtml());
				out.close();
			}
		
		}
		///////////// si res de res	
		}else{
			response.sendRedirect("/");
		}
		

	}
}
