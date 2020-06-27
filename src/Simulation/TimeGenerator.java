// [No amendment needed. Yet]

package Simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class TimeGenerator {
    
    static LocalDate dt;
    static boolean setBefore = false;
    static final Random r = new Random();
    static int h = 0, m = 0, s = 0;
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public void setH(int h){
        this.h = h;
    }
    
    public int getH(){
        return h;
    }
    
    public String getTime(){
        if(!setBefore) setDate();
        s += r.nextInt(2);
        
        while(s >= 60){
            s-= 60;
            m++;
            while(m >= 60) {
                m -= 60;
                h++;
                while(h >= 24) {
                    h -= 24;
                    nextDay(); 
                }
            }
        }
        return String.format("%02d:%02d:%02d", h, m, s);        
    }
    
    // For program only
    public String getDate(){
        if(!setBefore) setDate();
        return dt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    
    // MAIN, for tracer
    public Date getDateInFormat() throws ParseException{
        return sdf.parse(getDate());
    }   
    
    public String getStandardTime(){
        return getDate() + " " + getTime();
    }
    
    private void setDate(){
        dt = LocalDate.now();
        setBefore = true;
    }
    
    public void nextDay(){
        dt = dt.plusDays(1);
    }
    
    public int getDay(){
        if(!setBefore) setDate();
        return dt.getDayOfWeek().getValue();
    }
    
    public void plusHour(){
        h++;
    }
}