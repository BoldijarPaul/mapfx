package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Paul on 1/13/2017.
 */
@XmlRootElement(name = "clients")
public class Clients {
    public Clients() {

    }

    public Clients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> clients;
}
