package repository;

import model.Client;
import model.Clients;
import util.Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Paul on 10/8/2016.
 */
public class ClientRepository extends BaseIORepository<Client> {

    @Override
    protected void save() {
        try {
            Clients clients = new Clients(mItems);
            JAXBContext jaxbContext = JAXBContext.newInstance(Clients.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(clients, new File(getPath() + ".xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void load() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Clients.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Clients emps = (Clients) jaxbUnmarshaller.unmarshal(new File(getPath() + ".xml"));
            mItems = emps.clients;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getPath() {
        return getClass().getName();
    }
}
