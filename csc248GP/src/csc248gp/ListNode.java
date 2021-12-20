package csc248gp;

public class ListNode 
{
    Object data;
    ListNode next;
    
    ListNode(Object obj)
    {
        this(obj,null);
    }
    
    ListNode(Object obj, ListNode nextNode)
    {
        data = obj;
        next = nextNode;
    }

    Object getObject(){return data;}
    ListNode getNextt(){return next;}
    
    void setData (Object obj){data = obj;}
    void setNext (ListNode n){next = n;}
            
}
