public interface PQ {

    public void add(Comparable comp);

    public Comparable pop();
    
    public Comparable pull();
    
    public boolean isEmpty();
    
    public void clear();
    
    public int size();
    
    public boolean contains(Comparable comp);
}