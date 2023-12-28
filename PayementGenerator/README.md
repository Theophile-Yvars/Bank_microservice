# How To Use

Arguments :

**country** = A valid country where the transactions will be made

**number** = The number of transaction you want to create. **Must be > 0**

``` 
python3 src/main.py -country $(country) -number $(number)
``` 


## Example

Create 10 transaction from France

``` 
python3 src/main.py -country France -number 10
``` 