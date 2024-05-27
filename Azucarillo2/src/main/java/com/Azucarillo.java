import com.google.gson.Gson;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

class Jsonazucarillo{
    private String cita;
    private String autor;

    public Jsonazucarillo(String cita, String autor) {
        this.cita = cita;
        this.autor = autor;
    }

    public String getCita() {
        return cita;
    }

    public String getAutor() {
        return autor;
    }
}

public class Azucarillo {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://192.168.4.199:8090/azucarillo.php");
            BufferedReader fr = new BufferedReader(new InputStreamReader(url.openStream()));
            String linea = fr.readLine();
            String linea1 = "";
            while (linea != null ){
                linea1 = linea;
                linea = fr.readLine();
            }


            Jsonazucarillo azucarillo = new Gson().fromJson(linea1,Jsonazucarillo.class);
            System.out.printf("'%s' - %s", azucarillo.getAutor(),azucarillo.getCita());


        }catch (MalformedURLException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
