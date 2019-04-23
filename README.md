# orders
orers for interfell

1.- Data Base
On a Postgress DataBase run the nexts Scripts:

--CREATE DATABASE interfell

--CREATE SCHEMA interfell
--AUTHORIZATION postgres;

2.- The aplication 
2.1.- Please generate the war with maven and publish it on a server.

2.2.- The services:
-- header   key:User            value:{the consumer}
            key:Content-Type    value:application/json
            
//CRUD OF PRODUCTS----------------------------------------------------
// to add a new product POST:
    http://localhost:8080/b-orders/api/product
// the body example:
    { "sku":"123ASD", "name":"POTATOES", "price":40}
    
// to update a product PUT:
    http://localhost:8080/b-orders/api/product
// the body example:
    {"id":1, "sku":"123ASD", "name":"POTATOES", "price":80}
    
// to delete a product DELETE: -- NOTICE THAT JUST CHANGE THE VALUE ENABLED ON THE DATA BASE
    http://localhost:8080/b-orders/api/product
// the body example:
    {"id":1}
    
// to get all products GET: 
    http://localhost:8080/b-orders/api/product?page=0&size=10

---------------------------------------------------------------------
//CREATE AN ORDER----------------------------------------------------
// to add a new order POST:
    http://localhost:8080/b-orders/api/order
// the body example:
    { "code":"1545QW", "totalPrice":0, "quantity":0,"currency":"EUR"}
    
---------------------------------------------------------------------
//ADD A PRODUCT TO AN ORDER----------------------------------------------------
// to add a new order POST:
    http://localhost:8080/b-orders/api/order/product
// the body example:
    { "orderId":1, "productId":2}

---------------------------------------------------------------------
//UPDATE A PRODUCT TO AN ORDER----------------------------------------------------
// to update a new order PUT:
    http://localhost:8080/b-orders/api/order/product
// the body example:
    {"id":1, "orderId":1, "productId":3}
    
---------------------------------------------------------------------
//DELETE A PRODUCT TO AN ORDER----------------------------------------------------
// to delete a new order DELETE: -- NOTICE THAT JUST CHANGE THE VALUE ENABLED ON THE DATA BASE
    http://localhost:8080/b-orders/api/order/product
// the body example:
    {"id":1}
    
---------------------------------------------------------------------
//GET COINS FOR AN ORDER----------------------------------------------------
// to get coins GET: -- IT IS CALLING FIXER API
    http://localhost:8080/b-orders/api/fixer/rates
    
---------------------------------------------------------------------
//EVALUATE PRICE IN OTHER CURRENCY FOR AN ORDER----------------------------------------------------
// to get coins GET: -- IT IS CALLING FIXER API
--id is the id of the order
--to is the currency that we want to change
    http://localhost:8080/b-orders/api/order/priceCalculator?id=1&to=USD
