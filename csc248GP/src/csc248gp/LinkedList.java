package csc248gp;

public class LinkedList 
{
    private ListNode firstNode;
    private ListNode lastNode;
    private ListNode currentNode;
    private ListNode previousNode;
    private ListNode last;
    private String name;
    
    public LinkedList()
    {
        this("list");
    }
    
    public LinkedList(String s)
    {
        name = s;
        firstNode = lastNode = currentNode = null;
    }
    
    public boolean isEmpty()
    {
        return (firstNode == null);
    }
    
    public void insertAtBack (Object insertItem)
    {
        if (isEmpty())
            firstNode = lastNode = new ListNode(insertItem);
        else
            lastNode = lastNode.next = new ListNode(insertItem);
    }
    
    public Object removeFromFront() throws EmptyListException
    {
        Object removeItem = null;
        
        if (isEmpty())
            throw new EmptyListException(name);
        removeItem = firstNode.data;
        
        if (firstNode.equals(lastNode))
            firstNode = lastNode = null;
        else 
            firstNode = firstNode.next;
        
        return removeItem;
    }
    
    public Object getFirst()
    {
        if (isEmpty())
            return null;
        else 
        {
            currentNode = firstNode;
            return currentNode.data;
        }
    }
    
    public Object getLast()
    {
        if (isEmpty())
            return null;
        else
        {
            currentNode = lastNode;
            return currentNode.data;
        }
    }
    
    public Object getNext()
    {
        if (currentNode != lastNode)
        {
            currentNode = currentNode.next;
            return currentNode.data;
        }
        else 
            return null;
    }
}
