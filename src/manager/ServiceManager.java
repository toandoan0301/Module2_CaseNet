package manager;

import model.Service;
import util.ReadAndWrite;

import java.io.Serializable;
import java.util.List;

public class ServiceManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Service> list;

    public ServiceManager() {
        this.list = ReadAndWrite.readFileService();
    }

    public void add(Service service) {
        list.add(service);
        ReadAndWrite.writeFileService(list);
    }
    public void edit(Service service) {
        list.set(findIndexById(service.getId()),service);
        ReadAndWrite.writeFileService(list);
    }
    public void delete(String id) {
        list.remove(findIndexById(id));
        ReadAndWrite.writeFileService(list);
    }
    public Service findById(String id) {
        for (Service service : list) {
            if(service.getId().equals(id))
                return service;
        }
        return null;
    }
    public int findIndexById(String id){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }
    public void changeQuantity(int index, int quantity){
        list.get(index).updateQuantity(quantity);
        ReadAndWrite.writeFileService(list);
    }
    public List<Service> findAll(){
        return list;
    }
}
