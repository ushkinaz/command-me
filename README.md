[![Build Status](https://secure.travis-ci.org/ushkinaz/command-me.png?branch=master)](http://travis-ci.org/ushkinaz/command-me)

There are quite a lot of command line (cli) parsing libraries. While they do their job well, I found that none of them is easy and straightforward to use. While working on my projects I dreamed of a library with which I could do the following:

```java
public class Invitor {

    @Option
    public void setName(String name){

    }

    @Option
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
