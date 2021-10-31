# ReadingIsGood BookStore Project 

This project simple online book store implementation. 
Library Stack : 
    - Spring Web 
    - Spring Security 
    - Spring Data 
    - Mapstruct
    - MongoDb
    - Docker 

[Overview]
All app divided into 4 main endpoint;
 
  
## Customer Controller ##
*  persist new customers
*  query all orders of the customer ( Paging sounds really nice )
## Book Controller ##
*  persist new book
*  update book’s stock
## Order Controller ##
*  persist new order (statuses may used)
*  update stock records.
*  query order by Id
*  List orders by date interval ( startDate - endDate )

## Statistics Controller ##
* serve customer’s monthly order statistics. Fetches following stats by month 
     Total Order count
     Total amount of all purchased orders
     Total count of purchased books

# How to run application. 
you can execute shell script file on main path after you granted for execution. 
```
    chmod +x start.sh
    ./start.sh  
```
This sh file will be taking following actions.
- It will compile and create jar 
- Then Docker will be initiated 
- As soon as docker initiated, jar file will be copied into docker path 
- Mongo image will be downloaded and mongo container will run
- Finally application will be started 

# How to test application
You can use following link to test the application.  
**Postman Link** [Link](https://www.getpostman.com/collections/1bf5796287b471a2a30b)
