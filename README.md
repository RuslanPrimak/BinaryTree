# BinaryTree
Implementation algorithm for Binary Tree with deleting items and printing Tree

This implemetation includes such operates:
- adding value
- checking value for existence
- deleting value
- printing out tree structure via pseudo graphic

Example output:

Initial tree
              5              
       ┌──────┴───────┐      
       2              7      
  ┌────┴──┐      ┌────┴──┐   
  1       3      6       8   
 ┌┴─┐  ┌──┴┐    ┌┴─┐  ┌──┴┐  
           4              9  
          ┌┴─┐           ┌┴─┐
                             

After deleted item
             5            
       ┌─────┴──────┐     
       2            8     
  ┌────┴──┐      ┌──┴──┐  
  1       3      6     9  
 ┌┴─┐  ┌──┴┐    ┌┴─┐  ┌┴─┐
           4              
          ┌┴─┐            
                          

Random tree
                            27                             
          ┌──────────────────┴──────────┐                  
         22                            36                  
       ┌──┴──────┐    ┌─────────────────┴┐                 
      18        26                      41                 
     ┌─┴─────┐  ┌┴─┐      ┌──────────────┴──┐              
     4                   37                60              
  ┌──┴──┐                ┌┴─┐      ┌────────┴─────┐        
  3    12                         43             82        
 ┌┴─┐  ┌┴─┐                    ┌───┴─┐         ┌──┴─────┐  
                                    52        65       94  
                                   ┌─┴──┐   ┌──┴──┐    ┌┴─┐
                                  48       63    67        
                                  ┌┴─┐     ┌┴─┐  ┌┴─┐      
                                                           


