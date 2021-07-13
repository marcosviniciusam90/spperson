const express = require('express');
const app = express();

// diretório dist é onde está o código compilado que será executado
// podemos alterar o diretório que será gerada a build -> angular.json / outputPath
app.use(express.static(__dirname + '/dist/frontend'));

// qualquer rota vai servir o index.html
app.get('/*', function(req, res) {
  res.sendFile(__dirname + '/dist/frontend/index.html');
});

app.listen(4200); // porta

// Para rodar, execute no terminal o comando:
// node server.js