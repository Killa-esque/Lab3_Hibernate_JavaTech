package DAO;

import Pojo.Manufacture;

import java.util.List;

public interface ManufactureDAOInterface {
    public boolean add(Manufacture newManu);
    public Manufacture get(int id);
    public List<Manufacture> getAll();
    public boolean remove(int id);
    public boolean remove(Manufacture p);
    public boolean update(Manufacture p);
}
