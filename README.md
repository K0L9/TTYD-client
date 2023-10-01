# Building image

```shell
docker build --tag nocne-ptagi-ttyd:latest .
```


# Running

```shell
docker run -p8080:8080 -v <path-to-db-file-on-host>:/target.db nocne-ptagi-ttyd:latest
```