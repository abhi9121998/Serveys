package survey;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class CreatedSurvey extends HttpServlet {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/survey/";
    
    static final String USER = "root";
    static final String PASS = "";
    
    Connection conn = null;
    Statement stmt = null;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        
        ///String orgName=request.getParameter("orgName");
        //String email=request.getParameter("email");
        String formName=request.getParameter("formName");
        String formType=request.getParameter("formType");
        String formDesc=request.getParameter("formDesc");
        String duration=request.getParameter("duration");
        int d=Integer.parseInt(duration);
        String[] question=request.getParameterValues("ques");
        int no_ques=question.length;
        String[] answer_type=request.getParameterValues("element");
        String answer=request.getParameter("ans");
        
        out.println(formName);
        out.println(formType);
        out.println(formDesc);
        out.println(Arrays.toString(question));
        out.println(Arrays.toString(answer_type));
        out.println(answer);
        
        
        HttpSession h=request.getSession();
        String username=(String)h.getAttribute("currentSessionUser");
        String sql="CREATE TABLE IF NOT EXISTS '"+username+"'"+
                   "(id int NOT NULL AUTO_INCREMENT, "+
                   "surveyConducted VARCHAR(50), "+
                   "link VARCHAR(50), "+
                   "type VARCHAR(20), "+
                   "desc VARCHAR(100), "+
                   "startDateTime DATETIME, "+
                   "endDateTime DATETIME, "+
                   "responses int)";
       out.println(sql);
        //when click on create to make a survey
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        //String[] dateTime=timeStamp.split("_");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String startDate = sdf.format(c.getTime());
        c.add(Calendar.DATE, d);
        String endDate = sdf.format(c.getTime());
        //out.println(startDate+", "+endDate);
        
        String link="";
        int resp;
        sql="INSERT INTO '"+username+"'"+
            "(surveyConducted, link, type, desc, startDateTime, endDateTime)"+
            "VALUES ( '"+formName+"', '"+link+"', '"+formType+"', '"+formDesc+"', '"+startDate+"', '"+endDate+"' )";
        
        out.println(sql);
        
        sql="CREATE TABLE IF NOT EXISTS '"+username+"_"+formName+"_"+startDate+"'"+
            "(id int NOT NULL AUTO_INCREMENT, "+
            "question VARCHAR(200), "+
            "required VARCHAR(1) DEFAULT 'N', "+
            "type VARCHAR(10), "+
            "options VARCHAR(50) DEFAULT 'NA')";
        
        out.println(sql);
        
        sql="CREATE TABLE IF NOT EXISTS '"+username+"_"+formName+"_"+startDate+"_responses'"+
            "(email VARCHAR(50), "+
            "dateTime DATETIME, ";
        for(int i=1; i<=no_ques; i++)
        {
            if(i==no_ques)
            {
                sql+="q"+i+" VARCHAR(200))";
            }
            else
                sql+="q"+i+" VARCHAR(200), ";
        }
        out.println(sql);
    }
    
}
