package DAO;

import Pojo.Phone;

import java.util.List;

public interface PhoneDAOInterface {
    public boolean add(Phone newPhone);
    public Phone get(int id);
    public List<Phone> getAll();
    public boolean remove(int id);
    public boolean remove(Phone p);
    public boolean update(Phone p);



}
