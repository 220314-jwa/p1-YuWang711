package database;

import java.util.Set;

public interface GenericDAO<Data> {
	//CRUD, write create, read, update, delete
	//functions
	public int create(Data dataToCreate);
	
	public Data getById(int data);
	public Set<Data> getAll();
	public void update(Data dataToUpdate);
	public void delete(Data dataToDelete);

}
