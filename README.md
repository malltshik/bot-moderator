#### Bot-Moderator
How to run
```bash
git clone https://github.com/malltshik/bot-moderator.git
cd bot-moderator
./mvnw install
java -jar target/bot-moderator-0.0.1-SNAPSHOT.jar
```

Properties
```yml
channel:
  type: telegram #only one option for now
telegram:
  token: secret #required if channel.type telegram
news-api:
  token: secret #required for getting news 
```

_To apply properties you should create `application.yml` 
file at the same directory as `bot-moderator-0.0.1-SNAPSHOT.jar`_