package dao;

public interface GenericDAO<T> {
	public int update(T t);
	public int delete(T t);
	public void insert(T t);
}
