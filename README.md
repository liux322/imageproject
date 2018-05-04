Commands
========

test project
```bash
gradle clean test jacocoTestReport
```

run project
```bash
gradle bootRun
```

Fast Application Restarts
-------------------------

See https://docs.spring.io/spring-boot/docs/current/reference/html/howto-hotswapping.html for reference

in one terminal type 
```bash
gradle bootRun
```

in another
```bash
gradle -t compileJava
```


On every code change the project is going to be recompiled and server restarted automatically.


Request Example
===============

Request:



response:

```
{
  [
    {
      "id": 11,
      "url":"https://unsplash.it/500?image=11"
    },
    {
      "id": 12,
      "url":"https://unsplash.it/500?image=11"
    },
    {
      "id": 13,
      "url":"https://unsplash.it/500?image=11"
    },
    {
      "id": 14,
      "url":"https://unsplash.it/500?image=11"
    },
    {
      "id": 15,
      "url":"https://unsplash.it/500?image=11"
    }
  ]
}

```

Use case
========




