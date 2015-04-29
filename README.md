# showoffclojure

This project was intended to show off clojure's parallelism on a simple yet
almost real world sort of use case. It queries a subreddit url in JSON, parses
the JSON and attempts to fetch the titles of the article and also counts the
number of links and counts the most popular domains for a subreddit.

These are the results on my 8 core Macbook Pro 15"

## When run in sequence

```
$ lein run -m showoffclojure.core false

|                  :domain | :count |
|--------------------------+--------|
|         blog.eikeland.se |      1 |
|          doc.ccw-ide.org |      1 |
|              youtube.com |      5 |
|              blog.ndk.io |      1 |
|           alexeberts.com |      1 |
|             pragprog.com |      1 |
|     fn-code.blogspot.com |      1 |
|                  niwi.be |      1 |
|               amazon.com |      2 |
|             self.Clojure |     29 |
|           lite.dunaj.org |      1 |
|            oobaloo.co.uk |      1 |
|      inside.unbounce.com |      1 |
|   endlessparentheses.com |      1 |
|           nvbn.github.io |      1 |
|         rundis.github.io |      3 |
|               kartar.net |      1 |
|          core-async.info |      1 |
|            numergent.com |      3 |
|            blog.juxt.pro |      1 |
|            howistart.org |      1 |
|  fdatamining.blogspot.hk |      1 |
|  rafalcieslak.svbtle.com |      1 |
| timgilbert.wordpress.com |      1 |
|             fgiasson.com |      1 |
|        groups.google.com |      4 |
|         braveclojure.com |      1 |
|          jasonstrutz.com |      1 |
| dataissexy.wordpress.com |      1 |
|        insideclojure.org |      1 |
|             hackaday.com |      1 |
|          speakerdeck.com |      2 |
|               arrdem.com |      1 |
|            tomassetti.me |      1 |
|           mikeivanov.com |      1 |
|            yellerapp.com |      2 |
|          semaphoreci.com |      1 |
|           blog.wagjo.com |      1 |
|               github.com |     18 |
|         stuartsierra.com |      1 |
|    clojurist.blogspot.in |      1 |
Fetched  100  articles
"Elapsed time: 35269.828 msecs"
```

## When run in parallel

```
lein run -m showoffclojure.core true

|                  :domain | :count |
|--------------------------+--------|
|         blog.eikeland.se |      1 |
|          doc.ccw-ide.org |      1 |
|              youtube.com |      5 |
|              blog.ndk.io |      1 |
|           alexeberts.com |      1 |
|             pragprog.com |      1 |
|     fn-code.blogspot.com |      1 |
|                  niwi.be |      1 |
|               amazon.com |      2 |
|             self.Clojure |     28 |
|           lite.dunaj.org |      1 |
|            oobaloo.co.uk |      1 |
|      inside.unbounce.com |      1 |
|   endlessparentheses.com |      1 |
|           nvbn.github.io |      1 |
|         rundis.github.io |      3 |
|               kartar.net |      1 |
|          core-async.info |      1 |
|            numergent.com |      2 |
|            blog.juxt.pro |      1 |
|            howistart.org |      1 |
|  fdatamining.blogspot.hk |      1 |
|  rafalcieslak.svbtle.com |      1 |
| timgilbert.wordpress.com |      1 |
|             fgiasson.com |      1 |
|        groups.google.com |      4 |
|         braveclojure.com |      1 |
|          jasonstrutz.com |      1 |
| dataissexy.wordpress.com |      1 |
|        insideclojure.org |      1 |
|             hackaday.com |      1 |
|          speakerdeck.com |      2 |
|               arrdem.com |      1 |
|            tomassetti.me |      1 |
|           mikeivanov.com |      1 |
|            yellerapp.com |      2 |
|          semaphoreci.com |      1 |
|           blog.wagjo.com |      1 |
|               github.com |     17 |
|         stuartsierra.com |      1 |
|    clojurist.blogspot.in |      1 |
Fetched  100  articles
"Elapsed time: 5369.028 msecs"
```

Now that is almost a 7x performance improvement permitting the vagaries of
network and the behaviour of web servers
