package repository;

import model.Client;
import util.Utils;

/**
 * Created by Paul on 10/8/2016.
 */
public class ClientRepository extends BaseIORepository<Client> {

    @Override
    protected void save() {
        super.save();
    }

    @Override
    String getPath() {
        return getClass().getName();
    }
}
