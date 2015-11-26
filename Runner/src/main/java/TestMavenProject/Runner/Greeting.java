package TestMavenProject.Runner;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table; 
 
@Entity 
class Greeting implements Serializable
{
    @Id
    @GeneratedValue 
    private int id;
    @Basic 
    private String message;
    @Basic 
    private String language; 
    public Greeting() {}
    public Greeting(String message, String language) {
        this.message = message;    this.language = language;
        }
    public String toString() {
        return "Greeting id=" + id + ", message=" + message + ", language=" + language;
        }
}