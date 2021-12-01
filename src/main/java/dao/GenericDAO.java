package dao;

public interface GenericDAO<T> {
	public int update(T t);
	public int delete(T t);
	public int insert(T t);
}
