There are quite a lot of command line (cli) parsing libraries. While they do their job well, I found that none of them is easy and straight forward to use. While working on my projects I dreamed of a library with which I could do the following:

```java
public class Invitor {

    @Parameter
    public void setName(String name){

    }

    @Parameter
    public void setMoney(int amount){

    }

    @Action
    public void invite(){

    }
}
```

And then I simply call from command line

`java Invitor --name "John Smith" --money 5000 invite`

and then magic happens.
